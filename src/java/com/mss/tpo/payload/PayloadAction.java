/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.payload;

import com.mss.tpo.tpOnboarding.TpOnboardingBean;
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
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author Narendar
 */
public class PayloadAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private String resultType;
    private String loginId;
    private String password;
    private int id;
    private int roleId;
    private String created_by;
    private List txlist;
    private ArrayList<PayloadBean> payloadSearchList;
    private String docType;
    private String ibTransaction;
    private String obTransaction;
    private String transaction;
    private String direction;
    private String inputPath;
    private String outputPath;
    private String path;
    private String conn_type;
    private String formAction;
    private File upload;
    private String protocol;
    private Map protocolsList;
    private int communicationId;
    private List<File> upload850ib;
    private List<String> upload850ibFileName;
    private List<String> upload850ibContentType;
    private List<File> upload855ib;
    private List<String> upload855ibFileName;
    private List<String> upload855ibContentType;
    private List<File> upload856ib;
    private List<String> upload856ibFileName;
    private List<String> upload856ibContentType;
    private List<File> upload810ib;
    private List<String> upload810ibFileName;
    private List<String> upload810ibContentType;
    private List<File> upload850ob;
    private List<String> upload850obFileName;
    private List<String> upload850obContentType;
    private List<File> upload855ob;
    private List<String> upload855obFileName;
    private List<String> upload855obContentType;
    private List<File> upload856ob;
    private List<String> upload856obFileName;
    private List<String> upload856obContentType;
    private List<File> upload810ob;
    private List<String> upload810obFileName;
    private List<String> upload810obContentType;
    private List<TpOnboardingBean> tpoCommunicationsList;
    private List<String> tpoProtocolsHeadersList;
    private String filepath;
    public InputStream inputStream;
    public OutputStream outputStream;
    private String fileName;

    public String payloadUpload() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int roleId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID).toString());
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                setProtocolsList(DataSourceDataProvider.getInstance().getPayloadProtocols(loginId, roleId, partnerId));
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_ProtocolsHeadersList);
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_CommunicationsList);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String payloadHistory() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                httpServletRequest.getSession(false).removeAttribute(AppConstants.PAYLOAD_SEARCH_LIST);
                int roleId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID).toString());
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                setTxList(DataSourceDataProvider.getInstance().getTxList());
                payloadSearchList = ServiceLocator.getPayloadService().payloadSearch(loginId, roleId, partnerId, "initialFlag", this);
                httpServletRequest.getSession(false).setAttribute(AppConstants.PAYLOAD_SEARCH_LIST, payloadSearchList);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String payloadSearch() {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                httpServletRequest.getSession(false).removeAttribute(AppConstants.PAYLOAD_SEARCH_LIST);
                int roleId = Integer.parseInt(httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID).toString());
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                setTxList(DataSourceDataProvider.getInstance().getTxList());
                payloadSearchList = ServiceLocator.getPayloadService().payloadSearch(loginId, roleId, partnerId, "searchFlag", this);
                httpServletRequest.getSession(false).setAttribute(AppConstants.PAYLOAD_SEARCH_LIST, payloadSearchList);
                resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public String doPayloadUpload() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String partnerName = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_NAME).toString();
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                setCreated_by(httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString());
                String filePath = "";
                String resultMessage = "";
                String fileNames[] = null;
                int i = 0;
                if ("Inbound".equalsIgnoreCase(getDirection())) {
                    if ("850".equalsIgnoreCase(getTransaction())) {
                        if (getUpload850ibFileName() != null && !getUpload850ibFileName().equals(null) && getUpload850ibFileName().size() > 0) {
                            filePath = saveFileToDisk(partnerId, loginId);
                        }
                    } else if ("855".equalsIgnoreCase(getTransaction())) {
                        if (getUpload855ibFileName() != null && !getUpload855ibFileName().equals(null) && getUpload855ibFileName().size() > 0) {
                            filePath = saveFileToDisk(partnerId, loginId);
                        }
                    } else if ("856".equalsIgnoreCase(getTransaction())) {
                        if (getUpload856ibFileName() != null && !getUpload856ibFileName().equals(null) && getUpload856ibFileName().size() > 0) {
                            filePath = saveFileToDisk(partnerId, loginId);
                        }
                    } else if ("810".equalsIgnoreCase(getTransaction())) {
                        if (getUpload810ibFileName() != null && !getUpload810ibFileName().equals(null) && getUpload810ibFileName().size() > 0) {
                            filePath = saveFileToDisk(partnerId, loginId);
                        }
                    }
                } else if ("Outbound".equalsIgnoreCase(getDirection())) {
                    if ("850".equalsIgnoreCase(getTransaction())) {
                        if (getUpload850obFileName() != null && !getUpload850obFileName().equals(null) && getUpload850obFileName().size() > 0) {
                            filePath = saveFileToDisk(partnerId, loginId);
                            fileNames = new String[upload850ob.size()];
                            for (File file : upload850ob) {
                                fileNames[i] = upload850obFileName.get(i);
                                i++;
                            }
                        }
                    } else if ("855".equalsIgnoreCase(getTransaction())) {
                        if (getUpload855obFileName() != null && !getUpload855obFileName().equals(null) && getUpload855obFileName().size() > 0) {
                            filePath = saveFileToDisk(partnerId, loginId);
                            fileNames = new String[upload855ob.size()];
                            for (File file : upload855ob) {
                                fileNames[i] = upload855obFileName.get(i);
                                i++;
                            }
                        }
                    } else if ("856".equalsIgnoreCase(getTransaction())) {
                        if (getUpload856obFileName() != null && !getUpload856obFileName().equals(null) && getUpload856obFileName().size() > 0) {
                            filePath = saveFileToDisk(partnerId, loginId);
                            fileNames = new String[upload856ob.size()];
                            for (File file : upload856ob) {
                                fileNames[i] = upload856obFileName.get(i);
                                i++;
                            }
                        }
                    } else if ("810".equalsIgnoreCase(getTransaction())) {
                        if (getUpload810obFileName() != null && !getUpload810obFileName().equals(null) && getUpload810obFileName().size() > 0) {
                            filePath = saveFileToDisk(partnerId, loginId);
                            fileNames = new String[upload810ob.size()];
                            for (File file : upload810ob) {
                                fileNames[i] = upload810obFileName.get(i);
                                i++;
                            }
                        }
                    }
                }
                if ("Inbound".equalsIgnoreCase(getDirection())) {
                    resultMessage = ServiceLocator.getPayloadService().doPayloadUploadForInbound(partnerId, partnerName, loginId, filePath, this);
                } else if ("Outbound".equalsIgnoreCase(getDirection())) {
                    resultMessage = ServiceLocator.getPayloadService().doPayloadUploadForOutbound(partnerId, partnerName, loginId, filePath, this, fileNames);
                }
                httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_RESULT_MSG, resultMessage);
                resultType = SUCCESS;
            } catch (Exception ex) {
                resultType = "error";
            }
        }
        return resultType;
    }

    public String saveFileToDisk(int partnerId, String loginId) {
        File createPath = new File(Properties.getProperty("mscvp.TpoPayloadUpload"));
        try {
            //String partner_contactName = partnerId + "_" + loginId;
            String partnerName = DataSourceDataProvider.getInstance().getTpoPartnerName(partnerId);
            /*getrequestType is used to create a directory of the object type specified in the jsp page*/
            if ("Outbound".equalsIgnoreCase(getDirection())) {
                setConn_type("File_System");
            }
            if ("Inbound".equalsIgnoreCase(getDirection())) {
                setConn_type("Communication_Protocol");
            }
            if ("Communication_Protocol".equalsIgnoreCase(getConn_type())) {
                createPath = new File(createPath.getAbsolutePath() + "//" + partnerName + "//" + getDocType() + "//" + getDirection() + "//" + getTransaction() + "//" + getConn_type() + "//" + getProtocol());
            } else {
                createPath = new File(createPath.getAbsolutePath() + "//" + partnerName + "//" + getDocType() + "//" + getDirection() + "//" + getTransaction() + "//" + getConn_type());
            }
            /*This creates a directory forcefully if the directory does not exsist*/
            createPath.mkdir();
            /*here it takes the absolute path and the name of the file that is to be uploaded*/
            File theFile = null;
            int i = 0;
            if ("Inbound".equalsIgnoreCase(getDirection())) {
                if ("850".equalsIgnoreCase(getTransaction())) {
                    for (File file : upload850ib) {
                        theFile = new File(createPath.getAbsolutePath(), upload850ibFileName.get(i));
                        FileUtils.copyFile(file, theFile);
                        i++;
                    }
                    //  theFile = new File(createPath.getAbsolutePath() + "//" + getUpload850ibFileName());
                } else if ("855".equalsIgnoreCase(getTransaction())) {
                    for (File file : upload855ib) {
                        theFile = new File(createPath.getAbsolutePath(), upload855ibFileName.get(i));
                        FileUtils.copyFile(file, theFile);
                        i++;
                    }
                } else if ("856".equalsIgnoreCase(getTransaction())) {
                    for (File file : upload856ib) {
                        theFile = new File(createPath.getAbsolutePath(), upload856ibFileName.get(i));
                        FileUtils.copyFile(file, theFile);
                        i++;
                    }
                } else if ("810".equalsIgnoreCase(getTransaction())) {
                    for (File file : upload810ib) {
                        theFile = new File(createPath.getAbsolutePath(), upload810ibFileName.get(i));
                        FileUtils.copyFile(file, theFile);
                        i++;
                    }
                }
            } else if ("Outbound".equalsIgnoreCase(getDirection())) {
                if ("850".equalsIgnoreCase(getTransaction())) {
                    for (File file : upload850ob) {
                        theFile = new File(createPath.getAbsolutePath(), upload850obFileName.get(i));
                        FileUtils.copyFile(file, theFile);
                        i++;
                    }
                } else if ("855".equalsIgnoreCase(getTransaction())) {
                    for (File file : upload855ob) {
                        theFile = new File(createPath.getAbsolutePath(), upload855obFileName.get(i));
                        FileUtils.copyFile(file, theFile);
                        i++;
                    }
                } else if ("856".equalsIgnoreCase(getTransaction())) {
                    for (File file : upload856ob) {
                        theFile = new File(createPath.getAbsolutePath(), upload856obFileName.get(i));
                        FileUtils.copyFile(file, theFile);
                        i++;
                    }
                } else if ("810".equalsIgnoreCase(getTransaction())) {
                    for (File file : upload810ob) {
                        theFile = new File(createPath.getAbsolutePath(), upload810obFileName.get(i));
                        FileUtils.copyFile(file, theFile);
                        i++;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return createPath.getAbsolutePath().toString();
    }

    public String getCommunicationsList() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                int roleId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_ROLE_ID);
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                setProtocolsList(DataSourceDataProvider.getInstance().getPayloadProtocols(loginId, roleId, partnerId));
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_ProtocolsHeadersList);
                httpServletRequest.getSession(false).removeAttribute(AppConstants.TPO_CommunicationsList);
                tpoProtocolsHeadersList = new ArrayList<String>();
                if ("FTP".equalsIgnoreCase(getProtocol())) {
                    tpoProtocolsHeadersList.add("Protocol&nbsp;ID");
                    tpoProtocolsHeadersList.add("Partner&nbsp;ID");
                    tpoProtocolsHeadersList.add("Protocol");
                    tpoProtocolsHeadersList.add("Email");
                    tpoProtocolsHeadersList.add("Host");
                    tpoProtocolsHeadersList.add("Port");
                    tpoProtocolsHeadersList.add("UserID");
                    tpoProtocolsHeadersList.add("Password");
                    tpoProtocolsHeadersList.add("Directory");
                    tpoCommunicationsList = ServiceLocator.getPayloadService().getFTPCommunicationsList(loginId, roleId, partnerId, getProtocol());
                    httpServletRequest.getSession(false).setAttribute("protocol", "FTP");
                } else if ("SFTP".equalsIgnoreCase(getProtocol())) {
                    tpoProtocolsHeadersList.add("Protocol&nbsp;ID");
                    tpoProtocolsHeadersList.add("Partner&nbsp;ID");
                    tpoProtocolsHeadersList.add("Protocol");
                    tpoProtocolsHeadersList.add("Email");
                    tpoProtocolsHeadersList.add("Host");
                    tpoProtocolsHeadersList.add("Port");
                    tpoProtocolsHeadersList.add("UserID");
                    tpoProtocolsHeadersList.add("Password");
                    tpoProtocolsHeadersList.add("Directory");
                    tpoCommunicationsList = ServiceLocator.getPayloadService().getSFTPCommunicationsList(loginId, roleId, partnerId, getProtocol());
                    httpServletRequest.getSession(false).setAttribute("protocol", "SFTP");
                } else if ("HTTP".equalsIgnoreCase(getProtocol())) {
                    tpoProtocolsHeadersList.add("Protocol&nbsp;ID");
                    tpoProtocolsHeadersList.add("Partner&nbsp;ID");
                    tpoProtocolsHeadersList.add("Protocol");
                    tpoProtocolsHeadersList.add("END&nbsp;Point");
                    tpoProtocolsHeadersList.add("Port");
                    tpoProtocolsHeadersList.add("HTTP&nbsp;Mode");
                    tpoProtocolsHeadersList.add("URL");
                    tpoCommunicationsList = ServiceLocator.getPayloadService().getHTTPCommunicationsList(loginId, roleId, partnerId, getProtocol());
                    httpServletRequest.getSession(false).setAttribute("protocol", "HTTP");
                } else if ("SMTP".equalsIgnoreCase(getProtocol())) {
                    tpoProtocolsHeadersList.add("Protocol&nbsp;ID");
                    tpoProtocolsHeadersList.add("Partner&nbsp;ID");
                    tpoProtocolsHeadersList.add("Protocol");
                    tpoProtocolsHeadersList.add("Host");
                    tpoProtocolsHeadersList.add("Port");
                    tpoProtocolsHeadersList.add("Sender&nbsp;Email&nbsp;ID");
                    tpoProtocolsHeadersList.add("Receiver&nbsp;Email&nbsp;ID");
                    tpoCommunicationsList = ServiceLocator.getPayloadService().getSMTPCommunicationsList(loginId, roleId, partnerId, getProtocol());
                    httpServletRequest.getSession(false).setAttribute("protocol", "SMTP");
                } else if ("AS2".equalsIgnoreCase(getProtocol())) {
                    tpoProtocolsHeadersList.add("Protocol&nbsp;ID");
                    tpoProtocolsHeadersList.add("Partner&nbsp;ID");
                    tpoProtocolsHeadersList.add("Protocol");
                    tpoProtocolsHeadersList.add("My&nbsp;Organization");
                    tpoProtocolsHeadersList.add("Your&nbsp;Organization");
                    tpoProtocolsHeadersList.add("My&nbsp;Endpoint");
                    tpoProtocolsHeadersList.add("As2&nbsp;Messages&nbsp;Store");
                    tpoCommunicationsList = ServiceLocator.getPayloadService().getAS2CommunicationsList(loginId, roleId, partnerId, getProtocol());
                    httpServletRequest.getSession(false).setAttribute("protocol", "AS2");
                }
                httpServletRequest.getSession(false).setAttribute(AppConstants.TPO_ProtocolsHeadersList, tpoProtocolsHeadersList);
                httpServletRequest.getSession(false).setAttribute(AppConstants.TPO_CommunicationsList, tpoCommunicationsList);
                setFormAction("CommunicationList");
                        resultType = SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultType;
    }

    public void downloadPayloadFile() {
        String responseString = "";
        try {
            httpServletResponse.setContentType("application/force-download");
            File file = new File(getFilepath());
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

    public String reprocessPayloadData() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String resultMessage = ServiceLocator.getPayloadService().reprocessPayloadData(loginId, getInputPath(), getId(), getDirection(), getOutputPath(), getPath());
                httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_RESULT_MSG, resultMessage);
                resultType = SUCCESS;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return resultType;
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

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getConn_type() {
        return conn_type;
    }

    public void setConn_type(String conn_type) {
        this.conn_type = conn_type;
    }

    public List getTxList() {
        return txlist;
    }

    public void setTxList(List txlist) {
        this.txlist = txlist;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public List<File> getUpload850ib() {
        return upload850ib;
    }

    public void setUpload850ib(List<File> upload850ib) {
        this.upload850ib = upload850ib;
    }

    public List<String> getUpload850ibFileName() {
        return upload850ibFileName;
    }

    public void setUpload850ibFileName(List<String> upload850ibFileName) {
        this.upload850ibFileName = upload850ibFileName;
    }

    public List<String> getUpload850ibContentType() {
        return upload850ibContentType;
    }

    public void setUpload850ibContentType(List<String> upload850ibContentType) {
        this.upload850ibContentType = upload850ibContentType;
    }

    public List<File> getUpload855ib() {
        return upload855ib;
    }

    public void setUpload855ib(List<File> upload855ib) {
        this.upload855ib = upload855ib;
    }

    public List<String> getUpload855ibFileName() {
        return upload855ibFileName;
    }

    public void setUpload855ibFileName(List<String> upload855ibFileName) {
        this.upload855ibFileName = upload855ibFileName;
    }

    public List<String> getUpload855ibContentType() {
        return upload855ibContentType;
    }

    public void setUpload855ibContentType(List<String> upload855ibContentType) {
        this.upload855ibContentType = upload855ibContentType;
    }

    public List<File> getUpload856ib() {
        return upload856ib;
    }

    public void setUpload856ib(List<File> upload856ib) {
        this.upload856ib = upload856ib;
    }

    public List<String> getUpload856ibFileName() {
        return upload856ibFileName;
    }

    public void setUpload856ibFileName(List<String> upload856ibFileName) {
        this.upload856ibFileName = upload856ibFileName;
    }

    public List<String> getUpload856ibContentType() {
        return upload856ibContentType;
    }

    public void setUpload856ibContentType(List<String> upload856ibContentType) {
        this.upload856ibContentType = upload856ibContentType;
    }

    public List<File> getUpload810ib() {
        return upload810ib;
    }

    public void setUpload810ib(List<File> upload810ib) {
        this.upload810ib = upload810ib;
    }

    public List<String> getUpload810ibFileName() {
        return upload810ibFileName;
    }

    public void setUpload810ibFileName(List<String> upload810ibFileName) {
        this.upload810ibFileName = upload810ibFileName;
    }

    public List<String> getUpload810ibContentType() {
        return upload810ibContentType;
    }

    public void setUpload810ibContentType(List<String> upload810ibContentType) {
        this.upload810ibContentType = upload810ibContentType;
    }

    public List<File> getUpload850ob() {
        return upload850ob;
    }

    public void setUpload850ob(List<File> upload850ob) {
        this.upload850ob = upload850ob;
    }

    public List<String> getUpload850obFileName() {
        return upload850obFileName;
    }

    public void setUpload850obFileName(List<String> upload850obFileName) {
        this.upload850obFileName = upload850obFileName;
    }

    public List<String> getUpload850obContentType() {
        return upload850obContentType;
    }

    public void setUpload850obContentType(List<String> upload850obContentType) {
        this.upload850obContentType = upload850obContentType;
    }

    public List<File> getUpload855ob() {
        return upload855ob;
    }

    public void setUpload855ob(List<File> upload855ob) {
        this.upload855ob = upload855ob;
    }

    public List<String> getUpload855obFileName() {
        return upload855obFileName;
    }

    public void setUpload855obFileName(List<String> upload855obFileName) {
        this.upload855obFileName = upload855obFileName;
    }

    public List<String> getUpload855obContentType() {
        return upload855obContentType;
    }

    public void setUpload855obContentType(List<String> upload855obContentType) {
        this.upload855obContentType = upload855obContentType;
    }

    public List<File> getUpload856ob() {
        return upload856ob;
    }

    public void setUpload856ob(List<File> upload856ob) {
        this.upload856ob = upload856ob;
    }

    public List<String> getUpload856obFileName() {
        return upload856obFileName;
    }

    public void setUpload856obFileName(List<String> upload856obFileName) {
        this.upload856obFileName = upload856obFileName;
    }

    public List<String> getUpload856obContentType() {
        return upload856obContentType;
    }

    public void setUpload856obContentType(List<String> upload856obContentType) {
        this.upload856obContentType = upload856obContentType;
    }

    public List<File> getUpload810ob() {
        return upload810ob;
    }

    public void setUpload810ob(List<File> upload810ob) {
        this.upload810ob = upload810ob;
    }

    public List<String> getUpload810obFileName() {
        return upload810obFileName;
    }

    public void setUpload810obFileName(List<String> upload810obFileName) {
        this.upload810obFileName = upload810obFileName;
    }

    public List<String> getUpload810obContentType() {
        return upload810obContentType;
    }

    public void setUpload810obContentType(List<String> upload810obContentType) {
        this.upload810obContentType = upload810obContentType;
    }

    public Map getProtocolsList() {
        return protocolsList;
    }

    public void setProtocolsList(Map protocolsList) {
        this.protocolsList = protocolsList;
    }

    public int getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(int communicationId) {
        this.communicationId = communicationId;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getIbTransaction() {
        return ibTransaction;
    }

    public void setIbTransaction(String ibTransaction) {
        this.ibTransaction = ibTransaction;
    }

    public String getObTransaction() {
        return obTransaction;
    }

    public void setObTransaction(String obTransaction) {
        this.obTransaction = obTransaction;
    }

    public List<TpOnboardingBean> getTpoCommunicationsList() {
        return tpoCommunicationsList;
    }

    public void setTpoCommunicationsList(List<TpOnboardingBean> tpoCommunicationsList) {
        this.tpoCommunicationsList = tpoCommunicationsList;
    }

    public List<String> getTpoProtocolsHeadersList() {
        return tpoProtocolsHeadersList;
    }

    public void setTpoProtocolsHeadersList(List<String> tpoProtocolsHeadersList) {
        this.tpoProtocolsHeadersList = tpoProtocolsHeadersList;
    }

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFormAction() {
        return formAction;
    }

    public void setFormAction(String formAction) {
        this.formAction = formAction;
    }

}
