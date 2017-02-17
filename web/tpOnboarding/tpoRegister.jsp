<%@page import="com.mss.tpo.util.AppConstants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="TP On-boarding">
        <title>Miracle TP On-boarding</title>
        <link rel="stylesheet" media="screen" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap-theme.css"/>' media="screen" >
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap.min.css"/>'>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/main.css"/>'>
        <script>
            function doOnLoad() {
                $("#services").addClass("active");
                document.getElementById("regcontactName").value = "";
                document.getElementById("regcontactLName").value = "";
                document.getElementById("regcontactEmail").value = "";
                document.getElementById("regaddress").value = "";
                document.getElementById("regcity").value = "";
                document.getElementById("regstate").value = "";
                document.getElementById("regphoneNo").value = "";
                document.getElementById("regzipCode").value = "";
            }
        </script>
        <style>
            @media  (min-width: 992px) and (max-width: 1192px) {
                #set_align{
                    margin: 0 -13px !important;
                }
            }
        </style>
    </head>
    <body class="home" onload="doOnLoad()">
        <div>
            <s:include value="../includes/template/header.jsp"/>
        </div>
        <!-- Header -->
        <header id=" ">
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
                    <s:form action="tpoRegister" method="post" cssClass="contact-form" name="tporeg" id="tporeg" theme="simple">
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>First Name<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="regcontactName" id="regcontactName" placeholder="Contact Name" onchange="fieldLengthValidator(this);" tabindex="1"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Last Name<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="regcontactLName" id="regcontactLName" placeholder="Last Name" onchange="fieldLengthValidator(this);" tabindex="2"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group ajax_img">
                                    <label>Email<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="regcontactEmail" id="regcontactEmail" placeholder="E-mail" onchange="validateEmail(this);fieldLengthValidator(this);" onblur="isExistedUserEmail('userAdd','admin');" tabindex="3"/><i id="correctImg1" style="display: none;"  class="fa fa-check"></i>
                                    <i id="wrongImg1" style="display: none;" class="fa fa-times"></i><i id="loadingImageEmailCheck" style="display: none;" class="fa fa-spinner"></i>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Address<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="regaddress" id="regaddress" placeholder="Address" onchange="fieldLengthValidator(this);" tabindex="4"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>City<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="regcity" id="regcity" placeholder="City" onchange="fieldLengthValidator(this);" tabindex="5"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>State<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="regstate" id="regstate" placeholder="State" onchange="fieldLengthValidator(this);" tabindex="6"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Country<span class="text-danger">*</span></label>
                                    <s:select cssClass="form-control" headerKey="-1" headerValue="--select--" list="#@java.util.LinkedHashMap@{'US':'USA','IN':'India','CN':'Canada','UK':'United Kingdom'}" name="regcountry" id="regcountry" value="%{country}" tabindex="7"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Phone Number<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="regphoneNo" id="regphoneNo" placeholder="Phone No" onchange="fieldLengthValidator(this);" tabindex="8"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Zip Code<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="regzipCode" id="regzipCode" onchange="fieldLengthValidator(this);" tabindex="9"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-1 pull-right"> <s:submit value="Save" cssClass="btn btn-primary" onclick="return usrCreationValidation();" tabindex="10"/></div>
                                <div class="col-sm-2 col-md-1 pull-right"> <input type="button" id="set_align" value="Reset" class="btn btn-primary" onclick="resetRegisterUser();" tabindex="11"/></div>
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