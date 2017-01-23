<%@page import="com.mss.tpo.tpOnboarding.TpOnboardingBean"%>
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.mss.tpo.util.AppConstants"%>
<html>
    <head>
        <title>Miracle TP On-boarding portal</title>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="cache-control" content="no-cache" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" media="screen" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700"/>
        <link rel="stylesheet" href='<s:url value="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap.min.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/main.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/build.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap-theme.css" />' media="screen" type="text/css"/>

        <style>
            #transferModeDiv input{
                margin:0 1px 0 11px;
            }
            .head_label{
                font-size: 16px;
                /*  padding: 0px 19px 0px 0px;*/
                color:#000;
            }
            .labl_colunm{
                margin:2% 0;
            }
            .gutter_hide{
                padding:0;
            }
            
          
        </style>
    </head>
    <s:if test="%{formAction == 'doAddProfile'}">
        <body onload="onLoad();" class="home">
        </s:if>
        <s:else>
        <body onload="doOnLoad();" class="home">
        </s:else>
        <script type="text/javascript" src='<s:url value="/includes/js/wz_tooltip.js"/>'></script>
        <div>
            <s:include value="/includes/template/header.jsp"/>
        </div>    
        <header id="head">
            <div class="container">
                <s:if test="%{formAction == 'doAddProfile'}">
                    <h3 > <b>Profile Add</b></h3>
                </s:if>
                <s:else>
                    <h3 > <b>Profile Edit</b></h3>
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
                                    <h4 style="color: #2d8fc8">Communication Protocols</h4>
                                    <s:select headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'FTP':'FTP/FTPS','AS2':'AS2','SFTP':'SFTP','HTTP':'HTTP/HTTPS','SMTP':'SMTP'}" name="commnProtocol" id="commnProtocol" value="%{commnProtocol}" tabindex="10" cssClass="form-control" onchange="protocolsSelect(this.value)"/>
                                    <s:hidden name="protocolValue" id="protocolValue"></s:hidden>
                                    </div></div>
                            </div>
                        </div>
                        <div class="container">
                            <div id="transferModeDiv"  style="display: none">
                                <div class="pull-left">   <label>Transfer&nbsp;Mode&nbsp;:</label></div> 
                                <div class="pull-left">
                                    <input type="text" id="tempTransferMode" style="display:none"/>
                                <s:radio name="transferMode" id="transferMode" list="{'get','put'}" value="%{transferMode}" onchange="gettransferModeSelection(this.value)" cssClass="from-control"  tabindex="11"></s:radio>&nbsp;&nbsp;
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
                            <h4 style="color: #2d8fc8">FTP Server&nbsp;Details : </h4>
                            <div id="tpResultMessage"></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>FTP&nbsp;Type</label>
                                    <s:select list="#@java.util.LinkedHashMap@{'GET':'Get','PUT':'Put'}" name="ftp_method" id="ftp_method" value="%{ftp_method}" tabindex="12" cssClass="form-control"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Connection&nbsp;Type</label>
                                    <s:select list="#@java.util.LinkedHashMap@{'ACTIVE':'Active','PASSIVE':'Passive'}" name="ftp_conn_method" id="ftp_conn_method" value="%{ftp_conn_method}" tabindex="13" cssClass="form-control"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Receiving&nbsp;Protocol </label>
                                    <s:textfield  name="ftp_recv_protocol" id="ftp_recv_protocol" tabindex="14" value="FTP"  cssClass="form-control"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Response&nbsp;Timeout&nbsp;(sec) </label>
                                    <s:textfield  name="ftp_resp_time" id="ftp_resp_time" tabindex="15" value="%{ftp_resp_time}" onchange="fieldLengthValidator(this);"  cssClass="form-control"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>FTP&nbsp;Host</label>
                                    <s:textfield  name="ftp_host" id="ftp_host" tabindex="16" value="%{ftp_host}"  cssClass="form-control"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>FTP&nbsp;Port</label>
                                    <s:textfield  name="ftp_port" id="ftp_port" tabindex="17" value="%{ftp_port}" onchange="fieldLengthValidator(this);" cssClass="form-control"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>FTP&nbsp;User&nbsp;ID </label>
                                    <s:textfield name="ftp_userId" id="ftp_userId" tabindex="18" value="%{ftp_userId}" cssClass="form-control"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>FTP&nbsp;Password  </label>
                                    <s:textfield  name="ftp_pwd" id="ftp_pwd" tabindex="19" value="%{ftp_pwd}" cssClass="form-control"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>FTP&nbsp;Directory </label>
                                    <s:textfield  name="ftp_directory" id="ftp_directory" tabindex="20" value="%{ftp_directory}" cssClass="form-control"/>
                                </div></div>
                                <%--                                            <div class="col-sm-3">
                                                                                <div class="form-group"> <label>SSL&nbsp;Required </label>
                                                                                
                                                                                    <s:checkbox  name="ftp_ssl_req" id="ftp_ssl_req" tabindex="21" value="%{ftp_ssl_req}" onclick="sslRequired('ftp')" cssClass="form-control"/>
                                                                                </div>
                                                                            </div>--%>
                            <div class="col-sm-3">
                                <div class="" style="margin-top:30px">
                                    <label for="ftp_ssl_req">
                                        SSL&nbsp;Required
                                    </label>
                                    <s:checkbox  name="ftp_ssl_req" id="ftp_ssl_req" tabindex="21" value="%{ftp_ssl_req}" onclick="sslRequired('ftp')" cssClass=""/>
                                </div>
                            </div>
                        </div>

                        <div id="sftpDiv" style="display: none;clear:both">
                            <div id="protocolmsgSftp"></div>
                            <h3 style="color: #2d8fc8">SFTP Server&nbsp;Details  : </h3>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Connection&nbsp;Type </label>
                                    <s:select list="#@java.util.LinkedHashMap@{'ACTIVE':'Active','PASSIVE':'Passive'}" name="sftp_conn_method" id="sftp_conn_method" value="%{sftp_conn_method}" tabindex="22" cssClass="form-control"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Authentication&nbsp;Type </label>
                                    <s:select list="#@java.util.LinkedHashMap@{'Pwd_Only':'Password only','SSH_Public_Key':'SSH Public Key','pwd_and_public':'Password & Public Key'}" name="sftp_auth_method" id="sftp_auth_method" value="%{sftp_auth_method}" tabindex="23" cssClass="form-control"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>SSH&nbsp;Public&nbsp;key </label>
                                   
                                    <div id="download" >
                                        <%-- <s:textfield cssClass="button" name="sftp_public_key" id="sftp_public_key" tabindex="24" value="Download"/>--%>
                                        <a href="../tpOnboarding/tpOnboardingDownloads.action">Download this file</a>
                                    </div>
                                    <div id="upload">
                                        <%-- <s:file name="sftp_upload_public_key" id= "sftp_upload_public_key" label="sftp_upload_public_key" tabindex="25"/>--%>
                                        <s:file name="upload" id= "attachmentFileNameSftp" label="sftp_upload_public_key" tabindex="25"/>
                                    </div>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Remote&nbsp;Host&nbsp;IP&nbsp;Address </label>
                                    <s:textfield  name="sftp_host_ip" id="sftp_host_ip" tabindex="26" value="%{sftp_host_ip}" cssClass="form-control"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Remote&nbsp;Port </label>
                                    <s:textfield cssClass="form-control" name="sftp_remote_port" id="sftp_remote_port" tabindex="27" value="%{sftp_remote_port}" onchange="fieldLengthValidator(this);"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Remote&nbsp;UserId</label>
                                    <s:textfield cssClass="form-control" name="sftp_remote_userId" id="sftp_remote_userId" tabindex="28" value="%{sftp_remote_userId}"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Remote&nbsp;Password</label>
                                    <s:textfield cssClass="form-control" name="sftp_remote_pwd" id="sftp_remote_pwd" tabindex="29" value="%{sftp_remote_pwd}"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Type</label>
                                    <s:select list="#@java.util.LinkedHashMap@{'GET':'Get','PUT':'Put'}" name="sftp_method" id="sftp_method" value="%{sftp_method}" tabindex="30" cssClass="form-control"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Directory </label>
                                    <s:textfield cssClass="form-control" name="sftp_directory" id="sftp_directory" tabindex="31" value="%{sftp_directory}"/>
                                </div>
                            </div>
                        </div>

                        <div id="httpDiv" style="display: none;clear:both">
                            <div id="protocolmsgHttp"></div>
                            <h3 style="color: #2d8fc8">HTTP Server&nbsp;Details  : </h3>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Receiving&nbsp;Protocol </label>
                                    <s:textfield cssClass="form-control" name="http_recv_protocol" id="http_recv_protocol" tabindex="32" value="HTTP"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Response&nbsp;Timeout&nbsp;(sec)</label>
                                    <s:textfield cssClass="form-control" name="http_resp_time" id="http_resp_time" tabindex="33" value="%{http_resp_time}" onchange="fieldLengthValidator(this);"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>HTTP&nbsp;End&nbsp;Point</label>
                                    <s:textfield cssClass="form-control" name="http_endpoint" id="http_endpoint" tabindex="34" value="%{http_endpoint}"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>HTTP&nbsp;Port </label>
                                    <s:textfield cssClass="form-control" name="http_port" id="http_port" tabindex="35" value="%{http_port}" onchange="fieldLengthValidator(this);"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>HTTP&nbsp;Mode</label>
                                    <s:select list="#@java.util.LinkedHashMap@{'GET':'Get','POST':'Post'}" name="http_protocol_mode" id="http_protocol_mode" value="%{http_protocol_mode}" tabindex="36" cssClass="form-control"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>URL </label>
                                    <s:textfield cssClass="form-control" name="http_url" id="http_url" tabindex="37" value="%{http_url}" />
                                </div></div>
                            <div class="col-sm-3">
                                <div class="" style="margin-top:30px">
                                    <label for="http_ssl_req">
                                        SSL&nbsp;Required
                                    </label>
                                    <s:checkbox id="http_ssl_req" name="http_ssl_req" tabindex="38" value="%{http_ssl_req}" onclick="sslRequired('http')"/>
                                </div>
                            </div>
                        </div>

                        <div id="smtpDiv" style="display: none;clear:both">
                            <div id="protocolmsgSmtp"></div>
                            <h3 style="color: #2d8fc8">SMTP Server&nbsp;Details  : </h3>

                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>Receiving&nbsp;Protocol </label>
                                    <s:textfield cssClass="form-control" name="smtp_recv_protocol" id="smtp_recv_protocol" tabindex="38" value="SMTP" readonly="true"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>SMTP&nbsp;Server&nbsp;Host </label>
                                    <s:textfield cssClass="form-control" name="smtp_server_protocol" id="smtp_server_protocol" tabindex="39" value="%{smtp_server_protocol}"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>SMTP&nbsp;Server&nbsp;Port</label>
                                    <s:textfield cssClass="form-control" name="smtp_server_port" id="smtp_server_port" tabindex="40" value="%{smtp_server_port}" onchange="fieldLengthValidator(this);"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>From&nbsp;email&nbsp;address</label>
                                    <s:textfield cssClass="form-control" name="smtp_from_email" id="smtp_from_email" tabindex="41" value="%{smtp_from_email}" onchange="validateEmail(this);"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>To email&nbsp;address </label>
                                    <s:textfield cssClass="form-control" name="smtp_to_email" id="smtp_to_email" tabindex="42" value="%{smtp_to_email}" onchange="validateEmail(this);"/>
                                </div></div>
                        </div>

                        <div id="loadingImage"></div>
                        <div id="as2Div" style="display: none;clear:both">
                            <div id="protocolmsgAs2"></div>
                            <h3 style="color: #2d8fc8">AS2 Server&nbsp;Details  : </h3>
                            <div class="col-sm-12 labl_colunm">
                                <div class="col-sm-3">
                                    <label class="head_label">System&nbsp;Certificates :</label>
                                </div>
                                <div class="col-sm-6">    <label>Upload&nbsp;System&nbsp;Certificate </label><s:file name="upload" id= "attachmentFileNameAs2" label="as2_part_cert" tabindex="44" cssClass="form-control"/>  </div>

                                <%-- <s:file name="as2_part_cert" id= "as2_part_cert" label="as2_part_cert" tabindex="44"/>--%>

                            </div>
                            <div class="col-sm-12 labl_colunm">    
                                <div class="col-sm-3">

                                    <label class="head_label">Organization&nbsp;Profiles:</label>

                                </div>
                                <div class="col-sm-6 gutter_hide">
                                    <div class="col-sm-6 ">
                                        <label>Your&nbsp;Organization </label>
                                        <s:textfield cssClass="form-control" name="as2_partOrgName" id="as2_partOrgName" tabindex="46" value="%{as2_partOrgName}" onkeyup="as2PartnerProfileNames(this);"/>

                                    </div >
                                    <div class="col-sm-6">
                                        <label>My&nbsp;Organization</label>
                                        <s:textfield cssClass="form-control" name="as2_myOrgName" id="as2_myOrgName" tabindex="45" value="%{as2_myOrgName}" onkeyup="as2OrgProfileNames(this);" onblur="isExistedAS2PartnerProfileName();"/><img id="correctImg" style="display: none;" src="/<%=AppConstants.CONTEXT_PATH%>/includes/images/right.png" 
                                             width="13" height="13" border="0"><img id="wrongImg" style="display: none;" src="/<%=AppConstants.CONTEXT_PATH%>/includes/images/wrong.jpg" width="13" height="13" border="0"><img id="loadingImageAjax" style="display: none;" width="16" height="16" border="0" src="<s:url value="/includes/images/ajax-loader.gif"/>">
                                    </div>
                                </div>

                            </div>

                            <div class="col-sm-12 labl_colunm">  
                                <div class="col-sm-3">

                                    <label class="head_label">Partner&nbsp;Profiles:</label>

                                </div>
                                <div class="col-sm-6 gutter_hide">
                                    <div class="col-sm-6"> 
                                        <label>My&nbsp;Partner&nbsp;Profile&nbsp;Name</label>
                                        <s:textfield cssClass="form-control" name="as2_myPartname" id="as2_myPartname" tabindex="47" value="%{as2_myPartname}"/>

                                    </div>
                                    <div class="col-sm-6">    
                                        <label>Your&nbsp;Partner&nbsp;Profile&nbsp;Name </label>
                                        <s:textfield cssClass="form-control" name="as2_yourPartname" id="as2_yourPartname" tabindex="48" value="%{as2_yourPartname}" readonly="true"/>
                                    </div>
                                </div>

                            </div>
                            <hr>
                            <div class="col-sm-12 labl_colunm">               
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>AS2&nbsp;End&nbsp;Point </label>
                                        <label>My&nbsp;End&nbsp;Point </label>
                                        <s:textfield cssClass="form-control" name="as2_myEndPoint" id="as2_myEndPoint" tabindex="49" value="%{as2_myEndPoint}"/>
                                    </div></div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>Your&nbsp;End&nbsp;Point </label>
                                        <s:textfield cssClass="form-control" name="as2_partendpoint" id="as2_partendpoint" tabindex="50" value="%{as2_partendpoint}"/>
                                    </div></div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>Store&nbsp;AS2&nbsp;Messages&nbsp;in </label>
                                        <s:select headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'MailBox':'MailBox','FSA':'FSA'}" name="as2_strMsg" id="as2_strMsg" value="%{as2_strMsg}" tabindex="51" cssClass="form-control"/>
                                    </div></div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>Wait&nbsp;For&nbsp;Synchronous&nbsp;MDN&nbsp;Process</label>
                                        <s:select headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'YES':'Yes','NO':'No'}" name="as2_waitForSync" id="as2_waitForSync" value="%{as2_waitForSync}" tabindex="52"  cssClass="form-control"/>
                                    </div></div>
                                <div class="col-sm-3">
                                    <div class="form-group"> 
                                        <label>Payload&nbsp;Send&nbsp;Mode</label>
                                        <s:select headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'SYNC':'SYNC','ASYNC':'ASYNC','NoMDN':'No MDN'}" name="as2_payloadSendMode" id="as2_payloadSendMode" value="%{as2_payloadSendMode}" tabindex="52" cssClass="form-control"/>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="" style="margin-top:30px">
                                        <label for="as2_ssl_req">
                                            SSL&nbsp;Required
                                        </label>
                                        <s:checkbox  name="as2_ssl_req" id="as2_ssl_req" tabindex="21" value="%{as2_ssl_req}" onclick="sslRequired('as2')" cssClass=""/>
                                    </div>
                                </div>
                            </div>    
                        </div>
                    </div></div>
                <div id="site_content" class="jumbotron">
                    <div class="container">
                        <div id="sslDiv" style="display: none">
                            <h3 style="color: #2d8fc8">SSL : </h3>
                            <div class="col-sm-3">
                                <div class="form-group"> 
                                    <label>SSL&nbsp;Priority:</label>
                                    <s:select list="#@java.util.LinkedHashMap@{'MUST':'Must','NONE':'None'}" name="ssl_priority" id="ssl_priority" value="%{ssl_priority}" tabindex="54" cssClass="form-control" onchange="sslPriorityChange(this.value)"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group"> 
                                    <label>Cipher&nbsp;Strength:</label>
                                    <s:select list="#@java.util.LinkedHashMap@{'STRONG':'Strong','NONE':'None'}" name="ssl_cipher_stergth" id="ssl_cipher_stergth" value="%{ssl_cipher_stergth}" tabindex="56" cssClass="form-control"/>
                                </div></div>
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
                            <h3 style="color: #2d8fc8">SSL : </h3>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>SSL&nbsp;Priority:</label>
                                    <s:select list="#@java.util.LinkedHashMap@{'MUST':'Must','NONE':'None'}" name="ssl_priority2" id="ssl_priority2" value="%{ssl_priority2}" tabindex="54" cssClass="form-control" onchange="sslPriorityChange(this.value)"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group"> 
                                    <label>Cipher&nbsp;Strength:</label>
                                    <s:select list="#@java.util.LinkedHashMap@{'STRONG':'Strong','NONE':'None'}" name="ssl_cipher_stergth2" id="ssl_cipher_stergth2" value="%{ssl_cipher_stergth2}" tabindex="56" cssClass="form-control"/>
                                </div></div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label>CA&nbsp;Certificates&nbsp;(Certificate&nbsp;Groups):</label>
                                    <%--  <s:file name="certGroups" id="certGroups" label="certGroups" tabindex="58"/>--%>
                                    <s:file name="upload1" id= "attachmentFileName" label="certGroups" tabindex="58" cssClass="form-control"/> 

                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12">
                            <div class="col-md-2 pull-right">  
                                <s:if test="%{formAction == 'doAddProfile'}">
                                    <s:submit value="Save" cssClass="btn btn-info" tabindex="171" onclick="return checkProfile('add')"/>
                                </s:if>
                                <s:else>
                                    <s:submit value="Update" cssClass="btn btn-info" tabindex="171" onclick="return checkProfile('update')"/>
                                </s:else>
                            </div>
                            <div class="col-sm-1 pull-right">
                                <s:reset   value="Reset" cssClass="btn btn-info" tabindex="172"/>
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
        <script type="text/javascript">
            function onLoad()
            {
                $("#profiles").addClass("active");
            }
            function checkProfile(flag)
            {
                var commnProtocol = document.getElementById('commnProtocol').value;
                if (commnProtocol == 'FTP') {
                    ftpValidate = validateFTP(flag);
                    if (ftpValidate == false)
                        return false;
                } else if (commnProtocol == 'SFTP') {
                    sftpValidate = validateSFTP(flag);
                    if (sftpValidate == false)
                        return false;
                } else if (commnProtocol == 'HTTP') {
                    httpValidate = validateHTTP(flag);
                    if (httpValidate == false)
                        return false;
                } else if (commnProtocol == 'SMTP') {
                    smtpValidate = validateSMTP();
                    if (smtpValidate == false)
                        return false;
                } else if (commnProtocol == 'AS2') {
                    as2Validate = validateAS2(flag);
                    if (as2Validate == false)
                        return false;
                } else {
                    document.getElementById('tpoCommMsg').innerHTML = "<font color=red>Please fill minimum one Profile</font>";
                    return false;
                }

            }

            function doOnLoad() {
                $("#profiles").addClass("active");

                $('#commnProtocol').trigger("change");
                //$('#commnProtocol').attr('disabled', true);
                var commnProtocol = document.getElementById("commnProtocol").value;
                if (commnProtocol == "FTP")
                {
                    var ftp_ssl = document.getElementById("ftp_ssl_req").value;
                    if (ftp_ssl == "true")
                    {
                        document.getElementById("ftp_ssl_req").onclick();
                    }
                }
                else if (commnProtocol == "AS2")
                {
                    var as2_ssl = document.getElementById("as2_ssl_req").value;
                    if (as2_ssl == "true")
                    {
                        document.getElementById("as2_ssl_req").onclick();
                    }
                }
                else if (commnProtocol == "HTTP")
                {
                    var http_ssl_req = document.getElementById("http_ssl_req").value;
                    if (http_ssl_req == "true")
                    {
                        document.getElementById("http_ssl_req").onclick();
                    }
                }
            }
        </script>

    </body>
</html>
