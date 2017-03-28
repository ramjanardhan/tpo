/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function checkAdminProfile(flag) {
    var commnProtocol = document.getElementById('commnProtocol').value;
    if (commnProtocol == 'FTP') {
        ftpValidate = validateFTPForAdmin(flag);
        if (ftpValidate == false)
            return false;
    } else if (commnProtocol == 'SFTP') {
        sftpValidate = validateSFTPForAdmin(flag);
        if (sftpValidate == false)
            return false;
    } else if (commnProtocol == 'HTTP') {
        httpValidate = validateHTTPForAdmin(flag);
        if (httpValidate == false)
            return false;
    } else {
        document.getElementById('tpoCommMsg').innerHTML = "<font color=red>Please fill minimum one Profile</font>";
        return false;
    }
}

function protocolsSelectForAdmin(x) {
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
        document.getElementById("saveButton").style.display = 'none';
    } else {
        document.getElementById("responseString").style.display = 'none';
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
                document.getElementById("ssl_priority").value = "MUST";
                document.getElementById("ssl_cipher_stergth").value = "STRONG";
            }
            ftp_recv = document.getElementById("ftp_recv_protocol").value;
            if (ftp_recv == 'FTPS') {
                document.getElementById("ftp_ssl").style.display = 'block';
            } else {
                document.getElementById("ftp_ssl").style.display = 'none';
            }
        }
        if (protocol == 'SFTP') {
            $("#ftpDiv").hide();
            $("#sftpDiv").show();
            $("#httpDiv").hide();
            $("#smtpDiv").hide();
            $("#as2Div").hide();
            $("#sslDiv").hide();
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
        }
        if (protocol == 'HTTP') {
            $("#ftpDiv").hide();
            $("#sftpDiv").hide();
            $("#httpDiv").show();
            $("#smtpDiv").hide();
            $("#as2Div").hide();
            $("#sslDiv").hide();
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
                document.getElementById("ssl_priority").value = "MUST";
                document.getElementById("ssl_cipher_stergth").value = "STRONG";
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
        }
    }
}
function sslRequiredForAdmin(x) {
    if (x == 'ftp') {
        var ftp_ssl = document.getElementById("ftp_ssl_req").checked;
        if (ftp_ssl == true) {
            $("#sslDiv").show();
        } else {
            $("#sslDiv").hide();
        }
    }
    if (x == 'http') {
        var http_ssl = document.getElementById("http_ssl_req").checked;
        if (http_ssl == true) {
            $("#sslDiv").show();
        } else {
            $("#sslDiv").hide();
        }
    }

}

function sslPriorityChangeForAdmin(x) {
    if (x == 'NONE') {
        document.getElementById("ssl_passphrase").readOnly = true;
        $('#ssl_cipher_stergth').attr('disabled', true)
    } else {
        document.getElementById("ssl_passphrase").readOnly = false;
        $('#ssl_cipher_stergth').attr('disabled', false)
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

function validateEmail(thisObj) {
    var x = thisObj.value;
    var atpos = x.indexOf("@");
    var dotpos = x.lastIndexOf(".");
    if (atpos < 1 || dotpos < atpos + 2 || dotpos + 2 >= x.length) {
        thisObj.value = "";
        document.getElementById('tpResultMessage').innerHTML = "<font color=red>Not a valid e-mail address</font>";
    }
}

function validateFTPForAdmin(flag) {
    var ftp_method = document.getElementById("ftp_method").value;
    var ftp_conn_method = document.getElementById("ftp_conn_method").value;
    var ftp_recv_protocol = document.getElementById("ftp_recv_protocol").value;
    var ftp_resp_time = document.getElementById("ftp_resp_time").value;
    var ftp_host = document.getElementById("ftp_host").value;
    var ftp_port = document.getElementById("ftp_port").value;
    var ftp_userId = document.getElementById("ftp_userId").value;
    var ftp_pwd = document.getElementById("ftp_pwd").value;
    var ftp_directory = document.getElementById("ftp_directory").value;
    var ftp_ssl = document.getElementById("ftp_ssl_req").checked;
    document.getElementById('tpResultMessage').innerHTML = "";     //var commnProtocol =document.getElementById('commnProtocol').value;
    if (((ftp_method == null) || (ftp_method == "")) || ((ftp_conn_method == null) || (ftp_conn_method == "")) || ((ftp_recv_protocol == null) || (ftp_recv_protocol == "-1")) || ((ftp_resp_time == null) || (ftp_resp_time == "")) || ((ftp_host == null) || (ftp_host == "")) || ((ftp_port == null) || (ftp_port == "")) || ((ftp_userId == null) || (ftp_userId == "")) || ((ftp_pwd == null) || (ftp_pwd == "")) || ((ftp_directory == null) || (ftp_directory == ""))) {
        document.getElementById('protocolmsgFtp').innerHTML = "<font color=red>Please enter all mandatory fields</font>";
        return false;
    }
    if (ftp_ssl == true) {
        var fileName = document.getElementById("attachmentFileName").value;
        if (flag == 'add') {
            if (((fileName == null) || (fileName == ""))) {
                document.getElementById('protocolmsgSsl').innerHTML = "<font color=red>Please upload certificate in SSL</font>";
                return false;
            }
        }
    }
}

function validateSFTPForAdmin(flag) {
    var FileName = document.getElementById("attachmentFileNameSftp").value;
    var sftp_host_ip = document.getElementById("sftp_host_ip").value;
    var sftp_remote_port = document.getElementById("sftp_remote_port").value;
    var sftp_remote_userId = document.getElementById("sftp_remote_userId").value;
    var sftp_remote_pwd = document.getElementById("sftp_remote_pwd").value;
    var sftp_directory = document.getElementById("sftp_directory").value;
    document.getElementById('tpResultMessage').innerHTML = "";
    if (flag == 'add') {
        if (((FileName == null) || (FileName == ""))) {
            document.getElementById('protocolmsgSftp').innerHTML = "<font color=red>Please upload SSH public key</font>";
            return false;
        }
    }
    if (((sftp_host_ip == null) || (sftp_host_ip == "")) || ((sftp_remote_port == null) || (sftp_remote_port == "")) || ((sftp_remote_userId == null) || (sftp_remote_userId == "")) || ((sftp_remote_pwd == null) || (sftp_remote_pwd == "")) || ((sftp_directory == null) || (sftp_directory == ""))) {
        document.getElementById('protocolmsgSftp').innerHTML = "<font color=red>Please enter all mandatory fields</font>";
        return false;
    }
}

function validateHTTPForAdmin(flag) {
    var http_recv_protocol = document.getElementById("http_recv_protocol").value;
    var http_resp_time = document.getElementById("http_resp_time").value;
    var http_endpoint = document.getElementById("http_endpoint").value;
    var http_port = document.getElementById("http_port").value;
    var http_ssl_req = document.getElementById("http_ssl_req").checked;
    document.getElementById('tpResultMessage').innerHTML = "";
    if (((http_recv_protocol == null) || (http_recv_protocol == "-1")) || ((http_resp_time == null) || (http_resp_time == "")) || ((http_endpoint == null) || (http_endpoint == "")) || ((http_port == null) || (http_port == ""))) {
        document.getElementById('protocolmsgHttp').innerHTML = "<font color=red>Please enter all mandatory fields</font>";
        return false;
    }
    if (http_ssl_req == true) {
        var fileName = document.getElementById("attachmentFileName").value;
        if (flag == 'add') {
            if (((fileName == null) || (fileName == ""))) {
                document.getElementById('protocolmsgSsl').innerHTML = "<font color=red>Please upload certificate in SSL</font>";
                return false;
            }
        }
    }
}

function rxFTPchangeForAdmin(x) {
    if (x == 'FTP' || x == '-1') {
        document.getElementById("ftp_ssl_req").checked = false;
        document.getElementById("ftp_ssl").style.display = 'none';
        $("#sslDiv").hide();
    } else if (x == 'FTPS') {
        document.getElementById("ftp_ssl_req").checked = true;
        document.getElementById("ftp_ssl").style.display = 'block';
        $("#sslDiv").show();
    }
}

function rxHTTPchangeForAdmin(x) {
    if (x == 'HTTP' || x == '-1') {
        document.getElementById("http_ssl_req").checked = false;
        document.getElementById("http_ssl").style.display = 'none';
        $("#sslDiv").hide();
    } else if (x == 'HTTPS') {
        document.getElementById("http_ssl_req").checked = true;
        document.getElementById("http_ssl").style.display = 'block';
        $("#sslDiv").show();
    }
}

function getManageCommunication(x,a) {
    if(a != 'onload') {
        document.getElementById("partnerId").value="-1" ;   
        document.getElementById("protocol").value="-1" ; 
        $("#communicationsGrid").hide();
    }
    if (x == 'Remove Communication') {
        document.getElementById("connTypeDiv").style.display = 'block';
        document.getElementById("removeButton").style.display = 'block';
    }else{
        document.getElementById("removeButton").style.display = 'none';
    }
    if (x == 'Assign Communication') {
        document.getElementById("connTypeDiv").style.display = 'block';
        document.getElementById("saveButton").style.display = 'block';
    }else{
        document.getElementById("saveButton").style.display = 'none';
    }
}
function getProtocolList(protocol){
    var manageCommunication = document.forms["manageCommunicationForm"]["manageCommunication"].value;
    if(protocol!=-1){
         var partnerId=document.getElementById('partnerId').value;
        if(manageCommunication=='Remove Communication'){
            if(partnerId=="-1"){
                alert("Please select PartnerName");
                document.getElementById("protocol").value="-1";
                return false;
            }
        }
        window.location = "../admin/getManageCommunicationList.action?protocol="+protocol+"&manageCommunication="+manageCommunication+"&partnerId="+partnerId; 
    }
}

