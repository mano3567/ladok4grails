<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Ladok3 Status</title>
        <meta name="layout" content="main">
    </head>
    <body>
        <h2>Utbildningar</h2>
        <hr class="mb-2"/>
        <g:form name="educationForm" action="listUtbildning" method="POST">
            <div class="row mb-1">
                <div class="col-lg-2"><label for="edu">Lärosäte:</label></div>
                <div class="col-lg-10">
                    <g:select name="edu" from="${edus}" value="${edu?.name}" optionKey="name" optionValue="fullName" noSelection="['':'Välj lärosäte']" class="form-control"/>
                </div>
            </div>
            <div class="row mb-1">
                <div class="col-lg-2"><label for="educationType">Typ:</label></div>
                <div class="col-lg-10">
                    <g:select name="educationType" from="${educationTypes}" value="${educationType}" optionKey="id" optionValue="name" class="form-control"/>
                </div>
            </div>
            <div class="row mb-1">
                <div class="col-lg-2"><label for="latestVersion">Senaste version:</label></div>
                <div class="col-lg-10">
                    <g:select name="latestVersion" from="${latestVersions}" optionKey="id" optionValue="name" value="${latestVersion}" class="form-control"/>
                </div>
            </div>
            <div class="row mb-1">
                <div class="col-lg-2"><label for="searchFor">Sök efter:</label></div>
                <div class="col-lg-10">
                    <g:textField name="searchFor" value="${searchFor}" placeholder="Kod eller namn" class="form-control"/>
                </div>
            </div>
            <div class="row mb-1">
                <div class="col-lg-2"></div>
                <div class="col-lg-10"><g:submitButton name="find" value="Sök ..." class="btn btn-primary btn-default float-end"/></div>
            </div>
        </g:form>
        <g:if test="${params.find}">
            <hr class="mb-1"/>
            <div class="row mb-1">
                <div class="col-lg-12"><strong>${educations.size()} träffar</strong></div>
            </div>
            <g:if test="${educations}">
                <div class="row mb-1">
                    <div class="col-lg-2"><strong>Utbildningskod</strong></div>
                    <div class="col-lg-2"><strong>Lärosäte</strong></div>
                    <div class="col-lg-2"><strong>Typ</strong></div>
                    <div class="col-lg-6"><strong>Namn</strong></div>
                </div>
                <g:each in="${educations}" var="education">
                    <div class="row mb-1">
                        <div class="col-lg-2"><g:link action="showUtbildning" id="${education.id}">${education.utbildningsKod}</g:link></div>
                        <div class="col-lg-2">${education.edu}</div>
                        <div class="col-lg-2">${education.getUtbildningsTyp()?.kod}</div>
                        <div class="col-lg-6">${education.benamningSv}</div>
                    </div>
                </g:each>
            </g:if>
        </g:if>
    </body>
</html>
