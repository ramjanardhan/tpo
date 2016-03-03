/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function getXMLHttpRequest() {
    var xmlHttpReq = false;
    // to create XMLHttpRequest object in non-Microsoft browsers
    if (window.XMLHttpRequest) {
        xmlHttpReq = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        try {
            // to create XMLHttpRequest object in later versions
            // of Internet Explorer
            xmlHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (exp1) {
            try {
                // to create XMLHttpRequest object in older versions
                // of Internet Explorer
                xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (exp2) {
                xmlHttpReq = false;
            }
        }
    }
    return xmlHttpReq;
}

function readyStateHandlerText(req, responseTextHandler) {
    return function() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                (document.getElementById("loadingImage")).style.display = "none";
                responseTextHandler(req.responseXML);
            } else {
                alert("HTTP error" + req.status + " : " + req.statusText);
            }
        } else {

            (document.getElementById("loadingImage")).style.display = "block";
        }
    }
}

function readyStateHandlerLoadText(req, responseTextHandler) {
    return function() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                (document.getElementById("loadingImage")).style.display = "none";
                responseTextHandler(req.responseText);
            } else {
                alert("HTTP error" + req.status + " : " + req.statusText);
            }
        } else {

            (document.getElementById("loadingImage")).style.display = "block";
        }
    }
}

function addPartner(flag) {
    var addpartnerName = document.getElementById("addpartnerName").value;
    var addphoneNo = document.getElementById("addphoneNo").value;
    var addaddress1 = document.getElementById("addaddress1").value;
    var addcity = document.getElementById("addcity").value;
    var addstate = document.getElementById("addstate").value;
    var addcountry = document.getElementById("addcountry").value;
    var addzipCode = document.getElementById("addzipCode").value;
    var contactPerson = document.getElementById("contactPerson").value;
    var contactPersonLN = document.getElementById("contactPersonLN").value;
    var contactEmail = document.getElementById("contactEmail").value;
    var url = document.getElementById("url").value;
    var description = document.getElementById("description").value;
    if (flag == 'admin') {
        var roleId = document.getElementById("roleId").value;
    }

    if (addpartnerName.trim() == "") {
        document.getElementById('addpartnerMsg').innerHTML = "<font color=red>Please enter Partner name.</font>";
        return false;
    } else if (contactPerson.length == 0 || contactPerson == "" || contactPerson == null) {
        document.getElementById('addpartnerMsg').innerHTML = "<font color=red>Please enter contact person first name.</font>";
        return false;
    } else if (contactPersonLN.length == 0 || contactPersonLN == "" || contactPersonLN == null) {
        document.getElementById('addpartnerMsg').innerHTML = "<font color=red>Please enter contact person last name.</font>";
        return false;
    } else if (contactEmail.length == 0 || contactEmail == "" || contactEmail == null) {
        document.getElementById('addpartnerMsg').innerHTML = "<font color=red>Please enter contact email.</font>";
        return false;
    } else if (addphoneNo.length == 0 || addphoneNo == "" || addphoneNo == null) {
        document.getElementById('addpartnerMsg').innerHTML = "<font color=red>Please enter phone number.</font>";
        return false;
    } else if (addaddress1.length == 0 || addaddress1 == "" || addaddress1 == null) {
        document.getElementById('addpartnerMsg').innerHTML = "<font color=red>Please enter address.</font>";
        return false;
    } else if (addcity.length == 0 || addcity == "" || addcity == null) {
        document.getElementById('addpartnerMsg').innerHTML = "<font color=red>Please enter city.</font>";
        return false;
    } else if (addcountry.length == 0 || addcountry == "-1" || addcountry == null) {
        document.getElementById('addpartnerMsg').innerHTML = "<font color=red>Please select country.</font>";
        return false;
    } else if (addstate.length == 0 || addstate == "" || addstate == null) {
        document.getElementById('addpartnerMsg').innerHTML = "<font color=red>Please enter state.</font>";
        return false;
    } else if (addzipCode.length == 0 || addzipCode == "" || addzipCode == null) {
        document.getElementById('addpartnerMsg').innerHTML = "<font color=red>Please enter zipcode.</font>";
        return false;
    }
    if (flag == 'admin') {
        if (roleId == 1) {
            var adminUsersList = document.getElementById("adminUsersList").value;
        }
    }
    document.getElementById('addbutton').disabled = true;
    var req = getXMLHttpRequest();
    req.onreadystatechange = readyStateHandlerLoadText(req, addingPartnerNameResponse);
    var url;
    if (flag == 'admin') {
        if (roleId == 1) {
            url = '../ajax/addPartner.action?addpartnerName=' + addpartnerName + '&addphoneNo=' + addphoneNo + '&addaddress1=' + addaddress1 + '&addcity=' + addcity + '&addstate=' + addstate + '&addcountry=' + addcountry + '&addzipCode=' + addzipCode + '&name=' + contactPerson + '&email=' + contactEmail + '&url=' + url + '&description=' + description + '&lastName=' + contactPersonLN + '&roleId=' + roleId + '&assignTo=' + adminUsersList + '&flag=' + flag;
        } else {
            url = '../ajax/addPartner.action?addpartnerName=' + addpartnerName + '&addphoneNo=' + addphoneNo + '&addaddress1=' + addaddress1 + '&addcity=' + addcity + '&addstate=' + addstate + '&addcountry=' + addcountry + '&addzipCode=' + addzipCode + '&name=' + contactPerson + '&email=' + contactEmail + '&url=' + url + '&description=' + description + '&lastName=' + contactPersonLN + '&roleId=' + roleId + '&flag=' + flag;
        }
    } else {
        url = './ajax/selfAddPartner.action?addpartnerName=' + addpartnerName + '&addphoneNo=' + addphoneNo + '&addaddress1=' + addaddress1 + '&addcity=' + addcity + '&addstate=' + addstate + '&addcountry=' + addcountry + '&addzipCode=' + addzipCode + '&name=' + contactPerson + '&email=' + contactEmail + '&url=' + url + '&description=' + description + '&lastName=' + contactPersonLN + '&flag=' + flag;
    }
    //alert(url);
    req.open("GET", url, "true");
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    req.send(null);
}

function addingPartnerNameResponse(resText) {
    //alert(resText);
    document.getElementById("addpartnerMsg").innerHTML = resText;
    //$('#selfReg').modal('hide');
}

/* edit transaction gs and st value start*/
function senderCopy() {
    var senderIdgs = document.getElementById('gsSenderId');
    var senderIdst = document.getElementById('stSenderId');
    senderIdst.value = senderIdgs.value;
}

function receiverCopy() {
    var recIdgs = document.getElementById('gsReceiverId');
    var recIdst = document.getElementById('stReceiverId');
        recIdst.value = recIdgs.value;
}

function versionCopy() {
    var versiongs = document.getElementById('gsVersion');
    var versionst = document.getElementById('stVersion');
    versionst.value = versiongs.value;
}
/* edit transaction gs and st value end*/

function envelopeUpdating() {
    var envelopeDetails = '';
    var transaction = document.getElementById("transaction").value;
    var direction = document.getElementById("direction").value;
    var isaSenderId = document.getElementById("isaSenderId").value;
    var gsSenderId = document.getElementById("gsSenderId").value;
    var stSenderId = document.getElementById("stSenderId").value;
    var isaReceiverId = document.getElementById("isaReceiverId").value;
    var gsReceiverId = document.getElementById("gsReceiverId").value;
    var stReceiverId = document.getElementById("stReceiverId").value;
    var isaVersion = document.getElementById("isaVersion").value;
    var gsVersion = document.getElementById("gsVersion").value;
    var stVersion = document.getElementById("stVersion").value;
    var functionalGroupId = document.getElementById("functionalGroupId").value;
    var responsibleAgencyCode = document.getElementById("responsibleAgencyCode").value;
    var generateAcknowledgement = document.getElementById("generateAcknowledgement").checked;
    var transactionSetIdCode = document.getElementById("transactionSetIdCode").value;

    if (isaSenderId.length == 0 || isaSenderId == "" || isaSenderId == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter senderId ISA.</font>";
        return false;
    } else if (gsSenderId.length == 0 || gsSenderId == "" || gsSenderId == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter senderId GS.</font>";
        return false;
    } else if (stSenderId.length == 0 || stSenderId == "" || stSenderId == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter senderId ST.</font>";
        return false;
    } else if (isaReceiverId.length == 0 || isaReceiverId == "" || isaReceiverId == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter RecieverId ISA.</font>";
        return false;
    } else if (gsReceiverId.length == 0 || gsReceiverId == "" || gsReceiverId == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter RecieverId GS.</font>";
        return false;
    } else if (stReceiverId.length == 0 || stReceiverId == "" || stReceiverId == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter RecieverId ST.</font>";
        return false;
    } else if (isaVersion.length == 0 || isaVersion == "" || isaVersion == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter Version ISA.</font>";
        return false;
    } else if (gsVersion.length == 0 || gsVersion == "" || gsVersion == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter Version GS.</font>";
        return false;
    } else if (stVersion.length == 0 || stVersion == "" || stVersion == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter Version ST.</font>";
        return false;
    } else if (functionalGroupId.length == 0 || functionalGroupId == "" || functionalGroupId == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter Functional ID Code.</font>";
        return false;
    } else if (responsibleAgencyCode.length == 0 || responsibleAgencyCode == "" || responsibleAgencyCode == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter Responsible Agency Code.</font>";
        return false;
    }

    if (isaSenderId != null || isaSenderId != "") {
        if (gsSenderId != null && gsSenderId != "") {
            if (stSenderId != null && stSenderId != "") {
                if (isaReceiverId != null && isaReceiverId != "") {
                    if (gsReceiverId != null && gsReceiverId != "") {
                        if (stReceiverId != null && stReceiverId != "") {
                            if (isaVersion != null && isaVersion != "") {
                                if (gsVersion != null && gsVersion != "") {
                                    if (stVersion != null && stVersion != "") {
                                        if (functionalGroupId != null && functionalGroupId != "") {
                                            if (responsibleAgencyCode != null && responsibleAgencyCode != "") {

                                                envelopeDetails = transaction + "@" + direction + "@" + isaSenderId + "@" + gsSenderId + "@" + stSenderId + "@" + isaReceiverId + "@" + gsReceiverId + "@" + stReceiverId + "@" + isaVersion + "@" + gsVersion + "@" + stVersion + "@" + functionalGroupId + "@" + responsibleAgencyCode + "@" + generateAcknowledgement + "@" + transactionSetIdCode;

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    var req = getXMLHttpRequest();
    req.onreadystatechange = readyStateHandlerLoadText(req, envelopeUpdatingResponse);
    var url = '../ajax/updateEnvelope.action?envelopeDetails=' + envelopeDetails + '&transaction=' + transaction + '&direction=' + direction;
    //alert(url);
    req.open("GET", url, "true");
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    req.send(null);
}

function envelopeUpdatingResponse(resText) {
    //alert(resText);
    document.getElementById("resultMessage").innerHTML = resText;
}

function forgotpassword() {
    var regcontactEmail = document.getElementById("regcontactEmail").value;
    var userid = document.getElementById("userid").value;
    if (userid.length == 0 || userid == "" || userid == null) {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please enter your loginId.</font>";
        return false;
    } else if (regcontactEmail.length == 0 || regcontactEmail == "" || regcontactEmail == null) {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please enter your registered email address.</font>";
        return false;
    }
    var req = getXMLHttpRequest();
    req.onreadystatechange = readyStateHandlerLoadText(req, forgotPwdResponse);
    var url = "./ajax/forgotpassword.action?userid=" + userid + '&email=' + regcontactEmail;
    //alert(url);
    req.open("GET", url, "true");
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    req.send(null);
}

function forgotPwdResponse(resText) {
    //alert(resText);
    document.getElementById("tpResultMessage").innerHTML = resText;
}

function acceptPartner() {
    var roleId = document.getElementById('roleId').value;
    var loginId = document.getElementById('loginId').value;
    var partnerId = document.getElementById('partnerId').value;
    var assignTo = "";
    if (roleId == 1) {
        assignTo = document.getElementById("adminUsersList").value;
    }
    var req = getXMLHttpRequest();
    req.onreadystatechange = readyStateHandlerLoadText(req, acceptPartnerResponse);
    var url;
    if ((roleId == 1) && (assignTo != "-1")) {
        url = "../ajax/acceptPartner.action?assignTo=" + assignTo + "&id=" + partnerId;
    } else {
        url = "../ajax/acceptPartner.action?assignTo=" + loginId + "&id=" + partnerId;
    }
    //alert(url);
    req.open("GET", url, "true");
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    req.send(null);
}

function acceptPartnerResponse(resText) {
    document.getElementById("tpResultMessage").innerHTML = resText;
}

function partnerReject(pId, pName) {
    var release = confirm("Confirm to reject '" + pName + "' partner");
    if (release == true) {
        var req = getXMLHttpRequest();
        req.onreadystatechange = readyStateHandlerLoadText(req, rejectPartnerResponse);
        var url = "../ajax/rejectPartner.action?id=" + pId;
        //alert(url);
        req.open("GET", url, "true");
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        req.send(null);
    }
}

function rejectPartnerResponse(resText) {
    window.location = "../tpOnboarding/tpoPartnersList.action";
    document.getElementById("resultMsg").innerHTML = resText;
}

function testConnectionProfile(i, CommId, protocol, pName) {
    // document.getElementById("iValue").value = i;
    var req = getXMLHttpRequest();
    req.onreadystatechange = readyStateHandlerLoadText(req, testConnectionProfileResponse);
    var url = "../ajax/testProfile.action?communicationId=" + CommId + "&protocol=" + protocol + "&partnerName=" + pName;
    //alert(url);
    req.open("GET", url, "true");
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    req.send(null);
}

function testConnectionProfileResponse(resText) {
    var result;
    if (resText == 'OK') {
        result = 'Success';
    } else {
        result = 'Failed';
    }
    alert(result);
    // var x = document.getElementById("iValue").value;
    // document.getElementById(x).innerHTML = result;
}

function getPwdEmail(protocol) {
    var ftpMethod = document.getElementById("ftp_method").value;
    var ftpConnMethod = document.getElementById("ftp_conn_method").value;
    var ftpRecvProtocol = document.getElementById("ftp_recv_protocol").value;
    var ftpRespTime = document.getElementById("ftp_resp_time").value;
    var ftpHost = document.getElementById("ftp_host").value;
    var ftpPort = document.getElementById("ftp_port").value;
    var ftpUserId = document.getElementById("ftp_userId").value;
    var ftpPwd = document.getElementById("ftp_pwd").value;
    var ftpDirectory = document.getElementById("ftp_directory").value;

    var sftp_conn_method = document.getElementById("sftp_conn_method").value;
    var sftp_auth_method = document.getElementById("sftp_auth_method").value;
    var sftp_host_ip = document.getElementById("sftp_host_ip").value;
    var sftp_remote_port = document.getElementById("sftp_remote_port").value;
    var sftp_remote_userId = document.getElementById("sftp_remote_userId").value;
    var sftp_remote_pwd = document.getElementById("sftp_remote_pwd").value;
    var sftp_method = document.getElementById("sftp_method").value;
    var sftp_directory = document.getElementById("sftp_directory").value;

    var transferMode;
    if (document.addTpOnboard.transferMode.value) {
        transferMode = document.addTpOnboard.transferMode.value;
    }
    if (transferMode == "pull") {
        var req = getXMLHttpRequest();
        $("#loadingImageAjax").show();
        req.onreadystatechange = readyStateHandlerLoadText(req, isPwdSent);
        var url;
        if (protocol == "FTP") {
            url = "../ajax/sendPwdEmail.action?protocol=" + protocol + "&transferMode" + transferMode + "&ftp_method=" + ftpMethod + "&ftp_conn_method=" + ftpConnMethod + "&ftp_recv_protocol=" + ftpRecvProtocol + "&ftp_resp_time=" + ftpRespTime + "&ftp_host=" + ftpHost + "&ftp_port=" + ftpPort + "&ftp_userId=" + ftpUserId + "&ftp_pwd=" + ftpPwd + "&ftp_directory=" + ftpDirectory;
        }
        if (protocol == "SFTP") {
            url = "../ajax/sendPwdEmail.action?protocol=" + protocol + "&transferMode" + transferMode + "&sftp_conn_method=" + sftp_conn_method + "&sftp_auth_method=" + sftp_auth_method + "&sftp_host_ip=" + sftp_host_ip + "&sftp_remote_port=" + sftp_remote_port + "&sftp_remote_userId=" + sftp_remote_userId + "&sftp_remote_pwd=" + sftp_remote_pwd + "&sftp_method=" + sftp_method + "&sftp_directory=" + sftp_directory;
        }
        req.open("GET", url, "true");
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        req.send(null);
    }
}
function isPwdSent(resText) {
    $("#loadingImageAjax").hide();
    if (resText == 'Success') {
        alert("The Password and Server details sent successfully");
    }
}

function activateUser(id, name) {
    var activate = confirm("Confirm to activate '" + name + "'");
    if (activate == true) {
        var req = getXMLHttpRequest();
        req.onreadystatechange = readyStateHandlerLoadText(req, activateUserResponse);
        var url = "../ajax/activateUser.action?userid=" + id;
        req.open("GET", url, "true");
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        req.send(null);
    }
}

function activateUserResponse(resText) {
    window.location = "../tpOnboarding/tpoUsersList.action";
    document.getElementById("resultMsg").innerHTML = resText;
}

function inActivateUser(id, name) {
    var inactivate = confirm("Confirm to inactivate '" + name + "'");
    if (inactivate == true) {
        var req = getXMLHttpRequest();
        req.onreadystatechange = readyStateHandlerLoadText(req, inActivateUserResponse);
        var url = "../ajax/inActivateUser.action?userid=" + id;
        req.open("GET", url, "true");
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        req.send(null);
    }
}

function inActivateUserResponse(resText) {
    window.location = "../tpOnboarding/tpoUsersList.action";
    document.getElementById("resultMsg").innerHTML = resText;
}
