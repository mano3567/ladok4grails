package se.su.it.ladok

import grails.gorm.transactions.NotTransactional
import grails.gorm.transactions.Transactional
import groovy.json.JsonSlurper
import org.apache.http.HttpEntity
import org.apache.http.NameValuePair
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.config.CookieSpecs
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.utils.URIBuilder
import org.apache.http.config.Registry
import org.apache.http.config.RegistryBuilder
import org.apache.http.conn.HttpClientConnectionManager
import org.apache.http.conn.socket.ConnectionSocketFactory
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.apache.http.message.BasicNameValuePair

import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import java.security.KeyStore

@Transactional
class HttpClientService {
    SettingsService settingsService

    Map<Edu, CloseableHttpClient> httpClientsForL3ByEdy = [:]

    @NotTransactional
    CloseableHttpClient initiateLadok3Client(Edu edu) {
        CloseableHttpClient httpClient = null
        if(edu) {
            String clientCertificateFilePath = settingsService.getPathForCertByEdu(edu)
            String pwd = settingsService.getPassWordForCertByEdu(edu)
            if(clientCertificateFilePath && pwd) {
                KeyStore keyStore = KeyStore.getInstance("PKCS12")
                if (new File(clientCertificateFilePath).exists()) {
                    new File(clientCertificateFilePath).withInputStream { InputStream stream ->
                        keyStore.load(stream, pwd.toCharArray())
                    }
                } else {
                    throw new Exception("Client certificate not found in ${clientCertificateFilePath}.")
                }

                SSLContext context = SSLContext.getInstance("TLS")
                KeyManagerFactory e = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
                e.init(keyStore, pwd.toCharArray())
                context.init(e.getKeyManagers(), null, null)

                HttpClientBuilder builder = HttpClientBuilder.create()
                SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(context)
                builder.setSSLSocketFactory(sslConnectionFactory)

                Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create().register("https", sslConnectionFactory).build()

                HttpClientConnectionManager ccm = new PoolingHttpClientConnectionManager(registry)
                ccm.setMaxTotal(40)
                ccm.setDefaultMaxPerRoute(40)

                RequestConfig requestConfig = RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD) //Ladok3 servers needs this for login persistens
                        .setConnectTimeout(10000) //10 seconds
                        .setConnectionRequestTimeout(120000) //20 minutes
                        .setSocketTimeout(120000) //20 minutes
                        .build()

                builder.setConnectionManager(ccm)
                builder.setDefaultRequestConfig(requestConfig)
                httpClient = builder.build()
            }
        }
        return httpClient
    }

    @NotTransactional
    synchronized CloseableHttpClient getLadok3Client(Edu edu) {
        CloseableHttpClient httpClient = null
        if(edu) {
            if(!httpClientsForL3ByEdy.get(edu)) {
                httpClientsForL3ByEdy.put(edu, initiateLadok3Client(edu))
            }
            httpClient = httpClientsForL3ByEdy.get(edu)
        }
        return httpClient
    }

    @NotTransactional
    Object getLadok3MapFromJsonResponseByUrlAndType(Edu edu, String url, String acceptHeaderValue, Map query = [:], boolean noMap=false) {
        Object retObject = null
        CloseableHttpClient client = getLadok3Client(edu)

        URIBuilder uriBuilder = new URIBuilder(settingsService.getLadok3UrlForEdu(edu) + url)
        if (query && query.size() > 0) {
            List<NameValuePair> params = []
            query.each { k, v ->
                if (!(v instanceof String)) {
                    params.add(new BasicNameValuePair(k, String.valueOf(v)))
                } else {
                    params.add(new BasicNameValuePair(k, v))
                }
            }
            uriBuilder.addParameters(params)
        }

        HttpGet httpGet = new HttpGet(uriBuilder.build())
        if (acceptHeaderValue && !acceptHeaderValue.isEmpty()) {
            httpGet.addHeader("Accept", acceptHeaderValue)
        }

        int count = 0
        int maxTries = 3
        while (true) {
            try {
                CloseableHttpResponse response = client.execute(httpGet)
                if (response) {
                    try {
                        int statusCode = response.getStatusLine().getStatusCode()
                        if (statusCode == 200) {
                            String inputTxt
                            HttpEntity entity = response.getEntity()
                            InputStream inputStream = entity.getContent()
                            try {
                                inputTxt = inputStream.text
                                if (noMap) {
                                    retObject = inputTxt
                                } else {
                                    JsonSlurper slurper = new JsonSlurper()
                                    retObject = slurper.parseText(inputTxt) as Map
                                }
                            } catch (IOException ex2) {
                                throw ex2;
                            } finally {
                                inputStream.close()
                            }
                        } else if (statusCode == 404) {
                            retObject = null
                        } else if (statusCode == 429) {
                            log.error("getLadok3MapFromJsonResponseByUrlAndType: Error while trying to get ${url}, httpResponse ${statusCode}.")
                            throw new Exception("429 while doing REST REQUEST: ${response.statusLine}, ${url}")
                        } else {
                            log.error("getLadok3MapFromJsonResponseByUrlAndType: Error while trying to get ${url}, httpResponse ${statusCode}.")
                            throw new Exception("Failure while doing REST REQUEST: ${response.statusLine}, ${url}")
                        }
                    } catch (Throwable ex) {
                        throw ex
                    } finally {
                        response.close()
                    }
                } else {
                    log.error("getLadok3MapFromJsonResponseByUrlAndType: Error while trying to get ${url}, no response object returned.")
                    throw new Exception("getLadok3MapFromJsonResponseByUrlAndType: Error while trying to get ${url}, no response object returned.")
                }
                return retObject
            } catch (Exception retryEx) {
                if (++count >= maxTries) {
                    throw retryEx
                }
                log.info("getLadok3MapFromJsonResponseByUrlAndType: catched exception (${retryEx.message}). Retrying request in 1 minute, going to sleep...")
                Thread.sleep(60000)
                log.info("getLadok3MapFromJsonResponseByUrlAndType: Retrying request to url ${url}, attempt ${count} of ${maxTries}")
            }
        }
        return null
    }
}
