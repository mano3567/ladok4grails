<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Trigga Job</title>
        <meta name="layout" content="main">
    </head>
    <body>
        <h2>Trigga UpdateL3Kurs4EduJob</h2>
        <hr class="mb-2"/>
        <g:form name="triggerForm" action="triggerUpdateL3Kurs4EduJob" method="POST">
            <div class="row mb-1">
                <div class="col-lg-2"><label for="edu">Lärosäte</label></div>
                <div class="col-lg-10">
                    <g:select name="edu" from="${edus}" value="${edu?.name}" optionKey="name" optionValue="fullName" noSelection="['':'Välj lärosäte']" class="form-control"/>
                </div>
            </div>
            <div class="row mb-1">
                <div class="col-lg-2"></div>
                <div class="col-lg-10">
                    <g:submitButton name="trigger" value="Trigga job" class="btn btn-primary btn-default float-end"/>
                </div>
            </div>
        </g:form>
    </body>
</html>
