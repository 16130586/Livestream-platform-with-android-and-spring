package com.t4.LiveServer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.t4.LiveServer.model.wowza.WowzaStream;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "stream")
@NoArgsConstructor
@Data
public class Stream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer streamId;
    @Column(name = "wowza_id")
    private String wowzaId;
    @ManyToMany
    @JoinTable(name = "stream_type",
            joinColumns = @JoinColumn(name = "stream_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    private List<StreamType> streamType;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties("streams")
    private User owner;
    @Column(name = "total_view")
    private Integer totalView;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    private Date startTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "stream_status")
    private Integer status;
    @Column(name = "forwards")
    private String forwards;
    @Column(name = "forwards_url")
    private String forwardsUrl;
    @Column(name = "stored_url")
    private String storedUrl;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "stream_id")
    private List<Comment> comments;
    @Column(name = "primary_server_url")
    private String primaryServerURL;
    @Column(name = "host_port")
    private int hostPort;
    @Column(name = "application")
    private String application;
    @Column(name = "stream_name")
    private String streamName;

    public Stream(WowzaStream liveWowza){
        setWowzaId(liveWowza.id);
        setStatus(-1);
        setPrimaryServerURL(liveWowza.sourceConnectionInformation.primaryServer);
        setHostPort(liveWowza.sourceConnectionInformation.hostPort);
        setApplication(liveWowza.sourceConnectionInformation.application);
        setStreamName(liveWowza.sourceConnectionInformation.streamName);
    }

    public String getPrimaryServerURL() {
        return primaryServerURL;
    }

    public void setPrimaryServerURL(String primaryServerURL) {
        this.primaryServerURL = primaryServerURL;
    }


    public int getHostPort() {
        return hostPort;
    }

    public void setHostPort(int hostPort) {
        this.hostPort = hostPort;
    }


    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public Integer getStreamId() {
        return streamId;
    }

    public void setStreamId(Integer streamId) {
        this.streamId = streamId;
    }


    public String getWowzaId() {
        return wowzaId;
    }

    public void setWowzaId(String wowzaId) {
        this.wowzaId = wowzaId;
    }

    public List<StreamType> getStreamType() {
        return streamType;
    }

    public void setStreamType(List<StreamType> streamType) {
        this.streamType = streamType;
    }

    public Integer getTotalView() {
        return totalView;
    }

    public void setTotalView(Integer totalView) {
        this.totalView = totalView;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getForwards() {
        return forwards;
    }

    public void setForwards(String forwards) {
        this.forwards = forwards;
    }

    public String getForwardsUrl() {
        return forwardsUrl;
    }

    public void setForwardsUrl(String forwardsUrl) {
        this.forwardsUrl = forwardsUrl;
    }

    public String getStoredUrl() {
        return storedUrl;
    }

    public void setStoredUrl(String storedUrl) {
        this.storedUrl = storedUrl;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
