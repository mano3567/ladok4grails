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
import java.text.SimpleDateFormat

@Transactional
class Ladok3Service {
    HttpClientService httpClientService
    SettingsService settingsService

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
                        settingsService.setLadok3LatestEventUidForEdu(edu, uid)
                        settingsService.setLadok3LatestFeedIdForEdu(edu, feedId)
                    }
                }
            }
        }
    }

    @Transactional
    void updateL3BevisBenamning(Edu edu) {
        int count = 0
        try {
            Map response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/examen/bevisbenamning", "application/vnd.ladok-examen+json", [:])
            log.info("Processing (${response?.Bevisbenamning?response.Bevisbenamning.size():0}) updateLadok3BevisBenamning for edu: ${edu}")
            response.Bevisbenamning.each { Map l3bevis ->
                String kod = l3bevis.get('Kod', null) as String
                if(kod) {
                    L3BevisBenamning l3BevisBenamning = L3BevisBenamning.findByEduAndKod(edu, kod)
                    if(!l3BevisBenamning) {
                        l3BevisBenamning = L3BevisBenamning.newInstance(edu: edu, kod: kod)
                    }
                    l3BevisBenamning.anteckning = l3bevis.get('Anteckning', null)?.trim() as String
                    if(l3BevisBenamning.anteckning?.length()>511) {
                        l3BevisBenamning.anteckning = l3BevisBenamning.anteckning.substring(0, 511)
                    }
                    Map benamning = l3bevis.get('Benamning', [en: '', sv: ''])
                    l3BevisBenamning.benamningEn = benamning.get('en') as String
                    l3BevisBenamning.benamningSv = benamning.get('sv') as String
                    l3BevisBenamning.omfattning = l3bevis.get('Omfattning', 0.0) as double
                    l3BevisBenamning.uid = l3bevis.get('Uid', null) as String
                    try {
                        Map giltighetsPeriod = l3bevis.get('Giltighetsperiod', [Slutdatum: ''])
                        l3BevisBenamning.giltighetsPeriodSlutDatum = Date.parse('yyyy-MM-dd', giltighetsPeriod.Slutdatum as String)
                    } catch(Throwable exception) {
                        l3BevisBenamning.giltighetsPeriodSlutDatum = null
                    }
                    l3BevisBenamning.save(failOnError: true)

                    count++
                    if((count % 100) == 0) {
                        log.info("Number of bevisbenamningar this far (${edu}): ${count}")
                    }
                }
            }
        } catch(Throwable exception) {
            log.warn "Some problem calling /kataloginformation/grunddata/period: ${exception.getMessage()}"
        }
    }

    @Transactional
    void updateL3FinansieringsForm(Edu edu) {
        int count = 0
        Map response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/utbildningsinformation/grunddata/finansieringsform", "application/vnd.ladok-utbildningsinformation+json")
        log.info("Processing (${response?.Finansieringsform?response.Finansieringsform.size():0}) updateLadok3Finansieringsform for edu: ${edu}")
        if (response && response.Finansieringsform) {
            response.Finansieringsform.each { Map finansieringsform ->
                if(!edu || (!finansieringsform?.ID || finansieringsform.ID.isEmpty())) {
                    throw new Exception("Missing <edu> or <finansieringsform.ID")
                }
                if(finansieringsform) {
                    int ladokId = finansieringsform.get('ID', -1) as int
                    L3FinansieringsForm l3Finansieringsform = L3FinansieringsForm.findOrCreateByEduAndLadokId(edu, ladokId)
                    l3Finansieringsform.benamningEn = finansieringsform.Benamning?.en as String
                    l3Finansieringsform.benamningSv = finansieringsform.Benamning?.sv as String
                    l3Finansieringsform.beskrivningEn = finansieringsform.Beskrivning?.en as String
                    l3Finansieringsform.beskrivningSv = finansieringsform.Beskrivning?.sv as String
                    if(l3Finansieringsform.beskrivningSv && l3Finansieringsform.beskrivningSv.length()>512) {
                        l3Finansieringsform.beskrivningSv = l3Finansieringsform.beskrivningSv.substring(0, 512)
                    }
                    l3Finansieringsform.larosateId = finansieringsform.LarosateID?:-1 as int
                    l3Finansieringsform.kod = finansieringsform.Kod as String
                    String slutdatum = finansieringsform.Giltighetsperiod?.Slutdatum as String
                    if(slutdatum) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
                        l3Finansieringsform.slutDatum = sdf.parse(slutdatum)
                    }
                    String startDatum = finansieringsform.Giltighetsperiod?.Startdatum as String
                    if(startDatum) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
                        l3Finansieringsform.startDatum = sdf.parse(startDatum)
                    }
                    l3Finansieringsform.save(failOnError: true)
                    count++
                    if((count % 100) == 0) {
                        log.info("Number of finansieringsformer this far (${edu}): ${count}")
                    }
                }
            }
        }
    }

    @Transactional
    void updateL3Organizations(Edu edu) {
        int count = 0
        Map query = [page: 1, limit: 1, onlyCount: true]
        try {
            Map response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/kataloginformation/organisation/filtrera", "application/vnd.ladok-kataloginformation+json", query)
            if (response && response.TotaltAntalPoster && response.TotaltAntalPoster > 0) {
                int rowCount = response.TotaltAntalPoster as int
                log.info("Processing (${rowCount}) updateLadok3Organizations for edu: ${edu}")
                for (int i = 400; i < rowCount + 401; i += 400) {
                    query = [page: i / 400, limit: 400]
                    response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/kataloginformation/organisation/filtrera", "application/vnd.ladok-kataloginformation+json", query)
                    response.Resultat.each { Map l3org ->
                        String organisationsKod = l3org.get('Organisationskod')
                        L3Organisation l3Organisation = L3Organisation.findOrCreateByEduAndOrganisationsKod(edu, organisationsKod)
                        Map benamning = l3org.get('Benamning', [sv: '', en: ''])
                        l3Organisation.benamningEn = benamning.en?.trim() as String
                        l3Organisation.benamningSv = benamning.sv.trim() as String
                        String slutdatum = l3org.get('Giltighetsperiod', [Slutdatum: null]).get('Slutdatum', null) as String
                        if(slutdatum) {
                            SimpleDateFormat sdf = new SimpleDateFormat('yyyy-MM-dd')
                            try {
                                l3Organisation.giltighetsPeriodSlutDatum = sdf.parse(slutdatum)
                            } catch(Throwable exception) {
                                l3Organisation.giltighetsPeriodSlutDatum = null
                            }
                        } else {
                            l3Organisation.giltighetsPeriodSlutDatum = null
                        }
                        l3Organisation.organisationsEnhetsTyp = l3org.get('Organisationsenhetstyp', -1) as int
                        l3Organisation.uid = l3org.get('Uid', null)
                        l3Organisation.save(failOnError: true)

                        count++
                        if((count % 100) == 0) {
                            log.info("Number of organizations this far (${edu}): ${count}")
                        }
                    }
                }
            }
        } catch(Throwable exception) {
            log.warn "Some problem calling /kataloginformation/organisation/filtrera: ${exception.getMessage()}"
        }
    }

    @Transactional
    void updateL3Periods(Edu edu) {
        int count = 0
        try {
            Map response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/kataloginformation/grunddata/period", "application/vnd.ladok-kataloginformation+json", [:])
            log.info("Processing (${response?.Period?response.Period.size():0}) updateLadok3Periods for edu: ${edu}")
            response.Period.each { Map l3per ->
                String kod = l3per.get('Kod', null) as String
                if(kod) {
                    L3Period period = L3Period.findOrCreateByEduAndKod(edu, kod)
                    Map benamning = l3per.get('Benamning', [en: '', sv: ''])
                    period.benamningEn = benamning.get('en') as String
                    period.benamningSv = benamning.get('sv') as String
                    String fromDatum = l3per.get('FromDatum', null) as String
                    if(fromDatum) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
                        period.fromDatum = sdf.parse(fromDatum)
                    } else {
                        period.fromDatum = null
                    }
                    period.ladokId = l3per.get('ID', -1) as int
                    period.larosateId = l3per.get('LarosateID', -1) as int
                    period.periodTypId = l3per.get('PeriodtypID', -1) as int
                    String tomDatum = l3per.get('TomDatum', null) as String
                    if(tomDatum) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
                        period.tomDatum = sdf.parse(tomDatum)
                    } else {
                        period.tomDatum = null
                    }
                    period.save(failOnError: true)

                    count++
                    if((count % 100) == 0) {
                        log.info("Number of periods this far (${edu}): ${count}")
                    }
                }
            }
        } catch(Throwable exception) {
            log.warn "Some problem calling /kataloginformation/grunddata/period: ${exception.getMessage()}"
        }
    }

    @Transactional
    void updateL3StudieLokalisering(Edu edu) {
        int count = 0
        Map response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/utbildningsinformation/grunddata/studielokalisering", "application/vnd.ladok-utbildningsinformation+json")
        log.info("Processing (${response?.Studielokalisering?response.Studielokalisering.size():0}) updateLadok3StudieLokalisering for edu: ${edu}")
        if (response && response.Studielokalisering) {
            response.Studielokalisering.each { Map studieLokalisering ->
                if(!edu || (!studieLokalisering?.ID || studieLokalisering.ID.isEmpty())) {
                    throw new Exception("Missing <edu> or <studielokalisering.ID")
                }
                if(studieLokalisering) {
                    String kod = studieLokalisering.Kod?.trim() as String
                    L3StudieLokalisering l3StudieLokalisering = L3StudieLokalisering.findOrCreateByEduAndKod(edu, kod)
                    l3StudieLokalisering.benamningEn = studieLokalisering.Benamning?.en as String
                    l3StudieLokalisering.benamningSv = studieLokalisering.Benamning?.sv as String
                    l3StudieLokalisering.beskrivningEn = studieLokalisering.Beskrivning?.en as String
                    l3StudieLokalisering.beskrivningSv = studieLokalisering.Beskrivning?.sv as String
                    l3StudieLokalisering.ladokId = studieLokalisering.ID ? Integer.parseInt(studieLokalisering.ID ): -1
                    l3StudieLokalisering.larosateId = studieLokalisering.LarosateID ? Integer.parseInt(studieLokalisering.LarosateID) : -1
                    String slutDatum = studieLokalisering.Giltighetsperiod?.Slutdatum as String
                    if(slutDatum) {
                        SimpleDateFormat sdf = new SimpleDateFormat('yyyy-MM-dd')
                        l3StudieLokalisering.slutDatum = sdf.parse(slutDatum)
                    } else {
                        l3StudieLokalisering.slutDatum = null
                    }
                    String startDatum = studieLokalisering.Giltighetsperiod?.Startdatum as String
                    if(startDatum) {
                        SimpleDateFormat sdf = new SimpleDateFormat('yyyy-MM-dd')
                        l3StudieLokalisering.startDatum = sdf.parse(startDatum)
                    } else {
                        l3StudieLokalisering.startDatum = null
                    }

                    l3StudieLokalisering.save(failOnError: true)
                    count++
                    if((count % 100) == 0) {
                        log.info("Number of studielokaliseringar this far (${edu}): ${count}")
                    }
                }
            }
        }
    }

    @Transactional
    void updateL3StudieTakt(Edu edu) {
        int count = 0
        Map response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/kataloginformation/grunddata/studietakt", "application/vnd.ladok-kataloginformation+json")
        log.info("Processing (${response?.Studietakt?response.Studietakt.size():0}) updateLadok3StudieTakt for edu: ${edu}")
        if (response && response.Studietakt) {
            response.Studietakt.each { Map studietakt ->
                if(!edu || (!studietakt?.ID || studietakt.ID.isEmpty())) {
                    throw new Exception("Missing <edu> or <studietakt.ID")
                }
                if(studietakt) {
                    int takt = studietakt.ID ? Integer.parseInt(studietakt.ID as String) : -1
                    L3StudieTakt l3Studietakt = L3StudieTakt.findOrCreateByEduAndLadokId(edu, takt)
                    l3Studietakt.benamningEn = studietakt.Benamning?.en as String
                    l3Studietakt.benamningSv = studietakt.Benamning?.sv as String
                    l3Studietakt.beskrivningEn = studietakt.Beskrivning?.en as String
                    l3Studietakt.beskrivningSv = studietakt.Beskrivning?.sv as String
                    l3Studietakt.kod = studietakt.Kod as String
                    l3Studietakt.ladokId = studietakt.ID ? Integer.parseInt(studietakt.ID as String) : -1
                    l3Studietakt.larosateId = studietakt.LarosateID ? Integer.parseInt(studietakt.LarosateID as String) : -1

                    String slutDatum = studietakt.Giltighetsperiod?.Slutdatum as String
                    if(slutDatum) {
                        SimpleDateFormat sdf = new SimpleDateFormat('yyyy-MM-dd')
                        l3Studietakt.slutDatum = sdf.parse(slutDatum)
                    } else {
                        l3Studietakt.slutDatum = null
                    }

                    String startDatum = studietakt.Giltighetsperiod?.Startdatum as String
                    if(startDatum) {
                        SimpleDateFormat sdf = new SimpleDateFormat('yyyy-MM-dd')
                        l3Studietakt.startDatum = sdf.parse(startDatum)
                    } else {
                        l3Studietakt.startDatum = null
                    }

                    l3Studietakt.takt = studietakt.Takt ? Integer.parseInt(studietakt.Takt as String) : -1

                    l3Studietakt.save(failOnError: true)
                    count++
                    if((count % 100) == 0) {
                        log.info("Number of studietakter this far (${edu}): ${count}")
                    }
                }
            }
        }
    }

    @Transactional
    void updateL3UndervisningsForm(Edu edu) {
        int count = 0
        Map response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/utbildningsinformation/grunddata/undervisningsform", "application/vnd.ladok-utbildningsinformation+json")
        log.info("Processing (${response?.Undervisningsform?response.Undervisningsform.size():0}) updateLadok3Undervisningsform for edu: ${edu}")
        if (response && response.Undervisningsform) {
            response.Undervisningsform.each { Map undervisningsform ->
                if(!edu || (!undervisningsform?.ID || undervisningsform.ID.isEmpty())) {
                    throw new Exception("Missing <edu> or <undervisningsform.ID")
                }
                if(undervisningsform) {
                    int ladokId = undervisningsform.ID ? Integer.parseInt(undervisningsform.ID as String) : -1
                    L3UndervisningsForm l3Undervisningsform = L3UndervisningsForm.findOrCreateByEduAndLadokId(edu, ladokId)
                    l3Undervisningsform.benamningEn = undervisningsform.Benamning?.en as String
                    l3Undervisningsform.benamningSv = undervisningsform.Benamning?.sv as String
                    l3Undervisningsform.beskrivningEn = undervisningsform.Beskrivning?.en as String
                    l3Undervisningsform.beskrivningSv = undervisningsform.Beskrivning?.sv as String
                    l3Undervisningsform.kod = undervisningsform.Kod as String
                    l3Undervisningsform.larosateId = undervisningsform.LarosateID ? Integer.parseInt(undervisningsform.LarosateID as String) : -1
                    String slutDatum = undervisningsform.Giltighetsperiod?.Slutdatum as String
                    if(slutDatum) {
                        SimpleDateFormat sdf = new SimpleDateFormat('yyyy-MM-dd')
                        l3Undervisningsform.slutDatum = sdf.parse(slutDatum)
                    } else {
                        l3Undervisningsform.slutDatum = null
                    }
                    String startDatum = undervisningsform.Giltighetsperiod?.Startdatum as String
                    if(startDatum) {
                        SimpleDateFormat sdf = new SimpleDateFormat('yyyy-MM-dd')
                        l3Undervisningsform.startDatum = sdf.parse(startDatum)
                    } else {
                        l3Undervisningsform.startDatum = null
                    }

                    l3Undervisningsform.save(failOnError: true)
                    count++
                    if((count % 100) == 0) {
                        log.info("Number of undervisningsformer this far (${edu}): ${count}")
                    }
                }
            }
        }
    }

    @Transactional
    void updateL3UndervisningsTid(Edu edu) {
        int count = 0
        Map response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/utbildningsinformation/grunddata/undervisningstid", "application/vnd.ladok-utbildningsinformation+json")
        log.info("Processing (${response?.Undervisningstid?response.Undervisningstid.size():0}) updateLadok3Undervisningstid for edu: ${edu}")
        if (response && response.Undervisningstid) {
            response.Undervisningstid.each { Map undervisningstid ->
                if(!edu || (!undervisningstid?.ID || undervisningstid.ID.isEmpty())) {
                    throw new Exception("Missing <edu> or <undervisningstid.ID")
                }
                if(undervisningstid) {
                    int ladokId = undervisningstid.ID ? Integer.parseInt(undervisningstid.ID as String) : -1
                    L3UndervisningsTid l3Undervisningstid = L3UndervisningsTid.findOrCreateByEduAndLadokId(edu,ladokId)
                    l3Undervisningstid.benamningEn = undervisningstid.Benamning?.en as String
                    l3Undervisningstid.benamningSv = undervisningstid.Benamning?.sv as String
                    l3Undervisningstid.beskrivningEn  = undervisningstid.Beskrivning?.en as String
                    l3Undervisningstid.beskrivningSv = undervisningstid.Beskrivning?.sv as String
                    l3Undervisningstid.kod = undervisningstid.Kod as String
                    l3Undervisningstid.larosateId = undervisningstid.LarosateID ? Integer.parseInt(undervisningstid.LarosateID as String) : -1
                    String slutDatum = undervisningstid.Giltighetsperiod?.Slutdatum as String
                    if(slutDatum) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
                        l3Undervisningstid.slutDatum = sdf.parse(slutDatum)
                    } else {
                        l3Undervisningstid.slutDatum = null
                    }
                    String startDatum = undervisningstid.Giltighetsperiod?.Startdatum as String
                    if(startDatum) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
                        l3Undervisningstid.startDatum = sdf.parse(startDatum)
                    } else {
                        l3Undervisningstid.startDatum = null
                    }

                    l3Undervisningstid.save(failOnError: true)
                    count++
                    if((count % 100) == 0) {
                        log.info("Number of undervisningstider this far (${edu}): ${count}")
                    }
                }
            }
        }
    }

    @Transactional
    void updateL3Utbildningstyp(Edu edu) {
        int count = 0
        Map response = httpClientService.getLadok3MapFromJsonResponseByUrlAndType(edu, "/utbildningsinformation/grunddata/utbildningstyp", "application/vnd.ladok-utbildningsinformation+json")
        log.info("Processing (${response?.Utbildningstyp?response.Utbildningstyp.size():0}) updateLadok3Utbildningstyp for edu: ${edu}")
        if (response && response.Utbildningstyp) {
            response.Utbildningstyp.each { Map utbildningstyp ->
                if(!edu || (!utbildningstyp?.ID || utbildningstyp.ID.isEmpty())) {
                    throw new Exception("Missing <edu> or <utbildningstyp.ID")
                }
                if(utbildningstyp) {
                    int ladokId = Integer.parseInt(utbildningstyp.ID as String)
                    L3UtbildningsTyp l3UtbildningsTyp = L3UtbildningsTyp.findOrCreateByEduAndLadokId(edu, ladokId)
                    l3UtbildningsTyp.avserTillfalle = utbildningstyp.AvserTillfalle ?: false
                    l3UtbildningsTyp.benamningEn = utbildningstyp.Benamning?.en as String
                    l3UtbildningsTyp.benamningSv = utbildningstyp.Benamning?.sv as String
                    l3UtbildningsTyp.beskrivningEn = utbildningstyp.Beskrivning?.en as String
                    l3UtbildningsTyp.beskrivningSv = utbildningstyp.Beskrivning?.sv as String
                    l3UtbildningsTyp.forvaldOmfattning = utbildningstyp.Regler?.ForvaldOmfattning ?:-1
                    l3UtbildningsTyp.grundTyp = utbildningstyp.Grundtyp as String
                    l3UtbildningsTyp.harOmfattning = utbildningstyp.Regler?.HarOmfattning ?:false
                    l3UtbildningsTyp.individuell = utbildningstyp.Regler?.Individuell ?:false
                    l3UtbildningsTyp.kanUtannonseras = utbildningstyp.Regler?.KanUtannonseras ?:false
                    l3UtbildningsTyp.kod = utbildningstyp.Kod as String
                    l3UtbildningsTyp.ladokId = utbildningstyp.ID? Integer.parseInt(utbildningstyp.ID as String): -1
                    l3UtbildningsTyp.larosateId = utbildningstyp.LarosateID?:-1
                    l3UtbildningsTyp.nivaInomStudieordningId = utbildningstyp.NivaInomStudieordningID? Integer.parseInt(utbildningstyp.NivaInomStudieordningID as String):-1
                    l3UtbildningsTyp.sjalvstandig = utbildningstyp.Regler?.Sjalvstandig ?:false
                    l3UtbildningsTyp.sorteringsOrdning = utbildningstyp.Sorteringsordning?:-1
                    l3UtbildningsTyp.studieOrdningId = utbildningstyp.StudieordningID?:-1
                    l3UtbildningsTyp.tillatnaUtbildningstyperIStruktur = utbildningstyp.TillatnaUtbildningstyperIStruktur?utbildningstyp.TillatnaUtbildningstyperIStruktur.join(","):null
                    l3UtbildningsTyp.tillfalleInomUtbildningstyper  = utbildningstyp.TillfalleInomUtbildningstyper?utbildningstyp.TillfalleInomUtbildningstyper.join(","):null
                    l3UtbildningsTyp.versionsHanteras = utbildningstyp.Regler?.Versionshanteras ?:false

                    String slutDatum = utbildningstyp.Giltighetsperiod?.Slutdatum
                    if(slutDatum) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
                        l3UtbildningsTyp.slutDatum = sdf.parse(slutDatum)
                    } else {
                        l3UtbildningsTyp.slutDatum = null
                    }

                    String startDatum = utbildningstyp.Giltighetsperiod?.Startdatum
                    if(startDatum) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
                        l3UtbildningsTyp.startDatum = sdf.parse(startDatum)
                    } else {
                        l3UtbildningsTyp.startDatum = null
                    }

                    l3UtbildningsTyp.save(failOnError: true)
                    count++
                    if((count % 100) == 0) {
                        log.info("Number of utbildningstyper this far (${edu}): ${count}")
                    }
                }
            }
        }
    }

}
