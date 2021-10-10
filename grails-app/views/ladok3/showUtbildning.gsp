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
            <div class="col-lg-10">${education.utbildningsKod}</div>
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
        <div class="row mb-1">
            <div class="col-lg-2"><strong>Omfattning:</strong></div>
            <div class="col-lg-10">${education.omfattningsVarde}</div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>Avvecklad:</strong></div>
            <div class="col-lg-10"><g:formatBoolean boolean="${education.avvecklad}" true="Ja" false="Nej"/></div>
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
    </body>
</html>
