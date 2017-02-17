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
        <style>
            a, a label {
                cursor: hand;
            }
        </style>
        <script>
            function doOnLoad()
            {
                $("#partnersList").addClass("active");
            }
        </script>
    </head>
    <body class="home" onload="doOnLoad()">
        <div>
            <s:include value="../includes/template/header.jsp"/>
        </div> 
        <header id=" ">
            <div class="container">
                <h3><b>Partners</b></h3>
            </div>
        </header>     
        <div class="container">
            <s:form action="searchPartner" method="POST" enctype="multipart/form-data" name="searchPartner" id="searchPartner" theme="simple">
                <div id="site_content" class="jumbotron">
                    <div class="container">
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Partner Name</label>
                                    <s:textfield cssClass="form-control"  name="partnerName" id="partnerName" value="%{partnerName}" placeholder="Partner Name"  tabindex="1"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Country</label>
                                    <s:select cssClass="form-control" headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'US':'USA','IN':'India','CN':'Canada','UK':'United Kingdom'}" name="country" id="country" value="%{country}"  tabindex="2"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Status</label>
                                    <s:select cssClass="form-control" headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'INACTIVE':'INACTIVE','ACTIVE':'ACTIVE','REJECTED':'REJECTED'}" name="status" id="status" value="%{status}"   tabindex="3"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="col-md-1 pull-right">
                                    <s:submit value="Search" cssClass="btn btn-primary pull-right" tabindex="4"/>
                                </div>
                                <div class="col-sm-2 col-md-1 pull-right">
                                    <input type="button" value="Reset" id="set_align" tabindex="5" class="btn btn-primary pull-right" onclick="resetValues();"/>
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
                    <center><div id="resultMsg"></div></center>
                        <s:if test="#session.tpoSearchPartnersList != null"> 
                        <table id="partnersTable" name="partnersTable" class="table table-bordered table-hover">
                            <thead>
                            <th>NAME</th>
                            <th>PHONE</th>
                            <th>COUNTRY</th>
                            <th>CREATED_BY</th>
                            <th>CREATED_DATE</th>
                            <th>STATUS</th>
                            <th>ACCEPT</th>
                            <th>REJECT</th>
                            </thead>
                            <tbody>
                                <%
                                    int roleId = (Integer) session.getAttribute(AppConstants.TPO_ROLE_ID);
                                    String loginId = session.getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                                    java.util.List list = (java.util.List) session.getAttribute(AppConstants.TPO_SearchPartnersList);
                                    if (list.size() != 0) {
                                        TpOnboardingBean tpOnboardingBean;
                                        for (int i = 0; i < list.size(); i++) {
                                            tpOnboardingBean = (TpOnboardingBean) list.get(i);
                                            int id = tpOnboardingBean.getId();
                                %>
                                <tr>
                                    <td>
                                        <%
                                            out.println(tpOnboardingBean.getPartnerName());
                                        %>
                                    </td>
                                    <td>
                                        <%
                                            out.println(tpOnboardingBean.getPhoneNo());
                                        %>
                                    </td>
                                    <td>
                                        <%
                                            out.println(tpOnboardingBean.getCountry());
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
                                    <td align="center">
                                        <%
                                            if (tpOnboardingBean.getStatus().equalsIgnoreCase("ACTIVE")) {
                                        %>
                                        <a style="disable:true;color:#d4cecd;"><span class="glyphicon glyphicon-ok-sign"></span></a>
                                            <% } else {
                                            %>  
                                        <a style="color: green;" href='javascript:getPartnerName("<%=loginId%>","<%=roleId%>","<%=id%>","<%=(tpOnboardingBean.getPartnerName())%>")' id="acceptButton" ><span class="glyphicon glyphicon-ok-sign"></span></a>
                                            <%  }
                                            %>
                                    </td>
                                    <td align="center">
                                        <%
                                            if (tpOnboardingBean.getStatus().equalsIgnoreCase("REJECTED")) {
                                        %>
                                        <a style="disable:true;color:#d4cecd;"><span class="glyphicon glyphicon-remove-sign"></span></a>
                                            <% } else {
                                            %>  
                                        <a style="color: red" href='javascript:partnerReject("<%=id%>","<%=(tpOnboardingBean.getPartnerName())%>")'><span class="glyphicon glyphicon-remove-sign"></span></a>
                                            <%  }
                                            %>
                                    </td>
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
        <div class="modal fade" id="myModal" data-backdrop="static" data-keyword="false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header"  style="border:0">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="backToPartnerList()"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Do you want Accept partner ?</h4>
                    </div>
                    <div class="modal-body" style="margin-top: -15px; margin-bottom: -25px;">
                        <input type="hidden" id="partnerId" name="partnerId"/>
                        <input type="hidden" id="roleId" name="roleId"/>
                        <input type="hidden" id="loginId" name="loginId"/>
                        <center><div id="loadingImage"></div>
                            <div id="tpResultMessage"></div></center>
                        <div class="row">   
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label>Partner Name<span class="text-danger">*</span></label>
                                    <s:textfield cssClass="form-control"  name="addpartnerName" id="addpartnerName" readonly="true"/>
                                </div>
                            </div>
                            <s:if test="#session.tpoRoleId == 1">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label>Assign To<span class="text-danger">*</span></label>
                                        <s:select headerKey="-1" headerValue="--select--" cssClass="form-control" list="adminUsersList" name="adminUsersList" id="adminUsersList" />
                                    </div>
                                </div>
                            </s:if>
                        </div>  
                        <div class="modal-footer" style="border:0;margin:0">
                            <div class="row">
                                <div class="col-md-12" style="margin-bottom:8px">
                                    <div class="col-md-2 pull-right"><input type="button" id="accept" value="Submit" class="btn btn-primary" onclick="acceptPartner();"/></div>
                                </div>
                            </div>
                        </div>
                    </div>
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
                                        $(function() {
                                            $('#partnersTable').DataTable({
                                                "paging": true,
                                                "lengthChange": true,
                                                "searching": true,
                                                "ordering": true,
                                                "info": true,
                                                "autoWidth": false
                                            });
                                        });

                                        $("#acceptButton").click(function() {
                                            $('#myModal').modal('show');
                                        });

                                        function getPartnerName(loginId, roleId, pId, pName) {
                                            document.getElementById("loginId").value = loginId;
                                            document.getElementById("roleId").value = roleId;
                                            document.getElementById('partnerId').value = pId;
                                            document.getElementById("addpartnerName").value = pName;
                                            $("#myModal").modal("show");
                                        }

                                        function backToPartnerList() {
                                            window.location = "../tpOnboarding/tpoPartnersList.action";
                                        }

                                        function resetValues() {
                                            document.getElementById("partnerName").value = "";
                                            document.getElementById("country").value = "-1";
                                            document.getElementById("status").value = "-1";
                                        }
    </script>
</html>
