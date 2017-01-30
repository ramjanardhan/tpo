<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/build.css"/>' type="text/css"/>
<!-- Fixed navbar -->
<div class="navbar navbar-inverse" >
    <div class="container">
        <div class="navbar-header">
            <!-- Button for smallest screens -->
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"><span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
            <a class="navbar-brand" href="index.html"><img src="http://www.miraclesoft.com/images/logo.png" alt="miraclesoft"></a>
        </div>
        <div class="navbar-collapse collapse">
            <div class="row">
                <ul class="nav navbar-nav pull-right">
                    <li><a><b>Welcome TPO</b></a></li>
                    <li><a href="#">
                            <s:if test="#session.tpoLoginId != null">
                                <b><font color="white"><s:property value="#session.tpoContactName" /></font></b>
                                </s:if>
                                <s:else>
                                <b><font color="white">Guest</font></b>
                            </s:else></a>
                    </li>
                    <li> <s:if test="#session.tpoLoginId != null">
                            <a class="btn" href="<s:url value="../general/tpoLogout"/>">Logout</a>  
                        </s:if>
                        <s:else>
                            <a class="btn" href="<s:url action="../general/login"/>">LogIn</a>
                        </s:else>
                    </li>
                </ul>
            </div>
            <div class="row">
                <s:if test='%{#session.tpoRoleId== 1 || #session.tpoRoleId== 2 || #session.tpoRoleId== 3 || #session.tpoRoleId== 4 || #session.tpoRoleId== 5}'>
                    <ul class="nav navbar-nav pull-right menu_tab" style="border-style: solid solid none; border-color: rgb(255, 255, 255) rgb(255, 255, 255) -moz-use-text-color; -moz-border-top-colors: none; -moz-border-right-colors: none; -moz-border-bottom-colors: none; -moz-border-left-colors: none; border-image: none; border-width: 4px 1px 0px; margin-right: 423px; border-radius: 2px;">
                        <s:if test='%{#session.tpoRoleId== 1 || #session.tpoRoleId== 2}'>
                            <li id="partnerAdd"><a href="<s:url action="tpoPartnerAdd"/>"><i class="fa fa-briefcase" aria-hidden="true"></i>Add Partner</a></li>
                            <li id="partnersList"><a href="<s:url action="tpoPartnersList"/>"><i class="fa fa-handshake-o" aria-hidden="true"></i>Partners List</a></li>
                        </s:if> 
                        <s:if test='%{#session.tpoRoleId== 3 || #session.tpoRoleId== 4 || #session.tpoRoleId== 5}'>
                            <li class="dropdown" id="profiles">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i class="fa fa-user" aria-hidden="true"></i><span class="hidden-xs hidden-sm">Profiles</span><b class="caret"></b></a>
                                <ul class="dropdown-menu">
                                    <li id="manageProfiles"><a href="<s:url action="tpoManageProfiles"/>">Manage/Edit</a></li>
                                    <li id="addProfile"><a href="<s:url action="tpoAddProfile"/>">Add Profile</a></li>
                                </ul>
                            </li>
                            <li class="dropdown" id="envelopes">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i class="fa fa-envelope" aria-hidden="true"></i><span class="hidden-xs hidden-sm">Envelopes</span><b class="caret"></b></a>
                                <ul class="dropdown-menu">
                                    <li id="manageEnvelopes"><a href="<s:url action="tpoManageEnvelopes"/>">Manage/Edit</a></li>
                                    <li id="addEnvelope"><a href="<s:url action="tpoAddEnvelope"/>">Add Envelope</a></li>
                                </ul>
                            </li>
                        </s:if>
                        <li class="dropdown" id="services">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i class="fa fa-list" aria-hidden="true"></i><span class="hidden-xs hidden-sm">Services</span><b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <s:if test="#session.tpoRoleId == 1">
                                    <li id="userAdd"><a href="<s:url action="tpoUserAdd"/>">Add User</a></li>
                               <%-- <li id="adminUsersList"><a href="<s:url action="tpoAdminUsersList"/>">Users List</a></li>--%>
                                </s:if>
                                <s:if test='%{#session.tpoRoleId== 1 || #session.tpoRoleId== 2}'>
                                    <li id="resetPartnerPwd"><a href="<s:url action="tpoResetPartnerPwd"/>">Reset Partner Pwd</a></li>
                                </s:if>
                                <s:if test='%{#session.tpoRoleId== 3 || #session.tpoRoleId== 4 || #session.tpoRoleId== 5}'>
                                    <li id="partnerInfo"><a href="<s:url action="tpoPartnerInfo"/>">Partner Info</a></li>
                                    <li id="partnerUserAdd"><a href="<s:url action="tpoPartnerUserAdd"/>">Add User</a></li>
                                </s:if>
                                <s:if test='%{#session.tpoRoleId== 1 || #session.tpoRoleId== 3 || #session.tpoRoleId== 4}'>
                                    <li id="resetUserPwd"><a href="<s:url action="tpoResetUserPwd"/>">Reset User Pwd</a></li>
                                </s:if>
                                <li id="resetMyPwd"><a href="<s:url action="tpoResetMyPwd"/>">Reset My Pwd</a></li>
                            </ul>
                        </li>
                    </ul>
                </s:if>
            </div>

        </div><!--/.nav-collapse -->
    </div>
</div> 
<!-- /.navbar -->