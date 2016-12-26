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
    <spring:url value="/resources/css/ui.jqgrid.css" var="jqgridcss"/>
    <link href="${jqgridcss} " rel="stylesheet">
    <spring:url value="/resources/css/layout.jqgrid.css" var="jqgridlayoutcss"/>
    <link href="${jqgridlayoutcss} " rel="stylesheet">
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
    <spring:url value="/resources/js/jquery.jqGrid.min.js" var="jqGrid"/>
    <script src="${jqGrid}" type="text/javascript"
            language="javascript"></script>
    <spring:url value="/resources/js/grid.locale-en.js" var="gridLocale"/>
    <script src="${gridLocale}" type="text/javascript"
            language="javascript"></script>
    <script>
        jQuery(document).ready(function () {
            $('#pdfReport').click(function () {
                jQuery("<form action='" + encodeURI("pdfreport.action?massCentre=" + $('select[name=massCentre]').val() +
                "&date=" + $(':radio[name=date]:checked').val())
                + "' method='post' accept-charset='utf-8'" + "/>")
                        .appendTo('body').submit().remove();
            });
        });
    </script>
</head>
<body>
<%@ include file="headerTemplate.jsp" %>
<div class="container">
    <ul class="nav nav-pills">
        <li><a href="showcounts.action">Show Counts</a></li>

        <c:if test="${currentUser.systemRole == 'ADMIN'}">
            <li><a href="adduser.action">Add Users</a></li>
        </c:if>

        <li><a href="checkinsearch.action">Check In</a></li>
        <li><a href="checkoutsearch.action">Check Out</a></li>
        <li><a href="registration.action">Registration</a></li>
        <li><a href="getEditParentEntryForm.action">Edit</a></li>
        <li><a href="searchviewentry.action">Search</a></li>
        <li class="active"><a href="reportpage.action">Report</a></li>
        <li><a href="logout.action">Logout</a></li>
    </ul>
</div>
<div class="mainWrapper">
    <div class="row row-offcanvas row-offcanvas-right">
        <div class="col-xs-12 col-sm-12">
            <div class="panel panel-default">
                <div class="panel-heading headerColor">Report</div>
                <div class="panel-body">
                    <form id="checkinsearch-form"
                          action="${pageContext.request.contextPath}/pdfreport.action"
                          method="post">
                        <div class="row generalFormLayout">
                            <div class="col-md-12">
                                <div class="col-md-4">
                                    <label for="massCentre">Mass Centre:</label>
                                    <select id='massCentre' name="massCentre">
                                        <option value="All" selected="true">All</option>
                                        <option value="Tallaght">Tallaght</option>
                                        <option value="Lucan">Lucan</option>
                                        <option value="Beaumont">Beaumont</option>
                                    </select>
                                </div>
                                <div class="col-md-4">
                                    <label>Select Date:</label>
                                    <input type="radio" name="date" value="all" checked> All<br>
                                    <input type="radio" name="date" value="27-12-2016">27-12-2016<br>
                                    <input type="radio" name="date" value="28-12-2016">28-12-2016<br>
                                    <input type="radio" name="date" value="29-12-2016">29-12-2016<br>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div style="margin-top: 30px;">
                                    <input type="submit" value="Generate Report" id="pdfReport"
                                           class="btn btn-primary commonGreenBtn"/>
                                </div>
                            </div>

                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
