

<%-- 
    Document   : tpoSearchProfile
    Created on : Dec 27, 2016, 4:45:23 AM
    Author     : miracle1
--%>
<%@page import="com.mss.tpo.payload.PayloadBean"%>
<%@page import="com.mss.tpo.util.DataSourceDataProvider"%>
<%@page import="com.mss.tpo.tpOnboarding.TpOnboardingBean"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.mss.tpo.util.AppConstants"%>
<%@page import="com.mss.tpo.util.DataSourceDataProvider"%>
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
                $("#PayLoad").addClass("active");
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
                <h3><b>Payload History</b></h3>
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
                                    <label>Direction :</label>
                                    <s:select name="direction" id="direction" headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'Inbound':'Inbound','Outbound':'Outbound'}" tabindex="1" value=""  cssClass="form-control"/>         
                                </div>
                            </div>
                            
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>Transaction :</label>
                                         <s:select headerKey="-1" headerValue="-- select --" list="txList" name="txList" id="txList" value="" cssClass="form-control" />
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
                            <div id = "loadingImage"></div>
                            <s:if test="#session.payloadSearchList != null"> 
                                <table id="profiletable" class="table table-bordered table-hover">
                                    <thead>
                                    <th>CORRELATION&nbsp;ID</th>
                                    <th>TRANSACTION</th>
                                    <th>DIRECTION</th>
                                    <th>LT&nbsp;TEST&nbsp;STATUS</th>
                                    <th>LT&nbsp;TEST&nbsp;DATE</th>
                                    <th>CUR&nbsp;TEST&nbsp;STATUS</th>
                                    <th>CUR&nbsp;TEST&nbsp;DATE</th>                                    
                                    </thead>
                                    <tbody>
                                        
                                        <%
                                            
                                     int partnerId = (Integer) session.getAttribute(AppConstants.TPO_PARTNER_ID);
                                      String partnerName = DataSourceDataProvider.getInstance().getTpoPartnerName(partnerId);  
                                      session.getAttribute(AppConstants.TPO_PARTNER_ID);                                     
                                      java.util.List list = (java.util.List) session.getAttribute(AppConstants.PAYLOAD_SEARCH_LIST);
                                      System.out.println("list ==== "+list);
                                      if (list.size() != 0) {
                                                PayloadBean payloadBean;%>
                                                
                                            <%
                                                for (int i = 0; i < list.size(); i++) {
                                                payloadBean = (PayloadBean) list.get(i);
                                                
                                          %>
                                          
                                        <tr>
                                            <td>
                                                <%
                                                     out.println(payloadBean.getCorrelationID());
                                                %>
                                            </td>
                                            <td>
                                                <%
                                                   out.println(payloadBean.getTransaction());
                                                %>
                                            </td>
                                            <td>
                                                <%
                                                    out.println(payloadBean.getDirection());
                                                %>
                                            </td>
                                           
                                            <td>
                                                <%
                                                    out.println(payloadBean.getLastTestStatus());
                                                %>
                                            </td>  
                                            <td>
                                                <%
                                                    out.println(payloadBean.getLastTestDate().toString().substring(0, payloadBean.getLastTestDate().toString().lastIndexOf(":")));
                                                %>
                                            </td>                                           
                                            
                                             <td>
                                                <%
                                                   out.println(payloadBean.getCurrentTestStatus());
                                                %>
                                            </td>
                                            
                                             <td>
                                                <%
                                                    out.println(payloadBean.getCurrentTestDate().toString().substring(0, payloadBean.getCurrentTestDate().toString().lastIndexOf(":")));
                                                %>
                                            </td>
                                        </tr>
                                    </tbody>
                                    <%
                                            }
                                        }
                                    %>
                                </table>
                            </s:if> 
                             <%        session.setAttribute(AppConstants.PAYLOAD_SEARCH_LIST, null);   %>
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