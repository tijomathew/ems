<%--
  Created by IntelliJ IDEA.
  User: bibin
  Date: 16/10/16
  Time: 8:37 PM
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
            $('#successContainer').hide();
            $('#passwordMismatchContainer').hide();

            $('#changePasswordButton').click(function () {
                if ($('#confirmPassword').val() == '' || $('#newPassword').val() == '') {
                    alert('Please enter values in the boxes!!');
                    return true;
                } else {
                    var $form = $('#changePasswordForm');
                    var data = $('#changePasswordForm').serialize();

                    $.ajax({
                        type: $form.attr('method'),
                        url: $form.attr('action'),
                        data: data,
                        success: function (response) {
                            if (response == 'fail') {
                                $('#passwordMismatchContainer').show();
                            }
                            else if (response == 'success') {
                                $('#passwordMismatchContainer').hide();
                                $('#successContainer').show();
                            }

                        }
                    });
                }
            });

        });

    </script>

</head>
<body>
<%@ include file="headerTemplate.jsp" %>
<div style="width: 100%;height: 90%;">
    <div class="container" style="margin-top: 50px;">
        <form:form modelAttribute="changePasswordUser"
                   action="${pageContext.request.contextPath}/changepassword.action" id="changePasswordForm"
                   method="post">
            <div class="form-group">
                <label for="newPassword">New Password:</label>
                <form:password path="newPassword" class="form-control" id="newPassword" placeholder="New Password"/>
                <form:hidden path="email"/>
            </div>
            <div class="form-group">
                <label for="confirmPassword">Confirm Password:</label>
                <form:password path="confirmPassword" class="form-control" id="confirmPassword"
                               placeholder="Confirm Password"/>
            </div>
            <input class="btn btn-lg btn-success btn-block" type="button" value="Change Password"
                   id="changePasswordButton"/>
        </form:form>
        <div style="font-size: 15px;text-align: center;color: #a94442;padding: 1px;margin: 8px auto;display:block"
             class="alert alert-danger" id="passwordMismatchContainer">Passwords are mismatching.
        </div>
        <div class="alert alert-success" role="alert" id="successContainer">
            Password is updated successfully.<br/>
            Please <a href="login.action"> re-login</a> to continue the system use.
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
