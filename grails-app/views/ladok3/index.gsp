<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Ladok3 Status</title>
        <meta name="layout" content="main">
    </head>
    <body>
        <h2>Status för Ladok3</h2>
        <hr class="mb-2"/>
        <span><strong>${edus.size()} lärosäten</strong></span>

        <g:if test="${edus}">
            <div class="row mb-1">
                <div class="col-lg-6"><strong>Lärosäte</strong></div>
                <div class="col-lg-2"><strong>L3Enabled</strong></div>
                <div class="col-lg-2"><strong>Defined</strong></div>
                <div class="col-lg-2"><strong>Expires</strong></div>
            </div>
            <hr class="mb-1"/>
            <g:each in="${edus}" var="edu">
                <div class="row mb-1">
                    <div class="col-lg-6">${edu.fullName} (${edu.toString()})</div>
                    <div class="col-lg-2"><g:formatBoolean boolean="${isEnabled.get(edu)}" false="Nej" true="Ja"/></div>
                    <div class="col-lg-2"><g:formatBoolean boolean="${isDefined.get(edu)}" false="Nej" true="Ja"/></div>
                    <div class="col-lg-2"><g:formatDate date="${expirationDates.get(edu)}" format="yyyy-MM-dd"/></div>
                </div>
                <hr class="mb-1"/>
            </g:each>
        </g:if>
        <div class="row mb-1">
            <div class="col-lg-12">
                <g:link action="listBevisBenamning">BevisBenämning</g:link>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-12">
                <g:link action="listFinandieringsForm">FinansieringsForm</g:link>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-12">
                <g:link action="listOrganization">Organisation</g:link>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-12">
                <g:link action="listPeriod">Period</g:link>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-12">
                <g:link action="listStudieLokalisering">StudieLokalisering</g:link>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-12">
                <g:link action="listStudieTakt">StudieTakt</g:link>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-12">
                <g:link action="listUndervisningsForm">UndervisningsForm</g:link>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-12">
                <g:link action="listUndervisningsTid">UndervisningsTid</g:link>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-12">
                <g:link action="listUtbildningsTyp">UtbildningsTyp</g:link>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-12">
                <g:link action="listUtbildning">Utbildningar</g:link>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-12">
                <g:link action="triggerFeedInitializeJob">Trigga FeedInitializeJob</g:link>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-12">
                <g:link action="triggerUpdateL3BasicsJob">Trigga UpdateL3BasicsJob</g:link>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-12">
                <g:link action="triggerUpdateL3Events4EduJob">Trigga UpdateL3Events4EduJob</g:link>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-12">
                <g:link action="triggerUpdateL3Kurs4EduJob">Trigga UpdateL3Kurs4EduJob</g:link>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col-lg-12">
                <g:link action="triggerUpdateL3Program4EduJob">Trigga UpdateL3Program4EduJob</g:link>
            </div>
        </div>
        <hr/>
        <g:each in="${stats}" var="statistics">
            <div class="row mb-1">
                <div class="col-lg-6">${statistics.clazz}</div>
                <div class="col-lg-3">${statistics.edu}</div>
                <div class="col-lg-3">${statistics.count} st</div>
            </div>
            <hr class="mb-1"/>
        </g:each>
    </body>
</html>
