<%--
  Created by IntelliJ IDEA.
  User: grzesiek
  Date: 02.12.2017
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<title>
    Nieznany błąd
</title>
<body>
<div id="main" class="container">

    <div>
        <h1>Nieznany błąd</h1>
        <div>
            URL żądania: ${url}<br/>
            Wyjątek: ${exception.message}
            <!--
                Wyjątek ten będzie widoczny w źródle strony, ale nie dla użytkownika
            <c:forEach items="${exception.stackTrace}" var="st">
                ${st}
            </c:forEach>
            -->
        </div>
    </div>

</div>
</body>
</html>
