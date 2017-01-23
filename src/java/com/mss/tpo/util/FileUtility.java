/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import java.util.StringTokenizer;

public class FileUtility {

    private static FileUtility _instance;

    public static FileUtility getInstance() {
        if (_instance == null) {
            _instance = new FileUtility();
        }
        return _instance;
    }

    public String copyFiles(ArrayList<File> selected, File destinationDirectory) {
        String fileCopyStatus = "Error";
        for (File file : selected) {
            try {
                if (!destinationDirectory.exists()) {
                    destinationDirectory.mkdirs();
                }
                FileUtils.copyFileToDirectory(file, destinationDirectory);
                fileCopyStatus = "Success";
            } catch (IOException ex) {
                System.err.println("File Coping error--->" + ex.getMessage());
            }
        }
        return fileCopyStatus;
    }

    public String copyMapFiles(Map files) {
        String fileCopyStatus = "Error";
        Iterator it = files.entrySet().iterator();
        while (it.hasNext()) {
            try {
                Map.Entry pairs = (Map.Entry) it.next();
                File destinationDirectory = (File) pairs.getValue();
                File srcFile = (File) pairs.getKey();
                if (!destinationDirectory.exists()) {
                    destinationDirectory.mkdirs();
                }
                FileUtils.copyFileToDirectory(srcFile, destinationDirectory);
                fileCopyStatus = "Success";
            } catch (IOException ex) {
                System.err.println("File Coping error--->" + ex.getMessage());
            }
        }
        return fileCopyStatus;
    }

    public String copyPreMapFiles(Map files) {
        String fileCopyStatus = "Error";
        Iterator it = files.entrySet().iterator();
        while (it.hasNext()) {
            try {
                Map.Entry pairs = (Map.Entry) it.next();
                //File destinationDirectory = (File) pairs.getValue();
                String destinationDetails = (String) pairs.getValue();
                // StringTokenizer st = new StringTokenizer();
                String path = destinationDetails.substring(0, destinationDetails.lastIndexOf("\\") + 1);
                String fullToken = destinationDetails.substring(destinationDetails.lastIndexOf("\\") + 1, destinationDetails.length());
                StringTokenizer st = new StringTokenizer(fullToken, "|");
                String poNum = st.nextToken();
                String fileId = st.nextToken();
                String senderId = st.nextToken();
                String recv = st.nextToken();
                String tranc = st.nextToken();
                File destinationDirectory = new File(path);
                File srcFile = (File) pairs.getKey();
                if (!destinationDirectory.exists()) {
                    destinationDirectory.mkdirs();
                }

                FileUtils.copyFileToDirectory(srcFile, destinationDirectory);
                String newFile = destinationDirectory + "\\" + srcFile.getName();
                File renameFile = new File(newFile);
                int mid = renameFile.getName().lastIndexOf(".");
                String fname = renameFile.getName().substring(0, mid);
                String ext = renameFile.getName().substring(mid + 1, renameFile.getName().length());
                //String newName = fname+"_"+poNum+"_"+fileId+"."+ext;
                String newName = poNum.trim() + "_" + senderId.trim() + "_" + recv.trim() + "_" + tranc.trim() + "_Reprocess." + ext;
                renameFile.renameTo(new File(destinationDirectory + "\\" + newName));
                fileCopyStatus = "Success";
                if (fileCopyStatus.equals("Success")) {
                    //processBatchFile();
                    fileCopyStatus = "Success";
                } else {
                    fileCopyStatus = "error";
                }
            } catch (IOException ex) {
                System.err.println("File Coping error--->" + ex.getMessage());
            }
        }
        return fileCopyStatus;
    }

    public String copyPostMapFiles(Map files) {
        String fileCopyStatus = "Error";
        Iterator it = files.entrySet().iterator();
        while (it.hasNext()) {
            try {
                Map.Entry pairs = (Map.Entry) it.next();
                File destinationDirectory = (File) pairs.getValue();
                File srcFile = (File) pairs.getKey();
                if (!destinationDirectory.exists()) {
                    destinationDirectory.mkdirs();
                }
                FileUtils.copyFileToDirectory(srcFile, destinationDirectory);
                fileCopyStatus = "Success";
                if (fileCopyStatus.equals("Success")) {
                    //postprocessBatchFile();
                    fileCopyStatus = "Success";
                } else {
                    fileCopyStatus = "error";
                }
            } catch (IOException ex) {
                System.err.println("File Coping error--->" + ex.getMessage());
            }
        }
        return fileCopyStatus;
    }

    /*
     * Method for LoadTendering Retransmit Process
     * Description : To copy map files from one location to another.
     */
    public String loadTenderCopyPostMapFiles(Map files) {
        String fileCopyStatus = "Error";
        Iterator it = files.entrySet().iterator();
        while (it.hasNext()) {
            try {
                Map.Entry pairs = (Map.Entry) it.next();
                File destinationDirectory = (File) pairs.getValue();
                File srcFile = (File) pairs.getKey();
                if (!destinationDirectory.exists()) {
                    destinationDirectory.mkdirs();
                }
                FileUtils.copyFileToDirectory(srcFile, destinationDirectory);
                fileCopyStatus = "Success";
                if (fileCopyStatus.equals("Success")) {
                    fileCopyStatus = "Success";
                } else {
                    fileCopyStatus = "error";
                }
            } catch (IOException ex) {
                System.err.println("File Coping error--->" + ex.getMessage());
            }
        }
        return fileCopyStatus;
    }

    public String loadTenderCopyPreMapFiles(Map files) {
        String fileCopyStatus = "Error";
        Iterator it = files.entrySet().iterator();
        while (it.hasNext()) {
            try {
                Map.Entry pairs = (Map.Entry) it.next();
                File destinationDirectory = (File) pairs.getValue();
                File srcFile = (File) pairs.getKey();
                if (!destinationDirectory.exists()) {
                    destinationDirectory.mkdirs();
                }
                FileUtils.copyFileToDirectory(srcFile, destinationDirectory);
                String newFile = destinationDirectory + "\\" + srcFile.getName();
                File renameFile = new File(newFile);
                int mid = renameFile.getName().lastIndexOf(".");
                String fname = renameFile.getName().substring(0, mid);
                String ext = renameFile.getName().substring(mid + 1, renameFile.getName().length());
                //String newName = fname+"_"+poNum+"_"+fileId+"."+ext;
                String newName = fname + "_Reprocess." + ext;
                renameFile.renameTo(new File(destinationDirectory + "\\" + newName));
                fileCopyStatus = "Success";
                if (fileCopyStatus.equals("Success")) {
                    loadPreprocessBatchFile();
                    fileCopyStatus = "Success";
                } else {
                    fileCopyStatus = "error";
                }
            } catch (IOException ex) {
                System.err.println("File Coping error--->" + ex.getMessage());
            }
        }
        return fileCopyStatus;
    }

    /**
     * DESC : bat file Process
     */
    public void processBatchFile() {
        // String filePath = "C:\\SI5.2\\Resubmit\\ReSubmit.bat";
        String resubmitFilePath = Properties.getProperty("RESUBMT_PATH");
        System.err.println("resubmitFilePath-->" + resubmitFilePath);
        try {
            String[] array = {"cmd", "/C", "start", "/min", resubmitFilePath};
            Runtime.getRuntime().exec(array);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postprocessBatchFile() {
        // String filePath = "C:\\SI5.2\\Resubmit\\Retransmitted.bat";
        String retransmitFilePath = Properties.getProperty("RETRANSMIT_PATH");
        System.err.println("retransmitFilePath-->" + retransmitFilePath);
        try {
            String[] array = {"cmd", "/C", "start", "/min", retransmitFilePath};
            Runtime.getRuntime().exec(array);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load tendering ReProcess batch file
     */
    public void loadPreprocessBatchFile() {
        String filePath = "C:\\SI5.2\\Resubmit\\L_ReSubmit.bat";
        try {
            String[] array = {"cmd", "/C", "start", "/min", filePath};
            Runtime.getRuntime().exec(array);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
