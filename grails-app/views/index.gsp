<!DOCTYPE html>
<html lang="sv">
    <head>
        <meta name="layout" content="main"/>

        <meta charset="utf-8">
        <title>Ladok for Grails</title>
        <link href="https://media.metricspace.se/favicon.jpg" rel="icon" type="image/jpeg" />
    </head>
    <body>
        <div class="container">
            <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                <div class="row mb-1">
                    <div class="col-lg-12">
                        <g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link>
                    </div>
                </div>
            </g:each>
            <div class="row mb-1">
                <div class="col-lg-2">Environment:</div>
                <div class="col-lg-10">${grails.util.Environment.current.name}</div>
            </div>
            <div class="row mb-1">
                <div class="col-lg-2">App profile:</div>
                <div class="col-lg-10">${grailsApplication.config.grails?.profile}</div>
            </div>
            <div class="row mb-1">
                <div class="col-lg-2">App version:</div>
                <div class="col-lg-10"><g:meta name="info.app.version"/></div>
            </div>
            <div class="row mb-1">
                <div class="col-lg-2">Controllers:</div>
                <div class="col-lg-10">${grailsApplication.controllerClasses.size()}</div>
            </div>
            <div class="row mb-1">
                <div class="col-lg-2">Domains:</div>
                <div class="col-lg-10">${grailsApplication.domainClasses.size()}</div>
            </div>
            <div class="row mb-1">
                <div class="col-lg-2">Services:</div>
                <div class="col-lg-10">${grailsApplication.serviceClasses.size()}</div>
            </div>
            <div class="row mb-1">
                <div class="col-lg-2">Taglibs:</div>
                <div class="col-lg-10">${grailsApplication.tagLibClasses.size()}</div>
            </div>
            <g:each var="plugin" in="${applicationContext.getBean('pluginManager').allPlugins.sort{it.name}}">
                <div class="row mb-1">
                    <div class="col-lg-12">${plugin.name} : ${plugin.version}</div>
                </div>
            </g:each>
        </div>
    </body>
</html>
