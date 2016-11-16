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
    <script type="text/javascript">
        jQuery(document).ready(function () {
            $('#passwordForm').click(function () {
                $("#password-form").submit();
            });
        });

    </script>
</head>
<body>
<%@ include file="headerTemplate.jsp" %>
<p class="alert alert-info fade in text-center" style="margin-top:30px;">
    <strong>Password is mismatching!!</strong><br>
    <a href="#" id="passwordForm"> Go Back</a>
</p>
<form:form role="form" id="password-form"
           action="${pageContext.request.contextPath}/wrongpassword.action"
           method="post" modelAttribute="loginUser">
    <form:hidden path="email"/>
    <form:hidden path="password"/>


    </div>

</form:form>
<%@include file="footer.jsp" %>
</body>
</html>
