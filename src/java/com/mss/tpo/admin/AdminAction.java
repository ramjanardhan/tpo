/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.admin;

import com.mss.tpo.general.LoginAction;
import com.mss.tpo.util.AppConstants;
import com.mss.tpo.util.DataSourceDataProvider;
import com.mss.tpo.util.Properties;
import com.mss.tpo.util.ServiceLocator;
import com.mss.tpo.util.ServiceLocatorException;
import static com.opensymphony.xwork2.Action.LOGIN;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.json.annotations.JSON;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Narendar
 */
public class AdminAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    private static Logger logger = Logger.getLogger(LoginAction.class.getName());
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private String resultType;
    private String loginId;
    private int id;
    private int roleId;
    private int partnerId;
    private String partnerName;
    private String contactName;
    private String contactEmail;
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
    private Map map;
    private String formAction;
    private int CommunicationId;
    private String created_by;
    private String contentDisposition = "FileName=inline";
    public InputStream inputStream;
    public OutputStream outputStream;
    private String fileName;
    private String filepath;
    private String ssl_priority;
    private String ssl_cipher_stergth;
    private String certGroups;
    private File upload;
    private String uploadContentType;
    private String uploadFileName;
    private File upload1;
    private String upload1ContentType;
    private String upload1FileName;
    private List<AdminBean> tpoSearchProfileList;
    private ArrayList<AdminBean> tpoManageCommunicationList;
    private String protocol;
    private String manageCommunication;
    private int CommunicationMesId;
    private Map partnerNameList;
    private String certType;
    private String docdatepickerfrom;
    private String docdatepicker;
    private String reportrange;
    //Variables for CodeList functionality
    private String listName;
    private String name;
    private String selectedName;
    private List listNameMap;
     private String[] listName1;
     private String[] senderIdInst;                  
     private String[] recId;
     private String[] listVerson;
     private String[] senderItem;
     private String[] recItem;
     private String[] text1;
     private String[] text2;
     private String[] text3;
     private String[] text4;
     private String[] desc;
     private String[] text5;
     private String[] text6;
     private String[] text7;
     private String[] text8;
     private String[] text9;
    private String json;
  //  private String certType;
    private String daysCount;
    private String date;
     
   
    public String tpoAdminManageProfiles() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_SearchProfileList);
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                setCreated_by(loginId);
                tpoSearchProfileList = ServiceLocator.getAdminService().tpoAdminSearchProfile(loginId, roleId, partnerId, "initialFlag", this);
                setProtocolList(DataSourceDataProvider.getInstance().getCommunicationProtocols(roleId));
                httpServletRequest.getSession(false).setAttribute(AppConstants.TPO_SearchProfileList, tpoSearchProfileList);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String tpoAdminAddProfile() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_SearchProfileList);
                setProtocolList(DataSourceDataProvider.getInstance().getCommunicationProtocols(roleId));
                setFormAction("doAdminAddProfile");
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String doAdminAddProfile() throws Exception {
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
                String SFTPKeys[] = new String[]{"Connection Type", "Authentication Type", " SSH Public Key", "Remote Host IP Address ", "Remote Port ", "Remote UserId ", "Remote Password ", "Directory ", "Type"};
                String HTTPKeys[] = new String[]{"Receiving Protocol", "Response Timeout", "End Point", "Port", "Mode", "URL"};

                if ("FTP".equals(this.getCommnProtocol())) {
                    setTransferMode("pull");
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
                } else if ("SFTP".equals(this.getCommnProtocol())) {
                    setTransferMode("pull");
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
                    setTransferMode("push");
                    map = new HashMap();
                    map.put(HTTPKeys[0], getHttp_recv_protocol());
                    map.put(HTTPKeys[1], getHttp_resp_time());
                    map.put(HTTPKeys[2], getHttp_endpoint());
                    map.put(HTTPKeys[3], getHttp_port());
                    map.put(HTTPKeys[4], getHttp_protocol_mode());
                    map.put(HTTPKeys[5], getHttp_url());
                }
                setMap(map);

                if (getUploadFileName() != null && !getUploadFileName().equals(null) && getUploadFileName().length() > 1) {
                    saveFileToDisk(partnerId, loginId);
                }
                // if(isFtp_ssl_req()==true || "true".equalsIgnoreCase(getAs2_ssl_req()) || "true".equalsIgnoreCase(getHttp_ssl_req())){
                if (getUpload1FileName() != null && !getUpload1FileName().equals(null) && getUpload1FileName().length() > 1) {
                    saveFileToDisk1(partnerId, loginId);
                }
                String resultMessage = ServiceLocator.getAdminService().addAdminProfile(partnerId, partnerName, loginId, Email, this);
                //count = ServiceLocator.getTpOnboardingService().addTransaction(this);
                httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_RESULT_MSG, resultMessage);
                setFormAction("doAdminAddProfile");
                resultType = SUCCESS;
            } catch (Exception ex) {
                resultType = "error";
            }
        }
        return resultType;
    }

    public String tpoAdminSearchProfiles() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_SearchProfileList);
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                setCreated_by(loginId);
                setProtocolList(DataSourceDataProvider.getInstance().getCommunicationProtocols(roleId));
                tpoSearchProfileList = ServiceLocator.getAdminService().tpoAdminSearchProfile(loginId, roleId, partnerId, "searchFlag", this);
                httpServletRequest.getSession(false).setAttribute(AppConstants.TPO_SearchProfileList, tpoSearchProfileList);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String tpoAdminGetProfile() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int commnId = Integer.parseInt(httpServletRequest.getParameter("communicationId").toString());
                setCommunicationId(commnId);
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                setProtocolList(DataSourceDataProvider.getInstance().getCommunicationProtocols(roleId));
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                ServiceLocator.getAdminService().tpogetAdminProfile(getCommunicationId(), getCommnProtocol(), this);
                setFormAction("doAdminUpdateProfile");
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String doAdminUpdateProfile() {
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
                    setTransferMode("pull");
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
                } else if ("SFTP".equals(this.getCommnProtocol())) {
                    setTransferMode("pull");
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
                    setTransferMode("push");
                    map = new HashMap();
                    map.put(HTTPKeys[0], getHttp_recv_protocol());
                    map.put(HTTPKeys[1], getHttp_resp_time());
                    map.put(HTTPKeys[2], getHttp_endpoint());
                    map.put(HTTPKeys[3], getHttp_port());
                    map.put(HTTPKeys[4], getHttp_protocol_mode());
                    map.put(HTTPKeys[5], getHttp_url());
                }
                setMap(map);

                if (getUploadFileName() != null && !getUploadFileName().equals(null) && getUploadFileName().length() > 1) {
                    saveFileToDisk(partnerId, loginId);
                }
                if (getUpload1FileName() != null && !getUpload1FileName().equals(null) && getUpload1FileName().length() > 1) {
                    saveFileToDisk1(partnerId, loginId);
                }
                String resultMessage = ServiceLocator.getAdminService().tpoUpdateAdminProfile(partnerId, getCommunicationId(), getCommnProtocol(), Email, this);
                httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_RESULT_MSG, resultMessage);
                setFormAction("doAdminUpdateProfile");
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String tpoAdminDeleteProfile() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                setProtocolList(DataSourceDataProvider.getInstance().getCommunicationProtocols(roleId));
                if (("FTP".equals(this.getCommnProtocol())) || ("SFTP".equals(this.getCommnProtocol()))) {
                    setTransferMode("pull");
                }
                if ("HTTP".equals(this.getCommnProtocol())) {
                    setTransferMode("push");
                }
                String resultMessage = ServiceLocator.getAdminService().getDeleteAdminProfile(getCommunicationId(), getCommnProtocol(), partnerId, getTransferMode());
                httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_RESULT_MSG, resultMessage);
                resultType = SUCCESS;
            } catch (Exception ex) {
                ex.printStackTrace();
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

    // Manage Communication fro actions
    public String tpoAdminManageCommunication() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                setPartnerNameList(DataSourceDataProvider.getInstance().getMyPartnersList(loginId, roleId));
                setProtocolList(DataSourceDataProvider.getInstance().getCommunicationProtocols(roleId));
                setManageCommunication(getManageCommunication());
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_CommunicationsList);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String getManageCommunicationList() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                setPartnerNameList(DataSourceDataProvider.getInstance().getMyPartnersList(loginId, roleId));
                setProtocolList(DataSourceDataProvider.getInstance().getCommunicationProtocols(roleId));
                setManageCommunication(getManageCommunication());
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_CommunicationsList);
                if ("FTP".equalsIgnoreCase(getProtocol())) {
                    tpoManageCommunicationList = ServiceLocator.getAdminService().getFTPManageCommunicationsList(roleId, loginId, getPartnerId(), getManageCommunication());
                    httpServletRequest.getSession(false).setAttribute("protocol", "FTP");
                } else if ("SFTP".equalsIgnoreCase(getProtocol())) {
                    tpoManageCommunicationList = ServiceLocator.getAdminService().getSFTPManageCommunicationsList(roleId, loginId, getPartnerId(), getManageCommunication());
                    httpServletRequest.getSession(false).setAttribute("protocol", "SFTP");
                } else if ("HTTP".equalsIgnoreCase(getProtocol())) {
                    tpoManageCommunicationList = ServiceLocator.getAdminService().getHTTPManageCommunicationsList(roleId, loginId, getPartnerId(), getManageCommunication());
                    httpServletRequest.getSession(false).setAttribute("protocol", "HTTP");
                }
                httpServletRequest.getSession(false).setAttribute(AppConstants.TPO_CommunicationsList, tpoManageCommunicationList);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String doManageCommunicationAdd() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                String resultMessage = "";
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                setPartnerNameList(DataSourceDataProvider.getInstance().getMyPartnersList(loginId, roleId));
                setProtocolList(DataSourceDataProvider.getInstance().getCommunicationProtocols(roleId));
                setManageCommunication(getManageCommunication());
                //String communicationRemove[] = getCommunicationMesId();
                if (getManageCommunication().equalsIgnoreCase("Assign Communication")) {
                    resultMessage = ServiceLocator.getAdminService().doTpoManageCommunicationAdd(this);
                } else {
                    resultMessage = ServiceLocator.getAdminService().doTpoManageCommunicationRemove(getCommunicationMesId());
                }
                httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_RESULT_MSG, resultMessage);
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_CommunicationsList);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String tpoMonitorCertificate() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            resultType = SUCCESS;
        }
        return resultType;
    }

  
    public String getCertMonitor() throws Exception {
        String resultType = LOGIN;
        String days=null;
         days = getDaysCount();
        certType = getCertType();
        List list=null;
        System.out.println("days === "+days);
        if(getDaysCount()!=null){
        if(getDaysCount().equals("Other")){
             days = getDate();
        }
        }
              System.out.println("daysCount with specified days is  === "+days);
         list = ServiceLocator.getAdminService().getCertMonitorData(certType,days);
        
        httpServletRequest.getSession(false).setAttribute(AppConstants.CERTMONITOR_LIST, list);
        resultType = SUCCESS;
        return resultType;

    }


    public String tpoCodeList() throws ServiceLocatorException {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            httpServletRequest.getSession(false).removeAttribute(AppConstants.CODE_LIST);
            System.out.println("into action before getting codeList");
            setListNameMap(DataSourceDataProvider.getInstance().getListName());
            System.out.println("into action after getting codeList");
            resultType = SUCCESS;
        }
        return resultType;
    }

    public String getCodeListItems() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {

            try {
                setListNameMap(DataSourceDataProvider.getInstance().getListName());
                List codeList = new ArrayList();
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                codeList = ServiceLocator.getAdminService().doTpoCodeListItems(getListName());
                httpServletRequest.getSession(false).setAttribute(AppConstants.CODE_LIST, codeList);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }
    // method to get the code list according to the given name
      public String getCodeListName() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                 String resultMessage = "";
                List codeList = new ArrayList();
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                setListNameMap(ServiceLocator.getAdminService().getCodeListNames(getName()));
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }
      public String tpoCodeListAdd() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                 String resultMessage = "";
                List codeList = new ArrayList();


                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                 setListNameMap(DataSourceDataProvider.getInstance().getListName());
                resultMessage= ServiceLocator.getAdminService().addCodeList(getJson());
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
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

    public String getCommnProtocol() {
        return commnProtocol;
    }

    public void setCommnProtocol(String commnProtocol) {
        this.commnProtocol = commnProtocol;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
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

    public String getHttp_url() {
        return http_url;
    }

    public void setHttp_url(String http_url) {
        this.http_url = http_url;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Map getProtocolList() {
        return protocolList;
    }

    public void setProtocolList(Map protocolList) {
        this.protocolList = protocolList;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getManageCommunication() {
        return manageCommunication;
    }

    public void setManageCommunication(String manageCommunication) {
        this.manageCommunication = manageCommunication;
    }

    public int getCommunicationMesId() {
        return CommunicationMesId;
    }

    public void setCommunicationMesId(int CommunicationMesId) {
        this.CommunicationMesId = CommunicationMesId;
    }

    public Map getPartnerNameList() {
        return partnerNameList;
    }

    public void setPartnerNameList(Map partnerNameList) {
        this.partnerNameList = partnerNameList;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getDocdatepickerfrom() {
        return docdatepickerfrom;
    }

    public void setDocdatepickerfrom(String docdatepickerfrom) {
        this.docdatepickerfrom = docdatepickerfrom;
    }

    public String getDocdatepicker() {
        return docdatepicker;
    }

    public void setDocdatepicker(String docdatepicker) {
        this.docdatepicker = docdatepicker;
    }

    public String getReportrange() {
        return reportrange;
    }

    public void setReportrange(String reportrange) {
        this.reportrange = reportrange;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List getListNameMap() {
        return listNameMap;
    }

    public void setListNameMap(List listNameMap) {
        this.listNameMap = listNameMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelectedName() {
        return selectedName;
    }

    public void setSelectedName(String selectedName) {
        this.selectedName = selectedName;
    }

    public String[] getListName1() {
        return listName1;
    }

    public void setListName1(String[] listName1) {
        this.listName1 = listName1;
    }

    public String[] getSenderIdInst() {
        return senderIdInst;
    }

    public void setSenderIdInst(String[] senderIdInst) {
        this.senderIdInst = senderIdInst;
    }

    public String[] getRecId() {
        return recId;
    }

    public void setRecId(String[] recId) {
        this.recId = recId;
    }

    public String[] getListVerson() {
        return listVerson;
    }

    public void setListVerson(String[] listVerson) {
        this.listVerson = listVerson;
    }

    public String[] getSenderItem() {
        return senderItem;
    }

    public void setSenderItem(String[] senderItem) {
        this.senderItem = senderItem;
    }

    public String[] getRecItem() {
        return recItem;
    }

    public void setRecItem(String[] recItem) {
        this.recItem = recItem;
    }

    public String[] getText1() {
        return text1;
    }

    public void setText1(String[] text1) {
        this.text1 = text1;
    }

    public String[] getText2() {
        return text2;
    }

    public void setText2(String[] text2) {
        this.text2 = text2;
    }

    public String[] getText3() {
        return text3;
    }

    public void setText3(String[] text3) {
        this.text3 = text3;
    }

    public String[] getText4() {
        return text4;
    }

    public void setText4(String[] text4) {
        this.text4 = text4;
    }

    public String[] getDesc() {
        return desc;
    }

    public void setDesc(String[] desc) {
        this.desc = desc;
    }

    public String[] getText5() {
        return text5;
    }

    public void setText5(String[] text5) {
        this.text5 = text5;
    }

    public String[] getText6() {
        return text6;
    }

    public void setText6(String[] text6) {
        this.text6 = text6;
    }

    public String[] getText7() {
        return text7;
    }

    public void setText7(String[] text7) {
        this.text7 = text7;
    }

    public String[] getText8() {
        return text8;
    }

    public void setText8(String[] text8) {
        this.text8 = text8;
    }

    public String[] getText9() {
        return text9;
    }

    public void setText9(String[] text9) {
        this.text9 = text9;
    }

    public String getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(String daysCount) {
        this.daysCount = daysCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

           
}
