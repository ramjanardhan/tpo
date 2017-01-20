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
        <title>TP On-boarding</title>
        <link rel="stylesheet" media="screen" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap-theme.css"/>' media="screen" >
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap.min.css"/>'>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/main.css"/>'>
        <script>
            function doOnLoad() {
                $("#partnerAdd").addClass("active");
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
                <h2 class="thin"><b>Trading Partner Information</b></h2>
            </div>
        </header>
        <!-- /Header -->
        <!-- Intro -->
        <div class="container">
            <!-- <h3>Trading Partner</h3> -->		
            <!-- Highlights - jumbotron -->
            <div class="jumbotron">
                <div class="container">
                    <center>
                        <%
                            if (session.getAttribute(AppConstants.REQ_RESULT_MSG) != null) {
                                String reqponseString = session.getAttribute(AppConstants.REQ_RESULT_MSG).toString();
                                out.println(reqponseString);
                                session.setAttribute(AppConstants.REQ_RESULT_MSG, null);
                            }
                        %>
                        <div id="addpartnerMsg"></div>
                    </center>
                    <div id="loadingImage"></div>
                    <s:form action="" method="post" cssClass="contact-form"  name="tpoPartnerAdd" id="tpoPartnerAdd" theme="simple">
                        <s:hidden id="roleId" name="roleId" value="%{roleId}"/>
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="form-group ajax_img">
                                    <label>Partner Name<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="addpartnerName" id="addpartnerName" value="%{addpartnerName}" placeholder="Partner Name" onchange="addPtnerLengthValidator(this);" onblur="isExistedPartnerName('admin');"/>
                                    <i id="correctImg" style="display: none;"  class="fa fa-check"></i>
                                    <i id="wrongImg" style="display: none;" class="fa fa-times"></i>
                                    <i id="loadingImageAjax" style="display: none;" class="fa fa-spinner"></i>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Contact Person First Name<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control"  name="contactPerson" id="contactPerson" value="%{contactPerson}" placeholder="First Name" onchange="addPtnerLengthValidator(this);"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Contact Person Last Name<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="contactPersonLN" id="contactPersonLN" value="%{contactPersonLN}" placeholder="Last Name" onchange="addPtnerLengthValidator(this);"/>
                                </div>
                            </div>

                            <div class="col-sm-3">
                                <div class="form-group ajax_img">
                                    <label>Contact Email<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="contactEmail" id="contactEmail" placeholder="Contact Email" value="%{contactEmail}" onchange="validateEmail(this);fieldLengthValidator(this);" onblur="isExistedUserEmail('partnerAdd','admin');"/><i id="correctImg1" style="display: none;"  class="fa fa-check"></i>
                                    <i id="wrongImg1" style="display: none;" class="fa fa-times"></i><i id="loadingImageEmailCheck" style="display: none;" class="fa fa-spinner"></i>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Phone No.<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control"  name="addphoneNo" id="addphoneNo" value="%{addphoneNo}" placeholder="Phone no." onchange="addPtnerLengthValidator(this);"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Address<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="addaddress1" id="addaddress1" value="%{addaddress1}" placeholder="Address" onchange="addPtnerLengthValidator(this);"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>City<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="addcity" id="addcity" value="%{addcity}" placeholder="City" onchange="addPtnerLengthValidator(this);"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>State<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="addstate" id="addstate" value="%{addstate}" placeholder="State" onchange="addPtnerLengthValidator(this);"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Country<span class="text-danger">*</span></label>
                                    <s:select cssClass="form-control" list="#@java.util.LinkedHashMap@{'US':'USA','IN':'India','CN':'Canada','UK':'United Kingdom'}" name="addcountry" id="addcountry" value="%{addcountry}"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Zip Code<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control" name="addzipCode" id="addzipCode" value="%{addzipCode}" placeholder="Zip Code" onchange="addPtnerLengthValidator(this);"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>URL/Link</label>
                                    <s:textfield cssClass="form-control" name="url" id="url" value="%{url}" placeholder="www.example.com" onchange="addPtnerLengthValidator(this);"/>
                                </div>
                            </div>
                            <s:if test="#session.tpoRoleId == 1">
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>Assign To</label>
                                        <s:select headerKey="-1" headerValue="--select--" cssClass="form-control" list="adminUsersList" name="adminUsersList" id="adminUsersList" value="%{adminUsersList}"/>
                                    </div>
                                </div>
                            </s:if>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Description</label>
                                    <s:textarea cssClass="form-control" name="description" id="description" value="%{description}" onchange="addPtnerLengthValidator(this);"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-1 pull-right"><input type="button" id="addbutton" value="Add" class="btn btn-primary" onclick="addPartner('admin');"/></div>
                                <div class="col-md-1 pull-right"><input type="button" value="Reset" class="btn btn-primary" onClick="resetAddPartnerValues('admin');"/></div>
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