<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>UtbildningTillfälle</title>
        <meta name="layout" content="main">
    </head>
    <body>
        <h2>UtbildningTillfälle ${educationEvent.getUtbildning().utbildningsKod} / ${educationEvent.utbildningsTillfallesKod} (${educationEvent.edu})</h2>
        <hr class="mb-1"/>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>Lärosäte:</strong></div>
            <div class="col-lg-10">${educationEvent.edu.fullName}</div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>Utbildningskod:</strong></div>
            <div class="col-lg-10">
                <g:link action="showUtbildning" id="${educationEvent.getUtbildning().id}">${educationEvent.getUtbildning().utbildningsKod}</g:link> / ${educationEvent.utbildningsTillfallesKod}
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>OmfattningsVarde:</strong></div>
            <div class="col-lg-10">
                ${educationEvent.omfattningsVarde} hp
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>Från - till</strong></div>
            <div class="col-lg-10"><g:formatDate date="${educationEvent.startDatum}" format="yyyy-MM-dd"/> - <g:formatDate date="${educationEvent.slutDatum}" format="yyyy-MM-dd"/></div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>Inställt:</strong></div>
            <div class="col-lg-10">
                <g:formatBoolean boolean="${educationEvent.installt}" true="Ja" false="Nej"/>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>Utannonserat:</strong></div>
            <div class="col-lg-10">
                <g:formatBoolean boolean="${educationEvent.utannonserat}" true="Ja" false="Nej"/>
            </div>
        </div>
        <g:if test="${educationEvent.getFinansieringsForm()}">
            <div class="row mb-1">
                <div class="col-lg-2"><strong>FinansieringsForm:</strong></div>
                <div class="col-lg-10">
                    ${educationEvent.getFinansieringsForm().kod}
                </div>
            </div>
        </g:if>
        <g:if test="${educationEvent.getOrganisation()}">
            <div class="row mb-1">
                <div class="col-lg-2"><strong>Organisation:</strong></div>
                <div class="col-lg-10">
                    ${educationEvent.getOrganisation().benamningSv}
                </div>
            </div>
        </g:if>
        <g:if test="${educationEvent.getStartPeriod()}">
            <div class="row mb-1">
                <div class="col-lg-2"><strong>StartPeriod:</strong></div>
                <div class="col-lg-10">
                    ${educationEvent.getStartPeriod().kod}
                </div>
            </div>
        </g:if>
        <g:if test="${educationEvent.gertStudieLokalisering()}">
            <div class="row mb-1">
                <div class="col-lg-2"><strong>StudieLokalisering:</strong></div>
                <div class="col-lg-10">
                    ${educationEvent.gertStudieLokalisering().kod}
                </div>
            </div>
        </g:if>
        <g:if test="${educationEvent.getSTudieTakt()}">
            <div class="row mb-1">
                <div class="col-lg-2"><strong>Studietakt:</strong></div>
                <div class="col-lg-10">
                    ${educationEvent.getSTudieTakt().kod}
                </div>
            </div>
        </g:if>
        <g:if test="${educationEvent.getUndervisningsTid()}">
            <div class="row mb-1">
                <div class="col-lg-2"><strong>Undervisningstid:</strong></div>
                <div class="col-lg-10">
                    ${educationEvent.getUndervisningsTid().kod}
                </div>
            </div>
        </g:if>
        <g:if test="${educationEvent.getUtbildningsTyp()}">
            <div class="row mb-1">
                <div class="col-lg-2"><strong>UtbildningsTyp:</strong></div>
                <div class="col-lg-10">
                    ${educationEvent.getUtbildningsTyp().kod}
                </div>
            </div>
        </g:if>
        <g:if test="${educationEvent.getUtbildningsTillfallePerioder()}">
            <div class="row mb-1">
                <div class="col-lg-3"><strong>Period</strong></div>
                <div class="col-lg-3"><strong>Startdatum</strong></div>
                <div class="col-lg-3"><strong>Slutdatum</strong></div>
                <div class="col-lg-3"><strong>Poäng</strong></div>
            </div>
            <g:each in="${educationEvent.getUtbildningsTillfallePerioder()}" var="eventPeriod">
                <div class="row mb-1">
                    <div class="col-lg-3">${eventPeriod.period.kod}</div>
                    <div class="col-lg-3"><g:formatDate date="${eventPeriod.forstaUndervisningsDatum}" format="yyyy-MM-dd"/></div>
                    <div class="col-lg-3"><g:formatDate date="${eventPeriod.sistaUndervisningsDatum}" format="yyyy-MM-dd"/></div>
                    <div class="col-lg-3">${eventPeriod.omfattningsVarde}</div>
                </div>
            </g:each>
        </g:if>
    </body>
</html>
