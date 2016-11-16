<%--
  Created by IntelliJ IDEA.
  User: bibin
  Date: 13/10/16
  Time: 11:35 AM
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

        function runCountQuery(contextPath, type) {

            var opts = {sendMethod: 'auto'};
            var url = "/viewcounts.action";

            var query = new google.visualization.Query(contextPath + url + '?tqx=reqId:1&type=' + type, opts);

            // Send the query with a callback function.
            query.send(handleCountsQueryResponse);


        }

        function handleCountsQueryResponse(response) {
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
            runCountQuery("${pageContext.request.contextPath}", "All");

            $('#countsButton').click(function () {
                $("#countsButton").html('Loading');
                var day = $('input[name=day]:checked', '#countsForm').val();
                var type = $('select[name=type]').val();
                runCountQuery("${pageContext.request.contextPath}", type);
            });
        });
    </script>

</head>
<body>
<%@ include file="headerTemplate.jsp" %>
<div style="width: 100%;height: 90%;">
    <div class="container">
        <ul class="nav nav-pills">
            <li class="active"><a href="showcounts.action">Show Counts</a></li>
            <c:if test="${currentUser.systemRole == 'ADMIN'}">
                <li><a href="adduser.action">Add Users</a></li>
            </c:if>
            <li><a href="checkinsearch.action">Check In</a></li>
            <li><a href="checkoutsearch.action">Check Out</a></li>
            <li><a href="registration.action">Registration</a></li>
            <li><a href="getEditParentEntryForm.action">Edit</a></li>
            <li><a href="searchviewentry.action">Search</a></li>
            <li><a href="reportpage.action">Report</a></li>
            <li><a href="logout.action">Logout</a></li>
        </ul>
    </div>
    <div class="mainWrapper">
        <div class="row row-offcanvas row-offcanvas-right">
            <div class="col-xs-12 col-sm-12">
                <div class="panel panel-default">
                    <div class="panel-heading headerColor">Counts</div>
                    <div class="panel-body">
                        <form id="countsForm" class="form-inline">
                            <div class="form-group"><select name="type" id="type" class="form-control">
                                <option value="All">Registered</option>
                                <option value="checkin">Check-In</option>
                                <option value="checkout">Check-Out</option>
                            </select></div>
                            <input type="button" id="countsButton" value="Show Counts"
                                   class="btn btn-primary commonGreenBtn"/>
                        </form>
                        <div id="table_div" style="float:right;width: 85%"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="footer.jsp" %>

</body>
</html>
