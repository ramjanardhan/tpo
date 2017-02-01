<%@page import="com.mss.tpo.tpOnboarding.TpOnboardingBean"%>
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.mss.tpo.util.AppConstants"%>
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

            #mail_button{

            }

        </style>

    </head>
    <s:if test="%{formAction == 'doAddProfile'}">
        <body onload="onLoad();" class="home">
        </s:if>
        <s:else>
        <body onload="doOnLoad();" class="home">
        </s:else>
        
        <div>
            <s:include value="/includes/template/header.jsp"/>
        </div>    
        <header id="head">
            <div class="container">
                <s:if test="%{formAction == 'doAddProfile'}">
                    <h3 > <b>Add Profile</b></h3>
                </s:if>
                <s:else>
                    <h3 > <b>Edit Profile</b></h3>
                </s:else>
            </div>
        </header>            
        <div class="container">
            <s:form action="%{formAction}" method="POST" enctype="multipart/form-data" name="addTpOnboard" id="addTpOnboard" theme="simple">
                <s:hidden id="communicationId" name="communicationId" value="%{communicationId}"/>
                <s:hidden name="formAction" id="formAction" value="%{formAction}"/>
                <div id="site_content" class="jumbotron">
                    <div class="container">
                        <div>
                            <center>
                                <%
                                    if (session.getAttribute(AppConstants.REQ_RESULT_MSG) != null) {
                                        String reqponseString = session.getAttribute(AppConstants.REQ_RESULT_MSG).toString();
                                        out.println(reqponseString);
                                        session.setAttribute(AppConstants.REQ_RESULT_MSG, null);
                                    }
                                %>
                                <div id="protocolmsg"></div>
                                <s:hidden name="partnerName" id="partnerName" value="%{#session.tpoPartnerName}" ></s:hidden>
                                </center>
                            <s:if test="%{formAction == 'doUpdateProfile'}">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="col-md-1 pull-right"> 
                                            <s:url var="myUrl" action="../tpOnboarding/tpoManageProfiles.action"></s:url>
                                            <s:a href='%{#myUrl}'><input type="button" style="color: #61eaf1;" value="<< Back to list" class="btn btn-primary"/></s:a> 
                                            <%--  <s:a href='%{#myUrl}' ><span class="glyphicon glyphicon-arrow-left"></span></s:a> --%>
                                        </div>
                                    </div>
                                </div>
                            </s:if>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <div id="tpoCommMsg"></div>
                                    <h4 style="color: #2d8fc8;margin-left: -15px" class="heading_4">Communication Protocols</h4>
                                    <s:select headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'FTP':'FTP/FTPS','AS2':'AS2','SFTP':'SFTP','HTTP':'HTTP/HTTPS','SMTP':'SMTP'}" name="commnProtocol" id="commnProtocol" value="%{commnProtocol}" tabindex="1" cssClass="form-control" onchange="protocolsSelect(this.value)"/>
                                    <s:hidden name="protocolValue" id="protocolValue"></s:hidden>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="container">
                            <div id="transferModeDiv"  style="display: none">
                                <div class="pull-left">   <label>Transfer&nbsp;Mode&nbsp;:</label></div> 
                                <div class="pull-left">
                                    <input type="text" id="tempTransferMode" style="display:none"/>
                                <s:radio name="transferMode" id="transferMode" list="{'get','put'}" value="%{transferMode}" onchange="gettransferModeSelection(this.value)" cssClass="from-control"  tabindex="2"></s:radio>&nbsp;&nbsp;
                                    <div class="tooltip"><i class="fa fa-question-circle-o"></i>
                                        <span class="tooltiptext">get&nbsp;:&nbsp;Partner&nbsp;Server <br>put&nbsp;:&nbsp;Your&nbsp;Server </span>
                                    </div>
                                </div>
                            </div>
                            <div id="loaderdiv" class="loadingImg" style="display: none">
                                <span id ="LoadingContent" > <img src="<s:url value="/includes/images/Loader2.gif"/>"   ></span>
                        </div>
                        <div id="transferModeMsg" style="display: none;position: relative;right: 226px;bottom: 11px;"></div>

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
                                    <s:select headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'FTP':'FTP','FTPS':'FTPS'}" name="ftp_recv_protocol" id="ftp_recv_protocol" value="%{ftp_recv_protocol}" tabindex="5" cssClass="form-control" onchange="rxFTPchange(this.value);"/>
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
                                        <a style="width:0%" id="mail_button"  data-toggle="myToolTip" data-placement="top" title="Get an Email"  onclick="getPwdEamil('FTP')" class="input-group-addon tip-top"><i class="fa fa-envelope" aria-hidden="true"></i></a>
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
                                    <s:checkbox  style="display:none" name="ftp_ssl_req" id="ftp_ssl_req" tabindex="12" value="%{ftp_ssl_req}" onclick="sslRequired('ftp')" cssClass=""/>
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
                                    <label>Remote&nbsp;UserId</label>
                                    <s:textfield cssClass="form-control" name="sftp_remote_userId" id="sftp_remote_userId" tabindex="18" value="%{sftp_remote_userId}"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Remote&nbsp;Password</label>
                                    <div class="input-group" style="width: 100%;">
                                        <s:password cssClass="form-control" name="sftp_remote_pwd" id="sftp_remote_pwd" tabindex="19" value="%{sftp_remote_pwd}"/>
                                        <a style="width:0%" id="mail_button_sftp"  data-toggle="myToolTip" data-placement="top" title="Get an Email"  onclick="getPwdEamil('SFTP')" class="input-group-addon tip-top"><i class="fa fa-envelope" aria-hidden="true"></i></a>
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
                                    <s:select headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'HTTP':'HTTP','HTTPS':'HTTPS'}" name="http_recv_protocol" id="http_recv_protocol" value="%{http_recv_protocol}" tabindex="5" cssClass="form-control" onchange="rxHTTPchange(this.value);"/>
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
                                    <label>HTTP&nbsp;End&nbsp;Point</label>
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
                                    <s:checkbox style="display: none" id="http_ssl_req" name="http_ssl_req" tabindex="28" value="%{http_ssl_req}" onclick="sslRequired('http')"/>
                                </div>
                            </div>
                        </div>

                        <div id="smtpDiv" style="display: none;clear:both">
                            <div id="protocolmsgSmtp"></div>
                            <h4 style="color: #2d8fc8" class="heading_4">SMTP&nbsp;Server&nbsp;Details  : </h4>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Receiving&nbsp;Protocol </label>
                                    <s:textfield cssClass="form-control" name="smtp_recv_protocol" id="smtp_recv_protocol" tabindex="29" value="SMTP" readonly="true"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>SMTP&nbsp;Server&nbsp;Host </label>
                                    <s:textfield cssClass="form-control" name="smtp_server_protocol" id="smtp_server_protocol" tabindex="30" value="%{smtp_server_protocol}"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>SMTP&nbsp;Server&nbsp;Port</label>
                                    <s:textfield cssClass="form-control" name="smtp_server_port" id="smtp_server_port" tabindex="31" value="%{smtp_server_port}" onchange="fieldLengthValidator(this);"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>From&nbsp;E-mail&nbsp;address</label>
                                    <s:textfield cssClass="form-control" name="smtp_from_email" id="smtp_from_email" tabindex="32" value="%{smtp_from_email}" onchange="validateEmail(this);"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>To E-mail&nbsp;address </label>
                                    <s:textfield cssClass="form-control" name="smtp_to_email" id="smtp_to_email" tabindex="33" value="%{smtp_to_email}" onchange="validateEmail(this);"/>
                                </div>
                            </div>
                        </div>

                        <div id="loadingImage"></div>
                        <div id="as2Div" style="display: none;clear:both">
                            <div id="protocolmsgAs2"></div>
                            <h4 style="color: #2d8fc8" class="heading_4">AS2&nbsp;Server&nbsp;Details  : </h4>
                            <div class="col-sm-12 form-group">
                                <div class="col-sm-3 gutter_hide">
                                    <div class="threshold" style="/*! position: relative; *//*! top:31px */">
                                        <input value="System Certificates" name="thresholdSelect" disabled="disabled" class="jumbotron_bg" type="text">
                                    </div>
                                </div>
                                <div class="col-sm-3">    
                                    <label>Download&nbsp;System&nbsp;Certificate </label>
                                     <a href="../tpOnboarding/tpOnboardingDownloads.action">Download this file</a>
                                </div>
                                <div class="col-sm-3">
                                     <label>Upload&nbsp;System&nbsp;Certificate </label>
                                    <s:file name="upload" id= "attachmentFileNameAs2" label="as2_part_cert" tabindex="34"/>  
                                </div>
                               
                            </div>
                            <div class="col-sm-12">    
                                <div class="col-sm-3 gutter_hide">
                                    <div class="threshold" style="/*! position: relative; *//*! top:31px */">
                                        <input value="Organization Profiles" name="thresholdSelect" disabled="disabled" class="jumbotron_bg" type="text">
                                    </div>
                                </div>
                                <div class="col-sm-6 gutter_hide form-group">
                                    <div class="col-sm-6">
                                        <label>My&nbsp;Organization</label>
                                        <s:textfield cssClass="form-control" name="as2_myOrgName" id="as2_myOrgName" tabindex="35" value="%{as2_myOrgName}" onkeyup="as2OrgProfileNames(this);" onblur="isExistedAS2PartnerProfileName();"/><img id="correctImg" style="display: none;" src="/<%=AppConstants.CONTEXT_PATH%>/includes/images/right.png" 
                                             width="13" height="13" border="0"><img id="wrongImg" style="display: none;" src="/<%=AppConstants.CONTEXT_PATH%>/includes/images/wrong.jpg" width="13" height="13" border="0"><img id="loadingImageAjax" style="display: none;" width="16" height="16" border="0" src="<s:url value="/includes/images/ajax-loader.gif"/>">
                                    </div>
                                    <div class="col-sm-6 ">
                                        <label>Your&nbsp;Organization </label>
                                        <s:textfield cssClass="form-control" name="as2_partOrgName" id="as2_partOrgName" tabindex="36" value="%{as2_partOrgName}" onkeyup="as2PartnerProfileNames(this);"/>
                                    </div >
                                </div>
                            </div>
                            <div class="col-sm-12">  
                                <div class="col-sm-3 gutter_hide">
                                    <div class="threshold" style="/*! position: relative; *//*! top:31px */">
                                        <input value="Partner Profiles" name="thresholdSelect" disabled="disabled" class="jumbotron_bg" type="text">
                                    </div>
                                </div>
                                <div class="col-sm-6 gutter_hide form-group">
                                    <div class="col-sm-6"> 
                                        <label>My&nbsp;Partner&nbsp;Profile&nbsp;Name</label>
                                        <s:textfield cssClass="form-control" name="as2_myPartname" id="as2_myPartname" tabindex="37" value="%{as2_myPartname}"/>
                                    </div>
                                    <div class="col-sm-6">    
                                        <label>Your&nbsp;Partner&nbsp;Profile&nbsp;Name </label>
                                        <s:textfield cssClass="form-control" name="as2_yourPartname" id="as2_yourPartname" tabindex="38" value="%{as2_yourPartname}" readonly="true"/>
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="col-sm-12">  
                                <div class="col-sm-3 gutter_hide">
                                    <div class="threshold" style="/*! position: relative; *//*! top:31px */">
                                        <input value="AS2 EndPoint " name="thresholdSelect" disabled="disabled" class="jumbotron_bg" type="text">
                                    </div>
                                </div>
                                <div class="col-sm-6 gutter_hide form-group">
                                    <div class="col-sm-6"> 
                                        <label>My&nbsp;End&nbsp;Point</label>
                                        <s:textfield cssClass="form-control" name="as2_myEndPoint" id="as2_myEndPoint" tabindex="39" value="%{as2_myEndPoint}"/>
                                    </div>
                                    <div class="col-sm-6">    
                                        <label>Your&nbsp;End&nbsp;Point </label>
                                        <s:textfield cssClass="form-control" name="as2_partendpoint" id="as2_partendpoint" tabindex="40" value="%{as2_partendpoint}"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">        
                                <div class="col-sm-3 col-sm-offset-3">
                                    <div class="form-group">
                                        <label>Store&nbsp;AS2&nbsp;Messages&nbsp;in </label>
                                        <s:select headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'MailBox':'MailBox','FSA':'FSA'}" name="as2_strMsg" id="as2_strMsg" value="%{as2_strMsg}" tabindex="41" cssClass="form-control"/>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>Wait&nbsp;For&nbsp;Synchronous&nbsp;MDN&nbsp;Process</label>
                                        <s:select headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'YES':'Yes','NO':'No'}" name="as2_waitForSync" id="as2_waitForSync" value="%{as2_waitForSync}" tabindex="42"  cssClass="form-control"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">      
                                <div class="col-sm-3 col-sm-offset-3">
                                    <div class="form-group"> 
                                        <label>Payload&nbsp;Send&nbsp;Mode</label>
                                        <s:select headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'SYNC':'SYNC','ASYNC':'ASYNC','NoMDN':'No MDN'}" name="as2_payloadSendMode" id="as2_payloadSendMode" value="%{as2_payloadSendMode}" tabindex="43" cssClass="form-control"/>
                                    </div>
                                </div>
                                <div class="col-sm-3" style="display: none">
                                    <div class="" style="margin-top:30px">
                                        <label for="as2_ssl_req">
                                            SSL&nbsp;Required
                                        </label>
                                        <s:checkbox  name="as2_ssl_req" id="as2_ssl_req" tabindex="44" value="%{as2_ssl_req}" onclick="sslRequired('as2')" cssClass=""/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="site_content" class="jumbotron">
                    <div class="container">
                        <div id="sslDiv" style="display: none">
                            <h4 style="color: #2d8fc8" class="heading_4">SSL  : </h4>
                            <div class="col-sm-3">
                                <div class="form-group"> 
                                    <label>SSL&nbsp;Priority:</label>
                                    <s:select list="#@java.util.LinkedHashMap@{'MUST':'Must','NONE':'None'}" name="ssl_priority" id="ssl_priority" value="%{ssl_priority}" tabindex="45" cssClass="form-control" onchange="sslPriorityChange(this.value)"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group"> 
                                    <label>Cipher&nbsp;Strength:</label>
                                    <s:select list="#@java.util.LinkedHashMap@{'STRONG':'Strong','NONE':'None'}" name="ssl_cipher_stergth" id="ssl_cipher_stergth" value="%{ssl_cipher_stergth}" tabindex="46" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group"> 
                                    <label>CA&nbsp;Certificates&nbsp;(Certificate&nbsp;Groups):</label>
                                    <%--<s:textfield cssClass="button" name="ssl_sysStore" id="ssl_sysStore" tabindex="57" value="Download"/>--%>
                                    <a href="../tpOnboarding/tpOnboardingDownloads.action">Download this file</a>
                                </div>
                            </div>
                        </div>

                        <div id="sslDiv2" style="display: none">
                            <div id="protocolmsgSsl"></div>
                            <h4 style="color: #2d8fc8" class="heading_4">SSL  : </h4>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>SSL&nbsp;Priority:</label>
                                    <s:select list="#@java.util.LinkedHashMap@{'MUST':'Must','NONE':'None'}" name="ssl_priority2" id="ssl_priority2" value="%{ssl_priority2}" tabindex="47" cssClass="form-control" onchange="sslPriorityChange(this.value)"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group"> 
                                    <label>Cipher&nbsp;Strength:</label>
                                    <s:select list="#@java.util.LinkedHashMap@{'STRONG':'Strong','NONE':'None'}" name="ssl_cipher_stergth2" id="ssl_cipher_stergth2" value="%{ssl_cipher_stergth2}" tabindex="48" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>CA&nbsp;Certificates&nbsp;(Certificate&nbsp;Groups):</label>
                                    <%--  <s:file name="certGroups" id="certGroups" label="certGroups" tabindex="58"/>--%>
                                    <s:file name="upload1" id= "attachmentFileName" label="certGroups" tabindex="49"/> 
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12">
                            <div class="col-md-2 pull-right">  
                                <s:if test="%{formAction == 'doAddProfile'}">
                                    <s:submit value="Save" cssClass="btn btn-info" tabindex="50" onclick="return checkProfile('add')"/>
                                </s:if>
                                <s:else>
                                    <s:submit value="Update" cssClass="btn btn-info" tabindex="50" onclick="return checkProfile('update')"/>
                                </s:else>
                            </div>
                            <div class="col-sm-1 pull-right">
                                <s:reset   value="Reset" cssClass="btn btn-info" tabindex="51"/>
                            </div>
                        </div>
                    </s:form>
                </div>
            </div>       
        </div>
        <div class="row">
            <s:include value="../includes/template/footer.jsp"/>
        </div>
        <script language="JavaScript" src='<s:url value="/includes/js/tpOnbordingDeatails.js"/>'></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
        <script language="JavaScript" src='<s:url value="/includes/js/GeneralAjax.js"/>'></script>
        <script type="text/javascript" src='<s:url value="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"/>'></script>
<!--        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>-->
        <script type="text/javascript" src='<s:url value="/includes/js/wz_tooltip.js"/>'></script>
        <script type="text/javascript">
                                            function onLoad() {
                                                $("#profiles").addClass("active");
                                            }

                                            function doOnLoad() {
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

                                            $(document).ready(function () {
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
