<%-- 
    Document   : tpoSearchProfile
    Created on : Dec 27, 2016, 4:45:23 AM
    Author     : miracle1
--%>
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
        <script>
            function doOnLoad() {
                $("#profiles").addClass("active");
                showTransferDiv();
            }
            function showTransferDiv() {
                var protocol = document.getElementById("commnProtocol").value;
                if ((protocol == 'FTP') || (protocol == 'SFTP') || (protocol == 'HTTP')) {
                    document.getElementById("TransferDiv").style.display = "none";
                } else {
                    document.getElementById("TransferDiv").style.display = "none";
                }
            }
        </script>
        <style>
            #transferModeget{
                margin:5px;
            }
            #transferModeput{
                margin:5px;
            }
        </style>
    </head>
    <body class="home" onload="doOnLoad()">
        <div>
            <s:include value="../includes/template/header.jsp"/>
        </div> 
        <header id="head">
            <div class="container">
                <h3><b>Profile Search</b></h3>
            </div>
        </header>     
        <div class="container">
            <div id="profileDeleteMsg"></div>
            <s:form action="searchProfiles" method="POST" enctype="multipart/form-data" name="searchTpOnboard" id="searchTpOnboard" theme="simple">
                <div id="site_content" class="jumbotron">
                    <div class="container">
                        <center><font style="background-color: #96d1f8;position: static;border: 0px solid white;">
                            <%
                                if (session.getAttribute(AppConstants.REQ_RESULT_MSG) != null) {
                                    String reqponseString = session.getAttribute(AppConstants.REQ_RESULT_MSG).toString();
                                    out.println(reqponseString);
                                    session.setAttribute(AppConstants.REQ_RESULT_MSG, null);
                                }
                            %>
                            </font></center>
                        <div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Protocol Name</label>
                                    <s:select name="commnProtocol" id="commnProtocol" headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'FTP':'FTP/FTPS','AS2':'AS2','SFTP':'SFTP','HTTP':'HTTP/HTTPS','SMTP':'SMTP'}" tabindex="1" value="%{commnProtocol}"  cssClass="form-control" onchange="showTransferDiv()"/>         
                                </div>
                            </div>
                            <div class="col-sm-3" id="TransferDiv" style="display:none">
                                <div class="form-group">
                                    <label>Transfer Mode :</label>
                                    <div class="col-sm-12" style="padding: 0">
                                        <s:radio name="transferMode" id="transferMode" list="{'get','put'}" onchange="gettransferModeSelection(this.value)" cssClass="from-control"  tabindex="2"></s:radio>&nbsp;&nbsp;
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>Status</label>
                                    <s:select name="status" id="status" headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'ACTIVE':'ACTIVE','INACTIVE':'INACTIVE','REJECTED':'REJECTED'}" tabindex="3" value="%{status}"  cssClass="form-control"/>         
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
                    <div id="site_content" class="jumbotron">
                        <div class="container"> 
                            <s:if test="#session.tpoSearchProfileList != null"> 
                                <table id="profiletable" class="table table-bordered table-hover">
                                    <thead>
                                    <th>COMM_ID</th>
                                    <th>PROTOCOL</th>
                                    <th>TRANSFER&nbsp;MODE</th>
                                    <th>STATUS</th>
                                    <th>CREATED&nbsp;BY</th>
                                    <th>CREATED&nbsp;DATE</th>
                                    <th>EDIT</th>
                                    <th>DELETE </th>
                                    </thead>
                                    <tbody>
                                        <%
                                            java.util.List list = (java.util.List) session.getAttribute(AppConstants.TPO_SearchProfileList);
                                            if (list.size() != 0) {
                                                TpOnboardingBean tpOnboardingBean;
                                        %>
                                        <%
                                            for (int i = 0; i < list.size(); i++) {
                                                tpOnboardingBean = (TpOnboardingBean) list.get(i);%>
                                        <tr>
                                            <td>
                                                <%
                                                    out.println(tpOnboardingBean.getId());
                                                %>
                                            </td>
                                            <td>
                                                <%
                                                    out.println(tpOnboardingBean.getCommnProtocol());
                                                %>
                                            </td>
                                            <td>
                                                <%
                                                    out.println(tpOnboardingBean.getTransferMode());
                                                %>
                                            </td>
                                            <td>
                                                <%
                                                    if (tpOnboardingBean.getStatus().equalsIgnoreCase("REJECTED")) {
                                                        out.println("<font color='red'>" + tpOnboardingBean.getStatus() + "</font>");
                                                    } else if (tpOnboardingBean.getStatus().equalsIgnoreCase("ACTIVE")) {
                                                        out.println("<font color='green'>" + tpOnboardingBean.getStatus() + "</font>");
                                                    } else {
                                                        out.println("<font color='orange'>" + tpOnboardingBean.getStatus() + "</font>");
                                                    }
                                                    //out.println(tpOnboardingBean.getStatus());
                                                %>
                                            </td>
                                            <td>
                                                <%
                                                    out.println(tpOnboardingBean.getCreated_by().toUpperCase());
                                                %>
                                            </td>  
                                            <td>
                                                <%
                                                    out.println(tpOnboardingBean.getCreated_ts().toString().substring(0, tpOnboardingBean.getCreated_ts().toString().lastIndexOf(":")));
                                                %>
                                            </td>
                                            <td align="center">
                                                <s:url var="myUrl" action="../tpOnboarding/tpogetProfile.action">
                                                    <s:param name="communicationId"><%=(tpOnboardingBean.getId())%></s:param>
                                                    <s:param name="commnProtocol"><%=(tpOnboardingBean.getCommnProtocol())%></s:param>
                                                </s:url>
                                                <%
                                                    String disableButton = "";
                                                    if ("put".equalsIgnoreCase((tpOnboardingBean.getTransferMode()))) {
                                                        if (("FTP".equalsIgnoreCase((tpOnboardingBean.getCommnProtocol()))) || ("SFTP".equalsIgnoreCase((tpOnboardingBean.getCommnProtocol())))) {
                                                            disableButton = "no";
                                                        }
                                                    } else if ("get".equalsIgnoreCase((tpOnboardingBean.getTransferMode()))) {
                                                        if ("HTTP".equalsIgnoreCase((tpOnboardingBean.getCommnProtocol()))) {
                                                            disableButton = "no";
                                                        }
                                                    } else if (("AS2".equalsIgnoreCase((tpOnboardingBean.getCommnProtocol()))) || ("SMTP".equalsIgnoreCase((tpOnboardingBean.getCommnProtocol())))) {
                                                        disableButton = "no";
                                                    } else {
                                                        disableButton = "yes";
                                                    }
                                                    if ("no".equalsIgnoreCase(disableButton)) {
                                                %>
                                                <s:a href='%{#myUrl}' style="color: blue;"><span class="glyphicon glyphicon-pencil"></span></s:a>
                                                <% } else {
                                                %>  
                                                <s:a href='' style="disable:true;" ><span class="glyphicon glyphicon-pencil"></span></s:a>
                                                <%  }
                                                %>
                                            </td>
                                            <td align="center">   
                                                <s:url var="myUrl1" action="../tpOnboarding/tpoDeleteProfile.action">
                                                    <s:param name="communicationId"><%=(tpOnboardingBean.getId())%></s:param> 
                                                    <s:param name="commnProtocol"><%=(tpOnboardingBean.getCommnProtocol())%></s:param> 
                                                    <s:param name="transferMode"><%=(tpOnboardingBean.getTransferMode())%></s:param> 
                                                </s:url>
                                                <s:a href='%{#myUrl1}' style="color: red;"><span class="glyphicon glyphicon-trash"></span></s:a>
                                                </td>
                                            </tr>
                                        <%
                                            }
                                        } else {
                                            if (list.size() != 0) {
                                        %>
                                        <tr >
                                            <td align="right" colspan="28" style="background-color: white;">
                                            </td>
                                        </tr> 
                                    </tbody>
                                    <%
                                            }
                                        }
                                    %>
                                </table>
                            </s:if> 
                        </s:form>
                    </div>
                </div>
            </div>
        </div>
        <footer class="footer">
            <div class=" ">
                <s:include value="../includes/template/footer.jsp"/>
            </div>
        </footer>
        <script language="JavaScript" src='<s:url value="/includes/js/tpOnbordingDeatails.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/GeneralAjax.js"/>'></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
        <script src='<s:url value="../includes/plugins/datatables/jquery.dataTables.min.js"/>'></script>
        <script src='<s:url value="../includes/plugins/datatables/dataTables.bootstrap.min.js"/>'></script>  
        <script>
                                            $(function () {
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
                                                document.getElementById("commnProtocol").value = "-1";
                                                document.getElementById("status").value = "-1";
                                                document.getElementById("transferMode").checked = false;
                                            }
        </script>
    </body>
</html>
