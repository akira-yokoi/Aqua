package org.amd.aqua.model;

/**
 * Created by Akira on 2018/04/18.
 */

import java.io.Serializable;
import java.util.Date;

public class SupportItem implements Serializable {
    public int supportId;
    public int taskId;
    public int requestantId;
    public int supporterId;
    public int status;
    public int point;
    public String message;
    public Date createDate;
    public Date startDate;
    public Date endDate;
    public Date limitDate;
    public int preSupportId; // 前提となるサポートがある場合

    public SupportItem() {
    }

    public SupportItem(int supportId, int taskId, int requestantId, int supporterId, int status, int point, String message, Date createDate, Date startDate, Date endDate) {
        this.supportId = supportId;
        this.taskId = taskId;
        this.requestantId = requestantId;
        this.supporterId = supporterId;
        this.status = status;
        this.point = point;
        this.message = message;
        this.createDate = createDate;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}