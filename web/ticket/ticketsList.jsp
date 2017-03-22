<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport"    content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author"      content="TP On-boarding">
        <title>Miracle TP On-boarding</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" media="screen" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap-theme.css"/>' media="screen" >
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap.min.css"/>'>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/main.css"/>'>
        <link rel="stylesheet" href='<s:url value="/includes/plugins/datatables/dataTables.bootstrap.css"/>' type="text/css">
    </head>
    <body class="home">
        <div>
            <s:include value="../includes/template/header.jsp"/>
        </div>
        <!-- Header -->
        <header id="head">
            <div class="container">
                <h3><b>Search ticket</b></h3>
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

                    </center>
                    <div id="tpoResultMessage"></div>
                    <s:form action="doSearchTicket" method="post" cssClass="contact-form" name="tpoResetMyPwd" id="tpoResetMyPwd" theme="simple">
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label>KeyWord(s)</label>
                                    <s:textfield cssClass="form-control" name="keyword" id="keyword"   tabindex="1"/>
                                </div>
                            </div>

                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label>Assigned</label>
                                    <s:select cssClass="form-control" list="#@java.util.LinkedHashMap@{'Any':'Any'}" name="assigned" id="assigned" tabindex="2"/>
                                </div>
                            </div>

                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label>Category</label>
                                    <s:select cssClass="form-control" list="#@java.util.LinkedHashMap@{'High':'High','Medium':'Medium','Low':'Low'}" name="category" id="category" tabindex="3"/>
                                </div>
                            </div>

                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label>Priority</label>
                                    <s:select cssClass="form-control" list="#@java.util.LinkedHashMap@{'Any':'Any'}" name="priority" id="priority" tabindex="4"/>
                                </div>
                            </div>

                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label>Status</label>
                                    <s:select cssClass="form-control" list="#@java.util.LinkedHashMap@{'Any':'Any'}" name="status" id="status" tabindex="5"/>
                                </div>
                            </div>

                            <div class="col-md-12">
                                <div class="col-md-1 pull-right"> <s:submit value="Search" cssClass="btn btn-primary" tabindex="6" /></div>
                            </div>
                            <br>
                            <div class="col-md-12">
                                <div class="col-md-1 pull-right"> <input type="button" value="Clear Filters" class="btn btn-primary" tabindex="7" /></div>
                            </div>
                        </div>
                    </s:form>
                    <br>
                    <div id="site_content" class=" ">
                        <div class=" "> 
                            <table id="searchTable" class="table table-bordered table-hover">
                                <thead>
                                <th>ALL</th>
                                <th>PRIORITY</th>
                                <th>ISSUE_ID</th>
                                <th>REPORTER</th>
                                <th>ASSIGNED</th>
                                <th>TIME_SPENT</th>
                                <th>CATEGORY</th>
                                <th>RELEASE</th>
                                <th>STATUS</th>
                                <th>STATUS_CHANGE_DATE</th>
                                <th>LAST_ACTION_DATE</th>
                                <th>SUMMARY</th>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
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
                </div>
            </div>
            <!-- /Highlights -->
        </div>
        <br>
        <div class="container">
            <!--  <h3>Trading Partner</h3> -->		
            <!-- Highlights - jumbotron -->
            <div id="site_content" class="jumbotron">
                <div id="site_content" class=" ">
                    <div class="container">
                        <h3><b>Bulk Update Tool</b></h3>
                    </div>
                    <div class=" "> 
                        <table id="assignmentTable" class="table table-bordered table-hover">
                            <thead>
                            <th>ASSIGNMENT</th>
                            <th>STATUS</th>
                            <th>RELEASE</th>
                            <th>PRIORITY</th>
                            <th>CATEGORY</th>
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
            </div></div>
        <footer class="footer">
            <div class=" ">
                <s:include value="../includes/template/footer.jsp"/>
            </div>
        </footer>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
        <script src='<s:url value="../includes/plugins/datatables/jquery.dataTables.min.js"/>'></script>
        <script src='<s:url value="../includes/plugins/datatables/dataTables.bootstrap.min.js"/>'></script>
        <script>
            $(function() {
                $('#searchTable').DataTable({
                    "paging": true,
                    "lengthChange": true,
                    "searching": true,
                    "ordering": true,
                    "info": true,
                    "autoWidth": false
                });
                $('#assignmentTable').DataTable({
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

