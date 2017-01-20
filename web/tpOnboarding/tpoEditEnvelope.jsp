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
        <link rel="stylesheet" media="screen" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700"/>
        <link rel="stylesheet" href='<s:url value="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap.min.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/main.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap-theme.css" />' media="screen" type="text/css"/>

        <script>
            function doOnLoad() 
            {
                $("#envelopes").addClass("active");
            }
        </script>
    </head>
    <body onload="doOnLoad();" class="home">
        <script type="text/javascript" src='<s:url value="/includes/js/wz_tooltip.js"/>'></script>
        <div>
            <s:include value="../includes/template/header.jsp"/>
        </div>
        <header id="head">
            <div class="container">
                <h3 class="lableLeft1"><b>Edit&nbsp;Envelope</b></h3>   
            </div>
        </header>  
        <div class="container">
             <div id="loadingImage"></div>
            <s:form action="" method="POST" enctype="multipart/form-data" name="updateEnvelope" id="updateEnvelope" theme="simple">
                <div id="site_content" class="jumbotron">
                    <div class="container">
                        <%-- envelop --%>  
                         <center> <div id="resultMessage"></div></center>
                        <div id="envelope">
                            <h3 style="color: #2d8fc8">Envelope&nbsp;:</h3>
                            <input type="hidden" name="envelopeDetails" id="envelopeDetails" value=""/>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <div>
                                            <h4 style="color: black">Transaction&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield type="text" style="border: 0;cursor:default" name="transaction" id="transaction" value="%{tpOnboardingBean.transaction}" readonly="true" /></h4>
                                        </div> 
                                    </div></div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <h4 style="color: black">Direction&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield type="text" style="border: 0;cursor:default" name="direction" id="direction" value="%{tpOnboardingBean.direction}" readonly="true" /></h4>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <div><h4><h4>SenderID:</h4></h4> </div> 
                                    </div></div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>ISA</label>
                                        <s:textfield cssClass="form-control" name="isaSenderId" id="isaSenderId" value="%{tpOnboardingBean.isaSenderId}" tabindex="67" onchange="fieldLengthValidatorEditEnvelope(this);"/>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>GS</label>
                                        <s:textfield cssClass="form-control" name="gsSenderId" id="gsSenderId" value="%{tpOnboardingBean.gsSenderId}" tabindex="68" onchange="fieldLengthValidatorEditEnvelope(this);" onkeyup="senderCopy();"/>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>ST</label>
                                        <s:textfield cssClass="form-control" name="stSenderId" id="stSenderId" value="%{tpOnboardingBean.stSenderId}" tabindex="69" onchange="fieldLengthValidatorEditEnvelope(this);"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <div><h4>Receiver ID:</h4> </div> 
                                    </div></div>
                                <div class="col-sm-3">
                                    <div class="form-group">

                                        <s:select list="{'12345','123456'}" name="isaReceiverId" id="isaReceiverId" value="%{tpOnboardingBean.isaReceiverId}" tabindex="70" cssClass="form-control"/>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <s:select list="{'12345','123456'}" name="gsReceiverId" id="gsReceiverId" value="%{tpOnboardingBean.gsReceiverId}" tabindex="71" cssClass="form-control" onclick="receiverCopy();"/>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <s:textfield cssClass="form-control" name="stReceiverId" id="stReceiverId" value="%{tpOnboardingBean.stReceiverId}" tabindex="72"  />
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <div><h4>Version:</h4> </div>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <s:textfield cssClass="form-control" name="isaVersion" id="isaVersion" value="%{tpOnboardingBean.isaVersion}" tabindex="73" onchange="fieldLengthValidatorEditEnvelope(this);"/>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <s:textfield cssClass="form-control" name="gsVersion" id="gsVersion" value="%{tpOnboardingBean.gsVersion}" tabindex="74" onchange="fieldLengthValidatorEditEnvelope(this);" onkeyup="versionCopy();"/>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <s:textfield cssClass="form-control" name="stVersion" id="stVersion" value="%{tpOnboardingBean.stVersion}" tabindex="75" onchange="fieldLengthValidatorEditEnvelope(this);"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <div ><h4>Functional ID Code:</h4></div>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <s:textfield cssClass="form-control" name="functionalGroupId" id="functionalGroupId" value="%{tpOnboardingBean.functionalGroupId}" tabindex="76" onchange="fieldLengthValidatorEditEnvelope(this);"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <div><h4>Responsible Agency Code:</h4></div>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <s:textfield cssClass="form-control" name="responsibleAgencyCode" id="responsibleAgencyCode" placeholder="x" value="%{tpOnboardingBean.responsibleAgencyCode}" tabindex="77" onchange="fieldLengthValidatorEditEnvelope(this);"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <div ><h4>Generate An Acknowledgement:</h4></div>
                                    </div>                                  
                                </div> 
                                <div class="col-sm-3">
                                    <div class="form-group">
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <s:checkbox  name="generateAcknowledgement" id="generateAcknowledgement" tabindex="78" value="%{tpOnboardingBean.generateAcknowledgement}" />
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <div ><h4>Transaction Set ID Code:</h4></div>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <s:textfield cssClass="form-control" name="transactionSetIdCode" id="transactionSetIdCode" value="%{tpOnboardingBean.transactionSetIdCode}" tabindex="79" onchange="fieldLengthValidatorEditEnvelope(this);"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%-- envelop  End--%>
                            
                            <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-1 pull-right"><input type="button" value="Update" class="btn btn-primary" onclick="return envelopeUpdating();"/></div>
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
        <script language="JavaScript" src='<s:url value="/includes/js/GeneralAjax.js"/>'></script>
        <script language="JavaScript" src='<s:url value="/includes/js/tpOnbordingDeatails.js"/>'></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
    </body>
</html>