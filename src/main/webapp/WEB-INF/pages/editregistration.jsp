<%--
  Created by IntelliJ IDEA.
  User: bibin
  Date: 5/10/16
  Time: 9:33 AM
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

            $("button.addButton").hide();

            $("#consentChecked").removeAttr('checked','checked');
            $("#saveButton").attr('disabled', 'disabled');

            if ($("#medicalInfoChecked").prop('checked')) {
                $("#medicalInformation").css('display', 'block');
            }

            $("#consentChecked").click(function () {
                if ($("#consentChecked").prop('checked')) {
                    $("#saveButton").removeAttr('disabled', 'disabled');
                } else {
                    $("#saveButton").attr('disabled', 'disabled');
                }
            });

            $("#medicalInfoChecked").click(function () {
                if ($("#medicalInfoChecked").prop('checked')) {
                    $("#medicalInformation").css('display', 'block');
                    $("#medicalInformation").val('');
                } else {
                    $("#medicalInformation").css('display', 'none');
                    $("#medicalInformation").val('');
                }
            });

            $("#phoneNumber").keypress(function (e) {
                if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                    $("#errmsg").html("Digits Only").show().fadeOut("slow");
                    return false;
                }
            });

            $("#alternativePhoneNumber").keypress(function (e) {
                if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                    $("#errmsg1").html("Digits Only").show().fadeOut("slow");
                    return false;
                }
            });

            $("#saveButton").click(function () {
                var alertMessage = '';
                $("#registration-form").find(':input').removeClass('borderColor');
                $("#saveButton").html('Loading');
                var submitFlag = true;
                if ($("#massCentreName").val() == '0') {
                    submitFlag = false;
                    alertMessage = 'Please correct errors in the red highlighted fields and save again';
                    $("#massCentreName").addClass('borderColor');
                }

                if ($("#email").val() == '') {
                    submitFlag = false;
                    alertMessage = 'Please correct errors in the red highlighted fields and save again';
                    $("#email").addClass('borderColor');
                }

                if ($("#phoneNumber").val() == "") {
                    submitFlag = false;
                    alertMessage = 'Please correct errors in the red highlighted fields and save again';
                    $("#phoneNumber").addClass('borderColor');
                } else {
                    var value = $('#phoneNumber').val()
                    var regex = new RegExp(/^\+?[0-9]+$/);
                    if (!value.match(regex)) {
                        submitFlag = false;
                        alertMessage = 'Please correct errors in the red highlighted fields and save again';
                        $("#phoneNumber").addClass('borderColor');
                    }
                }

                if ($("#alternativePhoneNumber").val() == "") {
                    submitFlag = false;
                    alertMessage = 'Please correct errors in the red highlighted fields and save again';
                    $("#alternativePhoneNumber").addClass('borderColor');
                } else {
                    var value = $('#alternativePhoneNumber').val()
                    var regex = new RegExp(/^\+?[0-9]+$/);
                    if (!value.match(regex)) {
                        submitFlag = false;
                        alertMessage = 'Please correct errors in the red highlighted fields and save again';
                        $("#alternativePhoneNumber").addClass('borderColor');
                    }
                }

                if ($("#houseNo").val() == "") {
                    submitFlag = false;
                    alertMessage = 'Please correct errors in the red highlighted fields and save again';
                    $("#houseNo").addClass('borderColor');
                }

                if ($("#firstName").val() == "") {
                    submitFlag = false;
                    alertMessage = 'Please correct errors in the red highlighted fields and save again';
                    $("#firstName").addClass('borderColor');
                }

                if ($("#lastName").val() == "") {
                    submitFlag = false;
                    alertMessage = 'Please correct errors in the red highlighted fields and save again';
                    $("#lastName").addClass('borderColor');
                }

                if ($("#addressLineOne").val() == "") {
                    submitFlag = false;
                    alertMessage = 'Please correct errors in the red highlighted fields and save again';
                    $("#addressLineOne").addClass('borderColor');
                }

                if ($("#addressLineTwo").val() == "") {
                    submitFlag = false;
                    alertMessage = 'Please correct errors in the red highlighted fields and save again';
                    $("#addressLineTwo").addClass('borderColor');
                }

                if ($("#phoneNumber").val() != "" && $("#alternativePhoneNumber").val() != "") {
                    if ($("#phoneNumber").val() == $("#alternativePhoneNumber").val()) {
                        submitFlag = false;
                        alertMessage = 'Please enter different alternative number and save again';
                        $("#alternativePhoneNumber").addClass('borderColor');
                    }
                }

                if (!validateChildData()) {
                    alertMessage = 'Please fill the Child Data in the provided box';
                    submitFlag = false;
                }

                if (submitFlag) {
                    $("#registration-form").submit();
                } else {
                    alert(alertMessage);
                    $("#saveButton").html('Save');
                }
            });

            $('#studentInfo')
                // Add button click handler
                    .on('click', '.addButton', function () {
                        generateStudentTemplate();
                    })

                // Remove button click handler
                    .on('click', '.removeButton', function () {
                        var $row = $(this).parents('.form-group'),
                                index = $row.attr('studentNodes-index');

                        // Remove element containing the fields
                        $row.remove();

                        $("button.addButton").show();

                    }).on('click', '.deleteChildRow', function (e) {
                        e.preventDefault();
                        e.stopImmediatePropagation();
                        var $row = $(this).parents('.form-group'),
                                index = $row.attr('studentNodes-index');

                        // Remove element containing the fields
                        $row.remove();
                        $('div.form-group.childRows').each(function (idx) {
                            var $inputs = $(this).find(':input:not(button)');
                            idx = idx + 1;
                            $inputs.each(function () {
                                var $prop = $(this).attr('name').split(".")[1];
                                $(this).attr('name', 'studentNodeList[' + idx + '].' + $prop).attr('id', 'studentNodeList[' + idx + '].' + $prop);
                            });
                        });
                        if (validateChildData())
                            $("button.addButton").show();

                    })
                    .on("change", "div.generalFormLayout :input:visible", function (e) {
                        e.stopImmediatePropagation();
                        var $this = $(this);

                        if (validateChildData()) {
                            $("button.addButton").show();
                        } else {
                            $("button.addButton").hide();
                        }
                    });

        });

        function validateChildData() {
            var showAddButton = true
            $('#studentInfo div.generalFormLayout').each(function (idx) { //Evalues all inputs to check whether to show Add row button or not.
                var $parent = $(this), $inputs = $parent.find(":input:visible:not(:button)");
                if ($inputs.filter(function () { //filters any input without value
                            var hasValue = $(this).is(":checkbox") ? $parent.find(":checkbox").is(":checked") : ($(this).is("select") ? $(this).val() != '0' : $(this).val());
                            return !hasValue;
                        }).length) {
                    showAddButton = false;
                    return false;
                }
            });
            return showAddButton;
        }

    </script>
    <script type="text/javascript">
        function generateStudentTemplate() {

            var i = ($('.childRows').length + 1);
            if (i < 7) {
                var $template = $('#studentInfoTemplate'),
                        $clone = $template
                                .clone()
                                .attr('studentNodes-index', i)
                                .attr('id', 'child' + i)
                                .attr('class', 'panel-body form-group childRows')
                                .appendTo($('#studentInfo'));

                // Update the name attributes
                $clone
                        .find('[name="studentNodeList[0].id"]').val('').attr('name', 'studentNodeList[' + i + '].id').attr('id', 'id' + i).end()
                        .find('[name="studentNodeList[0].firstName"]').val('').attr('name', 'studentNodeList[' + i + '].firstName').attr('id', 'firstName' + i).end()
                        .find('[name="studentNodeList[0].lastName"]').val('').attr('name', 'studentNodeList[' + i + '].lastName').attr('id', 'lastName' + i).end()
                        .find('[name="studentNodeList[0].massCentre"]').val('0').attr('name', 'studentNodeList[' + i + '].massCentre').attr('id', 'massCentre' + i).end()
                        .find('[name="studentNodeList[0].venue"]').val('').attr('name', 'studentNodeList[' + i + '].venue').attr('id', 'venue' + i).end()
                        .find('[name="studentNodeList[0].day"]').val('').attr('name', 'studentNodeList[' + i + '].day').attr('id', 'day' + i).end()
                        .find("button.deleteChildRow").closest("div.hidden").removeClass("hidden").end()
            }

            $("button.addButton").hide();

        }

        function callSectionUpdate(elementId) {

            if (elementId != null) {

                var selectedClass = $('#' + elementId).val();
                var sectionId = $('#' + elementId).closest('div.panel-body').find("input[id ^= venue]").attr("id");
                switch (selectedClass) {
                    case "0":
                        $('#' + sectionId).val('');
                        $('#' + sectionId).closest('div.panel-body').find("input[id ^= day]").val('');
                        break;
                    case "Tallaght":
                    case "Bray":
                    case "St.Josephs":
                    case "Other":
                        $('#' + sectionId).val("Tallaght");
                        $('#' + sectionId).closest('div.panel-body').find("input[id ^= day]").val("27-12-2016");
                        break;
                    case "Inchicore":
                    case "Blanchardstown":
                    case "Lucan":
                        $('#' + sectionId).val("Lucan");
                        $('#' + sectionId).closest('div.panel-body').find("input[id ^= day]").val("28-12-2016");
                        break;
                    case "Phibsboro":
                    case "Beaumont":
                    case "Swords":
                        $('#' + sectionId).val("Beaumont");
                        $('#' + sectionId).closest('div.panel-body').find("input[id ^= day]").val("29-12-2016");
                        break;

                }

            }

        }

        $(document).ready(function () {
            <c:forEach items="${parentNodeForm.studentNodeList}" var="element" varStatus="count">

            <c:if test="${count.index gt 0}">
            generateStudentTemplate();
            </c:if>
            $("#id${count.index}").val("${element.id}");
            $("#firstName${count.index}").val("${element.firstName}");
            $("#lastName${count.index}").val("${element.lastName}");
            $("#massCentre${count.index}").val("${element.massCentre}");
            $("#venue${count.index}").val("${element.venue}");
            $("#day${count.index}").val("${element.day}");

            </c:forEach>
            if (validateChildData())
                $("button.addButton").show();
        });

    </script>
</head>


<body>

<%@ include file="headerTemplate.jsp" %>
<form:form role="form" id="registration-form" modelAttribute="parentNodeForm"
           action="${pageContext.request.contextPath}/editregistration.action"
           method="post">
    <%-- <div class="container">
         <ul class="nav nav-pills">
             <li><a href="showcounts.action">Show Counts</a></li>
             <c:if test="${currentUser.systemRole == 'ADMIN'}">
                 <li><a href="adduser.action">Add Users</a></li>
             </c:if>
             <li><a href="checkinsearch.action">Check In</a></li>
             <li><a href="checkoutsearch.action">Check Out</a></li>
             <li><a href="registration.action">Registration</a></li>
             <li class="active"><a href="getEditParentEntryForm.action">Edit</a></li>
             <li><a href="searchviewentry.action">Search</a></li>
             <li><a href="reportpage.action">Report</a></li>
             <li><a href="logout.action">Logout</a></li>
         </ul>
     </div>--%>
    <div class="mainWrapper">
        <div class="row row-offcanvas row-offcanvas-right">
            <div class="col-xs-12 col-sm-12">
                <h3 class="defaultBold">Seminar Registration Form</h3>

                <div class="panel panel-default">
                    <div class="panel-heading headerColor">Parent/Guardian Details</div>
                    <div class="panel-body">
                        <div class="row generalFormLayout">
                            <div class="col-md-4">
                                <div class="form-group">

                                    <label for="massCentreName">Mass Centre:<span style="color: red">*</span></label>
                                    <form:select path="massCentreName" class="form-control" required="true"
                                                 id="massCentreName">
                                        <form:option value="0">--Please Select--</form:option>
                                        <form:option value="Beaumont">Beaumont</form:option>
                                        <form:option value="Blanchardstown">Blanchardstown</form:option>
                                        <form:option value="Bray">Bray</form:option>
                                        <form:option value="Inchicore">Inchicore</form:option>
                                        <form:option value="Lucan">Lucan</form:option>
                                        <form:option value="Phibsborough">Phibsborough</form:option>
                                        <form:option value="StJosephs">St.Joseph’s</form:option>
                                        <form:option value="Swords">Swords</form:option>
                                        <form:option value="Tallaght">Tallaght</form:option>
                                        <form:option value="Other">Other</form:option>
                                    </form:select>
                                </div>

                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label for="firstName">First Name:<span style="color: red">*</span></label>
                                    <form:input path="firstName" id="firstName" class="form-control" required="true"
                                                placeholder="First Name"/>
                                    <form:hidden path="id" class="form-control" id="id" value="${parentNodeForm.id}"/>
                                </div>

                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label for="lastName">Last Name:<span style="color: red">*</span></label>
                                    <form:input path="lastName" id="lastName" class="form-control" required="true"
                                                placeholder="Last Name"/>
                                </div>

                            </div>
                        </div>

                        <div class="row generalFormLayout">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="phoneNumber">Phone - 1:<span style="color: red">*</span></label>
                                    <form:input path="phoneNumber" class="form-control" required="true"
                                                id="phoneNumber" placeholder="Phone No."/>
                                    <div id="errmsg" style="color: red"></div>
                                </div>

                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="alternativePhoneNumber">Phone - 2:<span
                                            style="color: red">*</span></label>
                                    <form:input path="alternativePhoneNumber" class="form-control" required="true"
                                                id="alternativePhoneNumber" placeholder="Alternative Phone No."/>
                                    <div id="errmsg1" style="color: red"></div>
                                </div>

                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="email">Email:<span style="color: red">*</span></label>
                                    <form:input path="email" class="form-control" required="true" type="email"
                                                id="email" placeholder="Email" readonly="true"/>
                                </div>

                            </div>
                        </div>

                        <div class="row generalFormLayout">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="houseNo">House No:<span
                                            style="color: red">*</span></label>
                                    <form:input path="houseNo" class="form-control" id="houseNo"
                                                placeholder="If NO, enter 0"
                                                title="If no house number, please enter 0"/>
                                </div>

                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="addressLineOne">Address Line - 1:<span
                                            style="color: red">*</span></label>
                                    <form:input path="addressLineOne" class="form-control" id="addressLineOne"
                                                placeholder="Address Line - 1"/>
                                </div>

                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="addressLineTwo">Address Line - 2:<span
                                            style="color: red">*</span></label>
                                    <form:input path="addressLineTwo" class="form-control"
                                                placeholder="Address Line - 2" id="addressLineTwo"/>
                                </div>

                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label for="addressLineThree">Address Line - 3:</label>
                                    <form:input path="addressLineThree" class="form-control"
                                                placeholder="Address Line - 3"/>
                                </div>
                                <form:hidden path="ip" value="<%=request.getRemoteAddr()%>"/>
                            </div>
                        </div>


                    </div>
                </div>


                <div class="panel panel-default" id="studentInfo">
                    <div class="panel-heading headerColor">Child Details</div>


                    <div class="panel-body" id="studentInfoTemplate">

                        <div class="row generalFormLayout">
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="firstName">First Name:<span style="color: red">*</span></label>
                                    <form:input path="studentNodeList[0].firstName" class="form-control" id="firstName0"
                                                required="true" placeholder="First Name"/>
                                    <form:hidden path="studentNodeList[0].id" class="form-control" id="id0" value=""/>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="lastName"> Last Name:<span style="color: red">*</span></label>
                                    <form:input class="form-control"
                                                path="studentNodeList[0].lastName" id="lastName0" required="true"
                                                placeholder="Last Name" value="0"/>
                                </div>

                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="studentNodeList[0].massCentre">Mass Centre:<span
                                            style="color: red">*</span></label>
                                    <form:select class="form-control" path="studentNodeList[0].massCentre"
                                                 id="massCentre0" onchange="callSectionUpdate($(this).attr('id'))">
                                        <form:option value="0">--Select--</form:option>
                                        <form:option value="Tallaght">Tallaght</form:option>
                                        <form:option value="Bray">Bray</form:option>
                                        <form:option value="St.Joseph's">St.Joseph's</form:option>
                                        <form:option value="Inchicore">Inchicore</form:option>
                                        <form:option value="Blanchardstown">Blanchardstown</form:option>
                                        <form:option value="Lucan">Lucan</form:option>
                                        <form:option value="Phibsboro">Phibsboro</form:option>
                                        <form:option value="Beaumont">Beaumont</form:option>
                                        <form:option value="Swords">Swords</form:option>
                                        <form:option value="Other">Other</form:option>
                                    </form:select>
                                </div>

                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="studentNodeList[0].venue">Venue:<span
                                            style="color: red">*</span></label>
                                    <form:input class="form-control" path="studentNodeList[0].venue"
                                                id="venue0" readonly="true"/>
                                </div>

                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Day<span style="color: red">*</span></label>
                                    <form:input class="form-control" path="studentNodeList[0].day"
                                                id="day0" readonly="true"/>

                                </div>
                            </div>
                        </div>
                        <div class="col-md-1 hidden">
                            <div class="form-group vcenter">
                                <label>Delete</label>
                                <button type="button" class="btn btn-danger deleteChildRow" data-type="minus"><span
                                        class="glyphicon glyphicon-minus"></span></button>
                            </div>
                        </div>
                    </div>
                    <button type="button" class="btn btn-primary addButton commonGreenBtn" id=""
                            name="actionButton">
                        Add Child
                    </button>

                </div>


            <div class="panel panel-default">
                <div class="panel-heading headerColor">Consent Form</div>
                <div class="panel-body" id="consentInfoTemplate">

                    <div>
                        Dear Parent/Guardian,<br>
                        The consent form is necessary to allow Syro Malabar Catholic Church Dublin, Ireland to
                        provide
                        the best ‘duty of care’ to the children in its care during the events as mentioned below. It
                        gives permission for your son/daughter to take part and also necessary to ensure
                        Children’s leaders are aware of any medical, learning issues associated with your
                        son/daughter so that we can give them a positive and engaging experience.<br>

                        <i><b>Event/Activity:</b> Blaze-Grace Lal Seminar from December 27, 28 and 29, 2016 from 9.30 AM
                            to
                            4.00 PM<br>
                            <b>Venue:</b> Beaumont: St.Fiachra's National School, Montrose Park, Beaumont, Dublin 5<br>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tallaght: Church of the Incarnation of Fettercairn, St.Marks parish, Belgard, Fettercairn, Springfield, Tallaght, Dublin-24<br>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Lucan: St.John the Evangelist National School, Adamstown, Lucan, Co. Dublin.</i><br>
                        1. I have read all the information provided concerning the programme of the above
                        activity.<br>
                        2. I hereby give permission for my son/daughter/ward to participate in the above
                        activity.<br>
                        3. I accept that my child may be included in photos/videos from the above activity that
                        might be published by the parish.<br>
                        4. Syro Malabar Catholic Church Dublin, Ireland only accept liability or responsibility for
                        an incident or accident caused by the negligence or breach of statutory duty of the
                        organisation its servants or agents.<br><br>

                        <div class="panel panel-warning">
                            <div class="panel-heading"><b>Important</b></div>
                            <div class="panel-body">If there is any medical condition/special requirement for your
                                child required, which the organisers ought to be aware, please click on the check
                                box and enter details in the text box.&nbsp;&nbsp;<form:checkbox
                                        path="medicalInfoFlag"
                                        style="width:15px;height:15px;"
                                        id="medicalInfoChecked"/>
                            </div>
                            <div>

                                <div><form:textarea path="medicalInformation" id="medicalInformation"
                                                    style="display:none" class="form-control"
                                                    placeholder="Please enter details here..."></form:textarea></div>
                            </div>
                            <div class="panel-body">If you require an appointment for a special guidance session
                                with Ms. Grace Lal for improving your child’s holistic growth/healthy family
                                development, please tick this box.&nbsp;&nbsp;<form:checkbox
                                        path="specialCounsellingRequired"
                                        style="width:15px;height:15px;"
                                        id="specialCounselingChecked"/>
                            </div>
                        </div>
                    </div>

                    <div>
                        <form:checkbox path="consentSigned" style="width:15px;height:15px;" id="consentChecked"/>
                        &nbsp;&nbsp;<strong>By
                        checking the checkbox, you are giving your consent for the above child/children.</strong>
                    </div>
                </div>
            </div>
            <div style="text-align: center">
                <button type="button" class="btn btn-primary commonGreenBtn" style="min-width:140px;"
                        id="saveButton" title="Agree Consent Form To Activate Save Button">Save
                </button>
            </div>


        </div>
    </div>
    </div>

</form:form>

<%@include file="footer.jsp" %>

</body>
</html>
