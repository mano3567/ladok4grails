<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Inställningar</title>
        <meta name="layout" content="main">
        <asset:javascript src="settings/list.js"/>
    </head>
    <body>
        <h2>Inställningar</h2>
        <hr class="mb-2"/>
        <span><strong>${configValues.size()} värden</strong></span>

        <g:if test="${configValues}">
            <div class="row mb-1">
                <div class="col-lg-3"><strong>Nyckel</strong></div>
                <div class="col-lg-6"><strong>Värde</strong></div>
                <div class="col-lg-1"><strong>Typ</strong></div>
                <div class="col-lg-2"><strong>Ändrad</strong></div>
            </div>
            <hr class="mb-1"/>
            <g:each in="${configValues}" var="configValue">
                <div class="row mb-1">
                    <div class="col-lg-3">${configValue.name}</div>
                    <div class="col-lg-6">
                        <g:textField name="value_${configValue.id}" value="${configValue.value}" class="form-control" data-config-value-id="${configValue.id}"/>
                    </div>
                    <div class="col-lg-1">${configValue.valueType}</div>
                    <div class="col-lg-2" id="updated_${configValue.id}"><g:render template="updated" model="[someDate: configValue.lastUpdated]"/></div>
                </div>
                <hr class="mb-1"/>
            </g:each>
        </g:if>
    </body>
</html>
