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
            }
        </script>
    </head>
    <body class="home" onload="doOnLoad()">
        <div>
            <s:include value="../includes/template/header.jsp"/>
        </div>
        <!-- Header -->
        <header id=" ">
            <div class="container">
                <h2 class="thin"><b>Reset Partner Password</b></h2>
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
                    </center>
                    <div id="tpoResultMessage"></div>
                    <s:form action="doTpoResetPartnerPwd" method="post" cssClass="contact-form" name="doTpoResetPartnerPwd" id="doTpoResetPartnerPwd" theme="simple">
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label>Partner<span class="text-danger">*</span></label>
                                    <s:select headerKey="-1" headerValue="--select--" cssClass="form-control" list="partnerNameList" name="partnerName" id="partnerName" value="%{partnerName}" onchange="showPartnerPwdBox()" tabindex="1"/>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="pwdBox" style="display: none">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label>Password<span class="text-danger">*</span></label>
                                    <s:password cssClass="form-control" name="regpassword" id="regpassword" placeholder="Password" onchange="fieldLengthValidator(this);" tabindex="2"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-1 pull-right"> <s:submit value="Update" cssClass="btn btn-primary" onclick="return checkPartnerPwd();" tabindex="3"/></div>
                            </div>
                        </div>
                    </s:form>
                </div>
            </div>
            <!-- /Highlights -->
        </div>
        <footer class="footer">
            <div>
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