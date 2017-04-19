<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.mss.tpo.util.AppConstants"%>
<%@page import="com.mss.tpo.admin.AdminBean"%>
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
        <script>
            function doOnLoad() {
                $("#partners").addClass("active");
                var manageCommunication = document.forms["manageCommunicationForm"]["manageCommunication"].value;
                if (manageCommunication != 0) {
                    getManageCommunication(manageCommunication, 'onload');
                }
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
                <h3><b>Manage Communication</b></h3>
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
                         <div id="resultMsg"></div>
                    <div id="loadingImage"></div>
                        <%
                            if (session.getAttribute(AppConstants.REQ_RESULT_MSG) != null) {
                                String responseString = session.getAttribute(AppConstants.REQ_RESULT_MSG).toString();
                                out.println(responseString);
                                session.setAttribute(AppConstants.REQ_RESULT_MSG, null);
                            }
                        %>
                    </center>
                    <s:form action="doManageCommunicationAdd" method="post" cssClass="contact-form" name="manageCommunicationForm" id="manageCommunicationForm" theme="simple">
                        <s:hidden name="formAction" id="formAction" value="%{formAction}"/>
                        <div class="">
                            <div>
                                <div class="row"> 
                                    <div class="col-sm-6"><s:radio name="manageCommunication" id="" list="{'Assign Communication','Remove Communication'}" value="%{manageCommunication}" cssClass="from-control" onchange="getManageCommunication(this.value,'radio');" tabindex="7"></s:radio></div>
                                    </div>
                                </div>
                                <br>
                                <div class="row" id="connTypeDiv" style="display: none">
                                    <div class="col-sm-3"> 
                                        <div class="form-group" >
                                            <label>Partner List<span class="text-danger">*</span></label>
                                        <s:select headerKey="-1" headerValue="-- Select --" list="PartnerNameList" tabindex="5" name="partnerId" id="partnerId" value="%{partnerId}"  cssClass="form-control" />         
                                    </div> 
                                </div> 
                                <div class="col-sm-3"> 
                                    <div class="form-group">
                                        <label>Protocol<span class="text-danger">*</span></label>
                                        <s:select headerKey="-1" headerValue="-- Select --" list="protocolList" name="protocol" id="protocol" value="%{protocol}" cssClass="form-control"  tabindex="6" onchange="getProtocolList(this.value);"/>         
                                    </div> 
                                </div>
                            </div>
                        </div>   
                        <div class="container" id="communicationsGrid">
                            <s:if test="#session.tpoCommunicationsList != null"> 
                                <label>Available Communications<span class="text-danger">*</span></label>
                                <div id="site_content" class="jumbotron" style="padding-left: 0px">
                                    <div class="container">
                                        <table id="ManageCommunicationTable" name="ManageCommunicationTable" class="table table-bordered table-hover">
                                            <thead>
                                                <tr>
                                                    <th>Select</th>
                                                        <%
                                                            String protocol = session.getAttribute("protocol").toString();
                                                        %>
                                                        <% if ("FTP".equalsIgnoreCase(protocol)) {%>
                                                    <th>ID</th>
                                                    <th>HOST</th>    
                                                    <th>PORT</th>    
                                                    <th>USER&nbsp;ID</th>   
                                                        <% }%>
                                                        <% if ("SFTP".equalsIgnoreCase(protocol)) {%>
                                                    <th>ID</th>
                                                    <th>HOST</th>    
                                                    <th>PORT</th>    
                                                    <th>USER&nbsp;ID</th>     
                                                        <% }%>
                                                        <% if ("HTTP".equalsIgnoreCase(protocol)) {%>
                                                    <th>ID</th>
                                                    <th>END&nbsp;POINT</th>    
                                                    <th>PORT</th>    
                                                    <th>URL</th>   
                                                        <% }%>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%
                                                    java.util.List list = (java.util.List) session.getAttribute(AppConstants.TPO_CommunicationsList);
                                                    if (list.size() != 0) {
                                                        AdminBean adminBean;
                                                        for (int i = 0; i < list.size(); i++) {
                                                            adminBean = (AdminBean) list.get(i);
                                                            if ("FTP".equalsIgnoreCase(protocol)) {%>
                                                <tr>
                                                    <td align="center"><input type="radio" name="CommunicationMesId" id="CommunicationMesId" value="<%=(adminBean.getId())%>" /></td>
                                                    <td> <% out.println(adminBean.getId()); %> </td>
                                                    <td> <% out.println(adminBean.getFtp_host()); %> </td>
                                                    <td> <% out.println(adminBean.getFtp_port()); %> </td>
                                                    <td> <% out.println(adminBean.getFtp_userId()); %> </td>
                                                </tr>
                                                <%  }
                                                    if ("SFTP".equalsIgnoreCase(protocol)) {%>
                                                <tr>
                                                    <td align="center"><input type="radio" name="CommunicationMesId" value="<%=(adminBean.getId())%>" /></td>
                                                    <td> <% out.println(adminBean.getId()); %> </td>
                                                    <td> <% out.println(adminBean.getSftp_host_ip()); %> </td>
                                                    <td> <% out.println(adminBean.getSftp_remote_port()); %> </td>
                                                    <td> <% out.println(adminBean.getSftp_remote_userId()); %> </td>
                                                </tr>
                                                <%  }
                                                    if ("HTTP".equalsIgnoreCase(protocol)) {%>
                                                <tr>
                                                    <td align="center"><input type="radio" name="CommunicationMesId" value="<%=(adminBean.getId())%>" /></td>
                                                    <td> <% out.println(adminBean.getId()); %> </td>
                                                    <td> <% out.println(adminBean.getHttp_endpoint()); %> </td>
                                                    <td> <% out.println(adminBean.getHttp_port()); %> </td>
                                                    <td> <% out.println(adminBean.getHttp_url()); %> </td>
                                                </tr>
                                                <%  }
                                                        }
                                                    }%>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </s:if> 
                        </div>
                        <div class="col-sm-12">
                            <div class="col-sm-1 pull-right" id="saveButton" style="display: none">
                                <s:submit value="Assign" cssClass="btn btn-primary pull-right" tabindex="7" onclick="return checkManageCommunications()"/>
                            </div>
                            <div class="col-sm-1 pull-right" id="removeButton" style="display: none">
                                <s:submit value="Remove" cssClass="btn btn-primary pull-right" tabindex="7" onclick="return checkManageCommunications()"/>
                            </div>
                        </div>
                    </s:form>
                </div>
            </div>
            <!-- /Highlights -->
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
        <script language="JavaScript" src='<s:url value="/includes/js/adminValidations.js"/>'></script>
        <script>
        $(function() {
            $('#ManageCommunicationTable').DataTable({
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
