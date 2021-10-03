<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Ladok3 Status</title>
        <meta name="layout" content="main">
        <asset:javascript src="settings/list.js"/>
    </head>
    <body>
        <h2>Status för Ladok3</h2>
        <hr class="mb-2"/>
        <span><strong>${edus.size()} lärosäten</strong></span>

        <g:if test="${edus}">
            <div class="row mb-1">
                <div class="col-lg-2"><strong>Lärosäte</strong></div>
                <div class="col-lg-2"><strong>L3Enabled</strong></div>
                <div class="col-lg-2"><strong>Defined</strong></div>
                <div class="col-lg-2"><strong>Expires</strong></div>
                <div class="col-lg-2"><strong>FeedId</strong></div>
                <div class="col-lg-2"><strong>Updated</strong></div>
            </div>
            <hr class="mb-1"/>
            <g:each in="${edus}" var="edu">
                <div class="row mb-1">
                    <div class="col-lg-2">${edu.fullName}</div>
                    <div class="col-lg-2"><g:formatBoolean boolean="${isEnabled.get(edu)}" false="Nej" true="Ja"/></div>
                    <div class="col-lg-2"><g:formatBoolean boolean="${isDefined.get(edu)}" false="Nej" true="Ja"/></div>
                    <div class="col-lg-2"><g:formatDate date="${expirationDates.get(edu)}" format="yyyy-MM-dd"/></div>
                    <div class="col-lg-2">${lastFeed.get(edu)?.getParsedFeedId()}</div>
                    <div class="col-lg-2">${lastFeed.get(edu)?.itsLastUpdated}</div>
                </div>
                <hr class="mb-1"/>
            </g:each>
        </g:if>
    </body>
</html>
