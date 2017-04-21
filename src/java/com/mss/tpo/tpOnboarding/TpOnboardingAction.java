/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.tpOnboarding;

import com.mss.tpo.general.LoginAction;
import com.mss.tpo.util.AppConstants;
import com.mss.tpo.util.DataSourceDataProvider;
import com.mss.tpo.util.Properties;
import com.mss.tpo.util.ServiceLocator;
import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author Narendar
 */
public class TpOnboardingAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(LoginAction.class.getName());
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private String resultType;
    private String loginId;
    private int id;
    private int roleId;
    private String partnerName;
    private String status;
    private Map protocolList;
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
    private String ftp_ssl_req;
    private String as2_sysCert;
    private String as2_part_cert;
    private String as2_myOrgName;
    private String as2_partOrgName;
    private String as2_myPartname;
    private String as2_yourPartname;
    private String as2_myEndPoint;
    private String as2_partendpoint;
    private String as2_strMsg;
    private String as2_waitForSync;
    private String as2_ssl_req;
    private String as2_payloadSendMode;
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
    private String http_resp_time;
    private String http_endpoint;
    private String http_port;
    private String http_protocol_mode;
    private String http_ssl_req;
    private String http_url;
    private String smtp_recv_protocol;
    private String smtp_server_protocol;
    private String smtp_server_port;
    private String smtp_from_email;
    private String smtp_to_email;
    private Map map;
    private boolean ib850;
    private boolean ib855;
    private boolean ib856;
    private boolean ib810;
    private boolean ob850;
    private boolean ob855;
    private boolean ob856;
    private boolean ob810;
    private String IB850Transaction;
    private String IB855Transaction;
    private String IB856Transaction;
    private String IB810Transaction;
    private String OB850Transaction;
    private String OB855Transaction;
    private String OB856Transaction;
    private String OB810Transaction;
    private String formAction;
    private int CommunicationId;
    private int regpartnerId;
    private String created_by;
    private TpOnboardingBean tpOnboardingBean;
    private int existFirst = 0;
    private String inputPath;
    private String contentDisposition = "FileName=inline";
    public InputStream inputStream;
    public OutputStream outputStream;
    private String fileName;
    private String filepath;
    private String ssl_priority;
    private String ssl_cipher_stergth;
    private String ssl_priority2;
    private String ssl_cipher_stergth2;
    private String certGroups;
    private File upload;
    private String uploadContentType;
    private String uploadFileName;
    private File upload1;
    private File upload2;
    private String upload1ContentType;
    private String upload1FileName;
    private Map partnerNameList;
    private Map adminUsersList;
    private Map partnerRolesList;
    private String transaction;
    private String direction;
    private List<TpOnboardingBean> tpoSearchProfileList;
    private ArrayList<TpOnboardingBean> tpoSearchEnvelopeList;

    public String execute() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID) != null) {
            int roleId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID).toString());
            int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
            String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
            String loginAccess = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ACCESS).toString();
            resultType = SUCCESS;
        }
        return resultType;
    }

    public String tpoManageProfiles() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_SearchProfileList);
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                setCreated_by(loginId);
                tpoSearchProfileList = ServiceLocator.getTpOnboardingService().tpoSearchProfile(loginId, roleId, partnerId, "initialFlag", this);
                setProtocolList(DataSourceDataProvider.getInstance().getCommunicationProtocols(roleId));
                httpServletRequest.getSession(false).setAttribute(AppConstants.TPO_SearchProfileList, tpoSearchProfileList);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String tpoAddProfile() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_SearchProfileList);
                setProtocolList(DataSourceDataProvider.getInstance().getCommunicationProtocols(roleId));
                setFormAction("doAddProfile");
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String doAddProfile() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String partnerName = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_NAME).toString();
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                String Email = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_EMAIL).toString();
                setCreated_by(httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString());
                setProtocolList(DataSourceDataProvider.getInstance().getCommunicationProtocols(roleId));
                String FTPKeys[] = new String[]{"Ftp Method", "Connection Method", "Receiving Protocol", "Response TimeOut", "Host", "Port ", "User ID ", "Password ", "Directory "};
                String AS2Keys[] = new String[]{"Upload Your System Certificate", "My Organization ", "Your Organization ", "My Partner Profle Name  ", "Your Partner Profle Name ", "My End Point ", "Your End Point", "AS2 Messages", "Synchronous MDN Process"};
                String SFTPKeys[] = new String[]{"Connection Type", "Authentication Type", " SSH Public Key", "Remote Host IP Address ", "Remote Port ", "Remote UserId ", "Remote Password ", "Directory ", "Type"};
                String HTTPKeys[] = new String[]{"Receiving Protocol", "Response Timeout", "End Point", "Port", "Mode", "URL"};
                String SMTPKeys[] = new String[]{"Receiving Protocol", "Server Host ", "Server Port", "From email address", "To email address"};

                if ("FTP".equals(this.getCommnProtocol())) {
                    map = new HashMap();
                    map.put(FTPKeys[0], getFtp_method());
                    map.put(FTPKeys[1], getFtp_conn_method());
                    map.put(FTPKeys[2], getFtp_recv_protocol());
                    map.put(FTPKeys[3], Integer.toString(getFtp_resp_time()));
                    map.put(FTPKeys[4], getFtp_host());
                    map.put(FTPKeys[5], getFtp_port());
                    map.put(FTPKeys[6], getFtp_userId());
                    map.put(FTPKeys[7], getFtp_pwd());
                    map.put(FTPKeys[8], getFtp_directory());
                } else if ("AS2".equals(this.getCommnProtocol())) {
                    map = new HashMap();
                    map.put(AS2Keys[0], getAs2_part_cert());
                    map.put(AS2Keys[1], getAs2_myOrgName());
                    map.put(AS2Keys[2], getAs2_partOrgName());
                    map.put(AS2Keys[3], getAs2_myPartname());
                    map.put(AS2Keys[4], getAs2_yourPartname());
                    map.put(AS2Keys[5], getAs2_myEndPoint());
                    map.put(AS2Keys[6], getAs2_partendpoint());
                    map.put(AS2Keys[7], getAs2_strMsg());
                    map.put(AS2Keys[8], getAs2_waitForSync());
                } else if ("SFTP".equals(this.getCommnProtocol())) {
                    map = new HashMap();
                    map.put(SFTPKeys[0], getSftp_conn_method());
                    map.put(SFTPKeys[1], getSftp_auth_method());
                    map.put(SFTPKeys[2], getSftp_public_key());
                    map.put(SFTPKeys[3], getSftp_host_ip());
                    map.put(SFTPKeys[4], getSftp_remote_port());
                    map.put(SFTPKeys[5], getSftp_remote_userId());
                    map.put(SFTPKeys[6], getSftp_remote_pwd());
                    map.put(SFTPKeys[7], getSftp_directory());
                    map.put(SFTPKeys[8], getSftp_method());
                } else if ("HTTP".equals(this.getCommnProtocol())) {
                    map = new HashMap();
                    map.put(HTTPKeys[0], getHttp_recv_protocol());
                    map.put(HTTPKeys[1], getHttp_resp_time());
                    map.put(HTTPKeys[2], getHttp_endpoint());
                    map.put(HTTPKeys[3], getHttp_port());
                    map.put(HTTPKeys[4], getHttp_protocol_mode());
                    map.put(HTTPKeys[5], getHttp_url());
                } else if ("SMTP".equals(this.getCommnProtocol())) {
                    map = new HashMap();
                    map.put(SMTPKeys[0], getSmtp_recv_protocol());
                    map.put(SMTPKeys[1], getSmtp_server_protocol());
                    map.put(SMTPKeys[2], getSmtp_server_port());
                    map.put(SMTPKeys[3], getSmtp_from_email());
                    map.put(SMTPKeys[4], getSmtp_to_email());
                }
                setMap(map);

                if (getUploadFileName() != null && !getUploadFileName().equals(null) && getUploadFileName().length() > 1) {
                    saveFileToDisk(partnerId, loginId);
                }
                // if(isFtp_ssl_req()==true || "true".equalsIgnoreCase(getAs2_ssl_req()) || "true".equalsIgnoreCase(getHttp_ssl_req())){
                if (getUpload1FileName() != null && !getUpload1FileName().equals(null) && getUpload1FileName().length() > 1) {
                    saveFileToDisk1(partnerId, loginId);
                }
                String resultMessage = ServiceLocator.getTpOnboardingService().addTpoProfile(partnerId, partnerName, loginId, Email, this);
                //count = ServiceLocator.getTpOnboardingService().addTransaction(this);
                httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_RESULT_MSG, resultMessage);
                setFormAction("doAddProfile");
                resultType = SUCCESS;
            } catch (Exception ex) {
                resultType = "error";
            }
        }
        return resultType;
    }

    public String tpoSearchProfiles() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_SearchProfileList);
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                setCreated_by(loginId);
                setProtocolList(DataSourceDataProvider.getInstance().getCommunicationProtocols(roleId));
                tpoSearchProfileList = ServiceLocator.getTpOnboardingService().tpoSearchProfile(loginId, roleId, partnerId, "searchFlag", this);
                httpServletRequest.getSession(false).setAttribute(AppConstants.TPO_SearchProfileList, tpoSearchProfileList);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String tpogetProfile() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int commnId = Integer.parseInt(httpServletRequest.getParameter("communicationId").toString());
                setCommunicationId(commnId);
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                setProtocolList(DataSourceDataProvider.getInstance().getCommunicationProtocols(roleId));
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                ServiceLocator.getTpOnboardingService().tpogetProfile(getCommunicationId(), getCommnProtocol(), this);
                setFormAction("doUpdateProfile");
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String doUpdateProfile() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String partnerName = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_NAME).toString();
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                String Email = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_EMAIL).toString();
                setCreated_by(loginId);
                setProtocolList(DataSourceDataProvider.getInstance().getCommunicationProtocols(roleId));
                String FTPKeys[] = new String[]{"Ftp Method", "Connection Method", "Receiving Protocol", "Response TimeOut", "Host", "Port ", "User ID ", "Password ", "Directory "};
                String AS2Keys[] = new String[]{"Upload Your System Certificate", "My Organization ", "Your Organization ", "My Partner Profle Name  ", "Your Partner Profle Name ", "My End Point ", "Your End Point", "AS2 Messages", "Synchronous MDN Process"};
                String SFTPKeys[] = new String[]{"Connection Type", "Authentication Type", " SSH Public Key", "Remote Host IP Address ", "Remote Port ", "Remote UserId ", "Remote Password ", "Directory ", "Type"};
                String HTTPKeys[] = new String[]{"Receiving Protocol", "Response Timeout", "End Point", "Port", "Mode", "URL"};
                String SMTPKeys[] = new String[]{"Receiving Protocol", "Server Host ", "Server Port", "From email address", "To email address"};

                if ("FTP".equals(this.getCommnProtocol())) {
                    map = new HashMap();
                    map.put(FTPKeys[0], getFtp_method());
                    map.put(FTPKeys[1], getFtp_conn_method());
                    map.put(FTPKeys[2], getFtp_recv_protocol());
                    map.put(FTPKeys[3], Integer.toString(getFtp_resp_time()));
                    map.put(FTPKeys[4], getFtp_host());
                    map.put(FTPKeys[5], getFtp_port());
                    map.put(FTPKeys[6], getFtp_userId());
                    map.put(FTPKeys[7], getFtp_pwd());
                    map.put(FTPKeys[8], getFtp_directory());
                } else if ("AS2".equals(this.getCommnProtocol())) {
                    map = new HashMap();
                    map.put(AS2Keys[0], getAs2_part_cert());
                    map.put(AS2Keys[1], getAs2_myOrgName());
                    map.put(AS2Keys[2], getAs2_partOrgName());
                    map.put(AS2Keys[3], getAs2_myPartname());
                    map.put(AS2Keys[4], getAs2_yourPartname());
                    map.put(AS2Keys[5], getAs2_myEndPoint());
                    map.put(AS2Keys[6], getAs2_partendpoint());
                    map.put(AS2Keys[7], getAs2_strMsg());
                    map.put(AS2Keys[8], getAs2_waitForSync());
                } else if ("SFTP".equals(this.getCommnProtocol())) {
                    map = new HashMap();
                    map.put(SFTPKeys[0], getSftp_conn_method());
                    map.put(SFTPKeys[1], getSftp_auth_method());
                    map.put(SFTPKeys[2], getSftp_public_key());
                    map.put(SFTPKeys[3], getSftp_host_ip());
                    map.put(SFTPKeys[4], getSftp_remote_port());
                    map.put(SFTPKeys[5], getSftp_remote_userId());
                    map.put(SFTPKeys[6], getSftp_remote_pwd());
                    map.put(SFTPKeys[7], getSftp_directory());
                    map.put(SFTPKeys[8], getSftp_method());
                } else if ("HTTP".equals(this.getCommnProtocol())) {
                    map = new HashMap();
                    map.put(HTTPKeys[0], getHttp_recv_protocol());
                    map.put(HTTPKeys[1], getHttp_resp_time());
                    map.put(HTTPKeys[2], getHttp_endpoint());
                    map.put(HTTPKeys[3], getHttp_port());
                    map.put(HTTPKeys[4], getHttp_protocol_mode());
                    map.put(HTTPKeys[5], getHttp_url());
                } else if ("SMTP".equals(this.getCommnProtocol())) {
                    map = new HashMap();
                    map.put(SMTPKeys[0], getSmtp_recv_protocol());
                    map.put(SMTPKeys[1], getSmtp_server_protocol());
                    map.put(SMTPKeys[2], getSmtp_server_port());
                    map.put(SMTPKeys[3], getSmtp_from_email());
                    map.put(SMTPKeys[4], getSmtp_to_email());
                }
                setMap(map);

                if (getUploadFileName() != null && !getUploadFileName().equals(null) && getUploadFileName().length() > 1) {
                    saveFileToDisk(partnerId, loginId);
                }
                // if(isFtp_ssl_req()==true || "true".equalsIgnoreCase(getAs2_ssl_req()) || "true".equalsIgnoreCase(getHttp_ssl_req())){
                if (getUpload1FileName() != null && !getUpload1FileName().equals(null) && getUpload1FileName().length() > 1) {
                    saveFileToDisk1(partnerId, loginId);
                }
                String resultMessage = ServiceLocator.getTpOnboardingService().tpoUpdateProfile(partnerId, getCommunicationId(), getCommnProtocol(), Email, this);
                httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_RESULT_MSG, resultMessage);
                setFormAction("doUpdateProfile");
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String tpoDeleteProfile() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                setProtocolList(DataSourceDataProvider.getInstance().getCommunicationProtocols(roleId));
                String resultMessage = ServiceLocator.getTpOnboardingService().getDeleteProfile(getCommunicationId(), getCommnProtocol(), partnerId, getTransferMode());
                httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_RESULT_MSG, resultMessage);
                resultType = SUCCESS;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return resultType;
    }

    public String getDeleteEnvelope() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String resultMessage = ServiceLocator.getTpOnboardingService().getDeleteEnvelope(getRegpartnerId(), getTransaction(), getDirection());
                httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_RESULT_MSG, resultMessage);
                resultType = SUCCESS;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return resultType;
    }

    public String tpoManageEnvelopes() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_SearchEnvelopeList);
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                setCreated_by(httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString());
                tpoSearchEnvelopeList = ServiceLocator.getTpOnboardingService().tpoSearchEnvelope(loginId, partnerId, "initialFlag", this);
                httpServletRequest.getSession(false).setAttribute(AppConstants.TPO_SearchEnvelopeList, tpoSearchEnvelopeList);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String tpoAddEnvelope() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String searchEnvelopes() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_SearchEnvelopeList);
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                setCreated_by(httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString());
                tpoSearchEnvelopeList = ServiceLocator.getTpOnboardingService().tpoSearchEnvelope(loginId, partnerId, "searchFlag", this);
                httpServletRequest.getSession(false).setAttribute(AppConstants.TPO_SearchEnvelopeList, tpoSearchEnvelopeList);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String doAddEnvelopes() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                setCreated_by(httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString());
                String resultMessage = ServiceLocator.getTpOnboardingService().addTpoEnvelope(partnerId, this);
                httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_RESULT_MSG, resultMessage);
                resultType = SUCCESS;
            } catch (Exception ex) {
                resultType = "error";
            }
        }
        return resultType;
    }

    public String tpoEditEnvelope() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int id = (Integer) httpServletRequest.getAttribute("id");
                System.out.println("envelope id::" + id);
                String direction = httpServletRequest.getParameter("direction");
                String transaction = httpServletRequest.getParameter("transaction");
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                setTpOnboardingBean(ServiceLocator.getTpOnboardingService().tpoEditEnvelope(id, partnerId, transaction, direction));
                resultType = SUCCESS;
            } catch (Exception ex) {
                resultType = "error";
            }
        }
        return resultType;
    }

    public void tpOnboardingDownloads() {
        String responseString = "";
        try {
            httpServletResponse.setContentType("application/force-download");
            File file = new File("C://TpOnboardingdetails//AS2Certificate//TPO_SYSTEM_CERT.cer");
            if (file.exists()) {
                fileName = file.getName();
                inputStream = new FileInputStream(file);
                outputStream = httpServletResponse.getOutputStream();
                httpServletResponse.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
                int noOfBytesRead = 0;
                byte[] byteArray = null;
                while (true) {
                    byteArray = new byte[1024];
                    noOfBytesRead = inputStream.read(byteArray);
                    if (noOfBytesRead == 0) {
                        break;
                    }
                    outputStream.write(byteArray, 0, noOfBytesRead);
                }
                responseString = "downLoaded!!";
                httpServletResponse.setContentType("text");
                httpServletResponse.getWriter().write(responseString);

            } else {
                throw new FileNotFoundException("File not found");
            }
        } catch (FileNotFoundException ex) {
            try {
                httpServletResponse.sendRedirect("../general/exception.action?exceptionMessage='No File found'");
            } catch (IOException ex1) {
                // Logger.getLogger(DownloadAction.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }/*catch (ServiceLocatorException ex) {
         ex.printStackTrace();
         }*/ finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean saveFileToDisk(int partnerId, String loginId) {
        boolean isSave = false;
        File createPath = new File(Properties.getProperty("mscvp.TponboardingFileUpload"));
        System.err.println("path:" + createPath);
        String partner_contactName = partnerId + "_" + loginId;
        /*getrequestType is used to create a directory of the object type specified in the jsp page*/
        createPath = new File(createPath.getAbsolutePath() + "//" + partner_contactName + "//" + getCommnProtocol());
        /*This creates a directory forcefully if the directory does not exsist*/
        createPath.mkdir();
        /*here it takes the absolute path and the name of the file that is to be uploaded*/
        File theFile = new File(createPath.getAbsolutePath() + "//" + getUploadFileName());
        setFilepath(theFile.toString());
        /*copies the file to the destination*/
        try {
            FileUtils.copyFile(upload, theFile);
            isSave = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return isSave;
    }

    public boolean saveFileToDisk1(int partnerId, String loginId) {
        boolean isSave = false;
        File createPath = new File(Properties.getProperty("mscvp.TponboardingFileUpload"));
        String partner_contactName = partnerId + "_" + loginId;
        /*getrequestType is used to create a directory of the object type specified in the jsp page*/
        createPath = new File(createPath.getAbsolutePath() + "//" + partner_contactName + "//" + getCommnProtocol() + "//" + "SSL");
        /*This creates a directory forcefully if the directory does not exsist*/
        createPath.mkdir();
        /*here it takes the absolute path and the name of the file that is to be uploaded*/
        File theFile = new File(createPath.getAbsolutePath() + "//" + getUpload1FileName());
        setCertGroups(theFile.toString());
        /*copies the file to the destination*/
        try {
            FileUtils.copyFile(upload1, theFile);
            isSave = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return isSave;
    }

    public void as2FileDownload() {
        String responseString = "";
        try {
            System.out.println("commId===>"+getId());
            String filePath = DataSourceDataProvider.getInstance().getAs2FilePath(getId());
            System.out.println("Action filePath::::"+filePath);
            httpServletResponse.setContentType("application/force-download");
            File file = new File(filePath);
            if (file.exists()) {
                fileName = file.getName();
                inputStream = new FileInputStream(file);
                outputStream = httpServletResponse.getOutputStream();
                httpServletResponse.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
                int noOfBytesRead = 0;
                byte[] byteArray = null;
                while (true) {
                    byteArray = new byte[1024];
                    noOfBytesRead = inputStream.read(byteArray);
                    if (noOfBytesRead == 0) {
                        break;
                    }
                    outputStream.write(byteArray, 0, noOfBytesRead);
                }
                responseString = "downLoaded!!";
                httpServletResponse.setContentType("text");
                httpServletResponse.getWriter().write(responseString);

            } else {
                throw new FileNotFoundException("File not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }/*catch (ServiceLocatorException ex) {
         ex.printStackTrace();
         }*/ finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getIB810Transaction() {
        return IB810Transaction;
    }

    public void setIB810Transaction(String IB810Transaction) {
        this.IB810Transaction = IB810Transaction;
    }

    public String getIB850Transaction() {
        return IB850Transaction;
    }

    public void setIB850Transaction(String IB850Transaction) {
        this.IB850Transaction = IB850Transaction;
    }

    public String getIB856Transaction() {
        return IB856Transaction;
    }

    public void setIB856Transaction(String IB856Transaction) {
        this.IB856Transaction = IB856Transaction;
    }

    public String getIB855Transaction() {
        return IB855Transaction;
    }

    public void setIB855Transaction(String IB855Transaction) {
        this.IB855Transaction = IB855Transaction;
    }

    public String getOB810Transaction() {
        return OB810Transaction;
    }

    public void setOB810Transaction(String OB810Transaction) {
        this.OB810Transaction = OB810Transaction;
    }

    public String getOB850Transaction() {
        return OB850Transaction;
    }

    public void setOB850Transaction(String OB850Transaction) {
        this.OB850Transaction = OB850Transaction;
    }

    public String getOB856Transaction() {
        return OB856Transaction;
    }

    public void setOB856Transaction(String OB856Transaction) {
        this.OB856Transaction = OB856Transaction;
    }

    public String getOB855Transaction() {
        return OB855Transaction;
    }

    public void setOB855Transaction(String OB855Transaction) {
        this.OB855Transaction = OB855Transaction;
    }

    public String getCommnProtocol() {
        return commnProtocol;
    }

    public void setCommnProtocol(String commnProtocol) {
        this.commnProtocol = commnProtocol;
    }

    public String getFtp_conn_method() {
        return ftp_conn_method;
    }

    public void setFtp_conn_method(String ftp_conn_method) {
        this.ftp_conn_method = ftp_conn_method;
    }

    public String getFtp_directory() {
        return ftp_directory;
    }

    public void setFtp_directory(String ftp_directory) {
        this.ftp_directory = ftp_directory;
    }

    public String getFtp_host() {
        return ftp_host;
    }

    public void setFtp_host(String ftp_host) {
        this.ftp_host = ftp_host;
    }

    public String getFtp_method() {
        return ftp_method;
    }

    public void setFtp_method(String ftp_method) {
        this.ftp_method = ftp_method;
    }

    public String getFtp_port() {
        return ftp_port;
    }

    public void setFtp_port(String ftp_port) {
        this.ftp_port = ftp_port;
    }

    public String getFtp_pwd() {
        return ftp_pwd;
    }

    public void setFtp_pwd(String ftp_pwd) {
        this.ftp_pwd = ftp_pwd;
    }

    public String getFtp_recv_protocol() {
        return ftp_recv_protocol;
    }

    public void setFtp_recv_protocol(String ftp_recv_protocol) {
        this.ftp_recv_protocol = ftp_recv_protocol;
    }

    public String getFtp_userId() {
        return ftp_userId;
    }

    public void setFtp_userId(String ftp_userId) {
        this.ftp_userId = ftp_userId;
    }

    public boolean isIb810() {
        return ib810;
    }

    public void setIb810(boolean ib810) {
        this.ib810 = ib810;
    }

    public boolean isIb850() {
        return ib850;
    }

    public void setIb850(boolean ib850) {
        this.ib850 = ib850;
    }

    public boolean isIb855() {
        return ib855;
    }

    public void setIb855(boolean ib855) {
        this.ib855 = ib855;
    }

    public boolean isIb856() {
        return ib856;
    }

    public void setIb856(boolean ib856) {
        this.ib856 = ib856;
    }

    public boolean isOb810() {
        return ob810;
    }

    public void setOb810(boolean ob810) {
        this.ob810 = ob810;
    }

    public boolean isOb850() {
        return ob850;
    }

    public void setOb850(boolean ob850) {
        this.ob850 = ob850;
    }

    public boolean isOb855() {
        return ob855;
    }

    public void setOb855(boolean ob855) {
        this.ob855 = ob855;
    }

    public boolean isOb856() {
        return ob856;
    }

    public void setOb856(boolean ob856) {
        this.ob856 = ob856;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getTransferMode() {
        return transferMode;
    }

    public void setTransferMode(String transferMode) {
        this.transferMode = transferMode;
    }

    public int getFtp_resp_time() {
        return ftp_resp_time;
    }

    public void setFtp_resp_time(int ftp_resp_time) {
        this.ftp_resp_time = ftp_resp_time;
    }

    public String getAs2_myEndPoint() {
        return as2_myEndPoint;
    }

    public void setAs2_myEndPoint(String as2_myEndPoint) {
        this.as2_myEndPoint = as2_myEndPoint;
    }

    public String getAs2_myOrgName() {
        return as2_myOrgName;
    }

    public void setAs2_myOrgName(String as2_myOrgName) {
        this.as2_myOrgName = as2_myOrgName;
    }

    public String getAs2_myPartname() {
        return as2_myPartname;
    }

    public void setAs2_myPartname(String as2_myPartname) {
        this.as2_myPartname = as2_myPartname;
    }

    public String getAs2_partOrgName() {
        return as2_partOrgName;
    }

    public void setAs2_partOrgName(String as2_partOrgName) {
        this.as2_partOrgName = as2_partOrgName;
    }

    public String getAs2_part_cert() {
        return as2_part_cert;
    }

    public void setAs2_part_cert(String as2_part_cert) {
        this.as2_part_cert = as2_part_cert;
    }

    public String getAs2_partendpoint() {
        return as2_partendpoint;
    }

    public void setAs2_partendpoint(String as2_partendpoint) {
        this.as2_partendpoint = as2_partendpoint;
    }

    public String getAs2_ssl_req() {
        return as2_ssl_req;
    }

    public void setAs2_ssl_req(String as2_ssl_req) {
        this.as2_ssl_req = as2_ssl_req;
    }

    public String getAs2_strMsg() {
        return as2_strMsg;
    }

    public void setAs2_strMsg(String as2_strMsg) {
        this.as2_strMsg = as2_strMsg;
    }

    public String getAs2_sysCert() {
        return as2_sysCert;
    }

    public void setAs2_sysCert(String as2_sysCert) {
        this.as2_sysCert = as2_sysCert;
    }

    public String getAs2_waitForSync() {
        return as2_waitForSync;
    }

    public void setAs2_waitForSync(String as2_waitForSync) {
        this.as2_waitForSync = as2_waitForSync;
    }

    public String getAs2_yourPartname() {
        return as2_yourPartname;
    }

    public void setAs2_yourPartname(String as2_yourPartname) {
        this.as2_yourPartname = as2_yourPartname;
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

    public String getHttp_endpoint() {
        return http_endpoint;
    }

    public void setHttp_endpoint(String http_endpoint) {
        this.http_endpoint = http_endpoint;
    }

    public String getHttp_protocol_mode() {
        return http_protocol_mode;
    }

    public void setHttp_protocol_mode(String http_protocol_mode) {
        this.http_protocol_mode = http_protocol_mode;
    }

    public String getHttp_recv_protocol() {
        return http_recv_protocol;
    }

    public void setHttp_recv_protocol(String http_recv_protocol) {
        this.http_recv_protocol = http_recv_protocol;
    }

    public String getHttp_ssl_req() {
        return http_ssl_req;
    }

    public void setHttp_ssl_req(String http_ssl_req) {
        this.http_ssl_req = http_ssl_req;
    }

    public String getSmtp_from_email() {
        return smtp_from_email;
    }

    public void setSmtp_from_email(String smtp_from_email) {
        this.smtp_from_email = smtp_from_email;
    }

    public String getSmtp_recv_protocol() {
        return smtp_recv_protocol;
    }

    public void setSmtp_recv_protocol(String smtp_recv_protocol) {
        this.smtp_recv_protocol = smtp_recv_protocol;
    }

    public String getSmtp_server_protocol() {
        return smtp_server_protocol;
    }

    public void setSmtp_server_protocol(String smtp_server_protocol) {
        this.smtp_server_protocol = smtp_server_protocol;
    }

    public String getSmtp_to_email() {
        return smtp_to_email;
    }

    public void setSmtp_to_email(String smtp_to_email) {
        this.smtp_to_email = smtp_to_email;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public TpOnboardingBean getTpOnboardingBean() {
        return tpOnboardingBean;
    }

    public void setTpOnboardingBean(TpOnboardingBean tpOnboardingBean) {
        this.tpOnboardingBean = tpOnboardingBean;
    }

    public int getExistFirst() {
        return existFirst;
    }

    public void setExistFirst(int existFirst) {
        this.existFirst = existFirst;
    }

    public String getHttp_port() {
        return http_port;
    }

    public void setHttp_port(String http_port) {
        this.http_port = http_port;
    }

    public String getHttp_resp_time() {
        return http_resp_time;
    }

    public void setHttp_resp_time(String http_resp_time) {
        this.http_resp_time = http_resp_time;
    }

    public String getSmtp_server_port() {
        return smtp_server_port;
    }

    public void setSmtp_server_port(String smtp_server_port) {
        this.smtp_server_port = smtp_server_port;
    }

    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getSsl_cipher_stergth() {
        return ssl_cipher_stergth;
    }

    public void setSsl_cipher_stergth(String ssl_cipher_stergth) {
        this.ssl_cipher_stergth = ssl_cipher_stergth;
    }

    public String getSsl_priority() {
        return ssl_priority;
    }

    public void setSsl_priority(String ssl_priority) {
        this.ssl_priority = ssl_priority;
    }

    public String getCertGroups() {
        return certGroups;
    }

    public void setCertGroups(String certGroups) {
        this.certGroups = certGroups;
    }

    public String getFtp_ssl_req() {
        return ftp_ssl_req;
    }

    public void setFtp_ssl_req(String ftp_ssl_req) {
        this.ftp_ssl_req = ftp_ssl_req;
    }

    public File getUpload1() {
        return upload1;
    }

    public void setUpload1(File upload1) {
        this.upload1 = upload1;
    }

    public String getUpload1ContentType() {
        return upload1ContentType;
    }

    public void setUpload1ContentType(String upload1ContentType) {
        this.upload1ContentType = upload1ContentType;
    }

    public String getUpload1FileName() {
        return upload1FileName;
    }

    public void setUpload1FileName(String upload1FileName) {
        this.upload1FileName = upload1FileName;
    }

    public String getSsl_cipher_stergth2() {
        return ssl_cipher_stergth2;
    }

    public void setSsl_cipher_stergth2(String ssl_cipher_stergth2) {
        this.ssl_cipher_stergth2 = ssl_cipher_stergth2;
    }

    public String getSsl_priority2() {
        return ssl_priority2;
    }

    public void setSsl_priority2(String ssl_priority2) {
        this.ssl_priority2 = ssl_priority2;
    }

    public String getHttp_url() {
        return http_url;
    }

    public void setHttp_url(String http_url) {
        this.http_url = http_url;
    }

    public String getAs2_payloadSendMode() {
        return as2_payloadSendMode;
    }

    public void setAs2_payloadSendMode(String as2_payloadSendMode) {
        this.as2_payloadSendMode = as2_payloadSendMode;
    }

    public Map getPartnerNameList() {
        return partnerNameList;
    }

    public void setPartnerNameList(Map partnerNameList) {
        this.partnerNameList = partnerNameList;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getFormAction() {
        return formAction;
    }

    public void setFormAction(String formAction) {
        this.formAction = formAction;
    }

    public int getCommunicationId() {
        return CommunicationId;
    }

    public void setCommunicationId(int CommunicationId) {
        this.CommunicationId = CommunicationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map getAdminUsersList() {
        return adminUsersList;
    }

    public void setAdminUsersList(Map adminUsersList) {
        this.adminUsersList = adminUsersList;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public File getUpload2() {
        return upload2;
    }

    public void setUpload2(File upload2) {
        this.upload2 = upload2;
    }

    public Map getPartnerRolesList() {
        return partnerRolesList;
    }

    public void setPartnerRolesList(Map partnerRolesList) {
        this.partnerRolesList = partnerRolesList;
    }

    public Map getProtocolList() {
        return protocolList;
    }

    public void setProtocolList(Map protocolList) {
        this.protocolList = protocolList;
    }

    public int getRegpartnerId() {
        return regpartnerId;
    }

    public void setRegpartnerId(int regpartnerId) {
        this.regpartnerId = regpartnerId;
    }

}
