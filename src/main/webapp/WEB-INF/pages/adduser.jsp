<%--
  Created by IntelliJ IDEA.
  User: tijo
  Date: 19/10/16
  Time: 4:54 PM
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
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>

    <script type="text/javascript">

        google.load("visualization", "1", {packages: ["table"]});

        function runUsersDisplayQuery(contextPath) {

            var opts = {sendMethod: 'auto'};
            var url = "/viewusers.action";

            var query = new google.visualization.Query(contextPath + url + '?tqx=reqId:1', opts);

            // Send the query with a callback function.
            query.send(handleUsersDisplayQueryResponse);


        }

        function handleUsersDisplayQueryResponse(response) {
            if (response.isError()) {
                alert('Error in query: ' + response.getMessage() + ' ' + response.getDetailedMessage());
                return;
            }
            var data = response.getDataTable();
            var table = new google.visualization.Table(document.getElementById('table_div'));

            var cssClassNames = {
                'headerRow': 'italic-darkblue-font large-font bold-font',
                'tableRow': '',
                'oddTableRow': 'beige-background',
                'selectedTableRow': '',
                'hoverTableRow': '',
                'headerCell': 'gold-border',
                'tableCell': '',
                'rowNumberCell': ''
            };

            var options = {
                allowHtml: true, showRowNumber: false,
                width: 'auto',
                height: 'auto',
                alternatingRowStyle: true,
                cssClassNames: cssClassNames
            };
            google.visualization.events.addListener(table, 'ready', function () {
                $(".google-visualization-table-table").attr('class', 'table');
                $("table").addClass('table table-striped table-bordered table-condensed"');
            });

            table.draw(data, options);
        }

        $(document).ready(function () {

            runUsersDisplayQuery("${pageContext.request.contextPath}");

            $('#addUserButton').click(function () {
                $("#addUserButton").html('Loading');
                var $form = $('#userForm');
                var data = $('#userForm').serialize();

                $.ajax({
                    type: $form.attr('method'),
                    url: $form.attr('action'),
                    data: data,
                    success: function (response) {
                        if (response == 'success') {
                            $('#passwordSendFailure').hide();
                            $('#userFailureContainer').hide();
                            $('#successContainer').show();
                            $("#addUserButton").html('Create User');
                            runUsersDisplayQuery("${pageContext.request.contextPath}");
                        }
                        else if (response == 'mailfail') {
                            $('#successContainer').hide();
                            $('#userFailureContainer').hide();
                            $('#passwordSendFailure').show();
                            $("#addUserButton").html('Create User');
                        } else if (response == 'fail') {
                            $('#successContainer').hide();
                            $('#passwordSendFailure').hide();
                            $('#userFailureContainer').show();
                            $("#addUserButton").html('Create User');
                        }

                    }
                });
            });
        });

    </script>
</head>
<body>
<%@ include file="headerTemplate.jsp" %>
<div style="width: 100%;height: 90%;">
    <div class="container">
        <ul class="nav nav-pills">
            <li><a href="showcounts.action">Show Counts</a></li>
            <li class="active"><a href="adduser.action">Add Users</a></li>
            <li><a href="checkinsearch.action">Check In</a></li>
            <li><a href="checkoutsearch.action">Check Out</a></li>
            <li><a href="registration.action">Registration</a></li>
            <li><a href="getEditParentEntryForm.action">Edit</a></li>
            <li><a href="searchviewentry.action">Search</a></li>
            <li><a href="reportpage.action">Report</a></li>
            <li><a href="logout.action">Logout</a></li>
        </ul>
    </div>
    <div class="container">
        <form:form modelAttribute="newUser"
                   action="${pageContext.request.contextPath}/addnewuser.action"
                   method="post" id="userForm">
            <div class="form-group">
                <label for="systemRole">System Role:</label>
                <form:select path="systemRole" id="systemRole" class="form-control">
                    <form:option value="ORGANIZER">Organizer</form:option>
                </form:select>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <form:input path="email" id="email" class="form-control"/>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <form:password path="password" id="password" class="form-control"/>
            </div>
            <button class="btn btn-lg btn-success btn-block" type="button" id="addUserButton">Create User</button>
        </form:form>
    </div>

    <div style="font-size: 15px;text-align: center;color: #a94442;padding: 1px;margin: 8px auto;display:none"
         class="alert alert-danger" id="passwordSendFailure">Password did not send by mail. Please send it
        manually!!
    </div>
    <div class="alert alert-danger" role="alert" id="userFailureContainer" style="display:none">
        User cannot be created as it exists in the system!! Please use another mail-ID!!<br/>
    </div>
    <div class="alert alert-success" role="alert" id="successContainer" style="display:none">
        User is created successfully!!<br/>
    </div>
    <div id="table_div" style="float:right;width: 85%"></div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
