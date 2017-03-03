
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.mss.tpo.util.AppConstants"%>
<%@page import="com.mss.tpo.tpOnboarding.TpOnboardingBean"%>
<html>
    <head>
        <title>Miracle TP On-boarding</title>
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
        <link rel="stylesheet" href='<s:url value="/includes/plugins/datatables/dataTables.bootstrap.css"/>' type="text/css">
        <style>
            .continum{
                white-space: nowrap;
            }
            .continum h4{
                font-size:15px !important; 
            }
            .btn-upload{
                width: 40px;
                border-radius: 5px;
                left: 221px;
                top: -45px;
                height: 33px;
                padding: 6px 1px;
            }
            .hide{
                display: none;
            }
            pre code {
                width: 484px;
                overflow-x: auto;
            }
            .multifile_remove_input {
                color: red;
                text-decoration: none;
            }
            #my-button{
                font-size: 15px;
                height: 31px;
                left: 448px;
                padding: 4px;
                text-align: center;
                top: 16px;
                width: 100px;
                z-index: 1;
                border-radius: 6px;
            }
            #my-button i{
                padding-left:9px;
            }
            .file_input{
                left: -140px;
                opacity: 0;
                position: relative;
                width: 100px;
                z-index: 100;
            }
            .multifile_container{
                clear:both;
            }
            .uploadButton{
                width: 40px;
                height: 30px;
                border-radius: 5px;
                left: 589px;
                top: 19px;
                margin: 0 12px;
            }
            .uploadButton > i{
                text-align: center;
                margin-left: 13px;
                margin-top: 8px; 
            }
        </style>
    </head>
    <body onload="doOnLoad();" class="home">
        <script type="text/javascript" src='<s:url value="/includes/js/wz_tooltip.js"/>'></script>
        <div>
            <s:include value="../includes/template/header.jsp"/>
        </div>
        <header id="head">
            <div class="container">
                <h3 > <b>PayLoad Upload Transactions  </b></h3>
            </div>
        </header>  
        <div class="container">
            <s:form action="doPayloadUpload" method="POST" name="payloadForm" enctype="multipart/form-data" theme="simple">
                <s:hidden name="transaction" id="transaction" value=""/>
                <div id="site_content" class="jumbotron">
                    <div class="container">
                        <center> <div id="resultMessage"></div>
                            <div class="row" id="responseString">   
                                <%
                                    if (session.getAttribute(AppConstants.REQ_RESULT_MSG) != null) {
                                        String responseString = session.getAttribute(AppConstants.REQ_RESULT_MSG).toString();
                                        out.println(responseString);
                                        session.setAttribute(AppConstants.REQ_RESULT_MSG, null);
                                    }
                                %>
                            </div>
                        </center> 
                        <div class="row"> 
                            <div class="col-sm-3"> 
                                <div class="form-group">
                                    <label>Doc_Type<span class="text-danger">*</span></label>
                                    <s:select headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'Standard':'Standard','Non-Standard':'Non-Standard'}" tabindex="1" name="docType" id="docType" value="%{docType}"  onchange="setdoctype();" cssClass="form-control" />         
                                </div> 
                            </div>  
                            <div class="col-sm-3"> 
                                <div class="form-group">
                                    <label>Direction<span class="text-danger">*</span></label>
                                    <s:select headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'Inbound':'Inbound','Outbound':'Outbound'}" tabindex="2" name="direction" id="direction" value="%{direction}"  onchange="setdirection();" cssClass="form-control" />         
                                </div> 
                            </div> 
                        </div>
                        <br>
                        <div class="row"> 
                            <div class="col-sm-3" id="inBoundTransactions" style="display: none">
                                <div class="form-group">
                                    <label>Inbound Transactions<span class="text-danger">*</span></label>
                                    <div class="lableLeft">
                                        <s:radio name="ibTransaction" id="ibTransaction" list="{'850','855','856','810'}" value="%{ibTransaction}" onchange="transactionChange(this.value)" cssClass="from-control" tabindex="3"></s:radio>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-3" id="outBoundTransactions" style="display: none">
                                    <div class="form-group">
                                        <label>Outbound Transactions<span class="text-danger">*</span></label>
                                        <div class="lableLeft">
                                        <s:radio name="obTransaction" id="obTransaction" list="{'850','855','856','810'}" value="%{obTransaction}" onchange="transactionChange(this.value)" cssClass="from-control" tabindex="4"></s:radio>
                                        </div>      
                                    </div>
                                </div>
                            </div>
                        <div class="row" >
                            <div class="col-sm-3"> 
                                <div class="form-group" id="connTypeDiv" style="display: none">
                                    <label>Connection Type<span class="text-danger">*</span></label>
                                    <s:select headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'Communication_Protocol':'Communication Protocol','File_System':'File system'}" tabindex="5" name="conn_type" id="conn_type" value="%{conn_type}"  cssClass="form-control" onchange="getPayloadProtocols();"/>         
                                </div> 
                            </div> 
                            <div class="col-sm-3"> 
                                <div class="form-group" id="protocolDiv" style="display: none">
                                    <label>Protocol<span class="text-danger">*</span></label>
                                    <s:select headerKey="-1" headerValue="-- Select --" list="protocolsList" name="protocol" id="protocol" value="%{protocol}" cssClass="form-control" onchange="getCommunicationsList(this.value);" tabindex="6"/>         
                                </div> 
                            </div>
                        </div>
                        <br>
                        <center><div id="CommMsg"></div></center>
                        <div class="container" id="communicationsGrid">
                            <s:if test="#session.tpoProtocolsHeadersList != null"> 
                                <s:if test="#session.tpoCommunicationsList != null"> 
                                    <label>Communications<span class="text-danger">*</span></label>

                                    <div id="site_content" class="jumbotron">
                                        <div class="container">
                                            <center><div id="resultMsg"></div></center>
                                            <center><div id="protocolmsgSsl"></div></center>
                                            <table id="payloadTable" name="payloadTable" class="table table-bordered table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Select</th>
                                                            <%
                                                                String protocol = session.getAttribute("protocol").toString();
                                                                java.util.List headerList = (java.util.List) session.getAttribute(AppConstants.TPO_ProtocolsHeadersList);
                                                                for (int i = 0; i < headerList.size(); i++) {
                                                            %>
                                                        <th> <% out.println(headerList.get(i));%></th>
                                                            <%
                                                                } %>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        java.util.List list = (java.util.List) session.getAttribute(AppConstants.TPO_CommunicationsList);
                                                        if (list.size() != 0) {
                                                            TpOnboardingBean tpOnboardingBean;
                                                            for (int i = 0; i < list.size(); i++) {
                                                                tpOnboardingBean = (TpOnboardingBean) list.get(i);
                                                    %>

                                                    <% if ("FTP".equalsIgnoreCase(protocol)) {%>
                                                    <tr>
                                                        <td><input type="radio" name="communicationId" value="<%=(tpOnboardingBean.getId())%>" /></td>
                                                        <td> <% out.println(tpOnboardingBean.getId()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getPartnerId()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getFtp_recv_protocol()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getContactEmail()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getFtp_host()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getFtp_port()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getFtp_userId()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getFtp_pwd()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getFtp_directory()); %> </td>
                                                    </tr>
                                                    <%  }   %>

                                                    <% if ("SFTP".equalsIgnoreCase(protocol)) {%>
                                                    <tr>
                                                        <td><input type="radio" name="communicationId" value="<%=(tpOnboardingBean.getId())%>" /></td>
                                                        <td> <% out.println(tpOnboardingBean.getId()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getPartnerId()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getCommnProtocol()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getContactEmail()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getSftp_host_ip()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getSftp_remote_port()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getSftp_remote_userId()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getSftp_remote_pwd()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getSftp_directory()); %> </td>
                                                    </tr>
                                                    <%  }   %>

                                                    <% if ("HTTP".equalsIgnoreCase(protocol)) {%>
                                                    <tr>
                                                        <td><input type="radio" name="communicationId" value="<%=(tpOnboardingBean.getId())%>" /></td>
                                                        <td> <% out.println(tpOnboardingBean.getId()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getPartnerId()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getHttp_recv_protocol()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getHttp_endpoint()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getHttp_port()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getHttp_protocol_mode()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getHttp_url()); %> </td>
                                                    </tr>
                                                    <%  }   %>

                                                    <% if ("SMTP".equalsIgnoreCase(protocol)) {%>
                                                    <tr>
                                                        <td><input type="radio" name="communicationId" value="<%=(tpOnboardingBean.getId())%>" /></td>
                                                        <td> <% out.println(tpOnboardingBean.getId()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getPartnerId()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getCommnProtocol()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getSmtp_recv_protocol()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getSmtp_server_port()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getSmtp_from_email()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getSmtp_to_email()); %> </td>
                                                    </tr>
                                                    <%  }   %>

                                                    <% if ("AS2".equalsIgnoreCase(protocol)) {%>
                                                    <tr>
                                                        <td><input type="radio" name="communicationId" value="<%=(tpOnboardingBean.getId())%>" /></td>
                                                        <td> <% out.println(tpOnboardingBean.getId()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getPartnerId()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getCommnProtocol()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getAs2_myOrgName()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getAs2_partOrgName()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getAs2_myEndPoint()); %> </td>
                                                        <td> <% out.println(tpOnboardingBean.getAs2_strMsg()); %> </td>
                                                    </tr>
                                                    <%  }   %>

                                                    <%     }
                                                        }%>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </s:if> 
                            </s:if> 
                        </div>
                        <br>
                        <%-- 850 Inbound   Start div--%>    
                        <div id="ibenvelop850" style="display: none" >
                            <table id="uploadTable850ib">
                                <tr><td><s:file name="upload850ib" id="upload850ib" theme="simple" /></td></tr>
                            </table>
                            <input type="button" id="remove850ib" name="remove850ib" value="remove"/>
                            <input type="button" value="add" id="addMoreFile850ib"/>
                        </div>
                        <%-- 850 Inbound  End div--%>
                        <%-- 855 Inbound   Start div--%>
                        <div id="ibenvelop855" style="display: none" >
                            <table id="uploadTable855ib" style="float:left">
                                <tr><td><s:file name="upload855ib" theme="simple" /></td></tr>
                            </table>
                            <input type="button" id="remove855ib" name="remove850ib" value="remove"/>
                            <input type="button" value="add" id="addMoreFile855ib"/>
                        </div>
                        <%-- 855 Inbound  End div--%>
                        <%-- 856 Inbound   Start div--%>
                        <div id="ibenvelop856" style="display: none" >
                            <table id="uploadTable856ib" style="float:left">
                                <tr><td><s:file name="upload856ib" theme="simple" /></td></tr>
                            </table>
                            <input type="button" id="remove856ib" name="remove856ib" value="remove"/>
                            <input type="button" value="add" id="addMoreFile856ib"/>
                        </div>
                        <%-- 856 Inbound   End div--%>
                        <%-- 810 Inbound   Start div--%>
                        <div id="ibenvelop810" style="display: none" >
                            <table id="uploadTable810ib" style="float:left">
                                <tr><td><s:file name="upload810ib" theme="simple" /></td></tr>
                            </table>
                            <input type="button" id="remove810ib" name="remove810ib" value="remove"/>
                            <input type="button" value="add" id="addMoreFile810ib"/>
                        </div>
                        <%-- 810 Inbound   End div--%>
                        <%-- 850 outbound   End div--%>
                        <div id="obenvelop850" style="display: none" >
                            <table id="uploadTable850ob" style="float:left">
                                <tr><td><s:file name="upload850ob" theme="simple" /></td></tr>
                            </table>
                            <input type="button" id="remove850ob" name="remove850ob" value="remove"/>
                            <input type="button" value="add" id="addMoreFile850ob"/>
                        </div>
                        <%-- 850 Outbound   End div--%>
                        <%-- 855 Outbound   Start div--%>
                        <div id="obenvelop855" style="display: none" >
                            <table id="uploadTable855ob" style="float:left">
                                <tr><td><s:file name="upload855ob" theme="simple" /></td></tr>
                            </table>
                            <input type="button" id="remove855ob" name="remove855ob" value="remove"/>
                            <input type="button" value="add" id="addMoreFile855ob"/>
                        </div>
                        <%-- 855 Outbound   End div--%>
                        <%-- 856 Outbound   Start div--%>
                        <div id="obenvelop856" style="display: none" >
                            <table id="uploadTable856ob" style="float:left">
                                <tr><td><s:file name="upload856ob" theme="simple" /></td></tr>
                            </table>
                            <input type="button" id="remove856ob" name="remove856ob" value="remove"/>
                            <input type="button" value="add" id="addMoreFile856ob"/>
                        </div>
                        <%-- 856 Outbound   End div--%>
                        <%-- 810 Outbound   Start div--%>
                        <div id="obenvelop810" style="display: none" >
                            <table id="uploadTable810ob" style="float:left">
                                <tr><td><s:file name="upload810ob" theme="simple" /></td></tr>
                            </table>
                            <input type="button" id="remove810ob" name="remove810ob" value="remove"/>
                            <input type="button" value="add" id="addMoreFile810ob"/>
                        </div>
                        <%-- 810 Outbound   End div--%>
                        <div class="col-sm-12">
                            <div class="col-sm-1 pull-right" id="saveButton" style="display: none">
                                <s:submit value="Save" cssClass="btn btn-primary pull-right" tabindex="7" onclick="return checkPayload()"/>
                            </div>
                            <div class="col-sm-1  pull-right">
                                <s:reset value="Reset" cssClass="btn btn-primary pull-right" tabindex="8" onclick="resetPayload()"/>
                            </div>
                        </div>
                    </div>
                </div>
            </s:form>
        </div>
        <footer class="footer">
            <div class=" ">
                <s:include value="../includes/template/footer.jsp"/>
            </div>
        </footer>
        <script language="JavaScript" src='<s:url value="/includes/js/headroom.min.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/jQuery.headroom.min.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/template.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/tpOnbordingDeatails.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/GeneralAjax.js"/>'></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script type="text/javascript" src='<s:url value="/includes/js/jquery.multifile.js"/>'></script>
        <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
        <script src='<s:url value="../includes/plugins/datatables/jquery.dataTables.min.js"/>'></script>
        <script src='<s:url value="../includes/plugins/datatables/dataTables.bootstrap.min.js"/>'></script>
        <script type="text/javascript">
        function doOnLoad() {
            $("#PayLoad").addClass("active");
            var docType = document.getElementById('docType').value;
            var direction = document.getElementById('direction').value;
            var transaction = document.getElementById('transaction').value;
            var conn_type = document.getElementById('conn_type').value;
            $("#docType").trigger("change");
            $("#direction").trigger("change");
            if (direction == 'Inbound') {
                var ibTransaction = document.forms["payloadForm"]["ibTransaction"].value;
                transactionChange(ibTransaction);
                //  $("#ibTransaction").trigger("change");
            }
            if (direction == 'Outbound') {
                var obTransaction = document.forms["payloadForm"]["obTransaction"].value;
                transactionChange(obTransaction);
                //$("#obTransaction").trigger("change");
            }
            $("#conn_type").trigger("change");
        }

        function setdoctype() {
            var docType = document.getElementById("docType").value;
        }

        function setdirection() {
            var direction = document.getElementById("direction").value;
            if (direction == 'Inbound') {
                 $('#conn_type').prop('disabled',false);
                $("#inBoundTransactions").show();
                $("#outBoundTransactions").hide();
                $("#ibenvelop850").hide();
                $("#ibenvelop855").hide();
                $("#ibenvelop856").hide();
                $("#ibenvelop810").hide();
                $("#obenvelop850").hide();
                $("#obenvelop855").hide();
                $("#obenvelop856").hide();
                $("#obenvelop810").hide();
            } else if (direction == 'Outbound') {
                document.getElementById('conn_type').value = "File_System";
                $('#conn_type').prop('disabled',true);
                $("#inBoundTransactions").hide();
                $("#outBoundTransactions").show();
                $("#communicationsGrid").hide();
                $("#protocolDiv").hide();
//                $("#ibenvelop850").hide();
//                $("#ibenvelop855").hide();
//                $("#ibenvelop856").hide();
//                $("#ibenvelop810").hide();
//                $("#obenvelop850").hide();
//                $("#obenvelop855").hide();
//                $("#obenvelop856").hide();
//                $("#obenvelop810").hide();
            } else {
                $("#inBoundTransactions").hide();
                $("#outBoundTransactions").hide();
                $("#communicationsGrid").hide();
                $("#protocolDiv").hide();
                $("#ibenvelop850").hide();
                $("#ibenvelop855").hide();
                $("#ibenvelop856").hide();
                $("#ibenvelop810").hide();
                $("#obenvelop850").hide();
                $("#obenvelop855").hide();
                $("#obenvelop856").hide();
                $("#obenvelop810").hide();
            }
            // document.getElementById("connTypeDiv").style.display = 'block';
        }

        function resetPayload() {
            window.location = "../payload/payloadUpload.action";
        }

        function transactionChange(x) {
            var direction = document.getElementById('direction').value;
            document.getElementById('transaction').value = x;
            if (direction == 'Inbound') {
                 $('#conn_type').prop('disabled',false);
                if (x == '850') {
                    document.getElementById("connTypeDiv").style.display = 'block';
                    document.getElementById("saveButton").style.display = 'block';
                    $("#ibenvelop850").show();
                    $("#ibenvelop855").hide();
                    $("#ibenvelop856").hide();
                    $("#ibenvelop810").hide();
                    $("#obenvelop850").hide();
                    $("#obenvelop855").hide();
                    $("#obenvelop856").hide();
                    $("#obenvelop810").hide();
                }
                if (x == '855') {
                    document.getElementById("connTypeDiv").style.display = 'block';
                    document.getElementById("saveButton").style.display = 'block';
                    $("#ibenvelop850").hide();
                    $("#ibenvelop855").show();
                    $("#ibenvelop856").hide();
                    $("#ibenvelop810").hide();
                    $("#obenvelop850").hide();
                    $("#obenvelop855").hide();
                    $("#obenvelop856").hide();
                    $("#obenvelop810").hide();
                }
                if (x == '856') {
                    document.getElementById("connTypeDiv").style.display = 'block';
                    document.getElementById("saveButton").style.display = 'block';
                    $("#ibenvelop850").hide();
                    $("#ibenvelop855").hide();
                    $("#ibenvelop856").show();
                    $("#ibenvelop810").hide();
                    $("#obenvelop850").hide();
                    $("#obenvelop855").hide();
                    $("#obenvelop856").hide();
                    $("#obenvelop810").hide();
                }
                if (x == '810') {
                    document.getElementById("connTypeDiv").style.display = 'block';
                    document.getElementById("saveButton").style.display = 'block';
                    $("#ibenvelop850").hide();
                    $("#ibenvelop855").hide();
                    $("#ibenvelop856").hide();
                    $("#ibenvelop810").show();
                    $("#obenvelop850").hide();
                    $("#obenvelop855").hide();
                    $("#obenvelop856").hide();
                    $("#obenvelop810").hide();
                }
            } else if (direction == 'Outbound') {
                $('#conn_type').prop('disabled',true);
                $("#communicationsGrid").hide();
                $("#protocolDiv").hide();
                if (x == '850') {
                    document.getElementById("connTypeDiv").style.display = 'block';
                    document.getElementById("saveButton").style.display = 'block';
                    $("#obenvelop850").show();
                    $("#obenvelop855").hide();
                    $("#obenvelop856").hide();
                    $("#obenvelop810").hide();
                    $("#ibenvelop850").hide();
                    $("#ibenvelop855").hide();
                    $("#ibenvelop856").hide();
                    $("#ibenvelop810").hide();
                }
                if (x == '855') {
                    document.getElementById("connTypeDiv").style.display = 'block';
                    document.getElementById("saveButton").style.display = 'block';
                    $("#obenvelop850").hide();
                    $("#obenvelop855").show();
                    $("#obenvelop856").hide();
                    $("#obenvelop810").hide();
                    $("#ibenvelop850").hide();
                    $("#ibenvelop855").hide();
                    $("#ibenvelop856").hide();
                    $("#ibenvelop810").hide();
                }
                if (x == '856') {
                    document.getElementById("connTypeDiv").style.display = 'block';
                    document.getElementById("saveButton").style.display = 'block';
                    $("#obenvelop850").hide();
                    $("#obenvelop855").hide();
                    $("#obenvelop856").show();
                    $("#obenvelop810").hide();
                    $("#ibenvelop850").hide();
                    $("#ibenvelop855").hide();
                    $("#ibenvelop856").hide();
                    $("#ibenvelop810").hide();
                }
                if (x == '810') {
                    document.getElementById("connTypeDiv").style.display = 'block';
                    document.getElementById("saveButton").style.display = 'block';
                    $("#obenvelop850").hide();
                    $("#obenvelop855").hide();
                    $("#obenvelop856").hide();
                    $("#obenvelop810").show();
                    $("#ibenvelop850").hide();
                    $("#ibenvelop855").hide();
                    $("#ibenvelop856").hide();
                    $("#ibenvelop810").hide();
                }
            } else {
                document.getElementById("connTypeDiv").style.display = 'none';
                document.getElementById("saveButton").style.display = 'none';
                $("#ibenvelop850").hide();
                $("#ibenvelop855").hide();
                $("#ibenvelop856").hide();
                $("#ibenvelop810").hide();
                $("#obenvelop850").hide();
                $("#obenvelop855").hide();
                $("#obenvelop856").hide();
                $("#obenvelop810").hide();
            }
        }

        $(document).ready(function() {
            $('#addMoreFile850ib').click(function() {
                $('#uploadTable850ib').append(
                        '<tr><td><s:file name="upload850ib" theme="simple" />' +
                        '</td></tr>');
                return false;
            });
            $('#remove850ib').click(function() {
                $('#uploadTable850ib tr:last').remove();
            })

            $('#addMoreFile855ib').click(function() {
                $('#uploadTable855ib').append(
                        '<tr><td><s:file name="upload855ib" theme="simple" />' +
                        '</td></tr>');
                return false;
            });
            $('#remove855ib').click(function() {
                $('#uploadTable855ib tr:last').remove();
            })

            $('#addMoreFile856ib').click(function() {
                $('#uploadTable856ib').append(
                        '<tr><td><s:file name="upload856ib" theme="simple" />' +
                        '</td></tr>');
                return false;
            });
            $('#remove856ib').click(function() {
                $('#uploadTable856ib tr:last').remove();
            })

            $('#addMoreFile810ib').click(function() {
                $('#uploadTable810ib').append(
                        '<tr><td><s:file name="upload810ib" theme="simple" />' +
                        '</td></tr>');
                return false;
            });
            $('#remove810ib').click(function() {
                $('#uploadTable810ib tr:last').remove();
            })

            $('#addMoreFile850ob').click(function() {
                $('#uploadTable850ob').append(
                        '<tr><td><s:file name="upload850ob" theme="simple" />' +
                        '</td></tr>');
                return false;
            });
            $('#remove850ob').click(function() {
                $('#uploadTable850ob tr:last').remove();
            })

            $('#addMoreFile855ob').click(function() {
                $('#uploadTable855ob').append(
                        '<tr><td><s:file name="upload855ob" theme="simple" />' +
                        '</td></tr>');
                return false;
            });
            $('#remove855ob').click(function() {
                $('#uploadTable855ob tr:last').remove();
            })

            $('#addMoreFile856ob').click(function() {
                $('#uploadTable856ob').append(
                        '<tr><td><s:file name="upload856ob" theme="simple" />' +
                        '</td></tr>');
                return false;
            });
            $('#remove856ob').click(function() {
                $('#uploadTable856ob tr:last').remove();
            })

            $('#addMoreFile810ob').click(function() {
                $('#uploadTable810ob').append(
                        '<tr><td><s:file name="upload810ob" theme="simple" />' +
                        '</td></tr>');
                return false;
            });
            $('#remove810ob').click(function() {
                $('#uploadTable810ob tr:last').remove();
            })

        });

        function getPayloadProtocols() {
            var conn_type = document.getElementById("conn_type").value;
            if (conn_type == 'Communication_Protocol') {
                document.getElementById("protocolDiv").style.display = 'block';
            } else {
                document.getElementById("protocolDiv").style.display = 'none';
            }
        }

        $(function() {
            $('#payloadTable').DataTable({
                "paging": true,
                "lengthChange": true,
                "searching": true,
                "ordering": true,
                "info": true,
                "autoWidth": false
            });
        });

        function fileUploadValidation(file) {
            if (file != '') {
                var extension = file.substring(file.lastIndexOf('.') + 1);
                if (extension == "cer" || extension == "cert") {
                    document.getElementById('resultMsg').innerHTML = "";
                } else {
                    document.getElementById('resultMsg').innerHTML = "<font color=red>Invalid file extension!Please select cer or cert file.</font>"
                    return false;
                }
            }
            return true;
        }
        </script>
    </body>
</html>