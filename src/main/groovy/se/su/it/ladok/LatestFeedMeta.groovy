package se.su.it.ladok

import groovy.transform.CompileStatic

@CompileStatic
class LatestFeedMeta {
    private String itsEventUid = null
    private String itsFeedId = null
    private String itsLastUpdated = null

    LatestFeedMeta(String someFeedId, String lastUpdated, String someEventUid) {
        itsEventUid = someEventUid?.trim()
        itsFeedId = someFeedId?.trim()
        itsLastUpdated = lastUpdated?.trim()
    }

    String getEventUid() {
        return itsEventUid
    }

    String getFeedId() {
        return itsFeedId
    }

    int getParsedFeedId() {
        int id = 0
        if(feedId) {
            String tmp = feedId.replaceAll('urn:id:', '').substring(1)
            tmp = tmp.substring(0, tmp.length()-1)
            id = Integer.parseInt(tmp)
        }
        return id
    }

    String getLastUpdated() {
        return itsLastUpdated
    }
}
