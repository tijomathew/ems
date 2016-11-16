<%--
  Created by IntelliJ IDEA.
  User: cufa-01
  Date: 19/10/16
  Time: 5:05 PM
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
            $('#email-submit').click(function () {
                $("#email-submit").val('Sending Password');
                $("#email-form").submit();
            });
        });
    </script>
</head>


<body>
<%@ include file="headerTemplate.jsp" %>
<div class="mainWrapper">
    <div class="row row-offcanvas row-offcanvas-right">
        <div class="col-xs-12 col-sm-12">
            <div class="panel panel-default">
                <div class="panel-heading headerColor">Enter Email</div>
                <div class="panel-body">
                    <form:form role="form" id="email-form"
                               action="${pageContext.request.contextPath}/getEmail.action"
                               method="post" modelAttribute="loginUser">
                        <div class="row generalFormLayout">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="registeredEmail">Registered Email:<span
                                            style="color: red">*</span></label>
                                    <form:input path="email" id="registeredEmail" class="form-control" required="true"
                                                placeholder="Registered Email"/>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <input type="submit" value="Submit" id="email-submit"
                                               class="btn btn-primary commonGreenBtn"/>
                                    </div>
                                </div>
                            </div>

                        </div>

                    </form:form>
                </div>
            </div>


        </div>
    </div>
</div>

<%@include file="footer.jsp" %>

</body>
</html>
