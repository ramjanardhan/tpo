<%-- 
    Author     : Narendar
--%>
<%@page import="com.mss.tpo.payload.PayloadBean"%>
<%@page import="com.mss.tpo.util.DataSourceDataProvider"%>
<%@page import="com.mss.tpo.tpOnboarding.TpOnboardingBean"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.mss.tpo.util.AppConstants"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Miracle TP On-boarding</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="cache-control" content="no-cache" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" media="screen" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700"/>
        <link rel="stylesheet" href='<s:url value="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap.min.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/main.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/build.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap-theme.css" />' media="screen" type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/plugins/datatables/dataTables.bootstrap.css"/>' type="text/css">
    </head>
    <body class="home" onload="doOnLoad()">
        <div>
            <s:include value="../includes/template/header.jsp"/>
        </div> 
        <header id="head">
            <div class="container">
                <h3><b>Exchange History</b></h3>
            </div>
        </header>     
        <div class="container">
            <s:form action="payloadSearch" method="POST" enctype="multipart/form-data" theme="simple">
                <div id="site_content" class="jumbotron">
                    <div class="container">
                        <div class="row" id="responseString">   
                            <center>
                                <%
                                    if (session.getAttribute(AppConstants.REQ_RESULT_MSG) != null) {
                                        String responseString = session.getAttribute(AppConstants.REQ_RESULT_MSG).toString();
                                        out.println(responseString);
                                        session.setAttribute(AppConstants.REQ_RESULT_MSG, null);
                                    }
                                %>
                            </center>
                        </div>
                        <div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Connection Type<span class="text-danger">*</span></label>
                                    <s:select name="conn_type" id="conn_type" headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'Communication_Protocol':'Communication Protocol','File_System':'File system'}" cssClass="form-control"/>         
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Direction :</label>
                                    <s:select name="direction" id="direction" headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'Inbound':'Inbound','Outbound':'Outbound'}" tabindex="1" value=""  cssClass="form-control"/>         
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Transaction :</label>
                                    <s:select headerKey="-1" headerValue="-- select --" list="txList" name="transaction" id="transaction" value="" cssClass="form-control" />
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="col-sm-1 pull-right">
                                        <s:submit value="Search" cssClass="btn btn-primary pull-right" tabindex="4" />
                                    </div>
                                    <div class="col-sm-1 pull-right">
                                        <%-- <s:submit  tabindex="5" cssClass="btn btn-primary pull-right"/> --%>
                                        <input type="button" value="Reset" tabindex="5" class="btn btn-primary pull-right" onclick="resetValues();"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id = "loadingImage"></div>
                    <div id="site_content" class="jumbotron">
                        <div class="container"> 
                            <s:if test="#session.payloadSearchList != null"> 
                                <table id="profiletable" class="table table-bordered table-hover">
                                    <thead>
                                    <th>CONNECTION&nbsp;TYPE</th>
                                    <th>COMMUNICATION&nbsp;ID</th>
                                    <th>INSTANCE&nbsp;ID</th>
                                    <th>TRANSACTION</th>
                                    <th>DIRECTION</th>
                                    <th>FILE&nbsp;NAME</th>
                                    <th>DOWNLOAD</th>
                                    <th>REPROCESS</th>
                                    <th>CUR&nbsp;TEST&nbsp;STATUS</th>
                                    <th>CUR&nbsp;TEST&nbsp;DATE</th>
                                    <th>LT&nbsp;TEST&nbsp;STATUS</th>
                                    <th>LT&nbsp;TEST&nbsp;DATE</th>
                                    </thead>
                                    <tbody>
                                        <%
                                            int partnerId = (Integer) session.getAttribute(AppConstants.TPO_PARTNER_ID);
                                            java.util.List list = (java.util.List) session.getAttribute(AppConstants.PAYLOAD_SEARCH_LIST);
                                            if (list.size() != 0) {
                                                PayloadBean payloadBean;
                                                for (int i = 0; i < list.size(); i++) {
                                                    payloadBean = (PayloadBean) list.get(i);
                                                  //  int id = 0;
                                        %>
                                        <tr>
                                            <td> <% out.println(payloadBean.getConnectionType()); %> </td>
                                            <td> <% if ((payloadBean.getCorrelationID()) == 0) {
                                                    out.println("--");
                                                } else {
                                                    out.println(payloadBean.getCorrelationID());
                                                }
                                                %> 
                                            </td> 
                                            <td> <% if ((payloadBean.getInstanceID()) == 0) {
                                                    out.println("--");
                                                } else {
                                                    out.println(payloadBean.getInstanceID());
                                                }
                                                %> 
                                            </td>
                                            <td> <% out.println(payloadBean.getTransaction()); %> </td>
                                            <td> <% out.println(payloadBean.getDirection()); %> </td>
                                            <td> <% if ((payloadBean.getFileName()) == null) {
                                                    out.println("--");
                                                } else {
                                                    out.println(payloadBean.getFileName());
                                                }%> </td>
                                            <td align="center">   
                                                <% String disable = "";
                                                    String path = "";
                                                    if ("Inbound".equalsIgnoreCase(payloadBean.getDirection())) {
                                                        if ((!("".equals(payloadBean.getOutputPath()))) && (payloadBean.getOutputPath() != null)) {
                                                            disable = "no";
                                                            path = (payloadBean.getOutputPath());
                                                        } else {
                                                            disable = "yes";
                                                        }
                                                    }
                                                    if ("Outbound".equalsIgnoreCase(payloadBean.getDirection())) {
                                                        if (payloadBean.getTransaction() == 850) {
                                                            if ((!("".equals(payloadBean.getInputPath()))) && (payloadBean.getInputPath() != null)) {
                                                                disable = "no";
                                                                path = (payloadBean.getInputPath());
                                                            } else {
                                                                disable = "yes";
                                                            }
                                                        } else {
                                                            if ((!("".equals(payloadBean.getOutputPath()))) && (payloadBean.getOutputPath() != null)) {
                                                                disable = "no";
                                                                path = (payloadBean.getOutputPath());
                                                            } else {
                                                                disable = "yes";
                                                            }
                                                        }
                                                    }
                                                    if ("no".equals(disable)) {%>
                                                <s:url var="downloadPayloadFileURL" action="../payload/downloadPayloadFile.action">
                                                    <s:param name="filepath"><%=(path)%></s:param> 
                                                </s:url>
                                                <s:a href='%{#downloadPayloadFileURL}' style="color: green;"><span class="glyphicon glyphicon-download"></span></s:a>
                                                <% } else {%>
                                                <a style="disable:true;color:#d4cecd;"><span class="glyphicon glyphicon-download"></span></a>
                                                    <%  }%> 
                                            </td>
                                            <td align="center">
                                                <s:url var="reprocessURL" action="../payload/reprocessPayloadData.action">
                                                    <s:param name="id"><%=(payloadBean.getId())%></s:param> 
                                                    <s:param name="direction"><%=(payloadBean.getDirection())%></s:param> 
                                                    <s:param name="inputPath"><%=(payloadBean.getInputPath())%></s:param> 
                                                    <s:param name="outputPath"><%=(payloadBean.getOutputPath())%></s:param> 
                                                    <s:param name="path"><%=(payloadBean.getPath())%></s:param> 
                                                </s:url>
                                                <% if ((!"".equals(payloadBean.getInputPath())) && ((payloadBean.getInputPath()) != null)) { %>
                                                <s:a href='%{#reprocessURL}' style="color: blue;"><span class="glyphicon glyphicon-refresh"></span></s:a>
                                                <% } else {%>
                                                <a style="disable:true;color:#d4cecd;"><span class="glyphicon glyphicon-refresh"></span></a>
                                                    <%  }%> 
                                            </td>
                                            <td> <% if ((payloadBean.getCurrentTestStatus()) == null) {
                                                    out.println("--");
                                                } else if (payloadBean.getCurrentTestStatus().equalsIgnoreCase("SUCCESS")) {
                                                    out.println("<font color='green'>" + payloadBean.getCurrentTestStatus() + "</font>");
                                                } else {
                                                    out.println("<font color='red'>" + payloadBean.getCurrentTestStatus() + "</font>");
                                                }
                                                %> </td>  
                                            <td> <% if ((payloadBean.getCurrentTestDate()) == null) {
                                                    out.println("--");
                                                } else {
                                                    out.println(payloadBean.getCurrentTestDate().toString().substring(0, payloadBean.getCurrentTestDate().toString().lastIndexOf(":")));
                                                }
                                                %> </td>  
                                            <td> <% if ((payloadBean.getLastTestStatus()) == null) {
                                                    out.println("--");
                                                } else if (payloadBean.getLastTestStatus().equalsIgnoreCase("SUCCESS")) {
                                                    out.println("<font color='green'>" + payloadBean.getLastTestStatus() + "</font>");
                                                } else {
                                                    out.println("<font color='red'>" + payloadBean.getLastTestStatus() + "</font>");
                                                }
                                                %> </td>  
                                            <td> <% if ((payloadBean.getLastTestDate()) == null) {
                                                    out.println("--");
                                                } else {
                                                    out.println(payloadBean.getLastTestDate().toString().substring(0, payloadBean.getLastTestDate().toString().lastIndexOf(":")));
                                                }
                                                %> </td>  
                                        </tr>
                                        <%
                                                }
                                            }
                                        %>
                                    </tbody>
                                </table>
                            </s:if> 
                        </div>
                    </div>
                </div>
            </s:form>
        </div>
        <footer class="footer">
            <div class=" ">
                <s:include value="../includes/template/footer.jsp"/>
            </div>
        </footer>
        <script language="JavaScript" src='<s:url value="/includes/js/headroom.min.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/jQuery.headroom.min.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/template.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/tpOnbordingDeatails.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/GeneralAjax.js"/>'></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
        <script src='<s:url value="../includes/plugins/datatables/jquery.dataTables.min.js"/>'></script>
        <script src='<s:url value="../includes/plugins/datatables/dataTables.bootstrap.min.js"/>'></script>
        <script>
                                            function doOnLoad() {
                                                $("#PayLoad").addClass("active");
                                            }

                                            $(function() {
                                                $('#profiletable').DataTable({
                                                    "paging": true,
                                                    "lengthChange": true,
                                                    "searching": true,
                                                    "ordering": true,
                                                    "info": true,
                                                    "autoWidth": false
                                                });
                                            });

                                            function resetValues() {
                                                document.getElementById("transaction").value = "-1";
                                                document.getElementById("direction").value = "-1";
                                                document.getElementById("conn_type").value = "-1";
                                            }
        </script>
    </body>
</html>