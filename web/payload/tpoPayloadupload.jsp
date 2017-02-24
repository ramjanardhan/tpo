
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
        <link rel="stylesheet" media="screen" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700"/>
        <link rel="stylesheet" href='<s:url value="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap.min.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/main.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap-theme.css" />' media="screen" type="text/css"/>
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
            <s:form action="doPayloadUpload" method="POST" enctype="multipart/form-data" theme="simple">
                <s:hidden name="transaction" id="transaction" value=""/>
                <div id="site_content" class="jumbotron" style="padding:3px 33px">
                    <div class="container">
                        <div class="row" id="responseString">   
                        <center>
                            <%
                                if (session.getAttribute(AppConstants.REQ_RESULT_MSG) != null) {
                                    String responseString = session.getAttribute(AppConstants.REQ_RESULT_MSG).toString();
                                    out.println(responseString);
                                    session.setAttribute(AppConstants.REQ_RESULT_MSG, null);
                                }
                            %>
                        </center> </div>
                        <div class="row">     
                            <div id="TransactionsDiv" class="col-sm-2"> 
                                <h4 style="color: #2d8fc8" class="heading_4">DocType : </h4>

                                <s:select headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'Standard':'Standard','Non-Standard':'Non-Standard'}" tabindex="1" name="docType" id="docType" value="%{docType}"  onchange="setdoctype();" cssClass="form-control" />         
                            </div>  
                            <div id="TransactionsDiv" class="col-sm-2"> 
                                <h4 style="color: #2d8fc8" class="heading_4">Direction : </h4>
                                <s:select headerKey="-1" headerValue="-- Select --" list="#@java.util.LinkedHashMap@{'Inbound':'Inbound','Outbound':'Outbound'}" tabindex="1" name="direction" id="direction" value="%{direction}"  onchange="setdirection();" cssClass="form-control" />         
                            </div> 
                        </div>
                        <br>
                        <div id="TransactionsDiv" > 
                            <div>                                     
                                <div>
                                    <div class="col-sm-3" id="inBoundTransactions" style="display: none">
                                        <div class="form-group">
                                            <label>Inbound Transactions</label>
                                            <div class="lableLeft">
                                                <s:radio name="ibTransaction" id="ibTransaction" list="{'850','855','856','810'}" value="%{ibTransaction}" onchange="transactionChange(this.value)" cssClass="from-control" ></s:radio>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-3" id="outBoundTransactions" style="display: none">
                                            <div class="form-group">
                                                <label>Outbound Transactions</label>
                                                <div class="lableLeft">
                                                <s:radio name="obTransaction" id="obTransaction" list="{'850','855','856','810'}" value="%{obTransaction}" onchange="transactionChange(this.value)" cssClass="from-control"></s:radio>
                                                </div>      
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div></div>
                    <div id="site_content" class="jumbotron" style="padding:3px 33px">
                        <div class="container">
                            <div class="row" id="ddt" style="display: none">
                                <div class="col-sm-4 continum">
                                    <div class="form-group">
                                        <div>
                                            <h4 style="color: black">DocumentType&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield type="text" style="border: 0;cursor:default" name="docTypeValue" id="docTypeValue" value="" readonly="true" cssClass="jumbotron_bg"/></h4>
                                    </div> 
                                </div></div>
                            <div class="col-sm-4 continum">
                                <div class="form-group">
                                    <h4 style="color: black">Direction&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield type="text" style="border: 0;cursor:default" name="directionValue" id="directionValue" value="" readonly="true" cssClass="jumbotron_bg"/></h4>
                                </div>
                            </div>
                            <div class="col-sm-4 continum">
                                <div class="form-group">
                                    <h4 style="color: black">Transaction&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;<s:textfield type="text" style="border: 0;cursor:default" name="transactionValue" id="transactionValue" value="" readonly="true" cssClass="jumbotron_bg"/></h4>
                                </div>
                            </div>
                        </div>
                        <br>
                        <%-- 850 Inbound   Start div--%>    
                        <div id="ibenvelop850" style="display: none" >
                            <table id="uploadTable850ib" border="1">
				<tr><td><s:file label="Choose File to Upload" name="upload850ib" theme="simple"/></td></tr>
			</table>
                         <input type="button" id="remove850ib" name="remove850ib" value="remove"/>
		<input type="button" label="Add more files" value="add" id="addMoreFile850ib"/>
                        </div>
                        <%-- 850 Inbound  End div--%>
                        <%-- 855 Inbound   Start div--%>
                        <div id="ibenvelop855" style="display: none" >
                             <table id="uploadTable855ib" border="1">
				<tr><td><s:file label="Choose File to Upload" name="upload855ib" theme="simple"/></td></tr>
			</table>
                        <input type="button" id="remove855ib" name="remove850ib" value="remove"/>
		<input type="button" label="Add more files" value="add" id="addMoreFile855ib"/>
                        </div>
                        <%-- 855 Inbound  End div--%>
                        <%-- 856 Inbound   Start div--%>
                        <div id="ibenvelop856" style="display: none" >
                             <table id="uploadTable856ib" border="1">
				<tr><td><s:file label="Choose File to Upload" name="upload856ib" theme="simple"/></td></tr>
			</table>
                        <input type="button" id="remove856ib" name="remove850ib" value="remove"/>
		<input type="button" label="Add more files" value="add" id="addMoreFile856ib"/>
                        </div>
                        <%-- 856 Inbound   End div--%>
                        <%-- 810 Inbound   Start div--%>
                        <div id="ibenvelop810" style="display: none" >
                             <table id="uploadTable810ib" border="1">
				<tr><td><s:file label="Choose File to Upload" name="upload810ib" theme="simple"/></td></tr>
			</table>
                        <input type="button" id="remove810ib" name="remove850ib" value="remove"/>
		<input type="button" label="Add more files" value="add" id="addMoreFile810ib"/>
                        </div>
                        <%-- 810 Inbound   End div--%>
                        <%-- 850 outbound   End div--%>
                        <div id="obenvelop850" style="display: none" >
                            <table id="uploadTable850ob" border="1">
				<tr><td><s:file label="Choose File to Upload" name="upload850ob" theme="simple"/></td></tr>
			</table>
                        <input type="button" id="remove850ob" name="remove850ib" value="remove"/>
		<input type="button" label="Add more files" value="add" id="addMoreFile850ob"/>
                        </div>
                        <%-- 850 Outbound   End div--%>
                        <%-- 855 Outbound   Start div--%>
                        <div id="obenvelop855" style="display: none" >
                            <table id="uploadTable855ob" border="1">
				<tr><td><s:file label="Choose File to Upload" name="upload855ob" theme="simple"/></td></tr>
			</table>
                        <input type="button" id="remove855ob" name="remove855ob" value="remove"/>
		<input type="button" label="Add more files" value="add" id="addMoreFile855ob"/>
                        </div>
                        <%-- 855 Outbound   End div--%>
                        <%-- 856 Outbound   Start div--%>
                        <div id="obenvelop856" style="display: none" >
                             <table id="uploadTable856ob" border="1">
				<tr><td><s:file label="Choose File to Upload" name="upload856ob" theme="simple"/></td></tr>
			</table>
                        <input type="button" id="remove856ob" name="remove856ob" value="remove"/>
		<input type="button" label="Add more files" value="add" id="addMoreFile856ob"/>
                        </div>
                        <%-- 856 Outbound   End div--%>
                        <%-- 810 Outbound   Start div--%>
                        <div id="obenvelop810" style="display: none" >
                             <table id="uploadTable810ob" border="1">
				<tr><td><s:file label="Choose File to Upload" name="upload810ob" theme="simple"/></td></tr>
			</table>
                        <input type="button" id="remove810ob" name="remove810ob" value="remove"/>
		<input type="button" label="Add more files" value="add" id="addMoreFile810ob"/>
                        </div>
                        <%-- 810 Outbound   End div--%>
                        <div class="col-sm-12">
                            <div class="col-sm-1 pull-right">
                                <s:submit   value="Save" cssClass="btn btn-primary pull-right" tabindex="112" onclick="return checkEnvelopes()"/>
                            </div>
                            <div class="col-sm-1  pull-right">
                                <s:reset   value="Reset" cssClass="btn btn-primary pull-right" tabindex="113" onclick="resetPayload()"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </s:form>
    </div>
</div>
</div>
</div>
</div> 
</div>        
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
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript" src='<s:url value="/includes/js/jquery.multifile.js"/>'></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script type="text/javascript">



//        $(document).ready(function () {
//            $('#file_input').multifile();
//            $('#file_input856').multifile();
//            $('#file_input850ib').multifile();
//            $('#file_input855ib').multifile();
//            $('#file_input856ib').multifile();
//            $('#file_input810ib').multifile();
//            $('#file_input850ob').multifile();
//            $('#file_input850ob_').multifile();
//            $('#file_input855ob').multifile();
//            $('#file_input856ob').multifile();
//            $('#file_input810ob').multifile();
//        });

        function doOnLoad()
        {
            $("#PayLoad").addClass("active");
            document.getElementById("docType").value = "-1";
            document.getElementById("direction").value = "-1";
            $("#inBoundTransactions").hide();
            $("#outBoundTransactions").hide();
            $("#ibenvelop850").hide();
            $("#ibenvelop855").hide();
            $("#ibenvelop856").hide();
            $("#ibenvelop810").hide();
            $("#obenvelop850").hide();
            $("#obenvelop855").hide();
            $("#obenvelop856").hide();
            $("#obenvelop810").hide();
            $("#ddt").hide();
        }
        function setdoctype()
        {
            document.getElementById("responseString").style.display="none";
            var docType = document.getElementById("docType").value;
            document.getElementById("docTypeValue").value = docType;
        }
        function setdirection()
        {
            document.getElementById("responseString").style.display="none";
            var direction = document.getElementById("direction").value;
            if (direction == 'Inbound') {
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
                $("#ddt").hide();
            } else if (direction == 'Outbound') {
                $("#inBoundTransactions").hide();
                $("#outBoundTransactions").show();
                $("#ibenvelop850").hide();
                $("#ibenvelop855").hide();
                $("#ibenvelop856").hide();
                $("#ibenvelop810").hide();
                $("#obenvelop850").hide();
                $("#obenvelop855").hide();
                $("#obenvelop856").hide();
                $("#obenvelop810").hide();
                $("#ddt").hide();
            } else {
                $("#inBoundTransactions").hide();
                $("#outBoundTransactions").hide();
                $("#ibenvelop850").hide();
                $("#ibenvelop855").hide();
                $("#ibenvelop856").hide();
                $("#ibenvelop810").hide();
                $("#obenvelop850").hide();
                $("#obenvelop855").hide();
                $("#obenvelop856").hide();
                $("#obenvelop810").hide();
                $("#ddt").hide();
            }
            document.getElementById("directionValue").value = direction;
        }

        function resetPayload() {
            document.getElementById("docType").value = "-1";
            document.getElementById("direction").value = "-1";
            $("#inBoundTransactions").hide();
            $("#outBoundTransactions").hide();
            $("#ibenvelop850").hide();
            $("#ibenvelop855").hide();
            $("#ibenvelop856").hide();
            $("#ibenvelop810").hide();
            $("#obenvelop850").hide();
            $("#obenvelop855").hide();
            $("#obenvelop856").hide();
            $("#obenvelop810").hide();
            $("#ddt").hide();
        }
        function transactionChange(x) {
            var direction = document.getElementById('direction').value;
            document.getElementById('transaction').value = x;
            if (direction == 'Inbound') {
                if (x == '850') {
                    document.getElementById('transactionValue').value = '850';
                    document.getElementById('directionValue').value = 'Inbound';
                    $("#ibenvelop850").show();
                    $("#ibenvelop855").hide();
                    $("#ibenvelop856").hide();
                    $("#ibenvelop810").hide();
                    $("#obenvelop850").hide();
                    $("#obenvelop855").hide();
                    $("#obenvelop856").hide();
                    $("#obenvelop810").hide();
                    $("#ddt").show();
                }
                if (x == '855') {
                    document.getElementById('transactionValue').value = '855';
                    document.getElementById('directionValue').value = 'Inbound';
                    $("#ibenvelop850").hide();
                    $("#ibenvelop855").show();
                    $("#ibenvelop856").hide();
                    $("#ibenvelop810").hide();
                    $("#obenvelop850").hide();
                    $("#obenvelop855").hide();
                    $("#obenvelop856").hide();
                    $("#obenvelop810").hide();
                    $("#ddt").show();
                }
                if (x == '856') {
                    document.getElementById('transactionValue').value = '856';
                    document.getElementById('directionValue').value = 'Inbound';
                    $("#ibenvelop850").hide();
                    $("#ibenvelop855").hide();
                    $("#ibenvelop856").show();
                    $("#ibenvelop810").hide();
                    $("#obenvelop850").hide();
                    $("#obenvelop855").hide();
                    $("#obenvelop856").hide();
                    $("#obenvelop810").hide();
                    $("#ddt").show();
                }
                if (x == '810') {
                    document.getElementById('transactionValue').value = '810';
                    document.getElementById('directionValue').value = 'Inbound';
                    $("#ibenvelop850").hide();
                    $("#ibenvelop855").hide();
                    $("#ibenvelop856").hide();
                    $("#ibenvelop810").show();
                    $("#obenvelop850").hide();
                    $("#obenvelop855").hide();
                    $("#obenvelop856").hide();
                    $("#obenvelop810").hide();
                    $("#ddt").show();
                }
            } else if (direction == 'Outbound') {
                if (x == '850') {
                    document.getElementById('transactionValue').value = '850';
                    document.getElementById('directionValue').value = 'Outbound';
                    $("#obenvelop850").show();
                    $("#obenvelop855").hide();
                    $("#obenvelop856").hide();
                    $("#obenvelop810").hide();
                    $("#ibenvelop850").hide();
                    $("#ibenvelop855").hide();
                    $("#ibenvelop856").hide();
                    $("#ibenvelop810").hide();
                    $("#ddt").show();
                }
                if (x == '855') {
                    document.getElementById('transactionValue').value = '855';
                    document.getElementById('directionValue').value = 'Outbound';
                    $("#obenvelop850").hide();
                    $("#obenvelop855").show();
                    $("#obenvelop856").hide();
                    $("#obenvelop810").hide();
                    $("#ibenvelop850").hide();
                    $("#ibenvelop855").hide();
                    $("#ibenvelop856").hide();
                    $("#ibenvelop810").hide();
                    $("#ddt").show();
                }
                if (x == '856') {
                    document.getElementById('transactionValue').value = '856';
                    document.getElementById('directionValue').value = 'Outbound';
                    $("#obenvelop850").hide();
                    $("#obenvelop855").hide();
                    $("#obenvelop856").show();
                    $("#obenvelop810").hide();
                    $("#ibenvelop850").hide();
                    $("#ibenvelop855").hide();
                    $("#ibenvelop856").hide();
                    $("#ibenvelop810").hide();
                    $("#ddt").show();
                }
                if (x == '810') {
                    document.getElementById('transactionValue').value = '810';
                    document.getElementById('directionValue').value = 'Outbound';
                    $("#obenvelop850").hide();
                    $("#obenvelop855").hide();
                    $("#obenvelop856").hide();
                    $("#obenvelop810").show();
                    $("#ibenvelop850").hide();
                    $("#ibenvelop855").hide();
                    $("#ibenvelop856").hide();
                    $("#ibenvelop810").hide();
                    $("#ddt").show();
                }
            } else {
                $("#ibenvelop850").hide();
                $("#ibenvelop855").hide();
                $("#ibenvelop856").hide();
                $("#ibenvelop810").hide();
                $("#obenvelop850").hide();
                $("#obenvelop855").hide();
                $("#obenvelop856").hide();
                $("#obenvelop810").hide();
                $("#ddt").hide();
            }
        }

$(document).ready(function() {
    $('#addMoreFile850ib').click(function() {
    	$('#uploadTable850ib').append(
                '<tr><td><s:file label="Choose File to Upload" name="upload850ib" theme="simple"/>'+
                '</td></tr>');
    	return false;
    });
    $('#remove850ib').click(function(){
        $('#uploadTable850ib tr:last').remove();
    })
     

    $('#addMoreFile855ib').click(function() {
    	$('#uploadTable855ib').append(
                '<tr><td><s:file label="Choose File to Upload" name="upload855ib" theme="simple"/>'+
                '</td></tr>');
    	return false;
    });
    $('#remove855ib').click(function(){
        $('#uploadTable855ib tr:last').remove();
    })
     

    $('#addMoreFile856ib').click(function() {
    	$('#uploadTable856ib').append(
                '<tr><td><s:file label="Choose File to Upload" name="upload856ib" theme="simple"/>'+
                '</td></tr>');
    	return false;
    });
    $('#remove856ib').click(function(){
        $('#uploadTable856ib tr:last').remove();
    })

    $('#addMoreFile810ib').click(function() {
    	$('#uploadTable810ib').append(
                '<tr><td><s:file label="Choose File to Upload" name="upload810ib" theme="simple"/>'+
                '</td></tr>');
    	return false;
    });
    $('#remove810ib').click(function(){
        $('#uploadTable810ib tr:last').remove();
    })
     

    $('#addMoreFile850ob').click(function() {
    	$('#uploadTable850ob').append(
                '<tr><td><s:file label="Choose File to Upload" name="upload850ob" theme="simple"/>'+
                '</td></tr>');
    	return false;
    });
    $('#remove850ob').click(function(){
        $('#uploadTable850ob tr:last').remove();
    })
     

    $('#addMoreFile855ob').click(function() {
    	$('#uploadTable855ob').append(
                '<tr><td><s:file label="Choose File to Upload" name="upload855ob" theme="simple"/>'+
                '</td></tr>');
    	return false;
    });
    $('#remove855ob').click(function(){
        $('#uploadTable855ob tr:last').remove();
    })
     

    $('#addMoreFile856ob').click(function() {
    	$('#uploadTable856ob').append(
                '<tr><td><s:file label="Choose File to Upload" name="upload856ob" theme="simple"/>'+
                '</td></tr>');
    	return false;
    });
    $('#remove856ob').click(function(){
        $('#uploadTable856ob tr:last').remove();
    })
     

    $('#addMoreFile810ob').click(function() {
    	$('#uploadTable810ob').append(
                '<tr><td><s:file label="Choose File to Upload" name="upload810ob" theme="simple"/>'+
                '</td></tr>');
    	return false;
    });
    $('#remove810ob').click(function(){
        $('#uploadTable810ob tr:last').remove();
    })
     
});

</script>
<script type="text/javascript" src='<s:url value="/includes/js/jquery.multifile.js"/>'></script>
</body>
</html>

