/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.payload;

import com.mss.tpo.util.AppConstants;
import com.mss.tpo.util.DataSourceDataProvider;
import com.mss.tpo.util.Properties;
import com.mss.tpo.util.ServiceLocator;
import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

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
    private String transaction;
    private String direction;
    private File upload;
    private File upload850ib;
    private File upload855ib;
    private File upload856ib;
    private File upload810ib;
    private File upload850ob;
    private File upload855ob;
    private File upload856ob;
    private File upload810ob;
    private String uploadContentType;
    private String uploadFileName;
    private String upload850ibFileName;
    private String upload855ibFileName;
    private String upload856ibFileName;
    private String upload810ibFileName;
    private String upload850obFileName;
    private String upload855obFileName;
    private String upload856obFileName;
    private String upload810obFileName;
    private String filepath;

    public String payloadUpload() throws Exception {
        resultType = LOGIN;
        if (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString() != null) {
            try {
                System.out.println("login : " + (httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString()));
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
                List list = DataSourceDataProvider.getInstance().getTxList();
                setTxList(list);
                System.out.println("list==================" + list);
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
                int partnerId = (Integer) httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_PARTNER_ID);
                String loginId = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString();
                setCreated_by(loginId);
                payloadSearchList = ServiceLocator.getPayloadService().payloadSearch(loginId, partnerId);
                httpServletRequest.getSession(false).setAttribute(AppConstants.PAYLOAD_SEARCH_LIST, payloadSearchList);

                System.out.println("action values in session=====" + httpServletRequest.getSession(false).getAttribute(AppConstants.PAYLOAD_SEARCH_LIST));
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
                String Email = httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_EMAIL).toString();
                setCreated_by(httpServletRequest.getSession(false).getAttribute(AppConstants.TPO_LOGIN_ID).toString());

                if ("Inbound".equalsIgnoreCase(getDirection())) {
                if ("850".equalsIgnoreCase(getTransaction())) {
                       if (getUpload850ibFileName() != null && !getUpload850ibFileName().equals(null) && getUpload850ibFileName().length() > 1) {
                    saveFileToDisk(partnerId, loginId);
                }
                } else if ("855".equalsIgnoreCase(getTransaction())) {
                      if (getUpload855ibFileName() != null && !getUpload855ibFileName().equals(null) && getUpload855ibFileName().length() > 1) {
                    saveFileToDisk(partnerId, loginId);
                }
                } else if ("856".equalsIgnoreCase(getTransaction())) {
                      if (getUpload856ibFileName() != null && !getUpload856ibFileName().equals(null) && getUpload856ibFileName().length() > 1) {
                    saveFileToDisk(partnerId, loginId);
                }
                } else if ("810".equalsIgnoreCase(getTransaction())) {
                       if (getUpload810ibFileName() != null && !getUpload810ibFileName().equals(null) && getUpload810ibFileName().length() > 1) {
                    saveFileToDisk(partnerId, loginId);
                }
                }
            } else  if ("Outbound".equalsIgnoreCase(getDirection())) {
                if ("850".equalsIgnoreCase(getTransaction())) {
                       if (getUpload850obFileName() != null && !getUpload850obFileName().equals(null) && getUpload850obFileName().length() > 1) {
                    saveFileToDisk(partnerId, loginId);
                }
                } else if ("855".equalsIgnoreCase(getTransaction())) {
                      if (getUpload855obFileName() != null && !getUpload855obFileName().equals(null) && getUpload855obFileName().length() > 1) {
                    saveFileToDisk(partnerId, loginId);
                }
                } else if ("856".equalsIgnoreCase(getTransaction())) {
                      if (getUpload856obFileName() != null && !getUpload856obFileName().equals(null) && getUpload856obFileName().length() > 1) {
                    saveFileToDisk(partnerId, loginId);
                }
                } else if ("810".equalsIgnoreCase(getTransaction())) {
                       if (getUpload810obFileName() != null && !getUpload810obFileName().equals(null) && getUpload810obFileName().length() > 1) {
                    saveFileToDisk(partnerId, loginId);
                }
                }
            }
//                
//                if (getUploadFileName() != null && !getUploadFileName().equals(null) && getUploadFileName().length() > 1) {
//                    saveFileToDisk(partnerId, loginId);
//                }

                String resultMessage = ServiceLocator.getPayloadService().doPayloadUpload(partnerId, loginId, this);
                httpServletRequest.getSession(false).setAttribute(AppConstants.REQ_RESULT_MSG, resultMessage);
                resultType = SUCCESS;
            } catch (Exception ex) {
                resultType = "error";
            }
        }
        return resultType;
    }

    public boolean saveFileToDisk(int partnerId, String loginId) {
        boolean isSave = false;
        File createPath = new File(Properties.getProperty("mscvp.TpoPayloadUpload"));
        System.err.println("path:" + createPath);
        String partner_contactName = partnerId + "_" + loginId;
        /*getrequestType is used to create a directory of the object type specified in the jsp page*/
        // createPath = new File(createPath.getAbsolutePath() + "//Payload");
         System.out.println("aaaa");
        createPath = new File(createPath.getAbsolutePath() + "//" + partner_contactName + "//" + getDocType()+ "//" + getDirection()+ "//" + getTransaction());
        /*This creates a directory forcefully if the directory does not exsist*/
        System.out.println("bbbb");
        createPath.mkdir();
        /*here it takes the absolute path and the name of the file that is to be uploaded*/
        System.out.println("cccc");
        File theFile = null;
         if ("Inbound".equalsIgnoreCase(getDirection())) {
                if ("850".equalsIgnoreCase(getTransaction())) {
                     theFile = new File(createPath.getAbsolutePath() + "//" + getUpload850ibFileName());
                } else if ("855".equalsIgnoreCase(getTransaction())) {
                    theFile = new File(createPath.getAbsolutePath() + "//" + getUpload855ibFileName());
                     System.out.println("theFile-->"+theFile.toString());
                } else if ("856".equalsIgnoreCase(getTransaction())) {
                    theFile = new File(createPath.getAbsolutePath() + "//" + getUpload856ibFileName());
                } else if ("810".equalsIgnoreCase(getTransaction())) {
                     theFile = new File(createPath.getAbsolutePath() + "//" + getUpload810ibFileName());
                }
            } else  if ("Outbound".equalsIgnoreCase(getDirection())) {
                if ("850".equalsIgnoreCase(getTransaction())) {
                     theFile = new File(createPath.getAbsolutePath() + "//" + getUpload850obFileName());
                } else if ("855".equalsIgnoreCase(getTransaction())) {
                    theFile = new File(createPath.getAbsolutePath() + "//" + getUpload855obFileName());
                } else if ("856".equalsIgnoreCase(getTransaction())) {
                    theFile = new File(createPath.getAbsolutePath() + "//" + getUpload856obFileName());
                } else if ("810".equalsIgnoreCase(getTransaction())) {
                     theFile = new File(createPath.getAbsolutePath() + "//" + getUpload810obFileName());
                }
            }
      //  File theFile = new File(createPath.getAbsolutePath() + "//" + getUploadFileName());
         System.out.println("theFile.toString()-->"+theFile.toString());
        setFilepath(theFile.toString());
        /*copies the file to the destination*/
        try {
            // FileUtils.copyFile(upload, theFile);
            if ("Inbound".equalsIgnoreCase(getDirection())) {
                if ("850".equalsIgnoreCase(getTransaction())) {
                    FileUtils.copyFile(upload850ib, theFile);
                } else if ("855".equalsIgnoreCase(getTransaction())) {
                    FileUtils.copyFile(upload855ib, theFile);
                } else if ("856".equalsIgnoreCase(getTransaction())) {
                    FileUtils.copyFile(upload856ib, theFile);
                } else if ("810".equalsIgnoreCase(getTransaction())) {
                    FileUtils.copyFile(upload810ib, theFile);
                }
            }
            if ("Outbound".equalsIgnoreCase(getDirection())) {
                if ("850".equalsIgnoreCase(getTransaction())) {
                    FileUtils.copyFile(upload850ob, theFile);
                } else if ("855".equalsIgnoreCase(getTransaction())) {
                    FileUtils.copyFile(upload855ob, theFile);
                } else if ("856".equalsIgnoreCase(getTransaction())) {
                    FileUtils.copyFile(upload856ob, theFile);
                } else if ("810".equalsIgnoreCase(getTransaction())) {
                    FileUtils.copyFile(upload810ob, theFile);
                }
            }
            isSave = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return isSave;
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

    public File getUpload850ib() {
        return upload850ib;
    }

    public void setUpload850ib(File upload850ib) {
        this.upload850ib = upload850ib;
    }

    public File getUpload856ib() {
        return upload856ib;
    }

    public void setUpload856ib(File upload856ib) {
        this.upload856ib = upload856ib;
    }

    public File getUpload810ib() {
        return upload810ib;
    }

    public void setUpload810ib(File upload810ib) {
        this.upload810ib = upload810ib;
    }

    public File getUpload850ob() {
        return upload850ob;
    }

    public void setUpload850ob(File upload850ob) {
        this.upload850ob = upload850ob;
    }

    public File getUpload855ob() {
        return upload855ob;
    }

    public void setUpload855ob(File upload855ob) {
        this.upload855ob = upload855ob;
    }

    public File getUpload856ob() {
        return upload856ob;
    }

    public void setUpload856ob(File upload856ob) {
        this.upload856ob = upload856ob;
    }

    public File getUpload810ob() {
        return upload810ob;
    }

    public void setUpload810ob(File upload810ob) {
        this.upload810ob = upload810ob;
    }

    public File getUpload855ib() {
        return upload855ib;
    }

    public void setUpload855ib(File upload855ib) {
        this.upload855ib = upload855ib;
    }

    public String getUpload850ibFileName() {
        return upload850ibFileName;
    }

    public void setUpload850ibFileName(String upload850ibFileName) {
        this.upload850ibFileName = upload850ibFileName;
    }

    public String getUpload855ibFileName() {
        return upload855ibFileName;
    }

    public void setUpload855ibFileName(String upload855ibFileName) {
        this.upload855ibFileName = upload855ibFileName;
    }

    public String getUpload856ibFileName() {
        return upload856ibFileName;
    }

    public void setUpload856ibFileName(String upload856ibFileName) {
        this.upload856ibFileName = upload856ibFileName;
    }

    public String getUpload810ibFileName() {
        return upload810ibFileName;
    }

    public void setUpload810ibFileName(String upload810ibFileName) {
        this.upload810ibFileName = upload810ibFileName;
    }

    public String getUpload850obFileName() {
        return upload850obFileName;
    }

    public void setUpload850obFileName(String upload850obFileName) {
        this.upload850obFileName = upload850obFileName;
    }

    public String getUpload855obFileName() {
        return upload855obFileName;
    }

    public void setUpload855obFileName(String upload855obFileName) {
        this.upload855obFileName = upload855obFileName;
    }

    public String getUpload856obFileName() {
        return upload856obFileName;
    }

    public void setUpload856obFileName(String upload856obFileName) {
        this.upload856obFileName = upload856obFileName;
    }

    public String getUpload810obFileName() {
        return upload810obFileName;
    }

    public void setUpload810obFileName(String upload810obFileName) {
        this.upload810obFileName = upload810obFileName;
    }

}
