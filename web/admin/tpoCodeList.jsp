<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="com.mss.tpo.util.AppConstants"%>
<%@page import="com.mss.tpo.admin.AdminBean"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="TP On-boarding">
        <title>Miracle TP On-boarding</title>
        <script type="text/javascript" src="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" media="screen" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700"/> 
        <link rel="stylesheet" href='<s:url value="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap.min.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/main.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/build.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap-theme.css" />' media="screen" type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/plugins/datatables/dataTables.bootstrap.css"/>' type="text/css">

        <script>
            function doOnLoad() {
                $("#services").addClass("active");
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
                <h3><b>Code List</b></h3>
            </div>
        </header> 
        <!-- /Header -->
        <!-- Intro -->
        <div class="container">
            <!--  <h3>Trading Partner</h3> -->		
            <!-- Highlights - jumbotron -->
            <div id="site_content" class="jumbotron block_div" style="padding-top: 9px;">
                <div class="container">
                    <div id="loadingImage"></div>
                    <s:form action="../admin/.action" method="post" cssClass="contact-form" name="certForm" id="certForm" theme="simple">
                        <s:hidden id="docdatepickerfrom" name="docdatepickerfrom" />
                        <s:hidden id="docdatepicker" name="docdatepicker"/>
                        <div class="">
                            <div class="row" id="connTypeDiv" >
                                <div class="col-sm-3">
                                    <label>Name Search</label>
                                    <s:textfield name="name"  id="name" cssClass="form-control"   value="%{name}" /> 

                                </div>
                                <div class="col-sm-3"> 
                                    <label>Code Lists</label>
                                    <s:select headerKey="-1" headerValue="--Select Type--" cssClass="form-control" list="#@java.util.LinkedHashMap@{}" name="codeList" id="codeList" value="%{certType}"  /> 
                                </div> 
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-sm-2"><s:submit value="Search"  cssClass="btn btn-primary" tabindex="16" /></div>

                                <div class="col-sm-3"> 
                                    <input type="button" id="add" name="add" class="btn btn-primary" value="Add Row"/>
                                </div> 
                                <div class="col-sm-3"> 
                                    <input type="button" class="btn btn-primary" value="Delete Row"/>
                                </div> 
                                <div class="col-sm-3"> 
                                    <input type="button" class="btn btn-primary" value="Clear Grid"/>
                                </div> 
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div class="container">
                <div id="site_content" class="jumbotron block_div" style="padding-top: 9px;">
                    <div class="container">
                        <div class="row">
                            <div class="col-sm-3">
                                <label>Code List Selected</label>
                                <s:textfield name="selectedCL"  id="selectedCL" cssClass="form-control"   value="%{selectedCL}" /> 
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-sm-3">
                                <label>Last Date Modified :</label>

                            </div><div>
                                <label style="margin-left: 494px">Number Of Code List Items :</label>

                            </div>
                        </div>  </div> 
                    </s:form>
                <div id="gridDiv">  
                    <%---  <s:if test="#session.certmonitorList != null"> 
                         GRid start --%>
                    <section class="content">
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="box">
                                    <div class="box-header">
                                        <h3 class="box-title">Table</h3>
                                    </div><!-- /.box-header -->
                                    <div class="box-body">
                                        <%!String cssValue = "whiteStripe";
                                            int resultsetTotal;%>
                                        <div style="overflow-x:auto;">
                                            <table align="left" width="100%" border="0" cellpadding="0" cellspacing="0">
                                                <tr>
                                                    <td style="background-color: white;">
                                                        <table id="results"  class="table table-bordered table-hover">
                                                            <thead><tr>
                                                                    <th>SELECT</th>
                                                                    <th>SENDER_ID</th>
                                                                    <th>RECEIVER_ID</th>
                                                                    <th>DESCRIPTION</th>
                                                                    <th>TEXT</th> 
                                                                </tr> </thead>
                                                            <tbody>
                                                            <td><input type="checkbox"/></td>
                                                            <td></td>
                                                            <td></td>
                                                            <td></td>
                                                            <td></td>
                                                            </tbody>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align="right" colspan="28" style="background-color: white;">
                                                        <div align="right" id="pageNavPosition"></div>
                                                    </td>
                                                </tr>
                                                <% session.setAttribute(AppConstants.CERTMONITOR_LIST, null);%>
                                            </table>
                                            <%--  <input type="hidden" name="sec_po_list" id="sec_po_list" value="<%=list.size()%>"/> --%>
                                        </div>
                                    </div>
                                    <%-- Grid End --%>
                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-sm-3"> 
                                <label>New Code List Name</label>
                                <s:textfield name="newname"  id="newname" cssClass="form-control"   value="%{newname}" /> 
                            </div>  

                            <div> 
                                <input type="button" class="btn btn-primary pull-right" style="margin-right: 91px;margin-top: 20px;" value="Import To SI"/>
                            </div> 


                        </div>
                    </section>
                    <%--  </s:if> --%>
                </div>
            </div>
        </div>
        <!-- /Highlights -->
    </div> 
    <footer class="footer">
        <div class=" ">
            <s:include value="../includes/template/footer.jsp"/>
        </div>
    </footer>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script language="JavaScript" src='<s:url value="/includes/js/headroom.min.js"/>'></script>
    <script language="JavaScript" src='<s:url value="/includes/js/jQuery.headroom.min.js"/>'></script>
    <script language="JavaScript" src='<s:url value="/includes/js/template.js"/>'></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
    <script language="JavaScript" src='<s:url value="/includes/js/adminValidations.js"/>'></script>
    <script src='<s:url value="/includes/bootstrap/js/app.min.js"/>'></script>
    <script src='<s:url value="/includes/plugins/datatables/jquery.dataTables.min.js"/>'></script>
    <script src='<s:url value="/includes/plugins/datatables/dataTables.bootstrap.min.js"/>'></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#add').click(function() {
                $('#results').append(
                '<tr><td><input type="checkbox" name="upload850ob" theme="simple"/></td><td></td><td></td><td></td><td></td></tr>');
                return false;
            });    
        });
        $(function() {
            $('#results').DataTable({
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