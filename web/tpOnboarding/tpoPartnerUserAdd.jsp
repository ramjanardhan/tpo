<%@page import="com.mss.tpo.util.AppConstants"%>
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
            }
        </script>
    </head>
    <body class="home" onload="doOnLoad()">
        <div>
            <s:include value="../includes/template/header.jsp"/>
        </div>
        <!-- Header -->
        <header id="head">
            <div class="container">
                <h2 class="thin"><b>User Creation</b></h2>
            </div>
        </header>
        <!-- /Header -->
        <!-- Intro -->
        <div class="container">
            <!--  <h3>Trading Partner</h3> -->		
            <!-- Highlights - jumbotron -->
            <div id="site_content" class="jumbotron">
                <div class="container">
                    <center>
                        <%
                            if (session.getAttribute(AppConstants.REQ_RESULT_MSG) != null) {
                                String responseString = session.getAttribute(AppConstants.REQ_RESULT_MSG).toString();
                                out.println(responseString);
                                session.setAttribute(AppConstants.REQ_RESULT_MSG, null);
                            }
                        %>
                        <div id="tpResultMessage"></div>
                        <div id="tpRegisteValidation"></div>
                    </center>
                    <div id="loadingImage"></div>
                    <s:form action="doAddPartnerUser" method="post" cssClass="contact-form" name="doAddPartnerUser" id="doAddPartnerUser" theme="simple">
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>First Name<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control"  name="contactName" id="contactName" value="%{contactName}" placeholder="First Name" onchange="fieldLengthValidator(this);" tabindex="1"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Last Name<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="contactLastName" id="contactLastName" value="%{contactLastName}" placeholder="Last Name" onchange="fieldLengthValidator(this);" tabindex="2"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group ajax_img">
                                    <label>Email<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="contactEmail" id="contactEmail" placeholder="E-mail" value="%{contactEmail}" onchange="validateEmail(this);fieldLengthValidator(this);" onblur="isExistedUserEmail('partnerAdd','partner');" tabindex="3"/><i id="correctImg1" style="display: none;"  class="fa fa-check"></i>
                                    <i id="wrongImg1" style="display: none;" class="fa fa-times"></i><i id="loadingImageEmailCheck" style="display: none;" class="fa fa-spinner"></i>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Phone Number<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="phoneNo" id="phoneNo" placeholder="Phone No" value="%{phoneNo}" onchange="fieldLengthValidator(this);" tabindex="4"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Address<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="address1" id="address1" value="%{address1}" placeholder="Address" onchange="fieldLengthValidator(this);" tabindex="5"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>City<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="city" id="city" value="%{city}" placeholder="City" onchange="fieldLengthValidator(this);" tabindex="6"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>State<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="state" id="state" value="%{state}" placeholder="State" onchange="fieldLengthValidator(this);" tabindex="7"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Country<span class="text-danger">*</span></label>
                                    <s:select cssClass="form-control" headerKey="-1" headerValue="--select--" list="#@java.util.LinkedHashMap@{'US':'USA','IN':'India','CN':'Canada','UK':'United Kingdom'}" name="country" id="country" value="%{country}" tabindex="8"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Zip Code<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="zipCode" id="zipCode" value="%{zipCode}" onchange="fieldLengthValidator(this);" tabindex="9"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-1 pull-right"> <s:submit value="Save" cssClass="btn btn-primary" onclick="return partnerUserAddValidation();" tabindex="10"/></div>
                                <div class="col-md-1 pull-right"> <input type="button" value="Reset" class="btn btn-primary" onclick="resetPartnerUserAdd();" tabindex="11"/></div>
                            </div>
                        </div>
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