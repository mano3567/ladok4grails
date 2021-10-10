<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Finansieringsform</title>
        <meta name="layout" content="main">
    </head>
    <body>
        <h2>FinansieringsForm L3</h2>
        <hr class="mb-2"/>
        <div class="row mb-3">
            <div class="col-lg-12"><strong>${items.size()} st</strong></div>
        </div>
        <hr/>
        <div class="row mb-1">
            <div class="col-lg-2"><strong>Kod</strong></div>
            <div class="col-lg-2"><strong>Lärosäte</strong></div>
            <div class="col-lg-8"><strong>Namn</strong></div>
        </div>
        <hr/>
        <g:each in="${items}" var="item">
            <div class="row">
                <div class="col-lg-2">${item.kod}</div>
                <div class="col-lg-2">${item.edu}</div>
                <div class="col-lg-8">${item.benamningSv}</div>
            </div>
            <hr class="mb-1"/>
        </g:each>
    </body>
</html>
