<%--
  Created by IntelliJ IDEA.
  User: grzesiek
  Date: 08.10.2017
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<jsp:include page="shared/header.jsp">
    <jsp:param name="pageName" value="home"/>
</jsp:include>

<html>
<head>
    <title>HOME</title>
</head>


<body>
<main class="grey darken-4">
    <center>
        <div class="section"></div>
        <div class="section"></div>
        <div class="container">
            <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                    <span class="card-title">Informacja</span>
                    <p>To jest moja Strona domowa</p>
                    <p class="lead">${message} (<= ta wiadomość jest wynikiem realizacji zadania 2)</p>
                    <br><br><br><br><br><br>
                </div>
                <div class="card-action">
                    <a href="vehicle/list">Lista komponentów JB wyświetlonych z użyciem JSTL (Zadanie 4)</a>
                </div>
            </div>
        </div>
    </center>
    <div class="section"></div>
    <div class="section"></div>
</main>
</body>
</html>

<jsp:include page="shared/footer.jsp"/>