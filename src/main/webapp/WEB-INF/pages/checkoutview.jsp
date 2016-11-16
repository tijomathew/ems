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
                $("#email-submit").val('Checking-Out');
                $("#email-form").submit();
            });
        });
    </script>
</head>

<jsp:useBean id="date" class="java.util.Date"/>
<fmt:formatDate value="${date}" pattern="MMM-dd" var="currentYear"/>

<body>
<%@ include file="headerTemplate.jsp" %>
<div style="width: 100%;height: 90%;">
    <div class="container">
        <ul class="nav nav-pills">
            <li><a href="showcounts.action">Show Counts</a></li>
            <c:if test="${currentUser.systemRole == 'ADMIN'}">
                <li><a href="adduser.action">Add Users</a></li>
            </c:if>
            <li><a href="checkinsearch.action">Check In</a></li>
            <li class="active"><a href="checkoutsearch.action">Check Out</a></li>
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
                    <div class="panel-heading headerColor">Update Check-Out</div>
                    <div class="panel-body">
                        <div class="col-md-12">
                            <label>Mass Centre
                                Name: </label>     <c:if
                                test="${not empty checkInParent.massCentreName}">${checkInParent.massCentreName}</c:if> </br>
                            <label>Parent/Guardian's
                                Name: </label>     <c:if
                                test="${not empty checkInParent.firstName}">${checkInParent.firstName}</c:if><c:if
                                test="${not empty checkInParent.lastName}"> ${checkInParent.lastName}</c:if></br>
                            <label>Phone - 1: </label>   <c:if test="${not empty checkInParent.phoneNumber}">${checkInParent.phoneNumber}</c:if></br>
                            <label>Phone -2: </label>  <c:if test="${not empty checkInParent.alternativePhoneNumber}"> ${checkInParent.alternativePhoneNumber}</c:if></br>
                            <label>Email: </label>   <c:if
                                test="${not empty checkInParent.email}">${checkInParent.email}</c:if></br>
                            <label>Address:</label>
                            <c:if test="${not empty checkInParent.houseNo}"> ${checkInParent.houseNo}</c:if>
                            <c:if
                                    test="${not empty checkInParent.addressLineOne}"> ${checkInParent.addressLineOne}</c:if>
                            <c:if
                                    test="${not empty checkInParent.addressLineTwo}"> ${checkInParent.addressLineTwo}</c:if>
                            <c:if
                                    test="${not empty checkInParent.addressLineThree}"> ${checkInParent.addressLineThree}</c:if>
                        </div>
                        <form:form role="form" id="checkoutsearch-form"
                                   action="${pageContext.request.contextPath}/checkoutupdate.action"
                                   method="post" modelAttribute="checkOutViewParent">
                            <div class="row generalFormLayout">
                                <form:hidden path="id" value="${checkOutParent.id}"/>
                                <c:forEach items="${checkOutParent.studentNodeList}" var="element" varStatus="count">
                                    <form:hidden path="studentNodeList[${count.index}].id" value="${element.id}"/>
                                    <div class="col-md-12">
                                        <div class="col-md-3">
                                            <label for="firstName">Child's First Name:</label>
                                            <form:input path="studentNodeList[${count.index}].firstName" id="firstName"
                                                        class="form-control"
                                                        value="${element.firstName}" readonly="true"/>
                                        </div>
                                        <div class="col-md-3">
                                            <label for="lastName">Child's Last Name:</label>
                                            <form:input path="studentNodeList[${count.index}].lastName" id="lastName"
                                                        class="form-control"
                                                        value="${element.lastName}" readonly="true"/>
                                        </div>
                                        <div class="col-md-2">
                                            <label>Section:</label>
                                            <form:input path="studentNodeList[${count.index}].retreatSection" id="id"
                                                        class="form-control"
                                                        value="${element.retreatSection}" readonly="true"/>
                                        </div>
                                        <div class="col-md-2">
                                            <label for="bandCode">Band Code:</label>
                                            <c:if test="${not empty element.retreatSection}">
                                                <c:if test="${element.retreatSection == 'Junior'}">
                                                    <form:input path="studentNodeList[${count.index}].bandCode"
                                                                id="bandCode"
                                                                class="form-control"
                                                                value="${element.bandCode}" readonly="true"
                                                                cssStyle="background-color: green;color: white"/>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${not empty element.retreatSection}">
                                                <c:if test="${element.retreatSection == 'Senior'}">
                                                    <form:input path="studentNodeList[${count.index}].bandCode"
                                                                id="bandCode"
                                                                class="form-control"
                                                                value="${element.bandCode}" readonly="true"
                                                                cssStyle="background-color: yellow;color: black"/>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${not empty element.retreatSection}">
                                                <c:if test="${element.retreatSection == 'SuperSenior'}">
                                                    <form:input path="studentNodeList[${count.index}].bandCode"
                                                                id="bandCode"
                                                                class="form-control"
                                                                value="${element.bandCode}" readonly="true"
                                                                cssStyle="background-color: red;color: white"/>
                                                </c:if>
                                            </c:if>
                                        </div>
                                        <c:if test="${not element.hasOutEntryOnDate}">
                                            <c:if test="${element.hasInEntryOnDate}">
                                                <div class="col-md-2">
                                                    <label for="checkOut">Check-Out:</label>${currentYear}
                                                    <form:checkbox path="studentNodeList[${count.index}].checkOut"
                                                                   id="checkOut"
                                                                   class="form-control"/>
                                                </div>
                                            </c:if>
                                        </c:if>
                                    </div>
                                </c:forEach>
                                <div class="col-md-12">
                                    <div style="margin-top: 30px;">
                                        <input type="submit" value="Check-Out" id="email-submit"
                                               class="btn btn-primary commonGreenBtn"/>
                                    </div>
                                </div>

                            </div>

                        </form:form>
                    </div>
                </div>


            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>

</body>
</html>
