<%-- 
    Author     : Narendar
--%>
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.mss.tpo.util.AppConstants"%>
<%@page import="com.mss.tpo.util.DataSourceDataProvider"%>
<%@page import="com.mss.tpo.util.DataSourceDataProvider"%>
<%@page import="com.mss.tpo.tpOnboarding.TpOnboardingBean"%>
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
        <link rel="stylesheet" href='<s:url value="../includes/plugins/datatables/dataTables.bootstrap.css"/>' type="text/css">
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
            .block_div{
                overflow: hidden; 
            }
            @media only screen and (max-width: 465px) {
                #Synchronous {
                    word-wrap: break-word;
                }
                #site_content{
                    padding: 3px 12px;
                }
            } 
            @media  (min-width: 992px) and (max-width: 1192px) {
                #set_align{
                    margin: 0 16px;
                }
            }
        </style>
    </head>
    <body class="home" onload="doOnLoad()">
        <div>
            <s:include value="../includes/template/header.jsp"/>
        </div> 
        <header id="">
            <div class="container">
                <h3><b>Profile Search</b></h3>
            </div>
        </header>     
        <div class="container">
            <div id="profileDeleteMsg"></div>
            <s:form action="searchProfiles" method="POST" enctype="multipart/form-data" name="searchTpOnboard" id="searchTpOnboard" theme="simple">
                <div id="site_content" class="jumbotron block_div">
                    <div class="">
                        <center>
                            <%
                                if (session.getAttribute(AppConstants.REQ_RESULT_MSG) != null) {
                                    String responseString = session.getAttribute(AppConstants.REQ_RESULT_MSG).toString();
                                    out.println(responseString);
                                    session.setAttribute(AppConstants.REQ_RESULT_MSG, null);
                                }
                            %>
                        </center>
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Protocol Name</label>
                                    <s:select name="commnProtocol" id="commnProtocol" headerKey="-1" headerValue="-- Select --" list="protocolList" tabindex="1" value="%{commnProtocol}"  cssClass="form-control" onchange="showTransferDiv()"/>         
                                </div>
                            </div>
                            <div class="col-sm-3" id="TransferDiv" style="display:none">
                                <div class="form-group">
                                    <label>Transfer Mode :</label>
                                    <div class="col-sm-12" style="padding: 0">
                                        <s:radio name="transferMode" id="transferMode" list="{'pull','push'}" onchange="gettransferModeSelection(this.value)" cssClass="from-control"  tabindex="2"></s:radio>&nbsp;&nbsp;
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>Status</label>
                                    <s:select name="status" id="status" headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'ACTIVE':'ACTIVE','INACTIVE':'INACTIVE','REJECTED':'REJECTED'}" tabindex="3" value="%{status}"  cssClass="form-control"/>         
                                </div>
                            </div>
                            <div class="row" style="margin:4% 0">
                                <div class="col-sm-12">
                                    <div class="col-sm-2  col-md-1 pull-right">
                                        <s:submit value="Search" cssClass="btn btn-primary pull-right" tabindex="4" />
                                    </div>
                                    <div class="col-sm-1 pull-right">
                                        <%-- <s:submit  tabindex="5" cssClass="btn btn-primary pull-right"/> --%>
                                        <input type="button" value="Reset" id="set_align" tabindex="5" class="btn btn-primary pull-right" onclick="resetValues();"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="site_content" class=" ">
                        <div class=" "> 
                            <div id = "loadingImage"></div>
                            <s:if test="#session.tpoSearchProfileList != null"> 
                                <table id="profiletable" class="table table-bordered table-hover">
                                    <thead>
                                    <th>ID</th>
                                    <th>PROTOCOL</th>
                                    <th>CREATED&nbsp;BY</th>
                                    <th>CREATED&nbsp;DATE</th>
<!--                                    <th>TEST</th>-->
                                    <!--  <th>TEST&nbsp;STATUS</th>-->
                                    <th>EDIT</th>
                                    <th>DELETE </th>
                                    </thead>
                                    <tbody>
                                        <%
                                            int partnerId = (Integer) session.getAttribute(AppConstants.TPO_PARTNER_ID);
                                            String partnerName = DataSourceDataProvider.getInstance().getTpoPartnerName(partnerId);
                                            java.util.List list = (java.util.List) session.getAttribute(AppConstants.TPO_SearchProfileList);
                                            if (list.size() != 0) {
                                                TpOnboardingBean tpOnboardingBean;
                                                for (int i = 0; i < list.size(); i++) {
                                                    tpOnboardingBean = (TpOnboardingBean) list.get(i);%>
                                        <tr>
                                            <td> <% out.println(tpOnboardingBean.getId()); %> </td>
                                            <td> <% out.println(tpOnboardingBean.getCommnProtocol()); %> </td>
                                            <td> <% out.println(tpOnboardingBean.getCreated_by()); %> </td>  
                                            <td> <% out.println(tpOnboardingBean.getCreated_ts().toString().substring(0, tpOnboardingBean.getCreated_ts().toString().lastIndexOf(":")));%> </td>
                                           <%-- <td align="center">   
                                           <!--  <s:hidden id = "iValue"/>-->
                                                <a style="color: green" href='javascript:testConnectionProfile("<%=i%>","<%=(tpOnboardingBean.getId())%>","<%=(tpOnboardingBean.getCommnProtocol())%>","<%=partnerName%>")'><span class="glyphicon glyphicon-circle-arrow-right"></span></a>
                                            </td>--%>
                                            <%--  <td> <div id = "<%=i%>"></div> </td>--%>
                                            <td align="center">
                                                <s:url var="myUrl" action="../tpOnboarding/tpogetProfile.action">
                                                    <s:param name="communicationId"><%=(tpOnboardingBean.getId())%></s:param>
                                                    <s:param name="commnProtocol"><%=(tpOnboardingBean.getCommnProtocol())%></s:param>
                                                </s:url>
                                                <%
                                                    String disable = "";
                                                    if ("push".equalsIgnoreCase((tpOnboardingBean.getTransferMode()))) {
                                                        if (("FTP".equalsIgnoreCase((tpOnboardingBean.getCommnProtocol()))) || ("SFTP".equalsIgnoreCase((tpOnboardingBean.getCommnProtocol())))) {
                                                            disable = "no";
                                                        }
                                                    } else if ("pull".equalsIgnoreCase((tpOnboardingBean.getTransferMode()))) {
                                                        if ("HTTP".equalsIgnoreCase((tpOnboardingBean.getCommnProtocol()))) {
                                                            disable = "no";
                                                        }
                                                    } else if (("AS2".equalsIgnoreCase((tpOnboardingBean.getCommnProtocol()))) || ("SMTP".equalsIgnoreCase((tpOnboardingBean.getCommnProtocol())))) {
                                                        disable = "no";
                                                    } else {
                                                        disable = "yes";
                                                    }
                                                    if ("no".equalsIgnoreCase(disable)) {
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
                                        <tr>
                                            <td align="right" colspan="28" style="background-color: white;"> </td>
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
                                                document.getElementById("commnProtocol").value = "-1";
                                                document.getElementById("status").value = "-1";
                                                document.getElementById("transferMode").checked = false;
                                            }
        </script>
    </body>
</html>
