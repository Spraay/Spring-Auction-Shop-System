<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


<jsp:include page="shared/header.jsp">
    <jsp:param name="pageName" value="registrationForm"/>
</jsp:include>
<html>
<body>

<div class="container">

    <div class="row" style="margin-top:20px">
        <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
            <%--@elvariable id="userCommand" type="krystianrytel.sklepaukcyjny.models.User"--%>
            <form:form modelAttribute="userCommand">
                <fieldset>
                    <h1>Proszę, zarejestruj się</h1>
                    <div class="form-group">
                    <form:errors path="*" cssClass="alert alert-danger">

                    </form:errors>
                    </div>

                    <div class="form-group">
                        <form:input path="username" cssClass="form-control" placeholder="nazwa użytkownika" required="true" autofocus="true" cssErrorClass="form-control is-invalid"/>
                    </div>

                    <div class="form-group">
                        <form:password path="password" cssClass="form-control"
                               placeholder="hasło" required="true" cssErrorClass="form-control is-invalid"/>
                    </div>

                    <div class="form-group">
                        <form:password path="passwordConfirm" cssClass="form-control" cssErrorClass="form-control is-invalid"
                               placeholder="powtórzone hasło" required="true"/>
                    </div>

                    <div class="row">
                        <div class="col-xs-6 col-sm-6 col-md-6">
                            <input type="submit" class="btn btn-lg btn-primary btn-block" value="Zarejestruj"/>
                        </div>
                        <div class="col-xs-6 col-sm-6 col-md-6">
                        </div>
                    </div>
                </fieldset>
            </form:form>
        </div>
    </div>

</div>
</body>
</html>
<jsp:include page="shared/footer.jsp"></jsp:include>