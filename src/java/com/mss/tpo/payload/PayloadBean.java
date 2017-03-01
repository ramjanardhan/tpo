
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mss.tpo.payload;

import java.sql.Timestamp;

/**
 *
 * @author miracle
 */
public class PayloadBean {

    private int id;
    private String docType;
    private String path;
    private String createdBy;
    private Timestamp createdTS;
    private String modifiedBy;
    private Timestamp modifiedTS;
    private String ststusFalg;
    private String lastTestStatus;
    private Timestamp lastTestDate;
    private String currentTestStatus;
    private Timestamp currentTestDate;
    private String status;
    private int transaction;
    private String direction;
    private int correlationID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getTransaction() {
        return transaction;
    }

    public void setTransaction(int transaction) {
        this.transaction = transaction;
    }

    public int getCorrelationID() {
        return correlationID;
    }

    public void setCorrelationID(int correlationID) {
        this.correlationID = correlationID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedTS() {
        return createdTS;
    }

    public void setCreatedTS(Timestamp createdTS) {
        this.createdTS = createdTS;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Timestamp getModifiedTS() {
        return modifiedTS;
    }

    public void setModifiedTS(Timestamp modifiedTS) {
        this.modifiedTS = modifiedTS;
    }

    public String getStstusFalg() {
        return ststusFalg;
    }

    public void setStstusFalg(String ststusFalg) {
        this.ststusFalg = ststusFalg;
    }

    public String getLastTestStatus() {
        return lastTestStatus;
    }

    public void setLastTestStatus(String lastTestStatus) {
        this.lastTestStatus = lastTestStatus;
    }

    public Timestamp getLastTestDate() {
        return lastTestDate;
    }

    public void setLastTestDate(Timestamp lastTestDate) {
        this.lastTestDate = lastTestDate;
    }

    public String getCurrentTestStatus() {
        return currentTestStatus;
    }

    public void setCurrentTestStatus(String currentTestStatus) {
        this.currentTestStatus = currentTestStatus;
    }

    public Timestamp getCurrentTestDate() {
        return currentTestDate;
    }

    public void setCurrentTestDate(Timestamp currentTestDate) {
        this.currentTestDate = currentTestDate;
    }

}
