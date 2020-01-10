package com.t4.androidclient.model.report;

public class ReportReason {
    private Integer reasonId;
    private String reason;

    public ReportReason(Integer reasonId, String reason) {
        this.reasonId = reasonId;
        this.reason = reason;
    }

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
