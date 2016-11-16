<%--
  Created by IntelliJ IDEA.
  User: bibin
  Date: 13/10/16
  Time: 11:22 AM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="tagLibraryTemplate.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Event Manager</title>

    <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapcss"/>
    <link href="${bootstrapcss} " rel="stylesheet">

    <spring:url value="/resources/css/style.css" var="stylecss"/>

    <link href="${stylecss} " rel="stylesheet">

    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>

</head>
<body>
<%@ include file="headerTemplate.jsp" %>

<div style="width: 100%;height: 90%;">

    <div class="container" style="margin-top: 50px;">
        <form:form modelAttribute="loginUser" id="login-form"
                   action="${pageContext.request.contextPath}/loggedin.action"
                   method="post">
            <div class="form-group">
                <label for="email">Username:</label>
                <form:input path="email" id="email" class="form-control" required="true" placeholder="email"/>
            </div>
            <div class="form-group">
                <label for="email">Password:</label>
                <form:password path="password" id="pwd" class="form-control" placeholder="Password" required="true"/>
            </div>
            <div class="form-group">
                <form:errors class="alert alert-danger" role="alert" id="loginErrorDisplay"></form:errors>
                <c:if test="${not empty showURLAccessDenied}">
                    <span class="alert alert-danger accessDenied" id="customErrorSpan">${showURLAccessDenied}</span>
                </c:if>
            </div>
            <input class="btn btn-lg btn-success btn-block" type="submit" id="addUserButton" value="Log in"/>
        </form:form>
    </div>
</div>

<%@include file="footer.jsp" %>
</body>
</html>
