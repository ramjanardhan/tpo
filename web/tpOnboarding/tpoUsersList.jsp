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
        <header id="head">
            <div class="container">
                <h3><b>Users</b></h3>
            </div>
        </header>     
        <div class="container">
            <s:form action="searchUser" method="POST" enctype="multipart/form-data" name="searchUser" id="searchUser" theme="simple">
                <div id="site_content" class="jumbotron">
                    <div class="container">
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>User Name</label>
                                    <s:textfield name="contactName" id="contactName" cssClass="form-control" value="%{contactName}" placeholder="User Name"  tabindex="1"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Country</label>
                                    <s:select name="country" id="country" cssClass="form-control" headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'US':'USA','IN':'India','CN':'Canada','UK':'United Kingdom'}" value="%{country}"  tabindex="2"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Status</label>
                                    <s:select name="status" id="status" cssClass="form-control" headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'A':'ACTIVE','I':'INACTIVE'}"  value="%{status}"   tabindex="3"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="col-sm-1 pull-right">
                                    <s:submit value="Search" cssClass="btn btn-primary pull-right" tabindex="4"/>
                                </div>
                                <div class="col-sm-1 pull-right">
                                    <input type="button" class="btn btn-primary pull-right" tabindex="5" value="Reset" onClick="this.form.reset();"/>
                            <!--    <input type="button" value="Reset" tabindex="5" class="btn btn-primary pull-right" onclick="resetUserList()"/>-->
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
                        <s:if test="#session.tpoSearchUsersList != null"> 
                        <table id="usersTable" name="usersTable" class="table table-bordered table-hover">
                            <thead>
                            <th>NAME</th>
                            <th>PHONE</th>
                            <th>COUNTRY</th>
                            <th>CREATED_BY</th>
                            <th>CREATED_DATE</th>
                            <th>STATUS</th>
<!--                            <th>ACTIVE</th>
                            <th>INACTIVE</th>-->
                            </thead>
                            <tbody>
                                <%
                                    int roleId = (Integer) session.getAttribute(AppConstants.TPO_ROLE_ID);
                                    String loginId = session.getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                                    java.util.List list = (java.util.List) session.getAttribute(AppConstants.TPO_SearchUsersList);
                                    if (list.size() != 0) {
                                        TpOnboardingBean tpOnboardingBean;
                                        for (int i = 0; i < list.size(); i++) {
                                            tpOnboardingBean = (TpOnboardingBean) list.get(i);
                                            int id = tpOnboardingBean.getId();
                                %>
                                <tr>
                                    <td>
                                        <%
                                            out.println(tpOnboardingBean.getContactName());
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
                                            if (tpOnboardingBean.getStatus().equalsIgnoreCase("I")) {
                                                out.println("<font color='red'>INACTIVE</font>");
                                            } else if (tpOnboardingBean.getStatus().equalsIgnoreCase("A")) {
                                                out.println("<font color='green'>ACTIVE</font>");
                                            }
                                            //out.println(tpOnboardingBean.getStatus());
                                        %>
                                    </td>
<%--                                    <td align="center">
                                         <a style="color: green;" href='javascript:getPartnerName("<%=loginId%>","<%=roleId%>","<%=id%>","<%=(tpOnboardingBean.getPartnerName())%>")' ><span class="glyphicon glyphicon-ok-sign"></span></a>
                                    </td>
                                    <td align="center">
                                        <a style="color: green;" href='javascript:getPartnerName("<%=loginId%>","<%=roleId%>","<%=id%>","<%=(tpOnboardingBean.getPartnerName())%>")' ><span class="glyphicon glyphicon-ok-sign"></span></a>
                                    </td>--%>
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
            $('#usersTable').DataTable({
                "paging": true,
                "lengthChange": true,
                "searching": true,
                "ordering": true,
                "info": true,
                "autoWidth": false
            });
        });

    </script>
</html>
