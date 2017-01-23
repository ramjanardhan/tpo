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
    return function () {
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
    return function () {
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
    if (flag == 'admin') {
        if (roleId == 1) {
            var url = '../ajax/addPartner.action?addpartnerName=' + addpartnerName + '&addphoneNo=' + addphoneNo + '&addaddress1=' + addaddress1 + '&addcity=' + addcity + '&addstate=' + addstate + '&addcountry=' + addcountry + '&addzipCode=' + addzipCode + '&name=' + contactPerson + '&email=' + contactEmail + '&url=' + url + '&description=' + description + '&lastName=' + contactPersonLN + '&roleId=' + roleId + '&assignTo=' + adminUsersList + '&flag=' + flag;
        } else {
            var url = '../ajax/addPartner.action?addpartnerName=' + addpartnerName + '&addphoneNo=' + addphoneNo + '&addaddress1=' + addaddress1 + '&addcity=' + addcity + '&addstate=' + addstate + '&addcountry=' + addcountry + '&addzipCode=' + addzipCode + '&name=' + contactPerson + '&email=' + contactEmail + '&url=' + url + '&description=' + description + '&lastName=' + contactPersonLN + '&roleId=' + roleId + '&flag=' + flag;
        }
    } else {
        var url = './ajax/selfAddPartner.action?addpartnerName=' + addpartnerName + '&addphoneNo=' + addphoneNo + '&addaddress1=' + addaddress1 + '&addcity=' + addcity + '&addstate=' + addstate + '&addcountry=' + addcountry + '&addzipCode=' + addzipCode + '&name=' + contactPerson + '&email=' + contactEmail + '&url=' + url + '&description=' + description + '&lastName=' + contactPersonLN + '&flag=' + flag;
    }
    //alert(url);
    req.open("GET", url, "true");
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    req.send(null);
}

function addingPartnerNameResponse(resText) {
    //alert(resText);
    document.getElementById("addpartnerMsg").innerHTML = resText;
}

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
    if (roleId == 1) {
        var url = "../ajax/acceptPartner.action?assignTo=" + assignTo + "&id=" + partnerId;
    } else {
        var url = "../ajax/acceptPartner.action?assignTo=" + loginId + "&id=" + partnerId;
    }
    alert(url);
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
        alert(url);
        req.open("GET", url, "true");
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        req.send(null);
    }
}

function rejectPartnerResponse(resText) {
    window.location = "../tpOnboarding/tpoPartnersList.action";
    document.getElementById("resultMsg").innerHTML = resText;
}




