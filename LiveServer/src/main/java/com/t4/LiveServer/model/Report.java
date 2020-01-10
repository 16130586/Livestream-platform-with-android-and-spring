package com.t4.LiveServer.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "report")
public class Report {
    private Integer reportId;
    private Integer ownerId;
    private Integer liveId;
    private String reason;
    private Date created;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    @Column(name = "owner_id")
    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Column(name = "live_id")
    public Integer getLiveId() {
        return liveId;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    @Column(name = "reason")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Column(name = "created")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
