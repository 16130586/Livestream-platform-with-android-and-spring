package com.t4.LiveServer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.t4.LiveServer.model.wowza.WowzaStream;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "stream")
@Data
public class Stream {

    private Integer streamId;
    private String wowzaId;
    private List<StreamType> streamType;
    @JsonIgnoreProperties(value = {"streams", "notifications", "favouriteType", "favouriteSaved"})
    private User owner;
    private String thumbnail;
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
    private int likeCount;
    private String application;
    private String streamName;
    private String title;
    private String hlsPlayBackUrl;

    public String getHlsPlayBackUrl() {
        return hlsPlayBackUrl;
    }

    public void setHlsPlayBackUrl(String hlsPlayBackUrl) {
        this.hlsPlayBackUrl = hlsPlayBackUrl;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Stream() {
    }

    public Stream(Integer streamId, String wowzaId) {
        this.streamId = streamId;
        this.wowzaId = wowzaId;
    }

    public Stream(WowzaStream liveWowza) {
        setWowzaId(liveWowza.id);
        setStatus(StreamStatus.INIT);
        setPrimaryServerURL(liveWowza.sourceConnectionInformation.primaryServer);
        setApplication(primaryServerURL.substring(primaryServerURL.lastIndexOf("/") + 1));
        setHostPort(liveWowza.sourceConnectionInformation.hostPort);
        setHlsPlayBackUrl(liveWowza.playerHlsPlaybackUrl);
        setThumbnail(liveWowza.posterImageUrl);
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

    @Column(name = "like_count")
    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
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
    @JsonIgnore
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

    @Column(name = "thumbnail")
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "stream_id")
    @JsonIgnore
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Stream)) return false;
        Stream s = (Stream) obj;
        return this.getStreamId() == s.getStreamId();
    }
    
    public synchronized void upView() {
        if (totalView == null || totalView < 0)
            totalView = 0;
        totalView++;
    }
}
