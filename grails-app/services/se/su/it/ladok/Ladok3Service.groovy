package se.su.it.ladok

import org.json.XML
import grails.gorm.transactions.NotTransactional
import grails.gorm.transactions.Transactional
import org.apache.abdera.Abdera
import org.apache.abdera.model.Document
import org.apache.abdera.model.Entry
import org.apache.abdera.model.Feed
import org.apache.abdera.parser.Parser

import java.security.KeyStore
import java.security.cert.X509Certificate

@Transactional
class Ladok3Service {
    HttpClientService httpClientService

    @NotTransactional
    Date getExpirationDateForCert(String certPath, String certPassWord) {
        Date expirationDate = null
        try {
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType())
            keystore.load(new FileInputStream(certPath), certPassWord.toCharArray())
            Enumeration aliases = keystore.aliases()
            for(; aliases.hasMoreElements();) {
                String alias = (String) aliases.nextElement()
                expirationDate = ((X509Certificate) keystore.getCertificate(alias)).getNotAfter()
            }
        } catch(Throwable exception) {
            log.info "Some problems in getExpirationDateForCert(String, String): ${exception.getMessage()}"
        }
        return expirationDate
    }

    @NotTransactional
    Feed getFeed(Edu edu, int feedId) {
        Feed feed = null
        if(edu && feedId>0) {
            String feedResource = "/handelser/feed/"+feedId
            try {
                String xmlFeed = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, feedResource, null, null, true)
                Abdera abdera = new Abdera()
                Parser parser = abdera.getParser()

                Document<Feed> doc = parser.parse(new ByteArrayInputStream(xmlFeed.getBytes("UTF-8")))
                feed = doc.getRoot().clone() as Feed
            } catch(Throwable exception) {
                log.info "Problem in getFeed(${edu.toString()}, ${feedId}): ${exception.getMessage()}"
                feed = null
            } finally {
                // some closing / cleaning?
            }
        }
        return feed
    }

    @NotTransactional
    LatestFeedMeta getUncachedUppfoljningFeedRecentId(Edu edu) {
        String feed = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu,"/uppfoljning/feed/recent",null, null, true)
        groovy.xml.XmlParser xmlParser = new groovy.xml.XmlParser()
        Node node = xmlParser.parseText(feed)
        String id = null
        String updated = null
        String eventUid = null
        node.children().each { Node child ->
            String name = child.name().toString().replaceAll("http://www.w3.org/2005/Atom", "").substring(2)
            if("id"==name) {
                id = child.value()
            } else if("updated"==name) {
                updated = child.value()
                if(updated.startsWith("[")) {
                    updated = updated.substring(1)
                }
                if(updated.endsWith("]")) {
                    updated = updated.substring(0, updated.length()-1)
                }
            } else if("entry"==name) {
                try {
                    child.children().each { Node grandChild ->
                        String cname = grandChild.name().toString().replaceAll("http://www.w3.org/2005/Atom", "").substring(2)
                        if(!eventUid && "id"==cname) {
                            eventUid = grandChild.value()
                        }
                    }
                } catch(Throwable e) {
                }
            }
        }
        return new LatestFeedMeta(id, updated, eventUid)
    }

    @Transactional
    void updateAtomFeedHistoryFromFeed(Edu edu, int feedId) {
        if(edu && feedId>0) {
            Feed feed = getFeed(edu, feedId)
            if(feed?.entries) {
                feed.entries.reverse().each {Entry entry ->
                    String entryAsString = XML.toJSONObject(entry.content).toString()
                    String uid = entry.getId().toString()
                    Map entryAsMap = XML.toJSONObject(entry.content) as Map
                    entryAsMap.keySet().sort().each { String eventType ->
                        AtomFeedHistory atomFeedHistory = AtomFeedHistory.findOrCreateByEduAndEventUidAndFeedId(edu, uid, feedId)
                        atomFeedHistory.eventType = eventType
                        atomFeedHistory.event = entryAsString
                        atomFeedHistory.save(failOnError: true)
                    }
                }
            }
        }
    }
}
