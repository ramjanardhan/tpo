/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var ib850 = "";
var ib855 = "";
var ib856 = "";
var ib810 = "";
var ob850 = "";
var ob855 = "";
var ob856 = "";
var ob810 = "";

var allowedFiles = [".cer", ".cert"];
var regex = new RegExp("([a-zA-Z0-9\s_\\.\-:])+(" + allowedFiles.join('|') + ")$");

function ValidateExtension(fileName) {
    var regex = new RegExp("([a-zA-Z0-9\s_\\.\-:])+(" + allowedFiles.join('|') + ")$");
    if (!regex.test(fileName.value.toLowerCase())) {
        document.getElementById('protocolmsgSsl').innerHTML = "<font color=red>Please upload files having extensions: <b>" + allowedFiles.join(', ') + "</b> only.</font>";
        return false;
    }
    return true;
}

function checkPartnerInfo() {
    var contactEmail = document.getElementById("contactEmail").value;
    var phoneNo = document.getElementById("phoneNo").value;
    var address1 = document.getElementById("address1").value;
    var city = document.getElementById("city").value;
    var state = document.getElementById("state").value;
    var country = document.getElementById("country").value;
    var zipCode = document.getElementById("zipCode").value;
    if (contactEmail == "") {
        document.getElementById("tpResultMessage").innerHTML = "<font style='color:red'>Please enter email</font>";
        return false;
    } else if (phoneNo == "") {
        document.getElementById("tpResultMessage").innerHTML = "<font style='color:red'>Please enter phone number</font>";
        return false;
    } else if (address1 == "") {
        document.getElementById("tpResultMessage").innerHTML = "<font style='color:red'>Please enter address</font>";
        return false;
    } else if (city == "") {
        document.getElementById("tpResultMessage").innerHTML = "<font style='color:red'>Please enter city</font>";
        return false;
    } else if (state == "") {
        document.getElementById("tpResultMessage").innerHTML = "<font style='color:red'>Please enter state</font>";
        return false;
    } else if (country == "-1") {
        document.getElementById("tpResultMessage").innerHTML = "<font style='color:red'>Please select country</font>";
        return false;
    } else if (zipCode == "") {
        document.getElementById("tpResultMessage").innerHTML = "<font style='color:red'>Please enter zip code</font>";
        return false;
    } else
        return true;
}


function checkPwd() {
    var pwd = document.getElementById("regpassword").value;
    var cnfPwd = document.getElementById("cnfPassword").value;
    if (pwd == "") {
        document.getElementById('tpoResultMessage').innerHTML = "<font color=red>Please enter Password</font>";
        return false;
    } else if (cnfPwd == "") {
        document.getElementById('tpoResultMessage').innerHTML = "<font color=red>Please enter Confirm Password</font>";
        return false;
    } else if (pwd != cnfPwd) {
        document.getElementById('tpoResultMessage').innerHTML = "<font color=red>Passwords not matched</font>";
        document.getElementById("regpassword").value = "";
        document.getElementById("cnfPassword").value = "";
        return false;
    } else if (pwd == cnfPwd) {
        document.getElementById('tpoResultMessage').innerHTML = "<font color=green>Passwords matched</font>";
        return true;
    }
}

function checkPartnerPwd() {
    var uservalue = document.getElementById("partnerName").value;
    var password = document.getElementById("regpassword").value;
    if (uservalue == "-1") {
        document.getElementById("tpoResultMessage").innerHTML = "<font color=red> Please select partner name</font>";
        return false;
    }
    if (password == "") {
        document.getElementById("tpoResultMessage").innerHTML = "<font color=red> Please enter password</font>";
        return false;
    }
}

function showPartnerPwdBox() {
    document.getElementById("pwdBox").style.display = 'block';
    var uservalue = document.getElementById("partnerName").value;
    if (uservalue == "-1") {
        document.getElementById("pwdBox").style.display = 'none';
    }
}

function checkUserPwd() {
    var uservalue = document.getElementById("contactName").value;
    var password = document.getElementById("regpassword").value;
    if (uservalue == "-1") {
        document.getElementById("tpoResultMessage").innerHTML = "<font color=red> Please select a user from the list </font>";
        return false;
    }
    if (password == "") {
        document.getElementById("tpoResultMessage").innerHTML = "<font color=red> Please enter password </font>";
        return false;
    }
}

function showUserPwdBox() {
    document.getElementById("pwdBox").style.display = 'block';
    var uservalue = document.getElementById("contactName").value;
    if (uservalue == "-1") {
        document.getElementById("pwdBox").style.display = 'none';
    }
}

function checkProfile(flag) {
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

function protocolsSelect(x) {
    document.getElementById("protocolValue").value = x;
    document.getElementById("responseString").style.display = 'none';
    if (x == '-1') {
        // document.getElementById('protocolmsg').innerHTML = "";
        document.getElementById('tpResultMessage').innerHTML = "";
        $("#ftpDiv").hide();
        $("#sftpDiv").hide();
        $("#httpDiv").hide();
        $("#smtpDiv").hide();
        $("#as2Div").hide();
        $("#sslDiv").hide();
        $("#sslDiv2").hide();
        $("#transferModeDiv").hide();
        document.getElementById("saveButton").style.display = 'none';
    } else if (x == 'AS2') {
        document.getElementById('protocolmsg').innerHTML = "";
        $("#ftpDiv").hide();
        $("#sftpDiv").hide();
        $("#httpDiv").hide();
        $("#smtpDiv").hide();
        $("#as2Div").show();
        $("#sslDiv").hide();
        $("#sslDiv2").hide();
        $("#transferModeDiv").hide();
        document.getElementById("saveButton").style.display = 'block';
        //$('#transferMode').attr('disabled', true);
        //document.forms["addTpOnboard"]["transferMode"].value = 'pull';
    } else if (x == 'SMTP') {
        document.getElementById('protocolmsg').innerHTML = "";
        $("#ftpDiv").hide();
        $("#sftpDiv").hide();
        $("#httpDiv").hide();
        $("#smtpDiv").show();
        $("#as2Div").hide();
        $("#sslDiv").hide();
        $("#sslDiv2").hide();
        $("#transferModeDiv").hide();
        document.getElementById("saveButton").style.display = 'block';
        //$('#transferMode').attr('disabled', true);
        //document.forms["addTpOnboard"]["transferMode"].value = 'pull';
    } else {
        document.getElementById('protocolmsg').innerHTML = "";
        $("#ftpDiv").hide();
        $("#sftpDiv").hide();
        $("#httpDiv").hide();
        $("#smtpDiv").hide();
        $("#as2Div").hide();
        $("#sslDiv").hide();
        $("#sslDiv2").hide();
        $("#transferModeDiv").show();
        document.getElementById("saveButton").style.display = 'none';
        //document.getElementById('transferMode').disabled =false;
    }
    document.getElementById("transferModeMsg").style.display = "none";
    var transferMode = document.forms["addTpOnboard"]["transferMode"].value;
    if (transferMode == "pull" || transferMode == "push")

    {
        gettransferModeSelection(transferMode);
    }
//gettransferModeSelection(transferMode);
//transferModeSelection(transferMode);
}
function sslRequired(x) {
    var transferMode;
    if (document.addTpOnboard.transferMode.value) {
        transferMode = document.addTpOnboard.transferMode.value;
    }
    if (x == 'ftp') {
        var ftp_ssl = document.getElementById("ftp_ssl_req").checked;
        if (ftp_ssl == true) {
            if (transferMode == 'pull') {
                $("#sslDiv").show();
            }
            if (transferMode == 'push') {
                $("#sslDiv2").show();
            }
        } else {
            $("#sslDiv").hide();
            $("#sslDiv2").hide();
        }
    }
    if (x == 'http') {
        var http_ssl = document.getElementById("http_ssl_req").checked;
        if (http_ssl == true) {
            if (transferMode == 'pull') {
                $("#sslDiv2").show();
            }
            if (transferMode == 'push') {
                $("#sslDiv").show();
            }
        } else {
            $("#sslDiv").hide();
            $("#sslDiv2").hide();
        }
    }
    if (x == 'as2') {
        var as2_ssl = document.getElementById("as2_ssl_req").checked;
        if (as2_ssl == true) {
            $("#sslDiv2").show();
        } else {
            $("#sslDiv").hide();
            $("#sslDiv2").hide();
        }
    }
}

function sslPriorityChange(x) {
    if (x == 'NONE') {
        document.getElementById("ssl_passphrase").readOnly = true;
        $('#ssl_cipher_stergth').attr('disabled', true)
    } else {
        document.getElementById("ssl_passphrase").readOnly = false;
        $('#ssl_cipher_stergth').attr('disabled', false)
    }
}

function fieldLengthValidator(element) {
    var i = 0;
    var k = 0;
    if (element.value != null && (element.value != "")) {
        if (element.id == 'contactLastName' || element.id == 'regcontactLName' || element.id == 'contactEmail' || element.id == 'contactName' || element.id == 'city' || element.id == 'state' || element.id == 'regcity' || element.id == 'regstate' || element.id == 'regpartnerName' || element.id == 'regcontactName' || element.id == 'regpassword' || element.id == 'regcontactEmail' || element.id == 'cnfPassword') {
            i = 50;
        }
        if (element.id == 'regzipCode' || element.id == 'zipCode') {
            i = 10;
            validatenumber(element);
        }
        if (element.id == 'regaddress' || element.id == 'address1') {
            i = 250;
        }
        if (element.id == 'phoneNo' || element.id == 'regphoneNo') {
            k = 1;
            i = 15;
            validatenumber(element);
            generalFormatPhone(element);
        }
        /* 850 IB validation */
        if (element.id == 'isa850senderIdIB' || element.id == 'gs850senderIdIB' || element.id == 'st850senderIdIB' || element.id == 'gs850VersionIB' || element.id == 'st850VersionIB') {
            i = 15;
        }
        if (element.id == 'isa850VersionIB') {
            i = 5;
        }
        if (element.id == 'fun850GroupIdIB' || element.id == 'res850AgecodeIB' || element.id == 'trans850IdcodeIB') {
            i = 3;
        }
        /* 850 end*/
        /* 855 IB validation */
        if (element.id == 'isa855senderIdIB' || element.id == 'gs855senderIdIB' || element.id == 'st855senderIdIB' || element.id == 'gs855VersionIB' || element.id == 'st855VersionIB') {
            i = 15;
        }
        if (element.id == 'isa855VersionIB') {
            i = 5;
        }
        if (element.id == 'fun855GroupIdIB' || element.id == 'res855AgecodeIB' || element.id == 'trans855IdcodeIB') {
            i = 3;
        }
        /* 855 end*/
        /* 856 IB validation */
        if (element.id == 'isa856senderIdIB' || element.id == 'gs856senderIdIB' || element.id == 'st856senderIdIB' || element.id == 'gs856VersionIB' || element.id == 'st856VersionIB') {
            i = 15;
        }
        if (element.id == 'isa856VersionIB') {
            i = 5;
        }
        if (element.id == 'fun856GroupIdIB' || element.id == 'res856AgecodeIB' || element.id == 'trans856IdcodeIB') {
            i = 3;
        }
        /* 856 end*/
        /* 810 IB validation */
        if (element.id == 'isa810senderIdIB' || element.id == 'gs810senderIdIB' || element.id == 'st810senderIdIB' || element.id == 'gs810VersionIB' || element.id == 'st810VersionIB') {
            i = 15;
        }
        if (element.id == 'isa810VersionIB') {
            i = 5;
        }
        if (element.id == 'fun810GroupIdIB' || element.id == 'res810AgecodeIB' || element.id == 'trans810IdcodeIB') {
            i = 3;
        }
        /* 810 end*/
        /* 850 OB validation */
        if (element.id == 'isa850senderIdOB' || element.id == 'gs850senderIdOB' || element.id == 'st850senderIdOB' || element.id == 'gs850VersionOB' || element.id == 'st850VersionOB') {
            i = 15;
        }
        if (element.id == 'isa850VersionOB') {
            i = 5;
        }
        if (element.id == 'fun850GroupIdOB' || element.id == 'res850AgecodeOB' || element.id == 'trans850IdcodeOB') {
            i = 3;
        }
        /* 850 end*/
        /* 855 OB validation */
        if (element.id == 'isa855senderIdOB' || element.id == 'gs855senderIdOB' || element.id == 'st855senderIdOB' || element.id == 'gs855VersionOB' || element.id == 'st855VersionOB') {
            i = 15;
        }
        if (element.id == 'isa855VersionOB') {
            i = 5;
        }
        if (element.id == 'fun855GroupIdOB' || element.id == 'res855AgecodeOB' || element.id == 'trans855IdcodeOB') {
            i = 3;
        }
        /* 855 end*/
        /* 856 OB validation */
        if (element.id == 'isa856senderIdOB' || element.id == 'gs856senderIdOB' || element.id == 'st856senderIdOB' || element.id == 'gs856VersionOB' || element.id == 'st856VersionOB') {
            i = 15;
        }
        if (element.id == 'isa856VersionOB') {
            i = 5;
        }
        if (element.id == 'fun856GroupIdOB' || element.id == 'res856AgecodeOB' || element.id == 'trans856IdcodeOB') {
            i = 3;
        }
        /* 856 end*/
        /* 810 OB validation */
        if (element.id == 'isa810senderIdOB' || element.id == 'gs810senderIdOB' || element.id == 'st810senderIdOB' || element.id == 'gs810VersionOB' || element.id == 'st810VersionOB') {
            i = 15;
        }
        if (element.id == 'isa810VersionOB') {
            i = 5;
        }
        if (element.id == 'fun810GroupIdOB' || element.id == 'res810AgecodeOB' || element.id == 'trans810IdcodeOB') {
            i = 3;
        }
        /* 810 end*/
        if (element.id == 'ftp_resp_time' || element.id == 'ftp_port' || element.id == 'sftp_remote_port' || element.id == 'http_resp_time' || element.id == 'http_port' || element.id == 'smtp_server_port') {
            //k=1;
            i = 5;
            validatenumber(element);
        }
        if (k == 1) {
            if (element.value.replace(/^\s+|\s+$/g, "").length <= 9) {
                str = new String(element.value);
                element.value = str.substring(0, i);
                document.getElementById('tpResultMessage').innerHTML = "<font color=red>Phone Number must be 10 characters</font>";
                element.value = "";
                element.focus();
                return false;
            }
            if (element.value.replace(/^\s+|\s+$/g, "").length > i) {
                str = new String(element.value);
                element.value = str.substring(0, i);
                document.getElementById('tpResultMessage').innerHTML = "<font color=red>The Value must be less than " + i + " characters</font>";
                element.focus();
                return false;
            }
        } else {
            if (element.value.replace(/^\s+|\s+$/g, "").length > i) {
                str = new String(element.value);
                element.value = str.substring(0, i);
                document.getElementById('tpResultMessage').innerHTML = "<font color=red>The Value must be less than " + i + " characters</font>";
                element.focus();
                return false;
            }
        }
        return true;
    }
}

function validatenumber(xxxxx) {
    var maintainplus = '';
    var numval = xxxxx.value
    if (numval.charAt(0) == '+') {
        var maintainplus = '+';
    }
    curnumbervar = numval.replace(/[\\A-Za-z!"£$%^&*+_={};:'@#~,¦\/<>?|`¬\]\[]/g, '');
    xxxxx.value = maintainplus + curnumbervar;
    var maintainplus = '';
    xxxxx.focus;
}

//PHONE NUMBER FORMAT SCRIPT :START   
function formatPhone(num) {
    str = new String(document.userForm.ophno.value);
    var pattern = /^\(\d{3}\)\-\d{3}\-\d{4}$/;
    document.userForm.ophno.value = str.replace(pattern, "");
    //document.userForm.ophno.value=str.replace(/[A-Za-z\(\)\.\-\x\s,]/gpattern, "");
    num = document.userForm.ophno.value;
    var _return;
    if (num.length == 10) {
        //  _return="(";
        var ini = num.substring(0, 3);
        _return += ini;
        var st = num.substring(3, 6);
        _return += "-" + st + "-";
        var end = num.substring(6, 10);
        _return += end;

        document.userForm.ophno.value = "";
        document.userForm.ophno.value = _return;
    } else if (num.length > 10) {
        //_return="(";
        var ini = num.substring(0, 3);
        _return += ini;
        var st = num.substring(3, 6);
        _return += "-" + st + "-";
        var end = num.substring(6, 10);
        _return += end + "x";
        var ext = num.substring(10, num.length);
        _return += ext;
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Phone number should be 10 characters</font>";
        document.userForm.ophno.value = _return;
        document.userForm.ophno.value = "";
        document.userForm.ophno.focus();
        return false;
    } else if (num.length < 10) {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please give atleast 10 charcters in phone number</font>";
        document.userForm.ophno.value = "";
    }
    return _return;
}

//to convert given phone format to specified format((111)-111-1111) 
function generalFormatPhone(element) {
    str = new String(element.value);
    //var str = element.value;
    if (str == "undefined") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please give atleast 10 charcters in phone number</font>";
        return false;
    } else {
        // var pattern = /^\(\d{3}\)\-\d{3}\-\d{4}$/;
        element.value = str.replace(/^(?:\(\d{3}\)|\d{3}-)\d{3}-\d{4}$/, "");
        // element.value = str.replace(pattern, "");
        num = element.value;
        var _return = "";
        if (num.length == 10) {
            var ini = num.substring(0, 3);
            _return += ini;
            var st = num.substring(3, 6);
            _return += "-" + st + "-";
            var end = num.substring(6, 10);
            _return += end;
            element.value = "";
            element.value = _return;
        } else if (num.length > 10) {
            //_return="(";
            var ini = num.substring(0, 3);
            _return += ini;
            var st = num.substring(3, 6);
            _return += "-" + st + "-";
            var end = num.substring(6, 10);
            _return += end + "x";
            var ext = num.substring(10, num.length);
            _return += ext;
            element.value = "";
            element.value = _return;
        }
        return _return;
    }
}
;

function validateEmail(thisObj) {
    var x = thisObj.value;
    var atpos = x.indexOf("@");
    var dotpos = x.lastIndexOf(".");
    if (atpos < 1 || dotpos < atpos + 2 || dotpos + 2 >= x.length) {
        thisObj.value = "";
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Not a valid e-mail address</font>";
    }
}

function checkEnvelopes() {
    var ib850 = document.getElementById("ib850").checked;
    var ib855 = document.getElementById("ib855").checked;
    var ib856 = document.getElementById("ib856").checked;
    var ib810 = document.getElementById("ib810").checked;
    var ob850 = document.getElementById("ob850").checked;
    var ob855 = document.getElementById("ob855").checked;
    var ob856 = document.getElementById("ob856").checked;
    var ob810 = document.getElementById("ob810").checked;
    var x = true;
    if (ib850 == true) {
        x = checkib850();
        if (x == false)
            return false;
    }
    if (ib855 == true) {
        x = checkib855();
        if (x == false)
            return false;
    }
    if (ib856 == true) {
        x = checkib856();
        if (x == false)
            return false;
    }
    if (ib810 == true) {
        x = checkib810();
        if (x == false)
            return false;
    }
    if (ob850 == true) {
        x = checkob850();
        if (x == false)
            return false;
    }
    if (ob855 == true) {
        x = checkob855();
        if (x == false)
            return false;
    }
    if (ob856 == true) {
        x = checkob856();
        if (x == false)
            return false;
    }
    if (ob810 == true) {
        x = checkob810();
        if (x == false)
            return false;
    }
    return x;
}

function IBsender850() {
    /* 850 inbound gs and st value */
    var n1 = document.getElementById('gs850senderIdIB');
    var n2 = document.getElementById('st850senderIdIB');
    n2.value = n1.value;
}
function IBversion850() {
    var versiongs850Ib = document.getElementById('gs850VersionIB');
    var versionst850Ib = document.getElementById('st850VersionIB');
    versionst850Ib.value = versiongs850Ib.value;
}

function IBrecId850() {
    var recIdgs850Ib = document.getElementById('gs850RecIdIB');
    var recIdst850Ib = document.getElementById('st850RecIdIB');
    if (recIdgs850Ib.value == '-1') {
        recIdst850Ib.value = "";
    } else {
        recIdst850Ib.value = recIdgs850Ib.value;
    }
}
/* 850 inbound gs and st value end*/

/* 855 inbound gs and st value */
function IBsender855() {
    var senderIdgs855Ib = document.getElementById('gs855senderIdIB');
    var senderIdst855Ib = document.getElementById('st855senderIdIB');
    senderIdst855Ib.value = senderIdgs855Ib.value;
}
function IBversion855() {
    var versiongs855Ib = document.getElementById('gs855VersionIB');
    var versionst855Ib = document.getElementById('st855VersionIB');
    versionst855Ib.value = versiongs855Ib.value;
}

function IBrecId855() {
    var recIdgs855Ib = document.getElementById('gs855RecIdIB');
    var recIdst855Ib = document.getElementById('st855RecIdIB');
    if (recIdgs855Ib.value == '-1') {
        recIdst855Ib.value = "";
    } else {
        recIdst855Ib.value = recIdgs855Ib.value;
    }
}
/* 855 inbound gs and st value end*/

/* 856 inbound gs and st value */
function IBsender856() {
    var senderIdgs856Ib = document.getElementById('gs856senderIdIB');
    var senderIdst856Ib = document.getElementById('st856senderIdIB');
    senderIdst856Ib.value = senderIdgs856Ib.value;
}

function IBversion856() {
    var versiongs856Ib = document.getElementById('gs856VersionIB');
    var versionst856Ib = document.getElementById('st856VersionIB');
    versionst856Ib.value = versiongs856Ib.value;
}

function IBrecId856() {
    var recIdgs856Ib = document.getElementById('gs856RecIdIB');
    var recIdst856Ib = document.getElementById('st856RecIdIB');
    if (recIdgs856Ib.value == '-1') {
        recIdst856Ib.value = "";
    } else {
        recIdst856Ib.value = recIdgs856Ib.value;
    }
}
/* 856 inbound gs and st value end*/

/* 810 inbound gs and st value */
function IBsender810() {
    var senderIdgs810Ib = document.getElementById('gs810senderIdIB');
    var senderIdst810Ib = document.getElementById('st810senderIdIB');
    senderIdst810Ib.value = senderIdgs810Ib.value;
}
function IBversion810() {
    var versiongs810Ib = document.getElementById('gs810VersionIB');
    var versionst810Ib = document.getElementById('st810VersionIB');
    versionst810Ib.value = versiongs810Ib.value;
}

function IBrecId810() {
    var recIdgs810Ib = document.getElementById('gs810RecIdIB');
    var recIdst810Ib = document.getElementById('st810RecIdIB');
    if (recIdgs810Ib.value == '-1') {
        recIdst810Ib.value = "";
    } else {
        recIdst810Ib.value = recIdgs810Ib.value;
    }
}
/* 810 inbound gs and st value end*/

/*----------------- Outbound values copy  ------------------*/

/* 850 outbound gs and st value */
function OBsender850() {
    var senderIdgs850Ob = document.getElementById('gs850senderIdOB');
    var senderIdst850Ob = document.getElementById('st850senderIdOB');
    senderIdst850Ob.value = senderIdgs850Ob.value;
}

function OBversion850() {
    var versiongs850Ob = document.getElementById('gs850VersionOB');
    var versionst850Ob = document.getElementById('st850VersionOB');
    versionst850Ob.value = versiongs850Ob.value;
}
function OB850recId() {
    var recIdgs850Ob = document.getElementById('gs850RecIdOB');
    var recIdst850Ob = document.getElementById('st850RecIdOB');
    if (recIdgs850Ob.value == '-1') {
        recIdst850Ob.value = "";
    } else {
        recIdst850Ob.value = recIdgs850Ob.value;
    }
}

/* 850 outbound gs and st value end*/

/* 855 outbound gs and st value */
function OBsender855() {
    var senderIdgs855Ob = document.getElementById('gs855senderIdOB');
    var senderIdst855Ob = document.getElementById('st855senderIdOB');
    senderIdst855Ob.value = senderIdgs855Ob.value;
}

function OBversion855() {
    var versiongs855Ob = document.getElementById('gs855VersionOB');
    var versionst855Ob = document.getElementById('st855VersionOB');
    versionst855Ob.value = versiongs855Ob.value;
}

function OBrecId855() {
    var recIdgs855Ob = document.getElementById('gs855RecIdOB');
    var recIdst855Ob = document.getElementById('st855RecIdOB');
    if (recIdgs855Ob.value == '-1') {
        recIdst855Ob.value = "";
    } else {
        recIdst855Ob.value = recIdgs855Ob.value;
    }
}
/* 855 outbound gs and st value end*/

/* 856 outbound gs and st value */
function OBsender856() {
    var senderIdgs856Ob = document.getElementById('gs856senderIdOB');
    var senderIdst856Ob = document.getElementById('st856senderIdOB');
    senderIdst856Ob.value = senderIdgs856Ob.value;
}

function OBversion856() {
    var versiongs856Ob = document.getElementById('gs856VersionOB');
    var versionst856Ob = document.getElementById('st856VersionOB');
    versionst856Ob.value = versiongs856Ob.value;
}

function OBrecId856() {
    var recIdgs856Ob = document.getElementById('gs856RecIdOB');
    var recIdst856Ob = document.getElementById('st856RecIdOB');
    if (recIdgs856Ob.value == '-1') {
        recIdst856Ob.value = "";
    } else {
        recIdst856Ob.value = recIdgs856Ob.value;
    }
}
/* 856 outbound gs and st value end*/

/* 810 outbound gs and st value */
function OBsender810() {
    var senderIdgs810Ob = document.getElementById('gs810senderIdOB');
    var senderIdst810Ob = document.getElementById('st810senderIdOB');
    senderIdst810Ob.value = senderIdgs810Ob.value;
}

function OBversion810() {
    var versiongs810Ob = document.getElementById('gs810VersionOB');
    var versionst810Ob = document.getElementById('st810VersionOB');
    versionst810Ob.value = versiongs810Ob.value;
}

function OBrecId810() {
    var recIdgs810Ob = document.getElementById('gs810RecIdOB');
    var recIdst810Ob = document.getElementById('st810RecIdOB');
    if (recIdgs810Ob.value == '-1') {
        recIdst810Ob.value = "";
    } else {
        recIdst810Ob.value = recIdgs810Ob.value;
    }
}
/* 810 outbound gs and st value end*/

/* edit transaction gs and st value start*/
function senderCopy() {
    var senderIdgs = document.getElementById('gsSenderId');
    var senderIdst = document.getElementById('stSenderId');
    senderIdst.value = senderIdgs.value;
}

function receiverCopy() {
    var recIdgs = document.getElementById('gsReceiverId');
    var recIdst = document.getElementById('stReceiverId');
    if (recIdgs.value == '-1') {
        recIdst.value = "";
    } else {
        recIdst.value = recIdgs.value;
    }
}

function versionCopy() {
    var versiongs = document.getElementById('gsVersion');
    var versionst = document.getElementById('stVersion');
    versionst.value = versiongs.value;
}
/* edit transaction gs and st value end*/

function checkib850() {
    var ibtransaction = document.getElementById("ibvalue850").value;
    var ibdirection = document.getElementById("ibdirection").value;
    var isa850senderIdIB = document.getElementById("isa850senderIdIB").value;
    var gs850senderIdIB = document.getElementById("gs850senderIdIB").value;
    var st850senderIdIB = document.getElementById("st850senderIdIB").value;
    var isa850RecIdIB = document.getElementById("isa850RecIdIB").value;
    var gs850RecIdIB = document.getElementById("gs850RecIdIB").value;
    var st850RecIdIB = document.getElementById("st850RecIdIB").value;
    var isa850VersionIB = document.getElementById("isa850VersionIB").value;
    var gs850VersionIB = document.getElementById("gs850VersionIB").value;
    var st850VersionIB = document.getElementById("st850VersionIB").value;
    var fun850GroupIdIB = document.getElementById("fun850GroupIdIB").value;
    var res850AgecodeIB = document.getElementById("res850AgecodeIB").value;
    var gen850AckIB = document.getElementById("gen850AckIB").checked;
    var trans850IdcodeIB = document.getElementById("trans850IdcodeIB").value;

    if (isa850senderIdIB.length == 0 || isa850senderIdIB == "" || isa850senderIdIB == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter senderId ISA.</font>";
        return false;
    } else if (gs850senderIdIB.length == 0 || gs850senderIdIB == "" || gs850senderIdIB == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter senderId GS.</font>";
        return false;
    } else if (st850senderIdIB.length == 0 || st850senderIdIB == "" || st850senderIdIB == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter senderId ST.</font>";
        return false;
    } else if (isa850RecIdIB.length == 0 || isa850RecIdIB == "-1" || isa850RecIdIB == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter RecieverId ISA.</font>";
        return false;
    } else if (gs850RecIdIB.length == 0 || gs850RecIdIB == "-1" || gs850RecIdIB == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter RecieverId GS.</font>";
        return false;
    } else if (st850RecIdIB.length == 0 || st850RecIdIB == "" || st850RecIdIB == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter RecieverId ST.</font>";
        return false;
    } else if (isa850VersionIB.length == 0 || isa850VersionIB == "" || isa850VersionIB == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter Version ISA.</font>";
        return false;
    } else if (gs850VersionIB.length == 0 || gs850VersionIB == "" || gs850VersionIB == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter Version GS.</font>";
        return false;
    } else if (st850VersionIB.length == 0 || st850VersionIB == "" || st850VersionIB == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter Version ST.</font>";
        return false;
    } else if (fun850GroupIdIB.length == 0 || fun850GroupIdIB == "" || fun850GroupIdIB == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter Functional ID Code.</font>";
        return false;
    } else if (res850AgecodeIB.length == 0 || res850AgecodeIB == "" || res850AgecodeIB == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please enter Responsible Agency Code.</font>";
        return false;
    }

    if (isa850senderIdIB != null || isa850senderIdIB != "") {
        if (gs850senderIdIB != null && gs850senderIdIB != "") {
            if (st850senderIdIB != null && st850senderIdIB != "") {
                if (isa850RecIdIB != null && isa850RecIdIB != "-1") {
                    if (gs850RecIdIB != null && gs850RecIdIB != "-1") {
                        if (st850RecIdIB != null && st850RecIdIB != "") {
                            if (isa850VersionIB != null && isa850VersionIB != "") {
                                if (gs850VersionIB != null && gs850VersionIB != "") {
                                    if (st850VersionIB != null && st850VersionIB != "") {
                                        if (fun850GroupIdIB != null && fun850GroupIdIB != "") {
                                            if (res850AgecodeIB != null && res850AgecodeIB != "") {
                                                ib850 = ibtransaction + "@" + ibdirection + "@" + isa850senderIdIB + "@" + gs850senderIdIB + "@" + st850senderIdIB + "@" + isa850RecIdIB + "@" + gs850RecIdIB + "@" + st850RecIdIB + "@" + isa850VersionIB + "@" + gs850VersionIB + "@" + st850VersionIB + "@" + fun850GroupIdIB + "@" + res850AgecodeIB + "@" + gen850AckIB + "@" + trans850IdcodeIB;
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
    document.getElementById("IB850Transaction").value = ib850;
    //alert("850 transaction "+document.getElementById("IB850Transaction").value);
}

function checkib855() {
    var ibtransaction = document.getElementById("ibvalue855").value;
    var ibdirection = document.getElementById("ibdirection855").value;
    var isa855senderIdIB = document.getElementById("isa855senderIdIB").value;
    var gs855senderIdIB = document.getElementById("gs855senderIdIB").value;
    var st855senderIdIB = document.getElementById("st855senderIdIB").value;
    var isa855RecIdIB = document.getElementById("isa855RecIdIB").value;
    var gs855RecIdIB = document.getElementById("gs855RecIdIB").value;
    var st855RecIdIB = document.getElementById("st855RecIdIB").value;
    var isa855VersionIB = document.getElementById("isa855VersionIB").value;
    var gs855VersionIB = document.getElementById("gs855VersionIB").value;
    var st855VersionIB = document.getElementById("st855VersionIB").value;
    var fun855GroupIdIB = document.getElementById("fun855GroupIdIB").value;
    var res855AgecodeIB = document.getElementById("res855AgecodeIB").value;
    var gen855AckIB = document.getElementById("gen855AckIB").checked;
    var trans855IdcodeIB = document.getElementById("trans855IdcodeIB").value;

    if (isa855senderIdIB.length == 0 || isa855senderIdIB == " " || isa855senderIdIB == null) {
        document.getElementById('resultMessage855ib').innerHTML = "<font color=red>Please enter senderId ISA.</font>";
        return false;
    } else if (gs855senderIdIB.length == 0 || gs855senderIdIB == "" || gs855senderIdIB == null) {
        document.getElementById('resultMessage855ib').innerHTML = "<font color=red>Please enter senderId GS.</font>";
        return false;
    } else if (st855senderIdIB.length == 0 || st855senderIdIB == "" || st855senderIdIB == null) {
        document.getElementById('resultMessage855ib').innerHTML = "<font color=red>Please enter senderId ST.</font>";
        return false;
    } else if (isa855RecIdIB.length == 0 || isa855RecIdIB == "-1" || isa855RecIdIB == null) {
        document.getElementById('resultMessage855ib').innerHTML = "<font color=red>Please enter RecieverId ISA.</font>";
        return false;
    } else if (gs855RecIdIB.length == 0 || gs855RecIdIB == "-1" || gs855RecIdIB == null) {
        document.getElementById('resultMessage855ib').innerHTML = "<font color=red>Please enter RecieverId GS.</font>";
        return false;
    } else if (st855RecIdIB.length == 0 || st855RecIdIB == "" || st855RecIdIB == null) {
        document.getElementById('resultMessage855ib').innerHTML = "<font color=red>Please enter RecieverId ST.</font>";
        return false;
    } else if (isa855VersionIB.length == 0 || isa855VersionIB == "" || isa855VersionIB == null) {
        document.getElementById('resultMessage855ib').innerHTML = "<font color=red>Please enter Version ISA.</font>";
        return false;
    } else if (gs855VersionIB.length == 0 || gs855VersionIB == "" || gs855VersionIB == null) {
        document.getElementById('resultMessage855ib').innerHTML = "<font color=red>Please enter Version GS.</font>";
        return false;
    } else if (st855VersionIB.length == 0 || st855VersionIB == "" || st855VersionIB == null) {
        document.getElementById('resultMessage855ib').innerHTML = "<font color=red>Please enter Version ST.</font>";
        return false;
    } else if (fun855GroupIdIB.length == 0 || fun855GroupIdIB == "" || fun855GroupIdIB == null) {
        document.getElementById('resultMessage855ib').innerHTML = "<font color=red>Please enter Functional ID Code.</font>";
        return false;
    } else if (res855AgecodeIB.length == 0 || res855AgecodeIB == "" || res855AgecodeIB == null) {
        document.getElementById('resultMessage855ib').innerHTML = "<font color=red>Please enter Responsible Agency Code.</font>";
        return false;
    }

    if (isa855senderIdIB != null || isa855senderIdIB != "") {
        if (gs855senderIdIB != null && gs855senderIdIB != "") {
            if (st855senderIdIB != null && st855senderIdIB != "") {
                if (isa855RecIdIB != null && isa855RecIdIB != "-1") {
                    if (gs855RecIdIB != null && gs855RecIdIB != "-1") {
                        if (st855RecIdIB != null && st855RecIdIB != "") {
                            if (isa855VersionIB != null && isa855VersionIB != "") {
                                if (gs855VersionIB != null && gs855VersionIB != "") {
                                    if (st855VersionIB != null && st855VersionIB != "") {
                                        if (fun855GroupIdIB != null && fun855GroupIdIB != "") {
                                            if (res855AgecodeIB != null && res855AgecodeIB != "") {
                                                ib855 = ibtransaction + "@" + ibdirection + "@" + isa855senderIdIB + "@" + gs855senderIdIB + "@" + st855senderIdIB + "@" + isa855RecIdIB + "@" + gs855RecIdIB + "@" + st855RecIdIB + "@" + isa855VersionIB + "@" + gs855VersionIB + "@" + st855VersionIB + "@" + fun855GroupIdIB + "@" + res855AgecodeIB + "@" + gen855AckIB + "@" + trans855IdcodeIB;
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
    document.getElementById("IB855Transaction").value = ib855;
    //alert("ib 855 transaction "+document.getElementById("IB855Transaction").value);       
}

function checkib856() {
    var ibtransaction = document.getElementById("ibvalue856").value;
    var ibdirection = document.getElementById("ibdirection856").value;
    var isa856senderIdIB = document.getElementById("isa856senderIdIB").value;
    var gs856senderIdIB = document.getElementById("gs856senderIdIB").value;
    var st856senderIdIB = document.getElementById("st856senderIdIB").value;
    var isa856RecIdIB = document.getElementById("isa856RecIdIB").value;
    var gs856RecIdIB = document.getElementById("gs856RecIdIB").value;
    var st856RecIdIB = document.getElementById("st856RecIdIB").value;
    var isa856VersionIB = document.getElementById("isa856VersionIB").value;
    var gs856VersionIB = document.getElementById("gs856VersionIB").value;
    var st856VersionIB = document.getElementById("st856VersionIB").value;
    var fun856GroupIdIB = document.getElementById("fun856GroupIdIB").value;
    var res856AgecodeIB = document.getElementById("res856AgecodeIB").value;
    var gen856AckIB = document.getElementById("gen856AckIB").checked;
    var trans856IdcodeIB = document.getElementById("trans856IdcodeIB").value;

    if (isa856senderIdIB.length == 0 || isa856senderIdIB == "" || isa856senderIdIB == null) {
        document.getElementById('resultMessage856ib').innerHTML = "<font color=red>Please enter senderId ISA.</font>";
        return false;
    } else if (gs856senderIdIB.length == 0 || gs856senderIdIB == "" || gs856senderIdIB == null) {
        document.getElementById('resultMessage856ib').innerHTML = "<font color=red>Please enter senderId GS.</font>";
        return false;
    } else if (st856senderIdIB.length == 0 || st856senderIdIB == "" || st856senderIdIB == null) {
        document.getElementById('resultMessage856ib').innerHTML = "<font color=red>Please enter senderId ST.</font>";
        return false;
    } else if (isa856RecIdIB.length == 0 || isa856RecIdIB == "-1" || isa856RecIdIB == null) {
        document.getElementById('resultMessage856ib').innerHTML = "<font color=red>Please enter RecieverId ISA.</font>";
        return false;
    } else if (gs856RecIdIB.length == 0 || gs856RecIdIB == "-1" || gs856RecIdIB == null) {
        document.getElementById('resultMessage856ib').innerHTML = "<font color=red>Please enter RecieverId GS.</font>";
        return false;
    } else if (st856RecIdIB.length == 0 || st856RecIdIB == "" || st856RecIdIB == null) {
        document.getElementById('resultMessage856ib').innerHTML = "<font color=red>Please enter RecieverId ST.</font>";
        return false;
    } else if (isa856VersionIB.length == 0 || isa856VersionIB == "" || isa856VersionIB == null) {
        document.getElementById('resultMessage856ib').innerHTML = "<font color=red>Please enter Version ISA.</font>";
        return false;
    } else if (gs856VersionIB.length == 0 || gs856VersionIB == "" || gs856VersionIB == null) {
        document.getElementById('resultMessage856ib').innerHTML = "<font color=red>Please enter Version GS.</font>";
        return false;
    } else if (st856VersionIB.length == 0 || st856VersionIB == "" || st856VersionIB == null) {
        document.getElementById('resultMessage856ib').innerHTML = "<font color=red>Please enter Version ST.</font>";
        return false;
    } else if (fun856GroupIdIB.length == 0 || fun856GroupIdIB == "" || fun856GroupIdIB == null) {
        document.getElementById('resultMessage856ib').innerHTML = "<font color=red>Please enter Functional ID Code.</font>";
        return false;
    } else if (res856AgecodeIB.length == 0 || res856AgecodeIB == "" || res856AgecodeIB == null) {
        document.getElementById('resultMessage856ib').innerHTML = "<font color=red>Please enter Responsible Agency Code.</font>";
        return false;
    }

    if (isa856senderIdIB != null || isa856senderIdIB != "") {
        if (gs856senderIdIB != null && gs856senderIdIB != "") {
            if (st856senderIdIB != null && st856senderIdIB != "") {
                if (isa856RecIdIB != null && isa856RecIdIB != "-1") {
                    if (gs856RecIdIB != null && gs856RecIdIB != "-1") {
                        if (st856RecIdIB != null && st856RecIdIB != "") {
                            if (isa856VersionIB != null && isa856VersionIB != "") {
                                if (gs856VersionIB != null && gs856VersionIB != "") {
                                    if (st856VersionIB != null && st856VersionIB != "") {
                                        if (fun856GroupIdIB != null && fun856GroupIdIB != "") {
                                            if (res856AgecodeIB != null && res856AgecodeIB != "") {
                                                ib856 = ibtransaction + "@" + ibdirection + "@" + isa856senderIdIB + "@" + gs856senderIdIB + "@" + st856senderIdIB + "@" + isa856RecIdIB + "@" + gs856RecIdIB + "@" + st856RecIdIB + "@" + isa856VersionIB + "@" + gs856VersionIB + "@" + st856VersionIB + "@" + fun856GroupIdIB + "@" + res856AgecodeIB + "@" + gen856AckIB + "@" + trans856IdcodeIB;
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
    document.getElementById("IB856Transaction").value = ib856; //alert("ib 856 transaction "+document.getElementById("IB856Transaction").value);     
}

function checkib810() {
    var ibtransaction = document.getElementById("ibvalue810").value;
    var ibdirection = document.getElementById("ibdirection810").value;
    var isa810senderIdIB = document.getElementById("isa810senderIdIB").value;
    var gs810senderIdIB = document.getElementById("gs810senderIdIB").value;
    var st810senderIdIB = document.getElementById("st810senderIdIB").value;
    var isa810RecIdIB = document.getElementById("isa810RecIdIB").value;
    var gs810RecIdIB = document.getElementById("gs810RecIdIB").value;
    var st810RecIdIB = document.getElementById("st810RecIdIB").value;
    var isa810VersionIB = document.getElementById("isa810VersionIB").value;
    var gs810VersionIB = document.getElementById("gs810VersionIB").value;
    var st810VersionIB = document.getElementById("st810VersionIB").value;
    var fun810GroupIdIB = document.getElementById("fun810GroupIdIB").value;
    var res810AgecodeIB = document.getElementById("res810AgecodeIB").value;
    var gen810AckIB = document.getElementById("gen810AckIB").checked;
    var trans810IdcodeIB = document.getElementById("trans810IdcodeIB").value;

    if (isa810senderIdIB.length == 0 || isa810senderIdIB == "" || isa810senderIdIB == null) {
        document.getElementById('resultMessage810ib').innerHTML = "<font color=red>Please enter senderId ISA.</font>";
        return false;
    } else if (gs810senderIdIB.length == 0 || gs810senderIdIB == "" || gs810senderIdIB == null) {
        document.getElementById('resultMessage810ib').innerHTML = "<font color=red>Please enter senderId GS.</font>";
        return false;
    } else if (st810senderIdIB.length == 0 || st810senderIdIB == "" || st810senderIdIB == null) {
        document.getElementById('resultMessage810ib').innerHTML = "<font color=red>Please enter senderId ST.</font>";
        return false;
    } else if (isa810RecIdIB.length == 0 || isa810RecIdIB == "-1" || isa810RecIdIB == null) {
        document.getElementById('resultMessage810ib').innerHTML = "<font color=red>Please enter RecieverId ISA.</font>";
        return false;
    } else if (gs810RecIdIB.length == 0 || gs810RecIdIB == "-1" || gs810RecIdIB == null) {
        document.getElementById('resultMessage810ib').innerHTML = "<font color=red>Please enter RecieverId GS.</font>";
        return false;
    } else if (st810RecIdIB.length == 0 || st810RecIdIB == "" || st810RecIdIB == null) {
        document.getElementById('resultMessage810ib').innerHTML = "<font color=red>Please enter RecieverId ST.</font>";
        return false;
    } else if (isa810VersionIB.length == 0 || isa810VersionIB == "" || isa810VersionIB == null) {
        document.getElementById('resultMessage810ib').innerHTML = "<font color=red>Please enter Version ISA.</font>";
        return false;
    } else if (gs810VersionIB.length == 0 || gs810VersionIB == "" || gs810VersionIB == null) {
        document.getElementById('resultMessage810ib').innerHTML = "<font color=red>Please enter Version GS.</font>";
        return false;
    } else if (st810VersionIB.length == 0 || st810VersionIB == "" || st810VersionIB == null) {
        document.getElementById('resultMessage810ib').innerHTML = "<font color=red>Please enter Version ST.</font>";
        return false;
    } else if (fun810GroupIdIB.length == 0 || fun810GroupIdIB == "" || fun810GroupIdIB == null) {
        document.getElementById('resultMessage810ib').innerHTML = "<font color=red>Please enter Functional ID Code.</font>";
        return false;
    } else if (res810AgecodeIB.length == 0 || res810AgecodeIB == "" || res810AgecodeIB == null) {
        document.getElementById('resultMessage810ib').innerHTML = "<font color=red>Please enter Responsible Agency Code.</font>";
        return false;
    }

    if (isa810senderIdIB != null || isa810senderIdIB != "") {
        if (gs810senderIdIB != null && gs810senderIdIB != "") {
            if (st810senderIdIB != null && st810senderIdIB != "") {
                if (isa810RecIdIB != null && isa810RecIdIB != "-1") {
                    if (gs810RecIdIB != null && gs810RecIdIB != "-1") {
                        if (st810RecIdIB != null && st810RecIdIB != "") {
                            if (isa810VersionIB != null && isa810VersionIB != "") {
                                if (gs810VersionIB != null && gs810VersionIB != "") {
                                    if (st810VersionIB != null && st810VersionIB != "") {
                                        if (fun810GroupIdIB != null && fun810GroupIdIB != "") {
                                            if (res810AgecodeIB != null && res810AgecodeIB != "") {
                                                ib810 = ibtransaction + "@" + ibdirection + "@" + isa810senderIdIB + "@" + gs810senderIdIB + "@" + st810senderIdIB + "@" + isa810RecIdIB + "@" + gs810RecIdIB + "@" + st810RecIdIB + "@" + isa810VersionIB + "@" + gs810VersionIB + "@" + st810VersionIB + "@" + fun810GroupIdIB + "@" + res810AgecodeIB + "@" + gen810AckIB + "@" + trans810IdcodeIB;
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
    document.getElementById("IB810Transaction").value = ib810; //alert("ib 810 transaction "+document.getElementById("IB810Transaction").value);  
}

function checkob850() {
    var obtransaction = document.getElementById("obvalue850").value;
    var obdirection = document.getElementById("obdirection").value;
    var isa850senderIdOB = document.getElementById("isa850senderIdOB").value;
    var gs850senderIdOB = document.getElementById("gs850senderIdOB").value;
    var st850senderIdOB = document.getElementById("st850senderIdOB").value;
    var isa850RecIdOB = document.getElementById("isa850RecIdOB").value;
    var gs850RecIdOB = document.getElementById("gs850RecIdOB").value;
    var st850RecIdOB = document.getElementById("st850RecIdOB").value;
    var isa850VersionOB = document.getElementById("isa850VersionOB").value;
    var gs850VersionOB = document.getElementById("gs850VersionOB").value;
    var st850VersionOB = document.getElementById("st850VersionOB").value;
    var fun850GroupIdOB = document.getElementById("fun850GroupIdOB").value;
    var res850AgecodeOB = document.getElementById("res850AgecodeOB").value;
    var gen850AckOB = document.getElementById("gen850AckOB").checked;
    var trans850IdcodeOB = document.getElementById("trans850IdcodeOB").value;

    if (isa850senderIdOB.length == 0 || isa850senderIdOB == "" || isa850senderIdOB == null) {
        document.getElementById('resultMessage850ob').innerHTML = "<font color=red>Please enter senderId ISA.</font>";
        return false;
    } else if (gs850senderIdOB.length == 0 || gs850senderIdOB == "" || gs850senderIdOB == null) {
        document.getElementById('resultMessage850ob').innerHTML = "<font color=red>Please enter senderId GS.</font>";
        return false;
    } else if (st850senderIdOB.length == 0 || st850senderIdOB == "" || st850senderIdOB == null) {
        document.getElementById('resultMessage850ob').innerHTML = "<font color=red>Please enter senderId ST.</font>";
        return false;
    } else if (isa850RecIdOB.length == 0 || isa850RecIdOB == "-1" || isa850RecIdOB == null) {
        document.getElementById('resultMessage850ob').innerHTML = "<font color=red>Please enter RecieverId ISA.</font>";
        return false;
    } else if (gs850RecIdOB.length == 0 || gs850RecIdOB == "-1" || gs850RecIdOB == null) {
        document.getElementById('resultMessage850ob').innerHTML = "<font color=red>Please enter RecieverId GS.</font>";
        return false;
    } else if (st850RecIdOB.length == 0 || st850RecIdOB == "" || st850RecIdOB == null) {
        document.getElementById('resultMessage850ob').innerHTML = "<font color=red>Please enter RecieverId ST.</font>";
        return false;
    } else if (isa850VersionOB.length == 0 || isa850VersionOB == "" || isa850VersionOB == null) {
        document.getElementById('resultMessage850ob').innerHTML = "<font color=red>Please enter Version ISA.</font>";
        return false;
    } else if (gs850VersionOB.length == 0 || gs850VersionOB == "" || gs850VersionOB == null) {
        document.getElementById('resultMessage850ob').innerHTML = "<font color=red>Please enter Version GS.</font>";
        return false;
    } else if (st850VersionOB.length == 0 || st850VersionOB == "" || st850VersionOB == null) {
        document.getElementById('resultMessage850ob').innerHTML = "<font color=red>Please enter Version ST.</font>";
        return false;
    } else if (fun850GroupIdOB.length == 0 || fun850GroupIdOB == "" || fun850GroupIdOB == null) {
        document.getElementById('resultMessage850ob').innerHTML = "<font color=red>Please enter Functional ID Code.</font>";
        return false;
    } else if (res850AgecodeOB.length == 0 || res850AgecodeOB == "" || res850AgecodeOB == null) {
        document.getElementById('resultMessage850ob').innerHTML = "<font color=red>Please enter Responsible Agency Code.</font>";
        return false;
    }

    if (isa850senderIdOB != null || isa850senderIdOB != "") {
        if (gs850senderIdOB != null && gs850senderIdOB != "") {
            if (st850senderIdOB != null && st850senderIdOB != "") {
                if (isa850RecIdOB != null && isa850RecIdOB != "-1") {
                    if (gs850RecIdOB != null && gs850RecIdOB != "-1") {
                        if (st850RecIdOB != null && st850RecIdOB != "") {
                            if (isa850VersionOB != null && isa850VersionOB != "") {
                                if (gs850VersionOB != null && gs850VersionOB != "") {
                                    if (st850VersionOB != null && st850VersionOB != "") {
                                        if (fun850GroupIdOB != null && fun850GroupIdOB != "") {
                                            if (res850AgecodeOB != null && res850AgecodeOB != "") {
                                                ob850 = obtransaction + "@" + obdirection + "@" + isa850senderIdOB + "@" + gs850senderIdOB + "@" + st850senderIdOB + "@" + isa850RecIdOB + "@" + gs850RecIdOB + "@" + st850RecIdOB + "@" + isa850VersionOB + "@" + gs850VersionOB + "@" + st850VersionOB + "@" + fun850GroupIdOB + "@" + res850AgecodeOB + "@" + gen850AckOB + "@" + trans850IdcodeOB;
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
    document.getElementById("OB850Transaction").value = ob850;
    //alert("ob 850 transaction "+document.getElementById("OB850Transaction").value);  
}

function checkob855() {
    var obtransaction = document.getElementById("obvalue855").value;
    var obdirection = document.getElementById("obdirection855").value;
    var isa855senderIdOB = document.getElementById("isa855senderIdOB").value;
    var gs855senderIdOB = document.getElementById("gs855senderIdOB").value;
    var st855senderIdOB = document.getElementById("st855senderIdOB").value;
    var isa855RecIdOB = document.getElementById("isa855RecIdOB").value;
    var gs855RecIdOB = document.getElementById("gs855RecIdOB").value;
    var st855RecIdOB = document.getElementById("st855RecIdOB").value;
    var isa855VersionOB = document.getElementById("isa855VersionOB").value;
    var gs855VersionOB = document.getElementById("gs855VersionOB").value;
    var st855VersionOB = document.getElementById("st855VersionOB").value;
    var fun855GroupIdOB = document.getElementById("fun855GroupIdOB").value;
    var res855AgecodeOB = document.getElementById("res855AgecodeOB").value;
    var gen855AckOB = document.getElementById("gen855AckOB").checked;
    var trans855IdcodeOB = document.getElementById("trans855IdcodeOB").value;

    if (isa855senderIdOB.length == 0 || isa855senderIdOB == "" || isa855senderIdOB == null) {
        document.getElementById('resultMessage855ob').innerHTML = "<font color=red>Please enter senderId ISA.</font>";
        return false;
    } else if (gs855senderIdOB.length == 0 || gs855senderIdOB == "" || gs855senderIdOB == null) {
        document.getElementById('resultMessage855ob').innerHTML = "<font color=red>Please enter senderId GS.</font>";
        return false;
    } else if (st855senderIdOB.length == 0 || st855senderIdOB == "" || st855senderIdOB == null) {
        document.getElementById('resultMessage855ob').innerHTML = "<font color=red>Please enter senderId ST.</font>";
        return false;
    } else if (isa855RecIdOB.length == 0 || isa855RecIdOB == "-1" || isa855RecIdOB == null) {
        document.getElementById('resultMessage855ob').innerHTML = "<font color=red>Please enter RecieverId ISA.</font>";
        return false;
    } else if (gs855RecIdOB.length == 0 || gs855RecIdOB == "-1" || gs855RecIdOB == null) {
        document.getElementById('resultMessage855ob').innerHTML = "<font color=red>Please enter RecieverId GS.</font>";
        return false;
    } else if (st855RecIdOB.length == 0 || st855RecIdOB == "" || st855RecIdOB == null) {
        document.getElementById('resultMessage855ob').innerHTML = "<font color=red>Please enter RecieverId ST.</font>";
        return false;
    } else if (isa855VersionOB.length == 0 || isa855VersionOB == "" || isa855VersionOB == null) {
        document.getElementById('resultMessage855ob').innerHTML = "<font color=red>Please enter Version ISA.</font>";
        return false;
    } else if (gs855VersionOB.length == 0 || gs855VersionOB == "" || gs855VersionOB == null) {
        document.getElementById('resultMessage855ob').innerHTML = "<font color=red>Please enter Version GS.</font>";
        return false;
    } else if (st855VersionOB.length == 0 || st855VersionOB == "" || st855VersionOB == null) {
        document.getElementById('resultMessage855ob').innerHTML = "<font color=red>Please enter Version ST.</font>";
        return false;
    } else if (fun855GroupIdOB.length == 0 || fun855GroupIdOB == "" || fun855GroupIdOB == null) {
        document.getElementById('resultMessage855ob').innerHTML = "<font color=red>Please enter Functional ID Code.</font>";
        return false;
    } else if (res855AgecodeOB.length == 0 || res855AgecodeOB == "" || res855AgecodeOB == null) {
        document.getElementById('resultMessage855ob').innerHTML = "<font color=red>Please enter Responsible Agency Code.</font>";
        return false;
    }

    if (isa855senderIdOB != null || isa855senderIdOB != "") {
        if (gs855senderIdOB != null && gs855senderIdOB != "") {
            if (st855senderIdOB != null && st855senderIdOB != "") {
                if (isa855RecIdOB != null && isa855RecIdOB != "-1") {
                    if (gs855RecIdOB != null && gs855RecIdOB != "-1") {
                        if (st855RecIdOB != null && st855RecIdOB != "") {
                            if (isa855VersionOB != null && isa855VersionOB != "") {
                                if (gs855VersionOB != null && gs855VersionOB != "") {
                                    if (st855VersionOB != null && st855VersionOB != "") {
                                        if (fun855GroupIdOB != null && fun855GroupIdOB != "") {
                                            if (res855AgecodeOB != null && res855AgecodeOB != "") {
                                                ob855 = obtransaction + "@" + obdirection + "@" + isa855senderIdOB + "@" + gs855senderIdOB + "@" + st855senderIdOB + "@" + isa855RecIdOB + "@" + gs855RecIdOB + "@" + st855RecIdOB + "@" + isa855VersionOB + "@" + gs855VersionOB + "@" + st855VersionOB + "@" + fun855GroupIdOB + "@" + res855AgecodeOB + "@" + gen855AckOB + "@" + trans855IdcodeOB;
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
    document.getElementById("OB855Transaction").value = ob855;
    //alert("ob 855 transaction "+document.getElementById("OB855Transaction").value);  
}

function checkob856() {
    var obtransaction = document.getElementById("obvalue856").value;
    var obdirection = document.getElementById("obdirection856").value;
    var isa856senderIdOB = document.getElementById("isa856senderIdOB").value;
    var gs856senderIdOB = document.getElementById("gs856senderIdOB").value;
    var st856senderIdOB = document.getElementById("st856senderIdOB").value;
    var isa856RecIdOB = document.getElementById("isa856RecIdOB").value;
    var gs856RecIdOB = document.getElementById("gs856RecIdOB").value;
    var st856RecIdOB = document.getElementById("st856RecIdOB").value;
    var isa856VersionOB = document.getElementById("isa856VersionOB").value;
    var gs856VersionOB = document.getElementById("gs856VersionOB").value;
    var st856VersionOB = document.getElementById("st856VersionOB").value;
    var fun856GroupIdOB = document.getElementById("fun856GroupIdOB").value;
    var res856AgecodeOB = document.getElementById("res856AgecodeOB").value;
    var gen856AckOB = document.getElementById("gen856AckOB").checked;
    var trans856IdcodeOB = document.getElementById("trans856IdcodeOB").value;

    if (isa856senderIdOB.length == 0 || isa856senderIdOB == "" || isa856senderIdOB == null) {
        document.getElementById('resultMessage856ob').innerHTML = "<font color=red>Please enter senderId ISA.</font>";
        return false;
    } else if (gs856senderIdOB.length == 0 || gs856senderIdOB == "" || gs856senderIdOB == null) {
        document.getElementById('resultMessage856ob').innerHTML = "<font color=red>Please enter senderId GS.</font>";
        return false;
    } else if (st856senderIdOB.length == 0 || st856senderIdOB == "" || st856senderIdOB == null) {
        document.getElementById('resultMessage856ob').innerHTML = "<font color=red>Please enter senderId ST.</font>";
        return false;
    } else if (isa856RecIdOB.length == 0 || isa856RecIdOB == "-1" || isa856RecIdOB == null) {
        document.getElementById('resultMessage856ob').innerHTML = "<font color=red>Please enter RecieverId ISA.</font>";
        return false;
    } else if (gs856RecIdOB.length == 0 || gs856RecIdOB == "-1" || gs856RecIdOB == null) {
        document.getElementById('resultMessage856ob').innerHTML = "<font color=red>Please enter RecieverId GS.</font>";
        return false;
    } else if (st856RecIdOB.length == 0 || st856RecIdOB == "" || st856RecIdOB == null) {
        document.getElementById('resultMessage856ob').innerHTML = "<font color=red>Please enter RecieverId ST.</font>";
        return false;
    } else if (isa856VersionOB.length == 0 || isa856VersionOB == "" || isa856VersionOB == null) {
        document.getElementById('resultMessage856ob').innerHTML = "<font color=red>Please enter Version ISA.</font>";
        return false;
    } else if (gs856VersionOB.length == 0 || gs856VersionOB == "" || gs856VersionOB == null) {
        document.getElementById('resultMessage856ob').innerHTML = "<font color=red>Please enter Version GS.</font>";
        return false;
    } else if (st856VersionOB.length == 0 || st856VersionOB == "" || st856VersionOB == null) {
        document.getElementById('resultMessage856ob').innerHTML = "<font color=red>Please enter Version ST.</font>";
        return false;
    } else if (fun856GroupIdOB.length == 0 || fun856GroupIdOB == "" || fun856GroupIdOB == null) {
        document.getElementById('resultMessage856ob').innerHTML = "<font color=red>Please enter Functional ID Code.</font>";
        return false;
    } else if (res856AgecodeOB.length == 0 || res856AgecodeOB == "" || res856AgecodeOB == null) {
        document.getElementById('resultMessage856ob').innerHTML = "<font color=red>Please enter Responsible Agency Code.</font>";
        return false;
    }

    if (isa856senderIdOB != null || isa856senderIdOB != "") {
        if (gs856senderIdOB != null && gs856senderIdOB != "") {
            if (st856senderIdOB != null && st856senderIdOB != "") {
                if (isa856RecIdOB != null && isa856RecIdOB != "-1") {
                    if (gs856RecIdOB != null && gs856RecIdOB != "-1") {
                        if (st856RecIdOB != null && st856RecIdOB != "") {
                            if (isa856VersionOB != null && isa856VersionOB != "") {
                                if (gs856VersionOB != null && gs856VersionOB != "") {
                                    if (st856VersionOB != null && st856VersionOB != "") {
                                        if (fun856GroupIdOB != null && fun856GroupIdOB != "") {
                                            if (res856AgecodeOB != null && res856AgecodeOB != "") {
                                                ob856 = obtransaction + "@" + obdirection + "@" + isa856senderIdOB + "@" + gs856senderIdOB + "@" + st856senderIdOB + "@" + isa856RecIdOB + "@" + gs856RecIdOB + "@" + st856RecIdOB + "@" + isa856VersionOB + "@" + gs856VersionOB + "@" + st856VersionOB + "@" + fun856GroupIdOB + "@" + res856AgecodeOB + "@" + gen856AckOB + "@" + trans856IdcodeOB;
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
    document.getElementById("OB856Transaction").value = ob856;
    //alert("ob 856 transaction "+document.getElementById("OB856Transaction").value); 
}

function checkob810() {
    var obtransaction = document.getElementById("obvalue810").value;
    var obdirection = document.getElementById("obdirection810").value;
    var isa810senderIdOB = document.getElementById("isa810senderIdOB").value;
    var gs810senderIdOB = document.getElementById("gs810senderIdOB").value;
    var st810senderIdOB = document.getElementById("st810senderIdOB").value;
    var isa810RecIdOB = document.getElementById("isa810RecIdOB").value;
    var gs810RecIdOB = document.getElementById("gs810RecIdOB").value;
    var st810RecIdOB = document.getElementById("st810RecIdOB").value;
    var isa810VersionOB = document.getElementById("isa810VersionOB").value;
    var gs810VersionOB = document.getElementById("gs810VersionOB").value;
    var st810VersionOB = document.getElementById("st810VersionOB").value;
    var fun810GroupIdOB = document.getElementById("fun810GroupIdOB").value;
    var res810AgecodeOB = document.getElementById("res810AgecodeOB").value;
    var gen810AckOB = document.getElementById("gen810AckOB").checked;
    var trans810IdcodeOB = document.getElementById("trans810IdcodeOB").value;

    if (isa810senderIdOB.length == 0 || isa810senderIdOB == "" || isa810senderIdOB == null) {
        document.getElementById('resultMessage810ob').innerHTML = "<font color=red>Please enter senderId ISA.</font>";
        return false;
    } else if (gs810senderIdOB.length == 0 || gs810senderIdOB == "" || gs810senderIdOB == null) {
        document.getElementById('resultMessage810ob').innerHTML = "<font color=red>Please enter senderId GS.</font>";
        return false;
    } else if (st810senderIdOB.length == 0 || st810senderIdOB == "" || st810senderIdOB == null) {
        document.getElementById('resultMessage810ob').innerHTML = "<font color=red>Please enter senderId ST.</font>";
        return false;
    } else if (isa810RecIdOB.length == 0 || isa810RecIdOB == "-1" || isa810RecIdOB == null) {
        document.getElementById('resultMessage810ob').innerHTML = "<font color=red>Please enter RecieverId ISA.</font>";
        return false;
    } else if (gs810RecIdOB.length == 0 || gs810RecIdOB == "-1" || gs810RecIdOB == null) {
        document.getElementById('resultMessage810ob').innerHTML = "<font color=red>Please enter RecieverId GS.</font>";
        return false;
    } else if (st810RecIdOB.length == 0 || st810RecIdOB == "" || st810RecIdOB == null) {
        document.getElementById('resultMessage810ob').innerHTML = "<font color=red>Please enter RecieverId ST.</font>";
        return false;
    } else if (isa810VersionOB.length == 0 || isa810VersionOB == "" || isa810VersionOB == null) {
        document.getElementById('resultMessage810ob').innerHTML = "<font color=red>Please enter Version ISA.</font>";
        return false;
    } else if (gs810VersionOB.length == 0 || gs810VersionOB == "" || gs810VersionOB == null) {
        document.getElementById('resultMessage810ob').innerHTML = "<font color=red>Please enter Version GS.</font>";
        return false;
    } else if (st810VersionOB.length == 0 || st810VersionOB == "" || st810VersionOB == null) {
        document.getElementById('resultMessage810ob').innerHTML = "<font color=red>Please enter Version ST.</font>";
        return false;
    } else if (fun810GroupIdOB.length == 0 || fun810GroupIdOB == "" || fun810GroupIdOB == null) {
        document.getElementById('resultMessage810ob').innerHTML = "<font color=red>Please enter Functional ID Code.</font>";
        return false;
    } else if (res810AgecodeOB.length == 0 || res810AgecodeOB == "" || res810AgecodeOB == null) {
        document.getElementById('resultMessage810ob').innerHTML = "<font color=red>Please enter Responsible Agency Code.</font>";
        return false;
    }

    if (isa810senderIdOB != null || isa810senderIdOB != "") {
        if (gs810senderIdOB != null && gs810senderIdOB != "") {
            if (st810senderIdOB != null && st810senderIdOB != "") {
                if (isa810RecIdOB != null && isa810RecIdOB != "-1") {
                    if (gs810RecIdOB != null && gs810RecIdOB != "-1") {
                        if (st810RecIdOB != null && st810RecIdOB != "") {
                            if (isa810VersionOB != null && isa810VersionOB != "") {
                                if (gs810VersionOB != null && gs810VersionOB != "") {
                                    if (st810VersionOB != null && st810VersionOB != "") {
                                        if (fun810GroupIdOB != null && fun810GroupIdOB != "") {
                                            if (res810AgecodeOB != null && res810AgecodeOB != "") {
                                                ob810 = obtransaction + "@" + obdirection + "@" + isa810senderIdOB + "@" + gs810senderIdOB + "@" + st810senderIdOB + "@" + isa810RecIdOB + "@" + gs810RecIdOB + "@" + st810RecIdOB + "@" + isa810VersionOB + "@" + gs810VersionOB + "@" + st810VersionOB + "@" + fun810GroupIdOB + "@" + res810AgecodeOB + "@" + gen810AckOB + "@" + trans810IdcodeOB;
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
    document.getElementById("OB810Transaction").value = ob810;
    //alert("ob 810 transaction "+document.getElementById("OB810Transaction").value); 
}

function fieldLengthValidatorEditEnvelope(element) {
    var i = 0;
    if (element.value != null && (element.value != "")) {
        if (element.id == 'isaSenderId' || element.id == 'gsSenderId' || element.id == 'stSenderId' || element.id == 'gsVersion' || element.id == 'stVersion') {
            i = 15;
        }
        if (element.id == 'isaVersion') {
            i = 5;
        }
        if (element.id == 'functionalGroupId' || element.id == 'responsibleAgencyCode' || element.id == 'transactionSetIdCode') {
            i = 3;
        }
        if (element.value.replace(/^\s+|\s+$/g, "").length > i) {
            str = new String(element.value);
            element.value = str.substring(0, i);
            document.getElementById('resultMessage').innerHTML = "<font color=red>The Value must be less than " + i + " characters</font>";
            element.focus();
            return false;
        }
        return true;
    }
}

function fieldLengthValidator850IB(element) {
    var i = 0;
    if (element.value != null && (element.value != "")) {
        /* 850 IB validation */
        if (element.id == 'isa850senderIdIB' || element.id == 'gs850senderIdIB' || element.id == 'st850senderIdIB' || element.id == 'gs850VersionIB' || element.id == 'st850VersionIB') {
            i = 15;
        }
        if (element.id == 'isa850VersionIB') {
            i = 5;
        }
        if (element.id == 'fun850GroupIdIB' || element.id == 'res850AgecodeIB' || element.id == 'trans850IdcodeIB') {
            i = 3;
        }
        /* 850 end*/         if (element.value.replace(/^\s+|\s+$/g, "").length > i) {
            str = new String(element.value);
            element.value = str.substring(0, i);
            document.getElementById('resultMessage').innerHTML = "<font color=red>The Value must be less than " + i + " characters</font>";
            element.focus();
            return false;
        }
        return true;
    }
}

function fieldLengthValidator855IB(element) {
    var i = 0;
    if (element.value != null && (element.value != "")) {         /* 855 IB validation */
        if (element.id == 'isa855senderIdIB' || element.id == 'gs855senderIdIB' || element.id == 'st855senderIdIB' || element.id == 'gs855VersionIB' || element.id == 'st855VersionIB') {
            i = 15;
        }
        if (element.id == 'isa855VersionIB') {
            i = 5;
        }
        if (element.id == 'fun855GroupIdIB' || element.id == 'res855AgecodeIB' || element.id == 'trans855IdcodeIB') {
            i = 3;
        }
        /* 855 end*/
        if (element.value.replace(/^\s+|\s+$/g, "").length > i) {
            str = new String(element.value);
            element.value = str.substring(0, i);
            document.getElementById('resultMessage855ib').innerHTML = "<font color=red>The Value must be less than " + i + " characters</font>";
            element.focus();
            return false;
        }
        return true;
    }
}

function fieldLengthValidator856IB(element) {
    var i = 0;
    if (element.value != null && (element.value != "")) {         /* 856 IB validation */
        if (element.id == 'isa856senderIdIB' || element.id == 'gs856senderIdIB' || element.id == 'st856senderIdIB' || element.id == 'gs856VersionIB' || element.id == 'st856VersionIB') {
            i = 15;
        }
        if (element.id == 'isa856VersionIB') {
            i = 5;
        }
        if (element.id == 'fun856GroupIdIB' || element.id == 'res856AgecodeIB' || element.id == 'trans856IdcodeIB') {
            i = 3;
        }
        /* 856 end*/
        if (element.value.replace(/^\s+|\s+$/g, "").length > i) {
            str = new String(element.value);
            element.value = str.substring(0, i);
            document.getElementById('resultMessage856ib').innerHTML = "<font color=red>The Value must be less than " + i + " characters</font>";
            element.focus();
            return false;
        }
        return true;
    }
}

function fieldLengthValidator810IB(element) {
    var i = 0;
    if (element.value != null && (element.value != "")) {         /* 810 IB validation */
        if (element.id == 'isa810senderIdIB' || element.id == 'gs810senderIdIB' || element.id == 'st810senderIdIB' || element.id == 'gs810VersionIB' || element.id == 'st810VersionIB') {
            i = 15;
        }
        if (element.id == 'isa810VersionIB') {
            i = 5;
        }
        if (element.id == 'fun810GroupIdIB' || element.id == 'res810AgecodeIB' || element.id == 'trans810IdcodeIB') {
            i = 3;
        }
        /* 810 end*/
        if (element.value.replace(/^\s+|\s+$/g, "").length > i) {
            str = new String(element.value);
            element.value = str.substring(0, i);
            document.getElementById('resultMessage810ib').innerHTML = "<font color=red>The Value must be less than " + i + " characters</font>";
            element.focus();
            return false;
        }
        return true;
    }
}

function fieldLengthValidator850OB(element) {
    var i = 0;
    if (element.value != null && (element.value != "")) {         /* 850 OB validation */
        if (element.id == 'isa850senderIdOB' || element.id == 'gs850senderIdOB' || element.id == 'st850senderIdOB' || element.id == 'gs850VersionOB' || element.id == 'st850VersionOB') {
            i = 15;
        }
        if (element.id == 'isa850VersionOB') {
            i = 5;
        }
        if (element.id == 'fun850GroupIdOB' || element.id == 'res850AgecodeOB' || element.id == 'trans850IdcodeOB') {
            i = 3;
        }
        /* 850 end*/
        if (element.value.replace(/^\s+|\s+$/g, "").length > i) {
            str = new String(element.value);
            element.value = str.substring(0, i);
            document.getElementById('resultMessage850ob').innerHTML = "<font color=red>The Value must be less than " + i + " characters</font>";
            element.focus();
            return false;
        }
        return true;
    }
}

function fieldLengthValidator855OB(element) {
    var i = 0;
    if (element.value != null && (element.value != "")) {         /* 855 OB validation */
        if (element.id == 'isa855senderIdOB' || element.id == 'gs855senderIdOB' || element.id == 'st855senderIdOB' || element.id == 'gs855VersionOB' || element.id == 'st855VersionOB') {
            i = 15;
        }
        if (element.id == 'isa855VersionOB') {
            i = 5;
        }
        if (element.id == 'fun855GroupIdOB' || element.id == 'res855AgecodeOB' || element.id == 'trans855IdcodeOB') {
            i = 3;
        }
        /* 855 end*/
        if (element.value.replace(/^\s+|\s+$/g, "").length > i) {
            str = new String(element.value);
            element.value = str.substring(0, i);
            document.getElementById('resultMessage855ob').innerHTML = "<font color=red>The Value must be less than " + i + " characters</font>";
            element.focus();
            return false;
        }
        return true;
    }
}

function fieldLengthValidator856OB(element) {
    var i = 0;
    if (element.value != null && (element.value != "")) {         /* 856 OB validation */
        if (element.id == 'isa856senderIdOB' || element.id == 'gs856senderIdOB' || element.id == 'st856senderIdOB' || element.id == 'gs856VersionOB' || element.id == 'st856VersionOB') {
            i = 15;
        }
        if (element.id == 'isa856VersionOB') {
            i = 5;
        }
        if (element.id == 'fun856GroupIdOB' || element.id == 'res856AgecodeOB' || element.id == 'trans856IdcodeOB') {
            i = 3;
        }
        /* 856 end*/
        if (element.value.replace(/^\s+|\s+$/g, "").length > i) {
            str = new String(element.value);
            element.value = str.substring(0, i);
            document.getElementById('resultMessage856ob').innerHTML = "<font color=red>The Value must be less than " + i + " characters</font>";
            element.focus();
            return false;
        }
        return true;
    }
}

function fieldLengthValidator810OB(element) {
    var i = 0;
    if (element.value != null && (element.value != "")) {
        /* 810 OB validation */
        if (element.id == 'isa810senderIdOB' || element.id == 'gs810senderIdOB' || element.id == 'st810senderIdOB' || element.id == 'gs810VersionOB' || element.id == 'st810VersionOB') {
            i = 15;
        }
        if (element.id == 'isa810VersionOB') {
            i = 5;
        }
        if (element.id == 'fun810GroupIdOB' || element.id == 'res810AgecodeOB' || element.id == 'trans810IdcodeOB') {
            i = 3;
        }
        /* 810 end*/
        if (element.value.replace(/^\s+|\s+$/g, "").length > i) {
            str = new String(element.value);
            element.value = str.substring(0, i);
            document.getElementById('resultMessage810ob').innerHTML = "<font color=red>The Value must be less than " + i + " characters</font>";
            element.focus();
            return false;
        }
        return true;
    }
}

function isExistedPartnerName(x) {
    var url;
    $("#correctImg").hide();
    $("#wrongImg").hide();
    $("#loadingImageAjax").hide();
    var partnerName = document.getElementById("addpartnerName").value;
    if (partnerName.trim() != "") {
        var req = getXMLHttpRequest();
        $("#loadingImageAjax").show();
        req.onreadystatechange = readyStateHandlerLoadText(req, isExistedPartnerNameResponse);
        if (x == 'admin') {
            url = "../ajax/isExistedPartnerName.action?name=" + partnerName;
        } else {
            url = "./ajax/isExistedPartnerName.action?name=" + partnerName;
        }
        req.open("GET", url, "true");
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        req.send(null);
    }
}

function isExistedPartnerNameResponse(resText) {
    $("#loadingImageAjax").hide();
    if (resText == 'Yes') {
        $("#correctImg").show();
    } else {
        $("#wrongImg").show();
        document.getElementById("addpartnerName").value = '';
    }
}

function isExistedUserEmail(flag, x) {
    var url;
    if (x == 'forgotPwd') {
        $("#correctImg2").hide();
        $("#wrongImg2").hide();
        $("#loadingImageEmailCheck2").hide();
    } else {
        $("#correctImg1").hide();
        $("#wrongImg1").hide();
        $("#loadingImageEmailCheck").hide();
    }
    if (flag == 'userAdd') {
        var email = document.getElementById("regcontactEmail").value;
    }
    if (flag == 'partnerAdd') {
        var email = document.getElementById("contactEmail").value;
    }
    if (x == 'forgotPwd') {
        var email = document.getElementById("regcontactEmail").value;
    }
    if (email.trim().length > 2) {
        var req = getXMLHttpRequest();
        $("#loadingImageEmailCheck").show();
        if (flag == 'userAdd') {
            req.onreadystatechange = readyStateHandlerLoadText(req, isExistedUserEmailResponse);
        }
        if (flag == 'partnerAdd') {
            req.onreadystatechange = readyStateHandlerLoadText(req, isExistedUserEmailResponse1);
        }
        if (x == 'forgotPwd') {
            req.onreadystatechange = readyStateHandlerLoadText(req, isExistedUserEmailResponse2);
        }
        if (x == 'self' || x == 'forgotPwd') {
            url = "./ajax/isExistedUserEmail.action?email=" + email;
        } else {
            url = "../ajax/isExistedUserEmail.action?email=" + email;
        }
        req.open("GET", url, "true");
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        req.send(null);
    }
}

function isExistedUserEmailResponse(resText) {
    $("#loadingImageEmailCheck").hide();
    if (resText == 'Yes') {
        $("#correctImg1").show();
    } else {
        $("#wrongImg1").show();
        document.getElementById("regcontactEmail").value = '';
    }
}

function isExistedUserEmailResponse1(resText) {
    $("#loadingImageEmailCheck").hide();
    if (resText == 'Yes') {
        $("#correctImg1").show();
    } else {
        $("#wrongImg1").show();
        document.getElementById("contactEmail").value = '';
    }
}
function isExistedUserEmailResponse2(resText) {
    $("#loadingImageEmailCheck2").hide();
    if (resText == 'No') {
        $("#correctImg2").show();
    } else {
        $("#wrongImg2").show();
        document.getElementById("regcontactEmail").value = '';
    }
}

function gettransferModeSelection(x) {
    document.getElementById('tempTransferMode').value = x;
    document.getElementById("transferModeMsg").style.display = "none";
    var protocol = document.getElementById("commnProtocol").value;
    var formAction = document.getElementById("formAction").value;
    if (protocol == '-1') {
        $("#ftpDiv").hide();
        $("#sftpDiv").hide();
        $("#httpDiv").hide();
        $("#smtpDiv").hide();
        $("#as2Div").hide();
        $("#sslDiv").hide();
    }
    if (protocol == 'FTP') {
        $("#ftpDiv").show();
        $("#sftpDiv").hide();
        $("#httpDiv").hide();
        $("#smtpDiv").hide();
        $("#as2Div").hide();
        $("#sslDiv").hide();
        $("#sslDiv2").hide();
        if (x == 'pull') {
            document.getElementById("saveButton").style.display = 'none';
            $("#mail_button").css("display", "");
            var transferMode = x;
            var recvProtocol;
            var protocol = document.getElementById("commnProtocol").value;
            var partnerName = document.getElementById("partnerName").value;
            $.ajax({
                url: '../ajax/getProtocolDetails.action?transferMode=' + transferMode + '&protocol=' + protocol,
                context: document.body,
                success: function(responseText) {
                    var json = $.parseJSON(responseText);
                    var ftpMethod = json["FTP_METHOD"];
                    var connMethod = json["CONNECTION_METHOD"];
                    recvProtocol = json["RECEIVING_PROTOCOL"];
                    var respTime = json["RESPONSE_TIMEOUT_SEC"];
                    var host = json["FTP_HOST"];
                    var port = json["FTP_PORT"];
                    var userId = json["FTP_USER_ID"];
                    var pwd = json["FTP_PASSWORD"];
                    var directory = json["FTP_DIRECTORY"];
                    var sslReq = false;
                    if (recvProtocol == 'FTPS') {
                        sslReq = json["SSL_REQUIRED_FLAG"];
                    } else {
                        document.getElementById("ftp_ssl").style.display = 'none';
                    }
                    var SSL_PRIORITY = '';
                    //var KEY_CERTIFICATE_PASSPHRASE = '';
                    var CIPHER_STRENGTH = '';
                    if (sslReq == 'true') {
                        SSL_PRIORITY = json["SSL_PRIORITY"];
                        //KEY_CERTIFICATE_PASSPHRASE = json["KEY_CERTIFICATE_PASSPHRASE"];
                        CIPHER_STRENGTH = json["CIPHER_STRENGTH"];
                    }
                    document.getElementById("ftp_method").value = ftpMethod;
                    document.getElementById("ftp_conn_method").value = connMethod;
                    document.getElementById("ftp_recv_protocol").value = recvProtocol;
                    document.getElementById("ftp_resp_time").value = respTime;
                    document.getElementById("ftp_host").value = host;
                    document.getElementById("ftp_port").value = port;
                    document.getElementById("ftp_userId").value = userId;
                    document.getElementById("ftp_pwd").value = pwd;
                    document.getElementById("ftp_directory").value = directory + '/' + partnerName;
                    if (sslReq == 'true') {
                        document.getElementById("ftp_ssl_req").checked = true;
                        document.getElementById("ssl_priority").value = SSL_PRIORITY;
                        document.getElementById("ssl_cipher_stergth").value = CIPHER_STRENGTH;
                        $("#sslDiv").show();
                    } else {
                        document.getElementById("ftp_ssl_req").checked = false;
                    }
                },
                error: function(e) {
                    document.getElementById("protocolmsg").style.display = 'none';
                    alert("error-->" + e);
                }
            });
            $('#ftp_method').attr('disabled', true);
            $('#ftp_conn_method').attr('disabled', true);
            $('#ftp_recv_protocol').attr('disabled', true);
            document.getElementById("ftp_resp_time").readOnly = true;
            document.getElementById("ftp_host").readOnly = true;
            document.getElementById("ftp_port").readOnly = true;
            document.getElementById("ftp_userId").readOnly = true;
            document.getElementById("ftp_pwd").readOnly = true;
            document.getElementById("ftp_directory").readOnly = true;
            $('#ssl_priority').attr('disabled', true);
            $('#ssl_cipher_stergth').attr('disabled', true);
        }
        if (x == 'push') {
            document.getElementById("saveButton").style.display = 'block';
            $("#mail_button").css("display", "none");
            var ftp_recv;
            if (formAction == 'doAddProfile') {
                document.getElementById("ftp_resp_time").value = "";
                document.getElementById("ftp_host").value = "";
                document.getElementById("ftp_port").value = "";
                document.getElementById("ftp_userId").value = "";
                document.getElementById("ftp_pwd").value = "";
                document.getElementById("ftp_directory").value = "";
                document.getElementById("ftp_recv_protocol").value = "-1";
                ftp_recv = document.getElementById("ftp_recv_protocol").value;
                if (ftp_recv == 'FTPS') {
                    document.getElementById("ftp_ssl_req").checked = true;
                    document.getElementById("ftp_ssl").style.display = 'block';
                } else {
                    document.getElementById("ftp_ssl_req").checked = false;
                    document.getElementById("ftp_ssl").style.display = 'none';
                }
                document.getElementById("ssl_priority2").value = "MUST";
                document.getElementById("ssl_cipher_stergth2").value = "STRONG";
            }
            $('#ftp_conn_method').attr('disabled', false);
            $('#ftp_recv_protocol').attr('disabled', false);
            ftp_recv = document.getElementById("ftp_recv_protocol").value;
            if (ftp_recv == 'FTPS') {
                document.getElementById("ftp_ssl_req").checked = true;
                document.getElementById("ftp_ssl").style.display = 'block';
            } else {
                document.getElementById("ftp_ssl_req").checked = false;
                document.getElementById("ftp_ssl").style.display = 'none';
            }
            document.getElementById("ftp_resp_time").readOnly = false;
            document.getElementById("ftp_host").readOnly = false;
            document.getElementById("ftp_port").readOnly = false;
            document.getElementById("ftp_userId").readOnly = false;
            document.getElementById("ftp_pwd").readOnly = false;
            document.getElementById("ftp_directory").readOnly = false;
            $('#ftp_method').attr('disabled', false);
            $('#ssl_priority2').attr('disabled', false);
            $('#ssl_cipher_stergth2').attr('disabled', false);
        }
    }
    if (protocol == 'SFTP') {
        $("#ftpDiv").hide();
        $("#sftpDiv").show();
        $("#httpDiv").hide();
        $("#smtpDiv").hide();
        $("#as2Div").hide();
        $("#sslDiv").hide();
        if (x == 'pull') {
            document.getElementById("saveButton").style.display = 'none';
            $("#mail_button_sftp").css("display", "");
            var transferMode = x;
            var protocol = document.getElementById("commnProtocol").value;
            var partnerName = document.getElementById("partnerName").value;
            $.ajax({
                url: '../ajax/getProtocolDetails.action?transferMode=' + transferMode + '&protocol=' + protocol,
                context: document.body,
                success: function(responseText) {
                    var json = $.parseJSON(responseText);
                    var CONN_METHOD = json["CONN_METHOD"];
                    var AUTH_METHOD = json["AUTH_METHOD"];
                    // var MY_SSH_PUB_KEY = json["MY_SSH_PUB_KEY"];
                    // var UPL_YOUR_SSH_PUB_KEY = json["UPL_YOUR_SSH_PUB_KEY"];
                    var REMOTE_HOST_IP_ADD = json["REMOTE_HOST_IP_ADD"];
                    var REMOTE_PORT = json["REMOTE_PORT"];
                    var REMOTE_USERID = json["REMOTE_USERID"];
                    var REMOTE_PWD = json["REMOTE_PWD"];
                    var METHOD = json["METHOD"];
                    var DIRECTORY = '/MSCVP/' + partnerName;
                    document.getElementById("sftp_conn_method").value = CONN_METHOD;
                    document.getElementById("sftp_auth_method").value = AUTH_METHOD;
                    document.getElementById("sftp_host_ip").value = REMOTE_HOST_IP_ADD;
                    document.getElementById("sftp_remote_port").value = REMOTE_PORT;
                    document.getElementById("sftp_remote_userId").value = REMOTE_USERID;
                    document.getElementById("sftp_remote_pwd").value = REMOTE_PWD;
                    document.getElementById("sftp_method").value = METHOD;
                    document.getElementById("sftp_directory").value = DIRECTORY;
                },
                error: function(e) {
                    document.getElementById("protocolmsg").style.display = 'none';
                    alert("error-->" + e);
                }
            });
            document.getElementById("upload").style.display = 'none';
            document.getElementById("download").style.display = 'block';
            $('#sftp_conn_method').attr('disabled', true);
            $('#sftp_auth_method').attr('disabled', true);
            document.getElementById("sftp_host_ip").readOnly = true;
            document.getElementById("sftp_remote_port").readOnly = true;
            document.getElementById("sftp_remote_userId").readOnly = true;
            document.getElementById("sftp_remote_pwd").readOnly = true;
            $('#sftp_method').attr('disabled', true);
            document.getElementById("sftp_directory").readOnly = true;
        }
        if (x == 'push') {
            document.getElementById("saveButton").style.display = 'block';
            $("#mail_button_sftp").css("display", "none");
            if (formAction == 'doAddProfile') {
                document.getElementById("sftp_host_ip").value = "";
                document.getElementById("sftp_remote_port").value = "";
                document.getElementById("sftp_remote_userId").value = "";
                document.getElementById("sftp_remote_pwd").value = "";
                document.getElementById("sftp_directory").value = "";
            }
            document.getElementById("upload").style.display = 'block';
            document.getElementById("download").style.display = 'none';
            $('#sftp_conn_method').attr('disabled', false);
            $('#sftp_auth_method').attr('disabled', false);
            document.getElementById("sftp_host_ip").readOnly = false;
            document.getElementById("sftp_remote_port").readOnly = false;
            document.getElementById("sftp_directory").readOnly = false;
            document.getElementById("sftp_remote_userId").readOnly = false;
            $('#sftp_method').attr('disabled', false);
            document.getElementById("sftp_remote_pwd").readOnly = false;
        }
    }
    if (protocol == 'HTTP') {
        $("#ftpDiv").hide();
        $("#sftpDiv").hide();
        $("#httpDiv").show();
        $("#smtpDiv").hide();
        $("#as2Div").hide();
        $("#sslDiv").hide();
        $("#sslDiv2").hide();
        if (x == 'push') {
            document.getElementById("saveButton").style.display = 'none';
            var transferMode = x;
            var RECEIVING_PROTOCOL;
            var protocol = document.getElementById("commnProtocol").value;
            var partnerName = document.getElementById("partnerName").value;
            $.ajax({
                url: '../ajax/getProtocolDetails.action?transferMode=' + transferMode + '&protocol=' + protocol,
                context: document.body,
                success: function(responseText) {
                    var json = $.parseJSON(responseText);
                    RECEIVING_PROTOCOL = json["RECEIVING_PROTOCOL"];
                    var RESPONSE_TIMEOUT_SEC = json["RESPONSE_TIMEOUT_SEC"];
                    var HTTP_END_POINT = json["HTTP_END_POINT"];
                    var HTTP_PORT = json["HTTP_PORT"];
                    var HTTP_MODE = json["HTTP_MODE"];
                    var sslReq = false;
                    if (RECEIVING_PROTOCOL == 'HTTPS') {
                        sslReq = json["SSL_REQUIRED"];
                    } else {
                        document.getElementById("http_ssl").style.display = 'none';
                    }
                    var SSL_PRIORITY = '';
                    //var KEY_CERTIFICATE_PASSPHRASE = '';
                    var CIPHER_STRENGTH = '';
                    if (sslReq == 'true') {
                        SSL_PRIORITY = json["SSL_PRIORITY"];
                        //KEY_CERTIFICATE_PASSPHRASE = json["KEY_CERTIFICATE_PASSPHRASE"];
                        CIPHER_STRENGTH = json["CIPHER_STRENGTH"];
                    }
                    document.getElementById("http_recv_protocol").value = RECEIVING_PROTOCOL;
                    document.getElementById("http_resp_time").value = RESPONSE_TIMEOUT_SEC;
                    document.getElementById("http_endpoint").value = HTTP_END_POINT;
                    document.getElementById("http_port").value = HTTP_PORT;
                    document.getElementById("http_protocol_mode").value = HTTP_MODE;
                    document.getElementById("http_url").value = '/' + partnerName;
                    if (sslReq == 'true') {
                        document.getElementById("http_ssl_req").checked = true;
                        $("#sslDiv").show();
                        document.getElementById("ssl_priority").value = SSL_PRIORITY;
                        //document.getElementById("ssl_passphrase").value = KEY_CERTIFICATE_PASSPHRASE;
                        document.getElementById("ssl_cipher_stergth").value = CIPHER_STRENGTH;
                    } else {
                        document.getElementById("http_ssl_req").checked = false;
                    }
                },
                error: function(e) {
                    document.getElementById("protocolmsg").style.display = 'none';
                    alert("error-->" + e);
                }
            });
            $('#http_recv_protocol').attr('disabled', true);
            document.getElementById("http_resp_time").readOnly = true;
            document.getElementById("http_endpoint").readOnly = true;
            document.getElementById("http_port").readOnly = true;
            document.getElementById("http_url").readOnly = true;
            $('#http_protocol_mode').attr('disabled', true);
        }
        if (x == 'pull') {
            document.getElementById("saveButton").style.display = 'block';
            var http_recv;
            if (formAction == 'doAddProfile') {
                document.getElementById("http_resp_time").value = "";
                document.getElementById("http_endpoint").value = "";
                document.getElementById("http_port").value = "";
                document.getElementById("http_url").value = "";
                document.getElementById("http_recv_protocol").value = "-1";
                http_recv = document.getElementById("http_recv_protocol").value;
                if (http_recv == 'HTTPS') {
                    document.getElementById("http_ssl_req").checked = true;
                    document.getElementById("http_ssl").style.display = 'block';
                } else {
                    document.getElementById("http_ssl_req").checked = false;
                    document.getElementById("http_ssl").style.display = 'none';
                }
            }
            $('#http_recv_protocol').attr('disabled', false);
            http_recv = document.getElementById("http_recv_protocol").value;
            if (http_recv == 'HTTPS') {
                document.getElementById("http_ssl_req").checked = true;
                document.getElementById("http_ssl").style.display = 'block';
            } else {
                document.getElementById("http_ssl_req").checked = false;
                document.getElementById("http_ssl").style.display = 'none';
            }
            document.getElementById("http_endpoint").readOnly = false;
            $('#http_protocol_mode').attr('disabled', false);
            document.getElementById("http_port").readOnly = false;
            document.getElementById("http_resp_time").readOnly = false;
            $('#ssl_priority2').attr('disabled', false);
            $('#ssl_cipher_stergth2').attr('disabled', false);
            document.getElementById("http_url").readOnly = false;
        }
    }
    if (protocol == 'SMTP') {
        $("#ftpDiv").hide();
        $("#sftpDiv").hide();
        $("#httpDiv").hide();
        $("#smtpDiv").show();
        $("#as2Div").hide();
        $("#sslDiv").hide();
        document.getElementById("saveButton").style.display = 'block';
        var transferMode = 'x';
        var protocol = document.getElementById("commnProtocol").value;
        document.getElementById("smtp_recv_protocol").value = "SMTP";
        document.getElementById("smtp_server_protocol").readOnly = false;
        document.getElementById("smtp_server_port").readOnly = false;
        $('#smtp_recv_protocol').attr('disabled', true);
        document.getElementById("smtp_server_port").readOnly = false;
        document.getElementById("smtp_from_email").readOnly = false;
        document.getElementById("smtp_to_email").readOnly = false;
    }
    if (protocol == 'AS2') {
        $("#ftpDiv").hide();
        $("#sftpDiv").hide();
        $("#httpDiv").hide();
        $("#smtpDiv").hide();
        $("#as2Div").show();
        $("#sslDiv").hide();
        $("#sslDiv2").hide();
        document.getElementById("saveButton").style.display = 'block';
        var transferMode = "x";
        var protocol = document.getElementById("commnProtocol").value;
        document.getElementById("as2_myOrgName").readOnly = false;
        document.getElementById("as2_partOrgName").readOnly = false;
        document.getElementById("as2_myPartname").readOnly = false;
        document.getElementById("as2_yourPartname").readOnly = false;
        document.getElementById("as2_myEndPoint").readOnly = false;
        document.getElementById("as2_partendpoint").readOnly = false;
        document.getElementById("as2_payloadSendMode").readOnly = false;
        document.getElementById("ssl_priority2").readOnly = false;
        document.getElementById("ssl_cipher_stergth2").readOnly = false;
        $('#as2_strMsg').attr('disabled', false);
        $('#as2_waitForSync').attr('disabled', false);
    }
}

function sslRequiredForGetDetails(x) {
    var transferMode;
    if (document.updateTpOnboard.transferMode.value) {
        transferMode = document.updateTpOnboard.transferMode.value;
    }
    if (x == 'ftp') {
        var ftp_ssl = document.getElementById("ftp_ssl_req").checked;
        if (ftp_ssl == true) {

            if (transferMode == 'pull') {
                $("#sslDiv").show();
                //                document.getElementById("ssl_priority").value = 'MUST';
                //                document.getElementById("ssl_passphrase").value = 'xxxx';
                //                document.getElementById("ssl_cipher_stergth").value = 'STRONG';
            }
            if (transferMode == 'push') {
                $("#sslDiv2").show();
                //                document.getElementById("ssl_priority").value = 'MUST';
                //                document.getElementById("ssl_passphrase").value = '';
                //                document.getElementById("ssl_cipher_stergth").value = 'NONE';
            }
        } else {
            $("#sslDiv").hide();
            $("#sslDiv2").hide();
        }
    }
    if (x == 'sftp') {
        var sftp_ssl = document.getElementById("sftp_ssl_req").checked;
        if (sftp_ssl == true) {
            $("#sslDiv").show();
            if (transferMode == 'pull') {
                document.getElementById("ssl_priority").value = 'MUST';
                document.getElementById("ssl_passphrase").value = 'xxxx';
                document.getElementById("ssl_cipher_stergth").value = 'STRONG';
            }
            if (transferMode == 'push') {
                document.getElementById("ssl_priority").value = 'MUST';
                document.getElementById("ssl_passphrase").value = '';
                document.getElementById("ssl_cipher_stergth").value = 'NONE';
            }
        } else {
            $("#sslDiv").hide();
            $("#sslDiv2").hide();
        }
    }
    if (x == 'http') {
        var http_ssl = document.getElementById("http_ssl_req").checked;
        if (http_ssl == true) {

            if (transferMode == 'pull') {
                $("#sslDiv2").show();
                //                document.getElementById("ssl_priority").value = 'MUST';
                //                document.getElementById("ssl_passphrase").value = 'xxxx';
                //                document.getElementById("ssl_cipher_stergth").value = 'xxxx';
            }
            if (transferMode == 'push') {
                $("#sslDiv").show();
                //                document.getElementById("ssl_priority").value = 'MUST';
                //                document.getElementById("ssl_passphrase").value = '';
                //                document.getElementById("ssl_cipher_stergth").value = '';
            }
        } else {
            $("#sslDiv").hide();
            $("#sslDiv2").hide();
        }
    }
    if (x == 'as2') {
        var as2_ssl = document.getElementById("as2_ssl_req").checked;
        if (as2_ssl == true) {
            $("#sslDiv2").show();
            //            if(transferMode == 'pull'){
            //                document.getElementById("ssl_priority").value = 'Must';
//                document.getElementById("ssl_passphrase").value = 'xxxx';
            //                document.getElementById("ssl_cipher_stergth").value = 'xxxx';
            //            }
            //            if(transferMode == 'push'){
            //                document.getElementById("ssl_priority").value = 'Must';
            //                document.getElementById("ssl_passphrase").value = '';
//                document.getElementById("ssl_cipher_stergth").value = '';
            //            }
        } else {
            $("#sslDiv").hide();
            $("#sslDiv2").hide();
        }
    }
}

function as2OrgProfileNames() {
    var as2_myOrgName = document.getElementById('as2_myOrgName');
    var as2_yourPartname = document.getElementById('as2_yourPartname');
    as2_yourPartname.value = as2_myOrgName.value;
}

function as2PartnerProfileNames() {
    var as2_partOrgName = document.getElementById('as2_partOrgName');
    var as2_myPartname = document.getElementById('as2_myPartname');
    as2_myPartname.value = as2_partOrgName.value;
}
function isExistedAS2PartnerProfileName() {
    $("#correctImg").hide();
    $("#wrongImg").hide();
    $("#loadingImageAjax").hide();
    var partnerName = document.getElementById("partnerName").value;
    var myOrgName = document.getElementById("as2_myOrgName").value;
    if (partnerName.trim() != "") {
        var req = getXMLHttpRequest();
        $("#loadingImageAjax").show();
        req.onreadystatechange = readyStateHandlerLoadText(req, isExistedAS2PartnerProfileNameResponse);
        var url = "../ajax/isExistedAS2PartnerProfileName.action?name=" + myOrgName + "&partnerName=" + partnerName;
        req.open("GET", url, "true");
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        req.send(null);
    }
}
function isExistedAS2PartnerProfileNameResponse(resText) {
    $("#loadingImageAjax").hide();
    if (resText == 'Yes') {
        $("#correctImg").show();
        document.getElementById("as2_yourPartname").value = document.getElementById("as2_myOrgName").value;
    } else {
        $("#wrongImg").show();
        document.getElementById("as2_myOrgName").value = '';
        document.getElementById("as2_yourPartname").value = '';
    }
}

function validateFTP(flag) {
    var ftp_method = document.getElementById("ftp_method").value;
    var ftp_conn_method = document.getElementById("ftp_conn_method").value;
    var ftp_recv_protocol = document.getElementById("ftp_recv_protocol").value;
    var ftp_resp_time = document.getElementById("ftp_resp_time").value;
    var ftp_host = document.getElementById("ftp_host").value;
    var ftp_port = document.getElementById("ftp_port").value;
    var ftp_userId = document.getElementById("ftp_userId").value;
    var ftp_pwd = document.getElementById("ftp_pwd").value;
    var ftp_directory = document.getElementById("ftp_directory").value;
    // var ftp_ssl_req = document.getElementById("ftp_ssl_req").value;
    var ftp_ssl = document.getElementById("ftp_ssl_req").checked;
    document.getElementById('tpResultMessage').innerHTML = "";     //var commnProtocol =document.getElementById('commnProtocol').value;
    if (((ftp_method == null) || (ftp_method == "")) || ((ftp_conn_method == null) || (ftp_conn_method == "")) || ((ftp_recv_protocol == null) || (ftp_recv_protocol == "-1")) || ((ftp_resp_time == null) || (ftp_resp_time == "")) || ((ftp_host == null) || (ftp_host == "")) || ((ftp_port == null) || (ftp_port == "")) || ((ftp_userId == null) || (ftp_userId == "")) || ((ftp_pwd == null) || (ftp_pwd == "")) || ((ftp_directory == null) || (ftp_directory == ""))) {
        document.getElementById('protocolmsgFtp').innerHTML = "<font color=red>Please enter all mandatory fields</font>";
        return false;
    }
    if ((document.getElementById('tempTransferMode').value) == 'push') {
        if (ftp_ssl == true) {
            // var ssl_priority = document.getElementById("ssl_priority2").value;
            // var ssl_cipher_stergth = document.getElementById("ssl_cipher_stergth2").value;
            var fileName = document.getElementById("attachmentFileName").value;
            if (flag == 'add') {
                if (((fileName == null) || (fileName == ""))) {
                    document.getElementById('protocolmsgSsl').innerHTML = "<font color=red>Please upload certificate in SSL</font>";
                    return false;
                }
            }
        }
    }
}

function validateSFTP(flag) {     // var sftp_conn_method = document.getElementById("sftp_conn_method").value;
    //  var sftp_auth_method = document.getElementById("sftp_auth_method").value;
    var FileName = document.getElementById("attachmentFileNameSftp").value;
    var sftp_host_ip = document.getElementById("sftp_host_ip").value;
    var sftp_remote_port = document.getElementById("sftp_remote_port").value;
    var sftp_remote_userId = document.getElementById("sftp_remote_userId").value;
    var sftp_remote_pwd = document.getElementById("sftp_remote_pwd").value;
    // var sftp_method = document.getElementById("sftp_method").value;
    var sftp_directory = document.getElementById("sftp_directory").value;
    // var ftp_ssl_req = document.getElementById("ftp_ssl_req").value;
    //var ftp_ssl = document.getElementById("ftp_ssl_req").checked;
    document.getElementById('tpResultMessage').innerHTML = "";
    //var commnProtocol =document.getElementById('commnProtocol').value;
    if (flag == 'add') {
        if (((FileName == null) || (FileName == ""))) {
            document.getElementById('protocolmsgSftp').innerHTML = "<font color=red>Please upload SSH public key</font>";
            return false;
        }
    }
    if ((document.getElementById('tempTransferMode').value) == 'push') {
        if (((sftp_host_ip == null) || (sftp_host_ip == "")) || ((sftp_remote_port == null) || (sftp_remote_port == "")) || ((sftp_remote_userId == null) || (sftp_remote_userId == "")) || ((sftp_remote_pwd == null) || (sftp_remote_pwd == "")) || ((sftp_directory == null) || (sftp_directory == ""))) {
            document.getElementById('protocolmsgSftp').innerHTML = "<font color=red>Please enter all mandatory fields</font>";
            return false;
        }
    }
}
function validateHTTP(flag) {
    // var sftp_conn_method = document.getElementById("sftp_conn_method").value;
    //  var sftp_auth_method = document.getElementById("sftp_auth_method").value;
    var http_recv_protocol = document.getElementById("http_recv_protocol").value;
    var http_resp_time = document.getElementById("http_resp_time").value;
    var http_endpoint = document.getElementById("http_endpoint").value;
    var http_port = document.getElementById("http_port").value;
    // var http_protocol_mode = document.getElementById("http_protocol_mode").value;
    // var sftp_method = document.getElementById("sftp_method").value;
    var http_ssl_req = document.getElementById("http_ssl_req").checked;
    // var ftp_ssl_req = document.getElementById("ftp_ssl_req").value;
    //var ftp_ssl = document.getElementById("ftp_ssl_req").checked;
    document.getElementById('tpResultMessage').innerHTML = "";
    //var commnProtocol =document.getElementById('commnProtocol').value;
    if (((http_recv_protocol == null) || (http_recv_protocol == "-1")) || ((http_resp_time == null) || (http_resp_time == "")) || ((http_endpoint == null) || (http_endpoint == "")) || ((http_port == null) || (http_port == ""))) {
        document.getElementById('protocolmsgHttp').innerHTML = "<font color=red>Please enter all mandatory fields</font>";
        return false;
    }
    if ((document.getElementById('tempTransferMode').value) == 'pull') {
        if (http_ssl_req == true) {
            // var ssl_priority = document.getElementById("ssl_priority2").value;
            // var ssl_cipher_stergth = document.getElementById("ssl_cipher_stergth2").value;
            var fileName = document.getElementById("attachmentFileName").value;
            if (flag == 'add') {
                if (((fileName == null) || (fileName == ""))) {
                    document.getElementById('protocolmsgSsl').innerHTML = "<font color=red>Please upload certificate in SSL</font>";
                    return false;
                }
            }
        }
    }
}
function validateAS2(flag) {
    // var sftp_conn_method = document.getElementById("sftp_conn_method").value;     //  var sftp_auth_method = document.getElementById("sftp_auth_method").value;
    var attachmentFileName = document.getElementById("attachmentFileNameAs2").value;
    var as2_myOrgName = document.getElementById("as2_myOrgName").value;
    var as2_partOrgName = document.getElementById("as2_partOrgName").value;
    var as2_myPartname = document.getElementById("as2_myPartname").value;
    var as2_yourPartname = document.getElementById("as2_yourPartname").value;
    var as2_myEndPoint = document.getElementById("as2_myEndPoint").value;
    var as2_partendpoint = document.getElementById("as2_partendpoint").value;
    var as2_strMsg = document.getElementById("as2_strMsg").value;
    var as2_waitForSync = document.getElementById("as2_waitForSync").value;
    var as2_payloadSendMode = document.getElementById("as2_payloadSendMode").value;
    // var ftp_ssl_req = document.getElementById("ftp_ssl_req").value;
    var as2_ssl_req = document.getElementById("as2_ssl_req").checked;
    document.getElementById('tpResultMessage').innerHTML = "";
    //var commnProtocol =document.getElementById('commnProtocol').value;
    if (flag == 'add') {
        if (((attachmentFileName == null) || (attachmentFileName == ""))) {
            document.getElementById('protocolmsgAs2').innerHTML = "<font color=red>Please upload system certificate</font>";
            return false;
        }
    }
    if (((as2_myOrgName == null) || (as2_myOrgName == "")) || ((as2_partOrgName == null) || (as2_partOrgName == "")) || ((as2_myPartname == null) || (as2_myPartname == "")) || ((as2_yourPartname == null) || (as2_yourPartname == "")) || ((as2_myEndPoint == null) || (as2_myEndPoint == "")) || ((as2_partendpoint == null) || (as2_partendpoint == "") || (as2_strMsg == "-1")) || ((as2_waitForSync == "-1") || (as2_payloadSendMode == "-1"))) {
        document.getElementById('protocolmsgAs2').innerHTML = "<font color=red>Please enter all mandatory fields</font>";
        return false;
    }
    if (as2_ssl_req == true) {
        // var ssl_priority = document.getElementById("ssl_priority2").value;
// var ssl_cipher_stergth = document.getElementById("ssl_cipher_stergth2").value;
        var fileName = document.getElementById("attachmentFileName").value;
        if (flag == 'add') {
            if (((fileName == null) || (fileName == ""))) {
                document.getElementById('protocolmsgSsl').innerHTML = "<font color=red>Please upload certificate in SSL</font>";
                return false;
            }
        }
    }
}

function validateSMTP() {
    var smtp_server_protocol = document.getElementById("smtp_server_protocol").value;
    var smtp_server_port = document.getElementById("smtp_server_port").value;
    var smtp_from_email = document.getElementById("smtp_from_email").value;
    var smtp_to_email = document.getElementById("smtp_to_email").value;
    document.getElementById('tpResultMessage').innerHTML = "";
    //var commnProtocol =document.getElementById('commnProtocol').value;
    if (((smtp_server_protocol == null) || (smtp_server_protocol == "")) || ((smtp_server_port == null) || (smtp_server_port == "")) || ((smtp_from_email == null) || (smtp_from_email == "")) || ((smtp_to_email == null) || (smtp_to_email == ""))) {
        document.getElementById('protocolmsgSmtp').innerHTML = "<font color=red>Please enter all mandatory fields</font>";
        return false;
    }
}

function addPartner1() {
    document.getElementById("headerLabel").style.color = "white";
    document.getElementById("headerLabel").innerHTML = "Add Partner";
    var overlay = document.getElementById('overlay');
    var specialBox = document.getElementById('specialBox');

    overlay.style.opacity = .8;
    if (overlay.style.display == "block") {
        overlay.style.display = "none";
        specialBox.style.display = "none";
    } else {
        overlay.style.display = "block";
        specialBox.style.display = "block";
    }
}
function resetPartnerValues() {
    document.getElementById('addbutton').disabled = false;
    document.getElementById("addpartnerMsg").value = "";
    document.getElementById("addpartnerName").value = "";
    document.getElementById("addphoneNo").value = "";
    document.getElementById("addaddress1").value = "";
    document.getElementById("addcity").value = "";
    document.getElementById("addstate").value = "";
    document.getElementById("addcountry").value = "-1";
    document.getElementById("addzipCode").value = "";
    document.getElementById("correctImg").style.display = "none";
    document.getElementById("wrongImg").style.display = "none";
}

function resetAddPartnerValues(flag) {
    document.getElementById('addbutton').disabled = false;
    document.getElementById("addpartnerMsg").value = "";
    document.getElementById("addpartnerName").value = "";
    document.getElementById("contactPerson").value = "";
    document.getElementById("contactPersonLN").value = "";
    document.getElementById("contactEmail").value = "";
    document.getElementById("addphoneNo").value = "";
    document.getElementById("addaddress1").value = "";
    document.getElementById("addcity").value = "";
    document.getElementById("addstate").value = "";
    document.getElementById("addcountry").value = "-1";
    document.getElementById("addzipCode").value = "";
    document.getElementById("url").value = "";
    if (flag == 'admin') {
        document.getElementById("adminUsersList").value = "-1";
    }
    document.getElementById("description").value = "";
    document.getElementById("correctImg").style.display = "none";
    document.getElementById("wrongImg").style.display = "none";
    document.getElementById("correctImg1").style.display = "none";
    document.getElementById("wrongImg1").style.display = "none";
}
function resetRegisterUser() {
    document.getElementById("regcontactName").value = "";
    document.getElementById("regcontactLName").value = "";
    document.getElementById("regaddress").value = "";
    document.getElementById("regcity").value = "";
    document.getElementById("regstate").value = "";
    document.getElementById("regzipCode").value = "";
    document.getElementById("regcontactEmail").value = "";
    document.getElementById("regcountry").value = "-1";
    document.getElementById("regphoneNo").value = "";
}

function addPtnerLengthValidator(element) {
    var i = 0;
    var k = 0;
    if (element.value != null && (element.value != "")) {
        if (element.id == 'addpartnerName' || element.id == 'contactPerson' || element.id == 'contactPersonLN' || element.id == 'contactEmail' || element.id == 'addphoneNo' || element.id == 'addaddress1' || element.id == 'addcity' || element.id == 'addstate' || element.id == 'url' || element.id == 'addzipCode') {
            i = 50;
        }
        if (element.id == 'description') {
            i = 250;
        }
        if (element.id == 'contactPassword') {
            i = 10;
        }
        if (element.id == 'addzipCode') {
            i = 10;
            validatenumber(element);
        }
        if (element.id == 'addphoneNo') {
            k = 1;
            i = 15;
            validatenumber(element);
            generalFormatPhone(element);
        }
        if (k == 1) {
            if (element.value.replace(/^\s+|\s+$/g, "").length <= 9) {
                str = new String(element.value);
                element.value = str.substring(0, i);
                document.getElementById('addpartnerMsg').innerHTML = "<font color=red>Phone number must be 10 characters</font>";
                element.value = "";
                element.focus();
                return false;
            }
            if (element.value.replace(/^\s+|\s+$/g, "").length > i) {
                str = new String(element.value);
                element.value = str.substring(0, i);
                document.getElementById('addpartnerMsg').innerHTML = "<font color=red>The value must be less than " + i + " characters</font>";
                element.focus();
                return false;
            }
        } else {
            if (element.value.replace(/^\s+|\s+$/g, "").length > i) {
                str = new String(element.value);
                element.value = str.substring(0, i);
                document.getElementById('addpartnerMsg').innerHTML = "<font color=red>The value must be less than " + i + " characters</font>";
                element.focus();
                return false;
            }
        }
        return true;
    }
}

function usrCreationValidation() {
    var regcontactName = document.getElementById("regcontactName").value;
    var regcontactLName = document.getElementById("regcontactLName").value;
    var regcontactEmail = document.getElementById("regcontactEmail").value;
    var regaddress = document.getElementById("regaddress").value;
    var regcity = document.getElementById("regcity").value;
    var regstate = document.getElementById("regstate").value;
    var regcountry = document.getElementById("regcountry").value;
    var regphoneNo = document.getElementById("regphoneNo").value;
    var regzipCode = document.getElementById("regzipCode").value;

    if (regcontactName == "") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please enter first name.</font>";
        return false;
    } else if (regcontactLName == "") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please enter last name.</font>";
        return false;
    } else if (regcontactEmail == "") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please enter email.</font>";
        return false;
    } else if (regaddress == "") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please enter address.</font>";
        return false;
    } else if (regcity == "") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please enter city.</font>";
        return false;
    } else if (regstate == "") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please enter state.</font>";
        return false;
    } else if (regstate == "") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please enter state.</font>";
        return false;
    } else if (regcountry == "-1") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please select country.</font>";
        return false;
    } else if (regzipCode == "") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please enter zip code.</font>";
        return false;
    }
    return true;
}

function partnerUserAddValidation() {
    var contactName = document.getElementById("contactName").value;
    var contactLastName = document.getElementById("contactLastName").value;
    var contactEmail = document.getElementById("contactEmail").value;
    var phoneNo = document.getElementById("phoneNo").value;
    var address1 = document.getElementById("address1").value;
    var city = document.getElementById("city").value;
    var state = document.getElementById("state").value;
    var country = document.getElementById("country").value;
    var zipCode = document.getElementById("zipCode").value;
    //contactName contactLastName contactEmail regpassword phoneNo address1 city state country zipCode
    if (contactName == "") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please enter first name.</font>";
        return false;
    } else if (contactLastName == "") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please enter last name.</font>";
        return false;
    } else if (contactEmail == "") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please enter email.</font>";
        return false;
    } else if (phoneNo == "") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please enter phone number.</font>";
        return false;
    } else if (address1 == "") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please enter address.</font>";
        return false;
    } else if (city == "") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please enter city.</font>";
        return false;
    } else if (state == "") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please enter state.</font>";
        return false;
    } else if (country == "-1") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please select country.</font>";
        return false;
    } else if (zipCode == "") {
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Please enter ZIP code.</font>";
        return false;
    }
    return true;
}

function resetPartnerUserAdd() {
    document.getElementById("contactName").value = "";
    document.getElementById("contactLastName").value = "";
    document.getElementById("contactEmail").value = "";
    document.getElementById("phoneNo").value = "";
    document.getElementById("address1").value = "";
    document.getElementById("city").value = "";
    document.getElementById("state").value = "";
    document.getElementById("country").value = "-1";
    document.getElementById("zipCode").value = "";
}

function rxFTPchange(x) {
    if (x == 'FTP' || x == '-1') {
        document.getElementById("ftp_ssl_req").checked = false;
        document.getElementById("ftp_ssl").style.display = 'none';
        $("#sslDiv2").hide();
    } else if (x == 'FTPS') {
        document.getElementById("ftp_ssl_req").checked = true;
        document.getElementById("ftp_ssl").style.display = 'block';
        $("#sslDiv2").show();
    }
}

function rxHTTPchange(x) {
    if (x == 'HTTP' || x == '-1') {
        document.getElementById("http_ssl_req").checked = false;
        document.getElementById("http_ssl").style.display = 'none';
        $("#sslDiv2").hide();
    } else if (x == 'HTTPS') {
        document.getElementById("http_ssl_req").checked = true;
        document.getElementById("http_ssl").style.display = 'block';
        $("#sslDiv2").show();
    }
}

function resetUserList() {
    document.getElementById("contactName").value = "";
    document.getElementById("country").value = "-1";
    document.getElementById("status").value = "-1";
}

function checkPayload() {
    var docType = document.getElementById("docType").value;
    if (docType == "-1" || docType == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please select Document type</font>";
        return false;
    }
    var direction = document.getElementById("direction").value;
    if (direction == "-1" || direction == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please select direction</font>";
        return false;
    }
    var conn_type = document.getElementById("conn_type").value;
    if (conn_type == "-1" || conn_type == null) {
        document.getElementById('resultMessage').innerHTML = "<font color=red>Please select connection type</font>";
        return false;
    } else if (conn_type == 'Communication_Protocol') {
        var protocol = document.getElementById("protocol").value;
        if (protocol == "-1" || protocol == null) {
            document.getElementById('resultMessage').innerHTML = "<font color=red>Please select protocol</font>";
            return false;
        } else if (protocol != "-1") {
            var communicationId = document.forms["payloadForm"]["communicationId"].value;
            if (communicationId == "") {
                document.getElementById('CommMsg').innerHTML = "<font color=red>Please select Communication Id</font>";
                return false;
            }
        }
    }
    var transaction = document.getElementById('transaction').value;
    if (direction == "Inbound") {
        if (transaction == "850") {
            var ib850 = document.getElementsByName("upload850ib").length;
            if (ib850 == 0) {
                document.getElementById('CommMsg').innerHTML = "<font color=red>Please Upload atleast one file</font>";
                return false;
            }
            else
                {
                    var i;
                    for(i=0;i<=ib850;i++){
                    var file= document.getElementsByName("upload850ib")[i].value;
                    if(file==""){
                        document.getElementById('resultMsg').innerHTML = "<font color=red>Please Upload file"+(i+1)+"</font>";
                    return false;
                }
                
                }
              return false;
                }
        }
        if (transaction == "855") {
            var ib855 = document.getElementsByName("upload855ib").length;
            if (ib855 == 0) {
                document.getElementById('CommMsg').innerHTML = "<font color=red>Please Upload atleast one file</font>";
                return false;
            }
            else
                {
                    var i;
                    for(i=0;i<=ib855;i++){
                    var file= document.getElementsByName("upload855ib")[i].value;
                    if(file==""){
                         document.getElementById('resultMsg').innerHTML = "<font color=red>Please Upload file"+(i+1)+"</font>";
                    return false;
                }
                }
              
                }
        }
        if (transaction == "856") {
            var ib856 = document.getElementsByName("upload856ib").length;
            if (ib856 == 0) {
                document.getElementById('CommMsg').innerHTML = "<font color=red>Please Upload atleast one file</font>";
                return false;
            }
            else
                {
                    var i;
                    for(i=0;i<=ib856;i++){
                    var file= document.getElementsByName("upload856ib")[i].value;
                    if(file==""){
                        document.getElementById('resultMsg').innerHTML = "<font color=red>Please Upload file"+(i+1)+"</font>";
                    return false;
                }
                }
              
                }
        }
        if (transaction == "810") {
            var ib810 = document.getElementsByName("upload810ib").length;
            if (ib810 == 0) {
                document.getElementById('CommMsg').innerHTML = "<font color=red>Please Upload atleast one file</font>";
                return false;
            }
            else
                {
                    var i;
                    for(i=0;i<=ib810;i++){
                    var file= document.getElementsByName("upload810ib")[i].value;
                    if(file==""){
                        document.getElementById('resultMsg').innerHTML = "<font color=red>Please Upload file"+(i+1)+"</font>";
                    return false;
                }
                }
              
                }
        }
    }
    if (direction == "Outbound") {
        if (transaction == "850") {
            var ob850 = document.getElementsByName("upload850ob").length;
            if (ob850 == 0) {
                document.getElementById('CommMsg').innerHTML = "<font color=red>Please Upload atleast one file</font>";
                return false;
            }
            else
                {
                    var i;
                    for(i=0;i<=ob850;i++){
                    var file= document.getElementsByName("upload850ob")[i].value;
                    if(file==""){
                        document.getElementById('resultMsg').innerHTML = "<font color=red>Please Upload file"+(i+1)+"</font>";
                    return false;
                }
                }
              
                }
        }
        if (transaction == "855") {
            var ob855 = document.getElementsByName("upload855ob").length;
            if (ob855 == 0) {
                document.getElementById('CommMsg').innerHTML = "<font color=red>Please Upload atleast one file</font>";
                return false;
            }
            else
                {
                    var i;
                    for(i=0;i<=ob855;i++){
                    var file= document.getElementsByName("upload855ob")[i].value;
                    if(file==""){
                         document.getElementById('resultMsg').innerHTML = "<font color=red>Please Upload file"+(i+1)+"</font>";
                    return false;
                }
                }
              
                }
        }
        if (transaction == "856") {
            var ob856 = document.getElementsByName("upload856ob").length;
            if (ob856 == 0) {
                document.getElementById('CommMsg').innerHTML = "<font color=red>Please Upload atleast one file</font>";
                return false;
            }
            else
                {
                    var i;
                    for(i=0;i<=ob856;i++){
                    var file= document.getElementsByName("upload856ob")[i].value;
                    if(file==""){
                         document.getElementById('resultMsg').innerHTML = "<font color=red>Please Upload file"+(i+1)+"</font>";
                    return false;
                }
                }
              
                }
        }
        if (transaction == "810") {
            var ob810 = document.getElementsByName("upload810ob").length;
            if (ob810 == 0) {
                document.getElementById('CommMsg').innerHTML = "<font color=red>Please Upload atleast one file</font>";
                return false;
            }
            else
                {
                    var i;
                    for(i=0;i<=ob810;i++){
                    var file= document.getElementsByName("upload810ob")[i].value;
                    if(file==""){
                          document.getElementById('resultMsg').innerHTML = "<font color=red>Please Upload file"+(i+1)+"</font>";
                    return false;
                }
                }
              
                }
        }
    }
}

function getCommunicationsList(protocol) {
    var docType = document.getElementById('docType').value;
    var direction = document.getElementById('direction').value;
    var transaction = document.getElementById('transaction').value;
    var conn_type = document.getElementById('conn_type').value;
    if (direction == 'Inbound') {
        var ibTransaction = document.forms["payloadForm"]["ibTransaction"].value;
        window.location = "../payload/getCommunicationsList.action?docType=" + docType + "&direction=" + direction + "&ibTransaction=" + ibTransaction + "&conn_type=" + conn_type + "&protocol=" + protocol;
    }
    if (direction == 'Outbound') {
        var obTransaction = document.forms["payloadForm"]["obTransaction"].value;
        window.location = "../payload/getCommunicationsList.action?docType=" + docType + "&direction=" + direction + "&obTransaction=" + obTransaction + "&conn_type=" + conn_type + "&protocol=" + protocol;
    }

}
