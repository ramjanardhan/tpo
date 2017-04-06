<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.mss.tpo.util.AppConstants"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="TP On-boarding">
        <title>Miracle TP On-boarding</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" media="screen" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700"/>
        <link rel="stylesheet" href='<s:url value="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap.min.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/main.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/build.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap-theme.css" />' media="screen" type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/plugins/datepicker/datepicker3.css"/>' type="text/css">
         <link rel="stylesheet" href='<s:url value="/includes/plugins/datatables/dataTables.bootstrap.css"/>' type="text/css">
        <script>
            function doOnLoad() {
                $("#ticket").addClass("active");
            }
        </script>

        <style>
            @media  (min-width: 992px) and (max-width: 1192px) {
                #set_align1{
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
        <header id="head">
            <div class="container">
                <h3><b>Edit Ticket</b></h3>
            </div>
        </header> 
        <!-- /Header -->
        <!-- Intro -->
        <div class="container">
            <!--  <h3>Trading Partner</h3> -->		
            <!-- Highlights - jumbotron -->
            <div id="site_content" class="jumbotron block_div" style="padding-top: 9px;">
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
                    <s:form action="" method="post" cssClass="contact-form" name="" id="" theme="simple">
                        <div class="">
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>Category<span class="text-danger">*</span></label>
                                    </div>
                                </div>
                                <div class="col-sm-3"> <s:select cssClass="form-control" headerKey="-1" headerValue="Please choose a category" list="{'High','Medium','Low Priority'}" name="type" id="type" value="" tabindex="1"/></div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>Priority<span class="text-danger">*</span></label>
                                    </div>
                                </div>
                                <div class="col-sm-3"><s:select cssClass="form-control" headerKey="-1" headerValue="--select--" list="{'High','Medium','Low Priority'}" name="type" id="type" value="" tabindex="2"/></div>
                            </div>
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>Assignment<span class="text-danger">*</span></label>
                                    </div>
                                </div>
                                <div class="col-sm-3"><s:select cssClass="form-control" headerKey="-1" headerValue="--select--" list="{'High','Medium','Low Priority'}" name="type" id="type" value="" tabindex="3"/></div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>Summary<span class="text-danger">*</span></label>
                                    </div>
                                </div>
                                <div class="col-sm-3"> <s:textfield cssClass="form-control"  name="profile" id="profile" value="" placeholder="Profile"  tabindex="4"/></div>
                            </div>
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>Initial&nbsp;Description <span class="text-danger">*</span></label>
                                    </div>
                                </div>
                                <div class="col-sm-3"><s:textfield cssClass="form-control" name="transaction" id="transaction" value="" placeholder="Transaction"  tabindex="5"/>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>Estimated&nbsp;Dev&nbsp;Time<span class="text-danger">*</span></label>
                                    </div>
                                </div>
                                <div class="col-sm-3"> <s:textfield cssClass="form-control" name="title" id="title" placeholder="Title" value=""  tabindex="6"/>
                                </div>
                            </div>
                            <div class="row"> 
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>Private<span class="text-danger">*</span></label>
                                    </div>
                                </div>
                                <div class="col-sm-3"><s:radio name="transferMode" id="transferMode" list="{'Yes','No'}" value="" cssClass="from-control"  tabindex="7"></s:radio></div>
                            </div>
                            <div class="row">
                                <div>
                                    <div class="col-md-2"> <input type="button" id="set_align1" value="Unassign Issue" class="btn btn-primary" onclick="" tabindex="9"/></div>
                                    <div class="col-md-2"> <s:submit value="Update Issue" cssClass="btn btn-primary"  tabindex="10"/></div>
                                    <div class="col-md-2"> <input type="button" id="set_align1" value="Reply" class="btn btn-primary" onclick="" tabindex="9"/></div>
                                    <div class="col-md-2"> <input type="button" id="set_align1" value="Mark as Duplicate" class="btn btn-primary" onclick="" tabindex="9"/></div>
                                    <div class="col-md-2"> <input type="button" id="set_align1" value="Close issue" class="btn btn-primary" onclick="" tabindex="9"/></div>
                                </div>
                            </div>
                        </div>   
                    </s:form>
                </div>
            </div>
            <!-- /Highlights -->
        </div>
                        <br>
                        <header id=" ">
            <div class="container">
                <h4 class="thin"><b>Attached Files</b></h4>
            </div>
        </header>
        <div class="container">
            <div id="site_content" class="jumbotron">
                <div class="container">
                    <div id="site_content" class=" ">
                        <div class=" "> 
                            <table id="filesTable" class="table table-bordered table-hover">
                                <thead>
                                <th>Files</th>
                                <th>Owner</th>
                                <th>Status</th>
                                <th>Date</th>
                                <th>Description</th>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <center><div>
                    <input type="button" value="Upload File" class="btn btn-primary"/>
                    </div></center>
                </div></div>
        </div>
<!--                        <br>
        <div class="container">
            <div id="site_content" class="jumbotron">
                <div class="container">
                    <h3>Time Tracking</h3>
                    <center><input type="button" value="Add Time Entry" class="btn btn-primary"/></center>
                </div></div>
        </div> -->
    </div> 
    <footer class="footer">
        <div class=" ">
            <s:include value="../includes/template/footer.jsp"/>
        </div>
    </footer>
    <script language="JavaScript" src='<s:url value="/includes/js/headroom.min.js"/>'></script>
    <script language="JavaScript" src='<s:url value="/includes/js/jQuery.headroom.min.js"/>'></script>
    <script language="JavaScript" src='<s:url value="/includes/js/template.js"/>'></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
    <script src='<s:url value="../includes/plugins/datepicker/bootstrap-datepicker.js"/>'></script>
    <script type="text/javascript" src='<s:url value="../includes/plugins/datepicker/moment.js"/>'></script>
    <script src='<s:url value="../includes/plugins/datatables/jquery.dataTables.min.js"/>'></script>
        <script src='<s:url value="../includes/plugins/datatables/dataTables.bootstrap.min.js"/>'></script>
     <script>
            $(function() {
                $('#filesTable').DataTable({
                    "paging": true,
                    "lengthChange": true,
                    "searching": true,
                    "ordering": true,
                    "info": true,
                    "autoWidth": false
                });
            });

        </script>
</body> 
</html>
