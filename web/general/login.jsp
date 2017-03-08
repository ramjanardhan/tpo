<%-- 
    Author     : Narendar
--%>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.mss.tpo.util.AppConstants" %>
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
        <style>
            .content_item p{
                font-size: 17px;
            }

            .modal-dialog1 {
                width: 70%;
                margin: 30px auto;
            }

            .contact-form {
                margin: 0 2%;
            }
            .modal-header1 {
                padding: 4px;
                border-bottom: 1px solid #e5e5e5;
                min-height: 16.428571429px;
            }

            .close {
                float: right;
                font-size: 34px;
                font-weight: 700;
                line-height: 1;
                color: #000;
                text-shadow: 0 1px 0 #fff;
                opacity: .2;
            }


            .navbar-header{
                width: 209px !important; 
                margin: 1px auto !important; 
            }


            .bx_shadow{
                box-shadow: 0pt 2px 5px rgba(105, 108, 109, 0.7), 0px 0px 8px 5px rgba(208, 223, 226, 0.4) inset;
            }
            .register
            {text-align: right;margin: 27px 0;}

            .register img{
                width:30px;
                height:30px;
            }
        </style>
    </head>
    <body class="home">
        <!-- Fixed navbar -->
        <div class="navbar navbar-inverse" >
            <div class="container">
                <div class="navbar-header">
                    <!-- Button for smallest screens -->
                    <button type="button" class="navbar-toggle hidden-xs" data-toggle="collapse" data-target=".navbar-collapse"><span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
                    <a class="navbar-brand" href="www.miraclesoft.com"><img src="http://www.miraclesoft.com/images/logo.png" alt="miraclesoft"></a>
                </div>
                <div class="navbar-collapse collapse">
                    <div class="row">
                        <ul class="nav navbar-nav pull-right">
                            <li>  <h2 class=" " style="color: rgb(255, 255, 255); margin: 13px;">TP On-boarding</h2> </li>
                        </ul>
                    </div>
                </div><!--/.nav-collapse -->
            </div>
        </div>
        <!-- /.navbar -->
        <!-- Header -->

        <!-- /Header -->
        <!-- Intro -->
        <div class="container">
            <div class="row">
                <!-- Article main content -->
                <article id="site_content" class="col-xs-12 maincontent jumbotron" style="padding:36px 13px;margin:0">
                    <header class="page-header" style="margin:2px 2px 16px">
                    </header>
                    <div class="col-sm-8">
                        <%String path = request.getContextPath();%>
                        <div class="content">
                            <div class="content_item hidden-xs">
                                <h4 style="color: #2d8fc8" class="heading_4"><b>TP On-boarding</b></h4>
                                <div>
                                    <p> Trading Partner provides a complete view of your inbound supply and outbound fulfillment activity across your supply chain network by providing and leveraging connectivity to all your supply chain trading partners, and enterprise applications.</p>
                                    <p> Supply chain visibility (SCV) is the ability of parts, components or products in transit to be tracked from the manufacturer to their final destination. The goal of SCV is to improve and strengthen the supply chain by making data readily available to all stakeholders, including the customer. Supply chain visibility technology promotes quick response to change by allowing privileged users to take action and reshape demand or redirect supply.</p>
                                    <div class="col-sm-12 register">  
                                        <!--                                        <img src="..includes/images/register.png" alt="miraclesoft" data-toggle="modal" data-target="#selfReg"/>-->
                                        For partner self registration   <img  alt="miraclesoft" data-toggle="modal" data-target="#selfReg" src='<%=path%>/includes/images/register.png'>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="col-sm-4">
                        <div class="panel panel-default bx_shadow login_bx">
                            <div class="panel-body">
                                <h3 class="thin text-center"><b>Sign in</b></h3>
                                <center> 
                                    <%
                                        if (request.getAttribute(AppConstants.REQ_ERROR_INFO) != null) {
                                            out.println("<font size=2 color=red>" + request.getAttribute(AppConstants.REQ_ERROR_INFO).toString() + "</font>");
                                        }
                                    %>
                                </center> 
                                <hr>
                                <s:form action="general/tpoLoginCheck" method="post" name="tpLoginForm" id="tpLoginForm" theme="simple">
                                    <div class="top-margin">
                                        <label>Username <span class="text-danger">*</span></label>
                                        <s:textfield cssClass="form-control" name="loginId" id="loginId"  tabindex="1"/>
                                    </div>
                                    <div class="top-margin">
                                        <label>Password <span class="text-danger">*</span></label>
                                        <s:password cssClass="form-control" name="password" id="password"  tabindex="2"/>
                                    </div>
                                    <hr>
                                    <div class="row">
                                        <div class="col-xs-8 col-sm-7">
                                            <a href="" data-toggle="modal" data-target="#myModal"  tabindex="4">Forgot password ?</a>
                                        </div><!-- /.col -->
                                        <div class="col-xs-4 col-sm-5">
                                            <!-- <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>-->
                                            <s:submit value="Sign In" cssClass="btn btn-info btn-block btn-flat" tabindex="3"/>
                                        </div><!-- /.col -->
                                    </div>
                                </div>
                            </s:form>
                            <div class="modal fade" id="myModal" data-backdrop="static" data-keyword="false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header"  style="border:0">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title" id="myModalLabel">Reset Password</h4>
                                        </div>
                                        <div class="modal-body" style="margin-top: -15px; margin-bottom: -25px;">
                                            <div id="tpResultMessage">
                                                <div class="row">   
                                                    <div class="col-sm-6">
                                                        <div class="form-group">
                                                            <label>login Id<span class="text-danger">*</span></label>
                                                            <s:textfield  cssClass="form-control" name="userid" id="userid" placeholder="User ID"/>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-6">
                                                        <div class="form-group ajax_img">
                                                            <label>Email<span class="text-danger">*</span></label>
                                                            <s:textfield cssClass="form-control" name="regcontactEmail" id="regcontactEmail" placeholder="Your Email" value="%{regcontactEmail}" onchange="validateEmail(this);fieldLengthValidator(this);" onblur="isExistedUserEmail('login','forgotPwd');"/>
                                                            <i id="correctImg2" style="display: none;"  class="fa fa-check"></i>
                                                            <i id="wrongImg2" style="display: none;" class="fa fa-times"></i>
                                                            <i id="loadingImageEmailCheck2" style="display: none;" class="fa fa-spinner"></i>
                                                        </div>
                                                    </div>
                                                </div>  
                                            </div>
                                            <div class="modal-footer" style="border:0;margin:0">
                                                <div class="row">
                                                    <div class="col-md-12" style="margin-bottom:8px">
                                                        <div class="col-md-2 pull-right"><input type="button" id="pwdButton" value="Submit" class="btn btn-primary" onclick="forgotpassword();"/></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal fade" id="selfReg" data-backdrop="static" data-keyword="false" tabindex="-1" role="dialog" aria-labelledby="selfRegLabel">
                                <div class="modal-dialog1" role="document">
                                    <div class="modal-content" style="margin:2%">
                                        <div class="modal-header1"  style="border:0">
                                            <button type="button" style="color:#ff0000" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                            <h3 class="modal-title" id="selfRegLabel" style="color:#285e8e">Partner self registration form</h3>
                                        </div>
                                        <hr>
                                        <center><div id="addpartnerMsg"></div>
                                            <div id="loadingImage"></div>
                                            <div id="tpResultMessage"></div></center>
                                            <s:form action="" method="post" cssClass="contact-form"  name="tpoPartnerAdd" id="tpoPartnerAdd" theme="simple">
                                            <div class="row">
                                                <div class="col-sm-4">
                                                    <div class="form-group ajax_img">
                                                        <label>Partner Name<span class="text-danger">*</span></label>
                                                        <s:textfield cssClass="form-control" name="addpartnerName" id="addpartnerName" value="%{addpartnerName}" placeholder="Partner Name" onchange="addPtnerLengthValidator(this);" onblur="isExistedPartnerName('self');"/>
                                                        <i id="correctImg" style="display: none;"  class="fa fa-check"></i>
                                                        <i id="wrongImg" style="display: none;" class="fa fa-times"></i>
                                                        <i id="loadingImageAjax" style="display: none;" class="fa fa-spinner"></i>
                                                    </div>
                                                </div>
                                                <div class="col-sm-4">
                                                    <div class="form-group">
                                                        <label>First Name<span class="text-danger">*</span></label>
                                                        <s:textfield cssClass="form-control"  name="contactPerson" id="contactPerson" value="%{contactPerson}" placeholder="First Name" onchange="addPtnerLengthValidator(this);"/>
                                                    </div>
                                                </div>
                                                <div class="col-sm-4">
                                                    <div class="form-group">
                                                        <label>Last Name<span class="text-danger">*</span></label>
                                                        <s:textfield cssClass="form-control" name="contactPersonLN" id="contactPersonLN" value="%{contactPersonLN}" placeholder="Last Name" onchange="addPtnerLengthValidator(this);"/>
                                                    </div>
                                                </div>
                                                <div class="col-sm-4">
                                                    <div class="form-group ajax_img">
                                                        <label>Contact Email<span class="text-danger">*</span></label>
                                                        <s:textfield cssClass="form-control" name="contactEmail" id="contactEmail" placeholder="Contact Email" value="%{contactEmail}" onchange="validateEmail(this);fieldLengthValidator(this);" onblur="isExistedUserEmail('partnerAdd','self');"/><i id="correctImg1" style="display: none;"  class="fa fa-check"></i>
                                                        <i id="wrongImg1" style="display: none;" class="fa fa-times"></i><i id="loadingImageEmailCheck" style="display: none;" class="fa fa-spinner"></i>
                                                    </div>
                                                </div>
                                                <div class="col-sm-4">
                                                    <div class="form-group">
                                                        <label>Phone Number<span class="text-danger">*</span></label>
                                                        <s:textfield cssClass="form-control"  name="addphoneNo" id="addphoneNo" value="%{addphoneNo}" placeholder="Phone no." onchange="addPtnerLengthValidator(this);"/>
                                                    </div>
                                                </div>
                                                <div class="col-sm-4">
                                                    <div class="form-group">
                                                        <label>Address<span class="text-danger">*</span></label>
                                                        <s:textfield cssClass="form-control" name="addaddress1" id="addaddress1" value="%{addaddress1}" placeholder="Address" onchange="addPtnerLengthValidator(this);"/>
                                                    </div>
                                                </div>
                                                <div class="col-sm-4">
                                                    <div class="form-group">
                                                        <label>City<span class="text-danger">*</span></label>
                                                        <s:textfield cssClass="form-control" name="addcity" id="addcity" value="%{addcity}" placeholder="City" onchange="addPtnerLengthValidator(this);"/>
                                                    </div>
                                                </div>
                                                <div class="col-sm-4">
                                                    <div class="form-group">
                                                        <label>State<span class="text-danger">*</span></label>
                                                        <s:textfield cssClass="form-control" name="addstate" id="addstate" value="%{addstate}" placeholder="State" onchange="addPtnerLengthValidator(this);"/>
                                                    </div>
                                                </div>
                                                <div class="col-sm-4">
                                                    <div class="form-group">
                                                        <label>Country<span class="text-danger">*</span></label>
                                                        <s:select headerKey="-1" headerValue="--Select--" cssClass="form-control" list="#@java.util.LinkedHashMap@{'US':'USA','IN':'India','CN':'Canada','UK':'United Kingdom'}" name="addcountry" id="addcountry" value="%{addcountry}"/>
                                                    </div>
                                                </div>
                                                <div class="col-sm-4">
                                                    <div class="form-group">
                                                        <label>Zip Code<span class="text-danger">*</span></label>
                                                        <s:textfield cssClass="form-control" name="addzipCode" id="addzipCode" value="%{addzipCode}" placeholder="Zip Code" onchange="addPtnerLengthValidator(this);"/>
                                                    </div>
                                                </div>
                                                <div class="col-sm-4">
                                                    <div class="form-group">
                                                        <label>URL/Link</label>
                                                        <s:textfield cssClass="form-control" name="url" id="url" value="%{url}" placeholder="www.example.com" onchange="addPtnerLengthValidator(this);"/>
                                                    </div>
                                                </div>
                                                <div class="col-sm-4">
                                                    <div class="form-group">
                                                        <label>Description</label>
                                                        <s:textarea cssClass="form-control" name="description" id="description" value="%{description}" onchange="addPtnerLengthValidator(this);"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12" style="margin-bottom:8px">
                                                    <div class="col-md-2 pull-right"><input type="button" id="addbutton" value="Add" class="btn btn-primary" onclick="addPartner('self');"/></div>
                                                    <div class="col-md-2 pull-right"><input type="button" value="Reset" class="btn btn-primary" onClick="resetAddPartnerValues('self');"/></div>
                                                </div>
                                            </div>
                                        </s:form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-12 register hidden-lg hidden-md hidden-sm">  
                        <!-- <img src="..includes/images/register.png" alt="miraclesoft" data-toggle="modal" data-target="#selfReg"/>-->
                        For partner self registration   
                        <a href="" data-toggle="modal" data-target="#selfReg"  tabindex="5"><img  alt="click here" src='<%=path%>/includes/images/register.png'></a>
                    </div>
                </article>
                <!-- /Article -->
            </div>
        </div>
        <footer class="footer">
            <div class=" 
                 ">
                <s:include value="../includes/template/footer.jsp"/>
            </div>
        </footer>	
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
        <script language="JavaScript" src='<s:url value="/includes/js/tpOnbordingDeatails.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/GeneralAjax.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/headroom.min.js"></s:url>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/jQuery.headroom.min.js"></s:url>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/template.js"></s:url>'></script>
    </body>
</html>
