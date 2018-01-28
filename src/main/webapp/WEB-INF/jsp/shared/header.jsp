<%--
  Created by IntelliJ IDEA.
  User: grzesiek
  Date: 14.10.2017
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<html>
<head>

    <!-- this is header-css -->
    <link rel="stylesheet" type="text/css"
          href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css"
          href="statics/css/main.css" />

</head>
<body>
<div>
    <!-- this is header -->
    <nav class="navbar navbar-inverse">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="/" ${param['pageName'] == 'home' ?'class="active"':''}>Strona główna</a>
            </div>
            <div id="navbar" class="collapse navbar-collapse ">
                <ul class="nav navbar-nav navbar-left">
                    <li ${param['pageName'] == 'vehicleListPage' ?'class="active"':''}><a href="vehicleList.html?all">Lista pojazdów</a></li>
                     <li ${param['pageName'] == 'custom' ?'class="active"':''}><a href="custom.html">Strona 4</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">

                    <security:authorize access="isAnonymous()">
                        <li ${param['pageName'] == 'registrationForm' ?'class="active"':''}>
                            <a href="/registrationForm.html">Zarejestruj</a>

                        </li>
                    </security:authorize>



                    <security:authorize access="isAnonymous()">
                        <li ${param['pageName'] == 'logonForm' ?'class="active"':''}>
                            <a href="/login">Zaloguj się</a>
                        </li>
                    </security:authorize>
                    <security:authorize access="isAuthenticated()">
                        <li>
                            <label style="color:yellow; margin-top: 15px;">
                                Witaj <security:authentication property="principal.username"/>!
                            </label>
                        </li>
                        <li>
                            <a href="#" onclick="document.getElementById('logout').submit()">Wyloguj się</a>
                            <form action="/logout" id="logout" method="post" style="display: none;">
                                <security:csrfInput/>
                            </form>
                        </li>
                    </security:authorize>
                </ul>

            </div>
        </div>
    </nav>
</div>
</body>
</html>