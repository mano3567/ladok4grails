package se.su.it.ladok

import grails.gorm.transactions.Transactional
import groovy.util.slurpersupport.GPathResult

@Transactional
class Ladok3Service {
    HttpClientService httpClientService

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
        LatestFeedMeta latestFeedMeta = new LatestFeedMeta(id, updated, eventUid)
        return latestFeedMeta
    }
}
