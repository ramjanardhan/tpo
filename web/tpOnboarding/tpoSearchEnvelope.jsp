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
                $("#envelopes").addClass("active");
            }
        </script>
    </head>
    <body class="home" onload="doOnLoad()">
        <div>
            <s:include value="../includes/template/header.jsp"/>
        </div> 
        <header id="head">
            <div class="container">
                <h3><b>Envelope Search</b></h3>
            </div>
        </header>     
        <div class="container">
            <s:form action="searchEnvelopes" method="POST" enctype="multipart/form-data" name="searchEnvelopes" id="searchEnvelopes" theme="simple">
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
                                    <label>Transactions</label>
                                    <s:select headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'850':'850','855':'855','856':'856','810':'810'}" tabindex="1" name="transaction" id="transaction" value="%{transaction}"  cssClass="form-control" />         
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Direction</label>
                                    <s:select headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'Inbound':'Inbound','Outbound':'Outbound'}" tabindex="2" name="direction" id="direction" value="%{direction}"  cssClass="form-control" />         
                                </div>
                            </div>
                        </div>
                        <div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="col-sm-1 pull-right">
                                        <s:submit value="Search" cssClass="btn btn-primary pull-right" tabindex="3"/>
                                    </div>
                                    <div class="col-sm-1 pull-right">
                                        <input type="button" value="Reset" tabindex="4" class="btn btn-primary pull-right" onclick="resetValues();"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </s:form>
        </div>
        <div class="container">
            <div id="site_content" class="jumbotron">
                <div class="container">
                    <div id="loadingImage"></div>
                    <s:if test="#session.tpoSearchEnvelopeList != null"> 
                        <table id="envelopetable" name="envelopetable" class="table table-bordered table-hover">
                            <thead>
                            <th>TRANSACTION</th>
                            <th>DIRECTION</th>
                            <th>CREATED_BY</th>
                            <th>CREATED_TS</th>
                            <th>EDIT</th>
                            <th>DELETE</th>
                            </thead>
                            <tbody>
                                <%
                                    int id;
                                    id = (Integer) session.getAttribute(AppConstants.TPO_PARTNER_ID);
                                    java.util.List list = (java.util.List) session.getAttribute(AppConstants.TPO_SearchEnvelopeList);
                                    if (list.size() != 0) {
                                        TpOnboardingBean tpOnboardingBean;

                                        for (int i = 0; i < list.size(); i++) {
                                            tpOnboardingBean = (TpOnboardingBean) list.get(i);%>
                                <tr>
                                    <td>
                                        <%
                                            out.println(tpOnboardingBean.getTransaction());
                                        %>
                                    </td>
                                    <td>
                                        <%
                                            out.println(tpOnboardingBean.getDirection());
                                        %>
                                    </td>
                                    <td>
                                        <%
                                            out.println(tpOnboardingBean.getCreated_by());
                                        %>
                                    </td>
                                    <td>
                                        <%
                                            out.println(tpOnboardingBean.getCreated_ts());
                                        %>
                                    </td>
                                    <td align="center">
                                        <s:url var="myUrl" action="../tpOnboarding/tpoEditEnvelope.action">
                                            <s:param name="transaction"><%=(tpOnboardingBean.getTransaction())%></s:param> 
                                            <s:param name="direction"><%=(tpOnboardingBean.getDirection())%></s:param> 
                                        </s:url>
                                        <s:a href='%{#myUrl}' style="color: blue;"><span class="glyphicon glyphicon-pencil"></s:a>
                                        </td>
                                        <td align="center">
                                        <s:url var="myUrl1" action="../tpOnboarding/tpoDeleteEnvelope.action">
                                            <s:param name="regpartnerId"><%=id%></s:param> 
                                            <s:param name="transaction"><%=(tpOnboardingBean.getTransaction())%></s:param> 
                                            <s:param name="direction"><%=(tpOnboardingBean.getDirection())%></s:param> 
                                        </s:url>
                                        <s:a href='%{#myUrl1}' style="color: red;"><span class="glyphicon glyphicon-trash"></span></s:a>
                                        </td>
                                    </tr>
                                <%
                                        }
                                    }%>
                            </tbody>
                        </table>
                    </s:if> 
                </div>
            </div>
        </div>
    </body>
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
                                                $('#envelopetable').DataTable({
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
                                            }
    </script>
</html>
