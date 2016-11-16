<%--
  Created by IntelliJ IDEA.
  User: bibin
  Date: 22/10/16
  Time: 11:33 AM
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
<p class="alert alert-info fade in text-center" style="margin-top:30px;">
    <strong>An E-mail entered is not registered with our system. Please enter the registered Email-ID</strong><br>
    <a href="email.action"> Go Back</a>
</p>
<%@include file="footer.jsp" %>
</body>
</html>
