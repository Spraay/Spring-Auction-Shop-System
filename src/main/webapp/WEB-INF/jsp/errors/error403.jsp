<%--
Created by IntelliJ IDEA.
User: grzesiek
Date: 03.10.2017
Time: 19:24
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../shared/header.jsp"></jsp:include>
<html>
<title>
    Odmowa dostępu
</title>
<body>
<div id="main" class="container">

    <div>
        <h1>403 - Odmowa dostępu</h1>
        <div>Witaj '<security:authentication property="principal.username"/>',
            nie masz odpowiednich praw dostępu do żądanej strony.
        </div>
    </div>

</div>
</body>
</html>
<jsp:include page="../shared/footer.jsp"></jsp:include>
