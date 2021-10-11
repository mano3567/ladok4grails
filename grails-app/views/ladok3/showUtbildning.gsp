<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Utbildning</title>
        <meta name="layout" content="main">
    </head>
    <body>
        <h2>Utbildning ${education.utbildningsKod} (${education.edu})</h2>
        <hr class="mb-1"/>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>Lärosäte:</strong></div>
            <div class="col-lg-10">${education.edu.fullName}</div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>Utbildningskod:</strong></div>
            <div class="col-lg-10">
                <g:if test="${education.getOverliggandeUtbildning()}">${education.getOverliggandeUtbildning().utbildningsKod} / </g:if>${education.utbildningsKod}
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>Benämning:</strong></div>
            <div class="col-lg-10">${education.benamningSv}</div>
        </div>
        <g:if test="${education.benamningEn}">
            <div class="row mb-1">
                <div class="col-lg-2"><strong>Benämning En:</strong></div>
                <div class="col-lg-10">${education.benamningEn}</div>
            </div>
        </g:if>
        <g:if test="${education.getOverliggandeUtbildning()}">
            <div class="row mb-1">
                <div class="col-lg-2"><strong>Överliggande utbildning:</strong></div>
                <div class="col-lg-10"><g:link action="showUtbildning" id="${education.getOverliggandeUtbildning().id}">${education.getOverliggandeUtbildning().utbildningsKod} (${education.getOverliggandeUtbildning().edu}): ${education.getOverliggandeUtbildning().benamningSv}</g:link></div>
            </div>
        </g:if>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>Omfattning:</strong></div>
            <div class="col-lg-10">${education.omfattningsVarde}</div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>Avvecklad:</strong></div>
            <div class="col-lg-10"><g:formatBoolean boolean="${education.avvecklad}" true="Ja" false="Nej"/></div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>EnhetsId:</strong></div>
            <div class="col-lg-10">${education.enhetId}</div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>ProcessStatusId:</strong></div>
            <div class="col-lg-10">${education.processStatusId}</div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>StudieOrdningId:</strong></div>
            <div class="col-lg-10">${education.studieOrdningId}</div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>UtbildningsFormId:</strong></div>
            <div class="col-lg-10">${education.utbildningsFormId}</div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>Har innehåll:</strong></div>
            <div class="col-lg-10"><g:formatBoolean boolean="${education.harInnehall}" true="Ja" false="Nej"/></div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>Senaste version:</strong></div>
            <div class="col-lg-10"><g:formatBoolean boolean="${education.senasteVersion}" true="Ja" false="Nej"/></div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>Version:</strong></div>
            <div class="col-lg-10">${education.versionsNummer}</div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>Skapad/ändrad:</strong></div>
            <div class="col-lg-10"><g:formatDate boolean="${education.dateCreated}" format="yyyy-MM-dd HH:mm"/> / <g:formatDate boolean="${education.lastUpdated}" format="yyyy-MM-dd HH:mm"/></div>
        </div>
        <g:if test="${education.getGiltigFranPeriod()}">
            <div class="row mb-1">
                <div class="col-lg-2"><strong>Giltig från:</strong></div>
                <div class="col-lg-10">${education.getGiltigFranPeriod().kod}</div>
            </div>
        </g:if>
        <g:if test="${education.getUtbildningsTyp()}">
            <div class="row mb-1">
                <div class="col-lg-2"><strong>Utbildningstyp:</strong></div>
                <div class="col-lg-10">${education.getUtbildningsTyp().kod}</div>
            </div>
        </g:if>
        <g:if test="${education.getOrganisation()}">
            <div class="row mb-1">
                <div class="col-lg-2"><strong>Organisation:</strong></div>
                <div class="col-lg-10">${education.getOrganisation().benamningSv}</div>
            </div>
        </g:if>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>Andra versioner:</strong></div>
            <div class="col-lg-10">${otherVersions.size()} st</div>
        </div>
        <g:if test="${otherVersions}">
            <hr class="mb-1"/>
            <div class="row mb-1">
                <div class="col-lg-2"><strong>Version</strong></div>
                <div class="col-lg-2"><strong>Giltig från</strong></div>
                <div class="col-lg-2"><strong>Senaste version</strong></div>
            </div>
            <hr class="mb-1"/>
            <g:each in="${otherVersions}" var="otherVersion">
                <div class="row mb-1">
                    <div class="col-lg-2"><g:link action="showUtbildning" id="${otherVersion.id}">${otherVersion.versionsNummer}</g:link></div>
                    <div class="col-lg-2"><g:link action="showUtbildning" id="${otherVersion.id}">${otherVersion.getGiltigFranPeriod().kod}</g:link></div>
                    <div class="col-lg-2"><g:formatBoolean boolean="${otherVersion.senasteVersion}" true="Ja" false="Nej"/></div>
                </div>
                <hr class="mb-1"/>
            </g:each>
        </g:if>
        <g:if test="${education.getChildren()}">
            <hr/>
            <div class="row mb-1">
                <div class="col-lg-12"><strong>${education.getChildren().size()} inriktningar</strong></div>
            </div>
            <hr class="mb-1"/>
            <div class="row mb-1">
                <div class="col-lg-2"><strong>Kod</strong></div>
                <div class="col-lg-2"><strong>Giltig från</strong></div>
                <div class="col-lg-8"><strong>Benämning</strong></div>
            </div>
            <hr class="mb-1"/>
            <g:each in="${education.getChildren()}" var="child">
                <div class="row mb-1">
                    <div class="col-lg-2"><g:link action="showUtbildning" id="${child.id}">${child.utbildningsKod}</g:link></div>
                    <div class="col-lg-2"><g:link action="showUtbildning" id="${child.id}">${child.getGiltigFranPeriod().kod}</g:link></div>
                    <div class="col-lg-8">${child.benamningSv}</div>
                </div>
                <hr class="mb-1"/>
            </g:each>
        </g:if>
    </body>
</html>
