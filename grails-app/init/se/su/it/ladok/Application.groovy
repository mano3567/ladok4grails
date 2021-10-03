package se.su.it.ladok

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration

import org.apache.catalina.connector.Connector
import org.apache.coyote.ajp.AbstractAjpProtocol
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ServletWebServerFactory
import org.springframework.context.EnvironmentAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource

import javax.naming.Context
import javax.naming.InitialContext
import javax.naming.NamingException
import com.mysql.cj.jdbc.MysqlDataSource

class Application extends GrailsAutoConfiguration  implements EnvironmentAware {
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }

    @Bean
    @Profile('development')
    ServletWebServerFactory servletContainer() {
        println "### Start setting up Tomcat AJP on port 8009 and encoding attributes to UTF-8 ###"
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory()
        tomcat.addAdditionalTomcatConnectors(createSslConnector())
        println "### Finished setting up Tomcat AJP on port 8009 and encoding attributes to UTF-8 ###"
        return tomcat
    }

    private Connector createSslConnector() {
        Connector ajpConnector = new Connector("AJP/1.3")
        try {
            ajpConnector.port = 8009
            ajpConnector.redirectPort = 8443
            ajpConnector.enableLookups = false
            ajpConnector.setProperty("redirectPort", "8443")
            ajpConnector.setProperty("protocol", "AJP/1.3")
            ajpConnector.setProperty("enableLookups", "false")
            ajpConnector.URIEncoding = "UTF-8"
            ((AbstractAjpProtocol) ajpConnector.getProtocolHandler()).setSecretRequired(false)
            return ajpConnector
        } catch (Throwable ex) {
            throw new IllegalStateException("Failed setting up AJP Connector", ex)
        }
    }

    @Override
    void setEnvironment(Environment environment) {
        //Load external config file if present
        Properties systemProps = System.properties
        String local = "/local"
        File externalConfigFile = new File("/local/ladok/conf/application.yml")
        if (externalConfigFile.exists()) {
            Resource resourceConfig = new FileSystemResource(externalConfigFile.absolutePath)
            YamlPropertiesFactoryBean ypfb = new YamlPropertiesFactoryBean()
            ypfb.setResources([resourceConfig] as Resource[])
            ypfb.afterPropertiesSet()
            Properties properties = ypfb.getObject()
            if (properties.size() > 0) {
                environment.propertySources.addFirst(new PropertiesPropertySource("${externalConfigFile.absolutePath}", properties))
            }
            println("### Added external configuration from file ${externalConfigFile.absolutePath} ###")
        } else {
            println("### No external configuration file found with filename ${externalConfigFile.absolutePath} ###")
        }

        Properties quartzProperties = new Properties()
        println("### Enabling quartz! This is a car server. ###")
        quartzProperties.setProperty("quartz.pluginEnabled", "true")
        quartzProperties.setProperty("quartz.autoStartup", "true")
        quartzProperties.setProperty("quartz.jdbcStore", "true")
        quartzProperties.setProperty("quartz.waitForJobsToCompleteOnShutdown", "false")
        quartzProperties.setProperty("quartz.purgeQuartzTablesOnStartup", "true")
        quartzProperties.setProperty("quartz.exposeSchedulerInRepository", "true")
        quartzProperties.setProperty("quartz.scheduler.instanceName", "ladokScheduler")
        environment.propertySources.addFirst(new PropertiesPropertySource("quartz", quartzProperties))

        if (grails.util.Environment.current == grails.util.Environment.DEVELOPMENT || grails.util.Environment.current == grails.util.Environment.TEST) {
            String fp = "${System.properties['user.dir']}/db/dbConfig.groovy"
            boolean extDbConfigured = new File(fp).exists()
            if (extDbConfigured) {
                println "### Using db/dbConfig.groovy to set up JNDI initial context ###"
                ConfigObject dbConfig = new ConfigSlurper().parse(new File(fp).toURI().toURL())
                try {
                    // Create initial context
                    System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory")
                    System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming")
                    InitialContext ic = new InitialContext()

                    ic.createSubcontext("java:")
                    ic.createSubcontext("java:comp")
                    ic.createSubcontext("java:comp/env")
                    ic.createSubcontext("java:comp/env/jdbc")

                    // Construct DataSource
                    MysqlDataSource dataSource = new MysqlDataSource()
                    dataSource.setURL(dbConfig.datasource.url)
                    dataSource.setUser(dbConfig.datasource.username)
                    dataSource.setPassword(dbConfig.datasource.password)
                    ic.bind("java:comp/env/jdbc/ladok", dataSource)

                    if(grails.util.Environment.current != grails.util.Environment.TEST) {
                        setDatabaseManagerSupportDataSource(environment)
                    }
                    println "### OK --- Using db/dbConfig.groovy to set up JNDI initial context ###"
                } catch (NamingException ex) {
                    println "### FAILED --- Using db/dbConfig.groovy to set up JNDI initial context ###"
                    ex.printStackTrace()
                }
            } else {
                println "### No db/dbConfig.groovy found using datasources found in configuration ###"
            }
        }
    }

    private void setDatabaseManagerSupportDataSource(Environment environment) {
        Properties dataSourceProperties = new Properties()

        dataSourceProperties.setProperty("dataSource.pooled", "true")
        dataSourceProperties.setProperty("dataSource.jmxExport", "true")
        dataSourceProperties.setProperty("dataSource.jdbcStore", "true")
        dataSourceProperties.setProperty("dataSource.driverClassName", "com.mysql.cj.jdbc.Driver")
        dataSourceProperties.setProperty("dataSource.username", "${new InitialContext().lookup('java:comp/env/jdbc/ladok').user}")
        dataSourceProperties.setProperty("dataSource.password", "${new InitialContext().lookup('java:comp/env/jdbc/ladok').password}")
        dataSourceProperties.setProperty("dataSource.url", "${new InitialContext().lookup('java:comp/env/jdbc/ladok').url}")

        environment.propertySources.addFirst(new PropertiesPropertySource("DatabaseManagerSupportDataSource", dataSourceProperties))
    }
}
