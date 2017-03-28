<%-- 
    Document   : tpoAdminAddProfile
    Author     : Narendar
--%>

<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.mss.tpo.util.AppConstants"%>
<%@page import="com.mss.tpo.admin.AdminBean"%>
<html>
    <head>
        <title>Miracle TP On-boarding</title>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="cache-control" content="no-cache" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" media="screen" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700"/>
        <link rel="stylesheet" href='<s:url value="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/main.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/build.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap-theme.css" />' media="screen" type="text/css"/>
        <style>
            .input-group-addon i{
                color:#2d8fc8;
            }
            .input-group-addon{
                cursor: pointer;
            }
            @media only screen and (max-width: 465px) {
                #Synchronous {
                    word-wrap: break-word;
                }
                #site_content{
                    padding: 3px 12px;
                }
            }
        </style>
    </head>
    <s:if test="%{formAction == 'doAdminAddProfile'}">
        <body onload="onLoad();" class="home">
        </s:if>
        <s:else>
        <body onload="doOnLoad();" class="home">
        </s:else>
        <div>
            <s:include value="/includes/template/header.jsp"/>
        </div>    
        <header>
            <div class="container">
                <s:if test="%{formAction == 'doAdminAddProfile'}">
                    <h3 > <b>Add Profile</b></h3>
                </s:if>
                <s:else>
                    <h3 > <b>Edit Profile</b></h3>
                </s:else>
            </div>
        </header>            
        <div class="container">
            <s:form action="%{formAction}" method="POST" enctype="multipart/form-data" name="addAdminProfile" id="addAdminProfile" theme="simple">
                <s:hidden id="communicationId" name="communicationId" value="%{communicationId}"/>
                <s:hidden id="transferMode" name="transferMode" value="%{transferMode}"/>
                <s:hidden name="formAction" id="formAction" value="%{formAction}"/>
                <div id="site_content" class="jumbotron">
                    <div class="container">
                        <div>
                            <center>
                                <div id="responseString">
                                    <%
                                        if (session.getAttribute(AppConstants.REQ_RESULT_MSG) != null) {
                                            String responseString = session.getAttribute(AppConstants.REQ_RESULT_MSG).toString();
                                            out.println(responseString);
                                            session.setAttribute(AppConstants.REQ_RESULT_MSG, null);
                                        }
                                    %>
                                </div> <div id="protocolmsg"></div>
                            </center>
                            <s:if test="%{formAction == 'doAdminUpdateProfile'}">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="col-md-1 pull-right"> 
                                            <s:url var="myUrl" action="../admin/tpoAdminManageProfiles.action"></s:url>
                                            <s:a href='%{#myUrl}'><input type="button" style="color: #61eaf1;" value="Back to list" class="btn btn-primary"/></s:a> 
                                            <%--  <s:a href='%{#myUrl}' ><span class="glyphicon glyphicon-arrow-left"></span></s:a> --%>
                                        </div>
                                    </div>
                                </div>
                            </s:if>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <div id="tpoCommMsg"></div>
                                    <h4 style="color: #2d8fc8;margin-left: -15px" class="heading_4">Communication Protocol</h4>
                                    <s:select headerKey="-1" headerValue="-- Select --" list="protocolList" name="commnProtocol" id="commnProtocol" value="%{commnProtocol}" tabindex="1" cssClass="form-control" onchange="protocolsSelectForAdmin(this.value)"/>
                                    <s:hidden name="protocolValue" id="protocolValue"></s:hidden>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="container">
                            <div id="ftpDiv" style="display: none;clear:both">
                                <div id="protocolmsgFtp"></div>
                                <h4 style="color: #2d8fc8" class="heading_4">FTP&nbsp;Server&nbsp;Details  : </h4>
                                <div id="tpResultMessage"></div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>FTP&nbsp;Type</label>
                                    <s:select list="#@java.util.LinkedHashMap@{'GET':'Get','PUT':'Put'}" name="ftp_method" id="ftp_method" value="%{ftp_method}" tabindex="3" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Connection&nbsp;Type</label>
                                    <s:select list="#@java.util.LinkedHashMap@{'ACTIVE':'Active','PASSIVE':'Passive'}" name="ftp_conn_method" id="ftp_conn_method" value="%{ftp_conn_method}" tabindex="4" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Receiving&nbsp;Protocol </label>
                                    <s:select headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'FTP':'FTP','FTPS':'FTPS'}" name="ftp_recv_protocol" id="ftp_recv_protocol" value="%{ftp_recv_protocol}" tabindex="5" cssClass="form-control" onchange="rxFTPchangeForAdmin(this.value);"/>
                                    <%-- <s:textfield  name="ftp_recv_protocol" id="ftp_recv_protocol" tabindex="5"  value="%{ftp_recv_protocol}" cssClass="form-control"/> --%>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Response&nbsp;Timeout&nbsp;(sec) </label>
                                    <s:textfield  name="ftp_resp_time" id="ftp_resp_time" tabindex="6" value="%{ftp_resp_time}" onchange="fieldLengthValidator(this);"  cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>FTP&nbsp;Host</label>
                                    <s:textfield  name="ftp_host" id="ftp_host" tabindex="7" value="%{ftp_host}"  cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>FTP&nbsp;Port</label>
                                    <s:textfield  name="ftp_port" id="ftp_port" tabindex="8" value="%{ftp_port}" onchange="fieldLengthValidator(this);" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>FTP&nbsp;User&nbsp;ID </label>
                                    <s:textfield name="ftp_userId" id="ftp_userId" tabindex="9" value="%{ftp_userId}" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>FTP&nbsp;Password  </label>
                                    <div class="input-group" style="width: 100%;">
                                        <s:password  cssClass="form-control" name="ftp_pwd" id="ftp_pwd" tabindex="10" value="%{ftp_pwd}" />
                                        <a style="width:0%" id="mail_button"  data-toggle="myToolTip" data-placement="top" title="Get an Email"  onclick="getPwdEmail('FTP')" class="input-group-addon tip-top"><i class="fa fa-envelope" aria-hidden="true"></i></a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>FTP&nbsp;Directory </label>
                                    <s:textfield  name="ftp_directory" id="ftp_directory" tabindex="11" value="%{ftp_directory}" cssClass="form-control"/>
                                </div>
                            </div>
                            <div id="ftp_ssl" class="col-sm-3">
                                <div class="" style="margin-top:30px">
                                    <label for="ftp_ssl_req" style="display: none">
                                        SSL&nbsp;Required
                                    </label>
                                    <s:checkbox  style="display:none" name="ftp_ssl_req" id="ftp_ssl_req" tabindex="12" value="%{ftp_ssl_req}" onclick="sslRequiredForAdmin('ftp')" cssClass=""/>
                                </div>
                            </div>
                        </div>

                        <div id="sftpDiv" style="display: none;clear:both">
                            <div id="protocolmsgSftp"></div>
                            <h4 style="color: #2d8fc8" class="heading_4">SFTP&nbsp;Server&nbsp;Details  : </h4>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Connection&nbsp;Type </label>
                                    <s:select list="#@java.util.LinkedHashMap@{'ACTIVE':'Active','PASSIVE':'Passive'}" name="sftp_conn_method" id="sftp_conn_method" value="%{sftp_conn_method}" tabindex="13" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Authentication&nbsp;Type </label>
                                    <s:select list="#@java.util.LinkedHashMap@{'Pwd_Only':'Password only','SSH_Public_Key':'SSH Public Key','pwd_and_public':'Password & Public Key'}" name="sftp_auth_method" id="sftp_auth_method" value="%{sftp_auth_method}" tabindex="14" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>SSH&nbsp;Public&nbsp;key </label>
                                    <div id="download" >
                                        <a href="../tpOnboarding/tpOnboardingDownloads.action">Download this file</a>
                                    </div>
                                    <div id="upload">
                                        <s:file name="upload" id= "attachmentFileNameSftp" label="sftp_upload_public_key" tabindex="15"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Remote&nbsp;Host&nbsp;IP&nbsp;Address </label>
                                    <s:textfield  name="sftp_host_ip" id="sftp_host_ip" tabindex="16" value="%{sftp_host_ip}" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Remote&nbsp;Port </label>
                                    <s:textfield cssClass="form-control" name="sftp_remote_port" id="sftp_remote_port" tabindex="17" value="%{sftp_remote_port}" onchange="fieldLengthValidator(this);"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Remote&nbsp;User Id</label>
                                    <s:textfield cssClass="form-control" name="sftp_remote_userId" id="sftp_remote_userId" tabindex="18" value="%{sftp_remote_userId}"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Remote&nbsp;Password</label>
                                    <div class="input-group" style="width: 100%;">
                                        <s:password cssClass="form-control" name="sftp_remote_pwd" id="sftp_remote_pwd" tabindex="19" value="%{sftp_remote_pwd}"/>
                                        <a style="width:0%" id="mail_button_sftp"  data-toggle="myToolTip" data-placement="top" title="Get an Email"  onclick="getPwdEmail('SFTP')" class="input-group-addon tip-top"><i class="fa fa-envelope" aria-hidden="true"></i></a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Type</label>
                                    <s:select list="#@java.util.LinkedHashMap@{'GET':'Get','PUT':'Put'}" name="sftp_method" id="sftp_method" value="%{sftp_method}" tabindex="20" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Directory </label>
                                    <s:textfield cssClass="form-control" name="sftp_directory" id="sftp_directory" tabindex="21" value="%{sftp_directory}"/>
                                </div>
                            </div>
                        </div>

                        <div id="httpDiv" style="display: none;clear:both">
                            <div id="protocolmsgHttp"></div>
                            <h4 style="color: #2d8fc8" class="heading_4">HTTP&nbsp;Server&nbsp;Details  : </h4>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Receiving&nbsp;Protocol </label>
                                    <s:select headerKey="-1" headerValue="--Select--" list="#@java.util.LinkedHashMap@{'HTTP':'HTTP','HTTPS':'HTTPS'}" name="http_recv_protocol" id="http_recv_protocol" value="%{http_recv_protocol}" tabindex="5" cssClass="form-control" onchange="rxHTTPchangeForAdmin(this.value);"/>
                                    <%--   <s:textfield cssClass="form-control" name="http_recv_protocol" id="http_recv_protocol" tabindex="22" value="HTTP"/> --%>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Response&nbsp;Timeout&nbsp;(sec)</label>
                                    <s:textfield cssClass="form-control" name="http_resp_time" id="http_resp_time" tabindex="23" value="%{http_resp_time}" onchange="fieldLengthValidator(this);"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>HTTP&nbsp;Endpoint</label>
                                    <s:textfield cssClass="form-control" name="http_endpoint" id="http_endpoint" tabindex="24" value="%{http_endpoint}"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>HTTP&nbsp;Port </label>
                                    <s:textfield cssClass="form-control" name="http_port" id="http_port" tabindex="25" value="%{http_port}" onchange="fieldLengthValidator(this);"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>HTTP&nbsp;Mode</label>
                                    <s:select list="#@java.util.LinkedHashMap@{'GET':'Get','POST':'Post'}" name="http_protocol_mode" id="http_protocol_mode" value="%{http_protocol_mode}" tabindex="26" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>URL </label>
                                    <s:textfield cssClass="form-control" name="http_url" id="http_url" tabindex="27" value="%{http_url}" />
                                </div>
                            </div>
                            <div id="http_ssl" class="col-sm-3" style="display: none">
                                <div class="" style="margin-top:30px">
                                    <label for="http_ssl_req" style="display: none">
                                        SSL&nbsp;Required
                                    </label>
                                    <s:checkbox style="display: none" id="http_ssl_req" name="http_ssl_req" tabindex="28" value="%{http_ssl_req}" onclick="sslRequiredForAdmin('http')"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="site_content" class="jumbotron">
                    <div class="container">
                        <div id="sslDiv" style="display: none">
                            <div id="protocolmsgSsl"></div>
                            <h4 style="color: #2d8fc8" class="heading_4">SSL  : </h4>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>SSL&nbsp;Priority:</label>
                                    <s:select list="#@java.util.LinkedHashMap@{'MUST':'Must','NONE':'None'}" name="ssl_priority" id="ssl_priority" value="%{ssl_priority}" tabindex="47" cssClass="form-control" onchange="sslPriorityChangeForAdmin(this.value)"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group"> 
                                    <label>Cipher&nbsp;Strength:</label>
                                    <s:select list="#@java.util.LinkedHashMap@{'STRONG':'Strong','NONE':'None'}" name="ssl_cipher_stergth" id="ssl_cipher_stergth" value="%{ssl_cipher_stergth}" tabindex="48" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>CA&nbsp;Certificate:</label>
                                    <%--  <s:file name="certGroups" id="certGroups" label="certGroups" tabindex="58"/>--%>
                                    <s:file name="upload1" id= "attachmentFileName" label="certGroups" tabindex="49"/> 
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12" id="saveButton" style="display: none">
                            <div class="col-md-2 pull-right">  
                                <s:if test="%{formAction == 'doAdminAddProfile'}">
                                    <s:submit value="Save" cssClass="btn btn-info" tabindex="50" onclick="return checkAdminProfile('add')"/>
                                </s:if>
                                <s:else>
                                    <s:submit value="Update" cssClass="btn btn-info" tabindex="50" onclick="return checkAdminProfile('update')"/>
                                </s:else>
                            </div>
                            <div class="col-sm-2 col-md-1 pull-right">
                                <s:reset   value="Reset" cssClass="btn btn-info" tabindex="51"/>
                            </div>
                            <div class="col-sm-2 col-md-1 pull-right">
                                <s:if test="%{formAction == 'doAdminAddProfile'}">
                                    <s:url var="myUrl" action="../tpOnboarding/tpoAddProfile.action"></s:url>
                                    <s:a href='%{#myUrl}'><input type="button" tabindex="52" value="Cancel" class="btn btn-info"/></s:a> 
                                </s:if>
                                <s:else>
                                    <s:url var="myUrl1" action="../tpOnboarding/tpoManageProfiles.action"></s:url>
                                    <s:a href='%{#myUrl1}'><input type="button" tabindex="52" value="Cancel" class="btn btn-info"/></s:a> 
                                </s:else>
                            </div>
                        </div>
                    </s:form>
                </div>
            </div>       
        </div>
        <div class=" " style="position:relative;bottom: 0;width:100%">
            <s:include value="../includes/template/footer.jsp"/>
        </div>
        <script language="JavaScript" src='<s:url value="/includes/js/adminValidations.js"/>'></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
        <script language="JavaScript" src='<s:url value="/includes/js/GeneralAjax.js"/>'></script>
        <script type="text/javascript" src='<s:url value="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"/>'></script>
        <script type="text/javascript" src='<s:url value="/includes/js/wz_tooltip.js"/>'></script>

        <script type="text/javascript">
                                            function onLoad() {
                                                $("#partners").addClass("active");
                                                document.getElementById("commnProtocol").value = "-1";
                                            }

                                            function doOnLoad() {
                                                // alert('hi');
                                                $("#profiles").addClass("active");
                                                $('#commnProtocol').trigger("change");
                                                var commnProtocol = document.getElementById("commnProtocol").value;
                                                if (commnProtocol == "FTP") {
                                                    var ftp_ssl = document.getElementById("ftp_ssl_req").value;
                                                    if (ftp_ssl == "true") {
                                                        document.getElementById("ftp_ssl_req").onclick();
                                                    }
                                                } else if (commnProtocol == "AS2") {
                                                    var as2_ssl = document.getElementById("as2_ssl_req").value;
                                                    if (as2_ssl == "true") {
                                                        document.getElementById("as2_ssl_req").onclick();
                                                    }
                                                } else if (commnProtocol == "HTTP") {
                                                    var http_ssl_req = document.getElementById("http_ssl_req").value;
                                                    if (http_ssl_req == "true") {
                                                        document.getElementById("http_ssl_req").onclick();
                                                    }
                                                }
                                            }

                                            $(document).ready(function() {
                                                $('[data-toggle="myToolTip"]').tooltip();

                                                $('button').tooltip();

                                                // options set in JS by class
                                                $(".tip-top").tooltip({
                                                    placement: 'top'
                                                });
                                            });

        </script>
    </body>
</html>

