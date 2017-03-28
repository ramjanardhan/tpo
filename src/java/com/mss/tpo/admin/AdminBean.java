/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mss.tpo.admin;

import java.sql.Timestamp;

/**
 *
 * @author Narendar
 */
public class AdminBean {
     private int id;
    private String created_by;
    private Timestamp created_ts;
     private String loginId;
    private String contactName;
      private int partnerId;
     private String status;
    private String commnProtocol;
    private String transferMode;
    private String ftp_conn_method;
    private String ftp_method;
    private String ftp_recv_protocol;
    private int ftp_resp_time;
    private String ftp_host;
    private String ftp_port;
    private String ftp_userId;
    private String ftp_pwd;
    private String ftp_directory;
    private boolean ftp_ssl_req;
    private String sftp_conn_method;
    private String sftp_auth_method;
    private String sftp_public_key;
    private String sftp_upload_public_key;
    private String sftp_host_ip;
    private String sftp_remote_port;
    private String sftp_remote_userId;
    private String sftp_remote_pwd;
    private String sftp_method;
    private String sftp_directory;
    private String http_recv_protocol;
    private int http_resp_time;
    private String http_endpoint;
    private int http_port;
    private String http_protocol_mode;
    private String http_url;
    private String http_ssl_req;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public Timestamp getCreated_ts() {
        return created_ts;
    }

    public void setCreated_ts(Timestamp created_ts) {
        this.created_ts = created_ts;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommnProtocol() {
        return commnProtocol;
    }

    public void setCommnProtocol(String commnProtocol) {
        this.commnProtocol = commnProtocol;
    }

    public String getTransferMode() {
        return transferMode;
    }

    public void setTransferMode(String transferMode) {
        this.transferMode = transferMode;
    }

    public String getFtp_conn_method() {
        return ftp_conn_method;
    }

    public void setFtp_conn_method(String ftp_conn_method) {
        this.ftp_conn_method = ftp_conn_method;
    }

    public String getFtp_method() {
        return ftp_method;
    }

    public void setFtp_method(String ftp_method) {
        this.ftp_method = ftp_method;
    }

    public String getFtp_recv_protocol() {
        return ftp_recv_protocol;
    }

    public void setFtp_recv_protocol(String ftp_recv_protocol) {
        this.ftp_recv_protocol = ftp_recv_protocol;
    }

    public int getFtp_resp_time() {
        return ftp_resp_time;
    }

    public void setFtp_resp_time(int ftp_resp_time) {
        this.ftp_resp_time = ftp_resp_time;
    }

    public String getFtp_host() {
        return ftp_host;
    }

    public void setFtp_host(String ftp_host) {
        this.ftp_host = ftp_host;
    }

    public String getFtp_port() {
        return ftp_port;
    }

    public void setFtp_port(String ftp_port) {
        this.ftp_port = ftp_port;
    }

    public String getFtp_userId() {
        return ftp_userId;
    }

    public void setFtp_userId(String ftp_userId) {
        this.ftp_userId = ftp_userId;
    }

    public String getFtp_pwd() {
        return ftp_pwd;
    }

    public void setFtp_pwd(String ftp_pwd) {
        this.ftp_pwd = ftp_pwd;
    }

    public String getFtp_directory() {
        return ftp_directory;
    }

    public void setFtp_directory(String ftp_directory) {
        this.ftp_directory = ftp_directory;
    }

    public boolean isFtp_ssl_req() {
        return ftp_ssl_req;
    }

    public void setFtp_ssl_req(boolean ftp_ssl_req) {
        this.ftp_ssl_req = ftp_ssl_req;
    }

    public String getSftp_conn_method() {
        return sftp_conn_method;
    }

    public void setSftp_conn_method(String sftp_conn_method) {
        this.sftp_conn_method = sftp_conn_method;
    }

    public String getSftp_auth_method() {
        return sftp_auth_method;
    }

    public void setSftp_auth_method(String sftp_auth_method) {
        this.sftp_auth_method = sftp_auth_method;
    }

    public String getSftp_public_key() {
        return sftp_public_key;
    }

    public void setSftp_public_key(String sftp_public_key) {
        this.sftp_public_key = sftp_public_key;
    }

    public String getSftp_upload_public_key() {
        return sftp_upload_public_key;
    }

    public void setSftp_upload_public_key(String sftp_upload_public_key) {
        this.sftp_upload_public_key = sftp_upload_public_key;
    }

    public String getSftp_host_ip() {
        return sftp_host_ip;
    }

    public void setSftp_host_ip(String sftp_host_ip) {
        this.sftp_host_ip = sftp_host_ip;
    }

    public String getSftp_remote_port() {
        return sftp_remote_port;
    }

    public void setSftp_remote_port(String sftp_remote_port) {
        this.sftp_remote_port = sftp_remote_port;
    }

    public String getSftp_remote_userId() {
        return sftp_remote_userId;
    }

    public void setSftp_remote_userId(String sftp_remote_userId) {
        this.sftp_remote_userId = sftp_remote_userId;
    }

    public String getSftp_remote_pwd() {
        return sftp_remote_pwd;
    }

    public void setSftp_remote_pwd(String sftp_remote_pwd) {
        this.sftp_remote_pwd = sftp_remote_pwd;
    }

    public String getSftp_method() {
        return sftp_method;
    }

    public void setSftp_method(String sftp_method) {
        this.sftp_method = sftp_method;
    }

    public String getSftp_directory() {
        return sftp_directory;
    }

    public void setSftp_directory(String sftp_directory) {
        this.sftp_directory = sftp_directory;
    }

    public String getHttp_recv_protocol() {
        return http_recv_protocol;
    }

    public void setHttp_recv_protocol(String http_recv_protocol) {
        this.http_recv_protocol = http_recv_protocol;
    }

    public int getHttp_resp_time() {
        return http_resp_time;
    }

    public void setHttp_resp_time(int http_resp_time) {
        this.http_resp_time = http_resp_time;
    }

    public String getHttp_endpoint() {
        return http_endpoint;
    }

    public void setHttp_endpoint(String http_endpoint) {
        this.http_endpoint = http_endpoint;
    }

    public int getHttp_port() {
        return http_port;
    }

    public void setHttp_port(int http_port) {
        this.http_port = http_port;
    }

    public String getHttp_protocol_mode() {
        return http_protocol_mode;
    }

    public void setHttp_protocol_mode(String http_protocol_mode) {
        this.http_protocol_mode = http_protocol_mode;
    }

    public String getHttp_url() {
        return http_url;
    }

    public void setHttp_url(String http_url) {
        this.http_url = http_url;
    }

    public String getHttp_ssl_req() {
        return http_ssl_req;
    }

    public void setHttp_ssl_req(String http_ssl_req) {
        this.http_ssl_req = http_ssl_req;
    }
    
    
}
