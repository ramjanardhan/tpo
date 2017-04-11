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
        <link rel="stylesheet" href='<s:url value="/includes/plugins/daterangepicker/daterangepicker-bs3.css"/>' type="text/css"> 
        <script>
            function doOnLoad() {
                $("#services").addClass("active");
                $("#daysCount").trigger("change");
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
                <h3><b>Certificate Monitoring</b></h3>
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
                    </center>
                    <div id="loadingImage"></div>
                    <s:form action="../admin/certMonitorSearch.action" method="post" cssClass="contact-form" name="certForm" id="certForm" theme="simple">
                        <s:hidden id="docdatepickerfrom" name="docdatepickerfrom" />
                        <s:hidden id="docdatepicker" name="docdatepicker"/>
                        <div class="">
                            <div class="row" id="connTypeDiv" >
                                <div class="col-sm-3">
                                    <label for="certType">Certificate Type <font style="color: red">*</font></label>
                                        <s:select headerKey="-1" headerValue="--Select Type--" cssClass="form-control" list="#@java.util.LinkedHashMap@{'CA':'CA Certificate','System':'System Certificate','Trusted':'Trusted Certificate'}" name="certType" id="certType" value="%{certType}"  /> 
                                </div>
                                <div class="col-sm-3"> 
                                    <label>Days in Expire Certificate</label>
                                    <s:select headerKey="-1" headerValue="--Select Type--" cssClass="form-control" list="#@java.util.LinkedHashMap@{'30':'Expiring in 30 days','60':'Expiring in 60 days','90':'Expiring in 90 days','Other':'Other'}" name="daysCount" id="daysCount" value="%{daysCount}"  /> 
                                </div> 


                                <div class="col-sm-3" id="daysdiv" style="display: none">
                                    <label>Enter Days</label>
                                    <s:textfield name="date" id="date" cssClass="form-control" value="%{date}" />
                                </div> 


                            </div>
                            <br>
                            <span id="span1">
                            </span>
                            <div class="row">
                                <div class="col-sm-2"><s:submit value="Search"  cssClass="btn btn-primary col-sm-12" tabindex="16" onclick="return validateCertType();"/></div>
                                <div class="col-sm-2"><strong><input type="button" value="Reset"  tabindex="17" class="btn btn-primary col-sm-12" onclick="return resetvalues();"/></strong></div>
                                        <s:hidden name="sampleValue" id="sampleValue" value="2"/>
                            </div>
                        </div>   
                    </s:form>
                    <div id="gridDiv">  
                        <s:if test="#session.certmonitorList != null"> 
                            <%--- GRid start --%>
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
                                                                            <th>CERTIFICATE NAME</th>
                                                                            <th>VALID FROM </th>
                                                                            <th> VALID TILL </th>
                                                                            <th>DAYS</th> 
                                                                        </tr> </thead>
                                                                    <tbody>
                                                                        <%
                                                                            java.util.LinkedList<LinkedHashMap<String, String>> list = (java.util.LinkedList<LinkedHashMap<String, String>>) session.getAttribute(AppConstants.CERTMONITOR_LIST);
                                                                            if (list.size() != 0) {
                                                                                Iterator<LinkedHashMap<String, String>> it = list.iterator();
                                                                                while (it.hasNext()) {
                                                                                    LinkedHashMap<String, String> map = it.next();
                                                                                    Collection<String> values = map.values();
                                                                                    Object[] valuesarray = values.toArray(new Object[values.size()]);
                                                                        %>
                                                                        <tr>
                                                                            <td> <% out.println(valuesarray[0]);%> </td>
                                                                            <td> <%  out.println(valuesarray[1]);%> </td>
                                                                            <td> <%out.println(valuesarray[2]);%> </td>  
                                                                            <td> <% String str = (String) valuesarray[3].toString();
                                                                                StringBuffer sb = new StringBuffer("Expired Since ");
                                                                                StringBuffer sb1 = new StringBuffer("Will Expire in ");
                                                                                StringBuffer sb2 = new StringBuffer(" Days");
                                                                                int days = Integer.parseInt(str);
                                                                                if (days < 0) {
                                                                                    str = str.replaceAll("-", "");
                                                                                %>
                                                                                <font style='color:red'> <%out.println(sb.append(str).append(sb2));%></font>
                                                                                <%} else if ((days > 0) && (days < 30)) {
                                                                                %><font style='color:magenta'> <%out.println(sb1.append(str).append(sb2));%></font>
                                                                                <%} else {
                                                                                        out.println(sb1.append(days).append(sb2));
                                                                                    }%>
                                                                            </td>
                                                                        </tr>
                                                                        <% }
                                                                            }
                                                                        %>
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
                                                    <input type="hidden" name="sec_po_list" id="sec_po_list" value="<%=list.size()%>"/>
                                                </div>
                                            </div>
                                            <%-- Grid End --%>
                                        </div>
                                    </div>
                                </div></section>
                            </s:if>
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
        <script src='<s:url value="/includes/plugins/daterangepicker/moment.js"/>'></script>
        <script src='<s:url value="/includes/plugins/daterangepicker/moment.min.js"/>'></script>
        <script src='<s:url value="/includes/plugins/daterangepicker/daterangepicker.js"/>'></script>
        <script src='<s:url value="/includes/bootstrap/js/app.min.js"/>'></script>
        <script src='<s:url value="/includes/plugins/datatables/jquery.dataTables.min.js"/>'></script>
        <script src='<s:url value="/includes/plugins/datatables/dataTables.bootstrap.min.js"/>'></script>
        <script type="text/javascript">
            $(function () {
                $("#daysCount").change(function () {
                    if ($("#daysCount").val() == "Other") {
                        $("#daysdiv").show();
                    } else
                    {
                        $("#daysdiv").hide();
                    }
                });
            });
            function Date1() {
                var date = document.certForm.reportrange.value;
                var arr = date.split("-");
                var x = arr[1].trim();
                document.getElementById("docdatepickerfrom").value = arr[0];
                document.getElementById("docdatepicker").value = x;
            }
            $(function() {
                function cb(start, end) {
                    $('#reportrange span').html(start.format('MM/DD/YYYY  HH:MM') + ' - ' + end.format('MM/DD/YYYY HH:MM'));
                }
                cb(moment().subtract(29, 'days'), moment());

                $('#reportrange').daterangepicker({
                    ranges: {
                        'Today': [moment(), moment()],
                        'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                        'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                        'This Month': [moment().startOf('month'), moment().endOf('month')],
                        'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
                        'Transactions Until': [moment()]
                    },
                    timePicker: true,
                    timePicker24Hour: true,
                    timePickerIncrement: 1,
                    locale: {
                        format: 'DD/MM/YYYY'
                    }
                }, cb);
            });

            function validateCertType() {
                var certType = document.getElementById('certType').value;
                if (certType == "-1") {
                    alert("please select certificate type");
                    return false;
                }
                return true;
            }

            function resetvalues() {
                document.getElementById('docdatepickerfrom').value = "";
                document.getElementById('docdatepicker').value = "";
                document.getElementById('certType').value = "-1";
                document.getElementById('reportrange').value = "";
                $('#gridDiv').hide();
            }

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