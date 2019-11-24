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

    private Integer streamId;
    private String wowzaId;
    private List<StreamType> streamType;
    @JsonIgnoreProperties(value = {"streams", "notifications", "favouriteType", "favouriteSaved"})
    private User owner;
    private Integer totalView;
    private Date startTime;
    private Date endTime;
    private Integer status;
    private String forwards;
    private String forwardsUrl;
    private String storedUrl;
    private List<Comment> comments;
    private String primaryServerURL;
    private int hostPort;
    private String application;
    private String streamName;

    public Stream() {
    }

    public Stream(WowzaStream liveWowza) {
        setWowzaId(liveWowza.id);
        setStatus(-1);
        setPrimaryServerURL(liveWowza.sourceConnectionInformation.primaryServer);
        setHostPort(liveWowza.sourceConnectionInformation.hostPort);
        setApplication(liveWowza.sourceConnectionInformation.application);
        setStreamName(liveWowza.sourceConnectionInformation.streamName);
    }

    @Column(name = "primary_server_url")
    public String getPrimaryServerURL() {
        return primaryServerURL;
    }

    public void setPrimaryServerURL(String primaryServerURL) {
        this.primaryServerURL = primaryServerURL;
    }

    @Column(name = "host_port")
    public int getHostPort() {
        return hostPort;
    }

    public void setHostPort(int hostPort) {
        this.hostPort = hostPort;
    }

    @Column(name = "application")
    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    @Column(name = "stream_name")
    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getStreamId() {
        return streamId;
    }

    public void setStreamId(Integer streamId) {
        this.streamId = streamId;
    }

    @Column(name = "wowza_id")
    public String getWowzaId() {
        return wowzaId;
    }

    public void setWowzaId(String wowzaId) {
        this.wowzaId = wowzaId;
    }

    @ManyToMany
    @JoinTable(name = "stream_type",
            joinColumns = @JoinColumn(name = "stream_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    public List<StreamType> getStreamType() {
        return streamType;
    }

    public void setStreamType(List<StreamType> streamType) {
        this.streamType = streamType;
    }

    @Column(name = "total_view")
    public Integer getTotalView() {
        return totalView;
    }

    public void setTotalView(Integer totalView) {
        this.totalView = totalView;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "stream_status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "forwards")
    public String getForwards() {
        return forwards;
    }

    public void setForwards(String forwards) {
        this.forwards = forwards;
    }

    @Column(name = "forwards_url")
    public String getForwardsUrl() {
        return forwardsUrl;
    }

    public void setForwardsUrl(String forwardsUrl) {
        this.forwardsUrl = forwardsUrl;
    }

    @Column(name = "stored_url")
    public String getStoredUrl() {
        return storedUrl;
    }

    public void setStoredUrl(String storedUrl) {
        this.storedUrl = storedUrl;
    }

    @ManyToOne
    @JoinColumn(name = "owner_id")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "stream_id")
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
