<%@page import="com.mss.tpo.admin.CodeListBean"%>
<%@page import="org.apache.naming.java.javaURLContextFactory"%>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="com.mss.tpo.util.AppConstants"%>
<%@page import="com.mss.tpo.admin.AdminBean"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="TP On-boarding">
        <title>Miracle TP On-boarding</title>
        <script type="text/javascript" src="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" media="screen" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700"/> 
        <link rel="stylesheet" href='<s:url value="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap.min.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/main.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/build.css"/>' type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/css/bootstrap/bootstrap-theme.css" />' media="screen" type="text/css"/>
        <link rel="stylesheet" href='<s:url value="/includes/plugins/datatables/dataTables.bootstrap.css"/>' type="text/css">

        <script>
            function doOnLoad() {
                $("#services").addClass("active");
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
                <h3><b>Code List Editor</b></h3>
            </div>
        </header> 
        <!-- /Header -->
        <!-- Intro -->
        <div class="container">
            <!--  <h3>Trading Partner</h3> -->		
            <!-- Highlights - jumbotron -->
            <div id="site_content" class="jumbotron block_div" style="padding-top: 9px;  width:50%;float: left">
                <div class="container">
                    <div id="loadingImage"></div>
                    <s:form action="../admin/getCodeListName.action" method="post" cssClass="contact-form" name="certForm" id="certForm" theme="simple">
                        <div class="">
                            <div class="row">
                                <div class="col-sm-3">
                                    <label>Name Search</label>
                                    <s:textfield name="name"  id="name" cssClass="form-control"   value="%{name}" style="width : 250%;" tabindex="1"/> 
                                </div>
                                <div class="col-sm-6" style="float : right; bottom: -25px;margin-right: -102px">  
                                    <div>
                                        <s:submit value="Search" cssClass="btn btn-primary" id="button" name="button" tabindex="2" /> 
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-3"> 
                                    <label>Code List </label>
                                    <s:select headerKey="-1" headerValue="--Select Type--" cssClass="form-control" list="listNameMap" name="listName" id="listName" value="%{listName}"  onchange="getList();"  style="width : 250%;" tabindex="3"/> 
                                </div> 
                            </div> 
                        </div>
                    </div>
                </div>
                <br>
                <div class="row" id="site_content" style="float :right;top: 50%">


                    <div class="col-sm-3" style="left : -169px; bottom : -81px"> 
                        <input type="button" id="add" name="add" class="btn btn-primary" value="Add Row" tabindex="4"/>
                    </div> 
                    <div class="col-sm-3" style="left : -120px; bottom : -81px"> 
                        <input type="button" class="btn btn-primary" value="Delete Row" id="deleteRow" tabindex="5"/>
                    </div> 
                    <div class="col-sm-3" style="left : -55px; bottom : -81px"> 
                        <input type="button" class="btn btn-primary" value="Clear Grid" tabindex="6"/>
                    </div> 
                </div>

            </div>
        </div>
        <br>
        <br>
        <div class="container">
            <div id="site_content" class="jumbotron block_div" style="padding-top: 9px;">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-3">
                            <label>Code List Selected</label>
                            <s:textfield name="selectedName"  id="selectedName" cssClass="form-control"   value="%{selectedName}" tabindex="7"/> 
                        </div>
                        <div>
                            <div class="col-sm-3" style="left:251px">
                                <label>Sender Identity</label>
                                <s:select headerKey="-1" headerValue="--Select Type--" cssClass="form-control" list="listNameMap" name="listName1" id="senderId" value="%{listName}" tabindex="8" /> 
                            </div>
                            <div class="col-sm-3" style="left: 282px">
                                <label>Receiver Identity</label>
                                <s:select headerKey="-1" headerValue="--Select Type--" cssClass="form-control" list="listNameMap" name="listName2" id="receiverId" value="%{listName}"  tabindex="9"/> 
                            </div>
                        </div>
                    </div>
                    <br>
                    <br>
                    <div class="row">
                        <div class="col-sm-3">
                            <label>Last Date Modified :</label>

                        </div><div>
                            <label style="margin-left: 494px">Number Of Code List Items :</label>

                        </div>
                    </div>  </div> 
                </s:form>
            <div id="gridDiv">  
                <%--  <s:if test="#session.codeList != null"> 
                       GRid start --%>
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title">Table</h3>
                                </div><!-- /.box-header -->
                                <div class="box-body">
                                    <%!String cssValue = "whiteStripe";
                                        int resultsetTotal;%>
                                    <div style="overflow-x:auto;">
                                        <table align="left" width="100%" border="0" cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td style="background-color: white;">
                                                    <table id="results"  class="table table-bordered table-hover">
                                                        <thead><tr>
                                                                <th>SELECT</th>
                                                                <th>LIST_NAME</th>
                                                                <th>SENDER_ID</th>
                                                                <th>RECEIVER_ID</th>
                                                                <th>LIST_VERSION</th>
                                                                <th>SENDER_ITEM</th>
                                                                <th>RECEIVER_ITEM</th>
                                                                <th>TEXT1</th>
                                                                <th>TEXT2</th>
                                                                <th>TEXT3</th>
                                                                <th>TEXT4</th>
                                                                <th>DESCRIPTION</th>
                                                                <th>TEXT5</th>
                                                                <th>TEXT6</th>
                                                                <th>TEXT7</th> 
                                                                <th>TEXT8</th> 
                                                                <th>TEXT9</th> 
                                                            </tr> </thead>
                                                        <tbody>

                                                            <%if (session.getAttribute("codeList") != null) {
                                                                    java.util.List codeList = new java.util.ArrayList();
                                                                    //CodeListBean codeListBean=null;
                                                                    codeList = (java.util.List) session.getAttribute("codeList");
                                                                    System.out.println("list size is " + codeList.size());
                                                                    int count;
                                                                    for (int j = 0; j < codeList.size(); j++) {
                                                                        CodeListBean codeListBean = (CodeListBean) codeList.get(j);


                                                            %><tr>   <td><input type="checkbox"  id="check<%=j + 1%>" name="check<%=j + 1%>"/></td>
                                                                <td><input value="<%=codeListBean.getListName()%>" id="listName<%=j + 1%>" name="listName<%=j + 1%>"/></td>
                                                                <td><input value="<%=codeListBean.getSender_id()%>" id="senderId<%=j + 1%>" name="senderId<%=j + 1%>"/></td>
                                                                <td><input value="<%=codeListBean.getReceiver_id()%>" id="recId<%=j + 1%>" name="recId<%=j + 1%>"/></td>
                                                                <td><input value="<%=codeListBean.getList_version()%>" id="listVersion<%=j + 1%>" name="listVersion<%=j + 1%>"/></td>
                                                                <td><input value="<%=codeListBean.getSender_item()%>" id="senderItem<%=j + 1%>" name="senderItem<%=j + 1%>"/></td>
                                                                <td><input value="<%=codeListBean.getReceiver_item()%>" id="recItem<%=j + 1%>" name="recItem<%=j + 1%>"/></td>
                                                                <td><input value="<%=codeListBean.getText1()%>" id="text1<%=j + 1%>" name="text1<%=j + 1%>"/></td>
                                                                <td><input value="<%=codeListBean.getText2()%>" id="text2<%=j + 1%>" name="text2<%=j + 1%>"/></td>
                                                                <td><input value="<%=codeListBean.getText3()%>" id="text3<%=j + 1%>" name="text3<%=j + 1%>"/></td>
                                                                <td><input value="<%=codeListBean.getText4()%>" id="text4<%=j + 1%>" name="text4<%=j + 1%>"/></td>
                                                                <td><input value="<%=codeListBean.getDescription()%>" id="desc<%=j + 1%>" name="desc<%=j + 1%>"/></td>
                                                                <td><input value="<%=codeListBean.getText5()%>" id="text5<%=j + 1%>" name="text5<%=j + 1%>"/></td>
                                                                <td><input value="<%=codeListBean.getText6()%>" id="text6<%=j + 1%>" name="text6<%=j + 1%>"/></td>
                                                                <td><input value="<%=codeListBean.getText7()%>" id="text7<%=j + 1%>" name="text7<%=j + 1%>"/></td>
                                                                <td><input value="<%=codeListBean.getText8()%>" id="text8<%=j + 1%>" name="text8<%=j + 1%>"/></td>
                                                                <td><input value="<%=codeListBean.getText9()%>" id="text9<%=j + 1%>" name="text9<%=j + 1%>"/></td>
                                                                    <%
                                                                            }
                                                                        }%>
                                                        </tbody>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="right" colspan="28" style="background-color: white;">
                                                    <div align="right" id="pageNavPosition"></div>
                                                </td>
                                            </tr>
                                        </table>
                                        <%--  <input type="hidden" name="sec_po_list" id="sec_po_list" value="<%=list.size()%>"/> --%>
                                    </div>
                                </div>
                                <%-- Grid End --%>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-sm-3"> 
                            <label>New Code List Name</label>
                            <s:textfield name="newname"  id="newname" cssClass="form-control"   value="%{newname}" tabindex="10"/> 
                        </div>  

                        <div> 
                            <input type="button" class="btn btn-primary pull-right" style="margin-right: 91px;margin-top: 20px;" value="Import To SI" tabindex="11" onclick="getRowValue()"/>
                        </div> 


                    </div>
                </section>
                <%--     </s:if>  --%>
            </div>
        </div>
    </div>
    <!-- /Highlights -->
</div> 
<footer class="footer">
    <div class=" ">
        <s:include value="../includes/template/footer.jsp"/>
    </div>
</footer>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script language="JavaScript" src='<s:url value="/includes/js/headroom.min.js"/>'></script>
<script language="JavaScript" src='<s:url value="/includes/js/jQuery.headroom.min.js"/>'></script>
<script language="JavaScript" src='<s:url value="/includes/js/template.js"/>'></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script language="JavaScript" src='<s:url value="/includes/js/adminValidations.js"/>'></script>
<script src='<s:url value="/includes/bootstrap/js/app.min.js"/>'></script>
<script src='<s:url value="/includes/plugins/datatables/jquery.dataTables.min.js"/>'></script>
<script src='<s:url value="/includes/plugins/datatables/dataTables.bootstrap.min.js"/>'></script>
<script type="text/javascript">
    var count=0;
    $(document).ready(function() {
        $('#add').click(function() {
            var rowCount = $('#results tr').length;
            if(rowCount==2)
            {
                $('#results').dataTable().fnDestroy();
                count=$('#results tr').length;
                $('#results').append(
                '<tr><td><input type="checkbox" name="check'+count+'" id="check'+count+'" theme="simple"/></td>'+
                    '<td><input type="text" value="" id="listName'+count+'" name="listName'+count+'"/></td>'+
                    '<td><input type="text" id="senderId'+count+'" name="senderId'+count+'"/></td>'+
                    '<td><input type="text" id="recId'+count+'" name="recId'+count+'"/></td>'+
                    '<td><input type="text" id="listVersion'+count+'" name="listVersion'+count+'"/></td>'+
                    '<td><input type="text" id="senderItem'+count+'" name="senderItem'+count+'"/></td>'+
                    '<td><input type="text" id="recItem'+count+'" name="recItem'+count+'"/></td>'+
                    '<td><input type="text" id="text1'+count+'" name="text1'+count+'"/></td>'+
                    '<td><input type="text" id="text2'+count+'" name="text2'+count+'"/></td>'+
                    '<td><input type="text" id="text3'+count+'" name="text3'+count+'"/></td>'+
                    '<td><input type="text" id="text4'+count+'" name="text4'+count+'"/></td>'+
                    '<td><input type="text" id="desc'+count+'" name="desc'+count+'"/></td>'+
                    '<td><input type="text" id="text5'+count+'" name="text5'+count+'"/></td>'+
                    '<td><input type="text" id="text6'+count+'" name="text6'+count+'"/></td>'+
                    '<td><input type="text" id="text7'+count+'" name="text7'+count+'"/></td>'+
                    '<td><input type="text" id="text8'+count+'" name="text8'+count+'"/></td>'+
                    '<td><input type="text"  id="text9'+count+'" name="text9'+count+'"/></td>'+
                    '</tr>');
                $('#results').dataTable();
            }else{
                count=$('#results tr').length;
                $('#results').append(
                '<tr><td><input type="checkbox" name="check'+count+'" id="check'+count+'" theme="simple"/></td>'+
                    '<td><input type="text" value="" id="listName'+count+'" name="listName'+count+'"/></td>'+
                    '<td><input type="text" id="senderId'+count+'" name="senderId'+count+'"/></td>'+
                    '<td><input type="text" id="recId'+count+'" name="recId'+count+'"/></td>'+
                    '<td><input type="text" id="listVersion'+count+'" name="listVersion'+count+'"/></td>'+
                    '<td><input type="text" id="senderItem'+count+'" name="senderItem'+count+'"/></td>'+
                    '<td><input type="text" id="recItem'+count+'" name="recItem'+count+'"/></td>'+
                    '<td><input type="text" id="text1'+count+'" name="text1'+count+'"/></td>'+
                    '<td><input type="text" id="text2'+count+'" name="text2'+count+'"/></td>'+
                    '<td><input type="text" id="text3'+count+'" name="text3'+count+'"/></td>'+
                    '<td><input type="text" id="text4'+count+'" name="text4'+count+'"/></td>'+
                    '<td><input type="text" id="desc'+count+'" name="desc'+count+'"/></td>'+
                    '<td><input type="text" id="text5'+count+'" name="text5'+count+'"/></td>'+
                    '<td><input type="text" id="text6'+count+'" name="text6'+count+'"/></td>'+
                    '<td><input type="text" id="text7'+count+'" name="text7'+count+'"/></td>'+
                    '<td><input type="text" id="text8'+count+'" name="text8'+count+'"/></td>'+
                    '<td><input type="text"  id="text9'+count+'" name="text9'+count+'"/></td>'+
                    '</tr>');
            }
            return false;
        }); 
    });
    $(function() {
        $('#deleteRow').click(function() {
            $('input:checked').each(function() {
                $(this).closest('tr').remove();
            })
        });
    }); 
    $(function() {
        $('#deleteRow').click(function() {
            $('input:checked').each(function() {
                $(this).closest('tr').remove();
            })
        });
                    
    }); 
      $(function() {
        $('#results').DataTable({
            "paging": true,
            "lengthChange": true,
            "searching": true,
            "ordering": true,
            "info": true,
            "autoWidth": false
        });
    });
    function getList()
    {
        var listName=document.getElementById("listName").value;
        document.getElementById("selectedName").value=listName;
        window.location="../admin/codeListSearch.action?listName="+listName+"&selectedName="+document.getElementById("selectedName").value;
    }
        
    function getRowValue(){
        var ips = { "jsonData" : [] };
        var rowCount = $('#results tr').length;
        for(i=1;i<rowCount;i++){
            if(document.getElementById('check'+i).checked){
              ips["jsonData"].push({
                    "listName1" : document.getElementById("listName"+i).value,
                    "senderIdInst" : document.getElementById('senderId'+i).value,
                    "recId": document.getElementById('recId'+i).value,
                    "listVerson" : document.getElementById('listVersion'+i).value,
                    "senderItem" : document.getElementById('senderItem'+i).value,
                    "recItem" : document.getElementById('recItem'+i).value,
                    "text1" : document.getElementById('text1'+i).value,
                    "text2" : document.getElementById('text2'+i).value,
                    "text3" : document.getElementById('text3'+i).value,
                    "text4" : document.getElementById('text4'+i).value,
                    "desc" : document.getElementById('desc'+i).value,
                    "text5" :  document.getElementById('text5'+i).value,
                    "text6" : document.getElementById('text6'+i).value,
                    "text7" : document.getElementById('text7'+i).value,
                    "text8" : document.getElementById('text8'+i).value,
                    "text9" : document.getElementById('text9'+i).value
                });
            }
        }
        var array = JSON.stringify(ips["jsonData"]);
        window.location="../admin/codeListAdd.action?json="+encodeURIComponent(array);
    } 
</script>
</body> 
</html>