<!DOCTYPE html>
<html lang="sv">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <title>
            <g:layoutTitle default="Ladok"/>
        </title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Metric Space</title>
        <link href="https://media.metricspace.se/favicon.jpg" rel="icon" type="image/jpeg" />
        <asset:stylesheet src="application.css"/>
        <asset:javascript src="application.js"/>
        <g:layoutHead/>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="/">Ladok for Grails</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNavDropdown">
                    <ul class="navbar-nav">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLinkLocal" role="button" data-bs-toggle="dropdown" aria-expanded="false">Inom system</a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLinkLocal">
                                <li><g:link controller="settings" action="list" class="dropdown-item">Settings</g:link></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">Lokala grannar</a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                <li><a class="dropdown-item" href=https://www.su.se/" title="su">SU</a></li>
                                <li><a class="dropdown-item" href=https://vfu.su.se/" title="vfu">VFU</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLinkOther" role="button" data-bs-toggle="dropdown" aria-expanded="false">Andra länkar</a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLinkOther">
                                <li><a class="dropdown-item" href="https://www.student.ladok.se/" title="ladok">Ladok för studenter</a></li>
                                <li><a class="dropdown-item" href="https://www.start.ladok.se/" title="ladok">Ladok för anställda</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="https://www.antagning.se/" title="ge antagning">Antagning</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container">
            <g:layoutBody/>
        </div>
    </body>
</html>
