<%@page import="com.mss.tpo.util.AppConstants"%>
<%@page import="com.mss.tpo.tpOnboarding.TpOnboardingBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport"    content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author"      content="TP On-boarding">
        <title>Miracle TP On-boarding</title>
        <link rel="stylesheet" media="screen" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap-theme.css"/>' media="screen" >
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap.min.css"/>'>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/main.css"/>'>
        <script>
            function doOnLoad() {
                $("#services").addClass("active");
                $('#saveButton').attr('disabled', true);
                $('#country').attr('disabled', true);
            }
            function enableEdit() {
                $('#saveButton').attr('disabled', false);
                $('#editButton').attr('disabled', true);
                document.getElementById("phoneNo").readOnly = false;
                document.getElementById("address1").readOnly = false;
                document.getElementById("contactEmail").readOnly = false;
                document.getElementById("city").readOnly = false;
                document.getElementById("state").readOnly = false;
                $('#country').attr('disabled', false);
                document.getElementById("zipCode").readOnly = false;
            }
        </script>
    </head>
    <body class="home" onload="doOnLoad()">
        <div>
            <s:include value="../includes/template/header.jsp"/>
        </div>
        <!-- Header -->
        <!--  Header -->
        <header id=" ">
            <div class="container">
                <h3><b>Trading Partner Information</b></h3>
            </div>
        </header>
        <!-- /Header -->
        <!-- Intro -->
        <div class="container">
            <!-- <h3>Trading Partner</h3> -->		
            <!-- Highlights - jumbotron -->
            <div id="site_content" class="jumbotron">
                <div class="container">
                    <div id="loadingImage"></div>
                    <s:form action="updatePartnerInfo" method="post" cssClass="contact-form"  name="partnerInfo" id="partnerInfo" theme="simple">
                        <center>
                            <%
                                if (session.getAttribute(AppConstants.REQ_RESULT_MSG) != null) {
                                    String responseString = session.getAttribute(AppConstants.REQ_RESULT_MSG).toString();
                                    out.println(responseString);
                                    session.setAttribute(AppConstants.REQ_RESULT_MSG, null);
                                }
                            %>
                            <div id="tpResultMessage"></div>
                        </center>
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Partner Name<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="partnerName" id="partnerName" value="%{tpOnboardingBean.partnerName}" onchange="fieldLengthValidator(this);" readOnly="true"  tabindex="1"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Email<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="contactEmail" id="contactEmail" value="%{tpOnboardingBean.contactEmail}" onchange="validateEmail(this);fieldLengthValidator(this);" readOnly="true"  tabindex="2"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Phone Number<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="phoneNo" id="phoneNo" value="%{tpOnboardingBean.phoneNo}" onchange="fieldLengthValidator(this);" readOnly="true"  tabindex="3"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Address<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="address1" id="address1" value="%{tpOnboardingBean.address1}" onchange="fieldLengthValidator(this);" readOnly="true"  tabindex="4"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>City<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="city" id="city" value="%{tpOnboardingBean.city}" onchange="fieldLengthValidator(this);" readOnly="true"  tabindex="5"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>State<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="state" id="state" value="%{tpOnboardingBean.state}" onchange="fieldLengthValidator(this);" readOnly="true"  tabindex="6"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Country<span class="text-danger">*</span></label>
                                    <s:select cssClass="form-control" headerKey="-1" headerValue="--select--" list="#@java.util.LinkedHashMap@{'US':'USA','IN':'India','CN':'Canada','UK':'United Kingdom'}" name="country" id="country" value="%{tpOnboardingBean.country}"  tabindex="7"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Zip Code<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="zipCode" id="zipCode" value="%{tpOnboardingBean.zipCode}" onchange="fieldLengthValidator(this);" readOnly="true"  tabindex="8"/>
                                </div>
                            </div>
                        </div>
                        <s:if test="#session.tpoRoleId != 5">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="col-md-1 pull-right"><s:submit id="saveButton" value="Save" cssClass="btn btn-primary" onClick="return checkPartnerInfo()"  tabindex="9"/></div>
                                    <div class="col-md-1 pull-right"><input id="editButton" type="button" value="Edit" class="btn btn-primary" onClick="enableEdit();"  tabindex="10"/></div>
                                </div>
                            </div>
                        </s:if>
                    </s:form>
                </div>
            </div>
            <!-- /Highlights -->
        </div>
        <footer class="footer">
            <div class=" ">
                <s:include value="../includes/template/footer.jsp"/>
            </div>
        </footer>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
        <script language="JavaScript" src='<s:url value="/includes/js/headroom.min.js"></s:url>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/jQuery.headroom.min.js"></s:url>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/template.js"></s:url>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/GeneralAjax.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/tpOnbordingDeatails.js"/>'></script>
    </body>
</html>