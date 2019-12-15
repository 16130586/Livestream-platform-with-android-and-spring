package viewModel;

import java.util.Date;

public class StreamViewModel {
    public int streamId;
    public UserModelView owner;
    public int totalView;
    public Date startTime,endTime;
    public String primaryServerURL;
    public int hostPort;
    public String application;
    public String streamName;
    public String title;
    public String thumbnailView;
    public int ownerId;
    public String onwerName;
    public String ownerAvatar;
    public int status;
    public StreamViewModel(){}

    public StreamViewModel (String title, String onwerName, String ownerAvatar, String thumbnailView, int status) {
        this.title = title;
        this.onwerName = onwerName;
        this.ownerAvatar = ownerAvatar;
        this.thumbnailView = thumbnailView;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStreamId() {
        return streamId;
    }

    public void setStreamId(int streamId) {
        this.streamId = streamId;
    }

    public UserModelView getOwner() {
        return owner;
    }

    public void setOwner(UserModelView owner) {
        this.owner = owner;
    }

    public int getTotalView() {
        return totalView;
    }

    public void setTotalView(int totalView) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getOnwerName() {
        return onwerName;
    }

    public void setOnwerName(String onwerName) {
        this.onwerName = onwerName;
    }

    public String getOwnerAvatar() {
        return ownerAvatar;
    }

    public void setOwnerAvatar(String ownerAvatar) {
        this.ownerAvatar = ownerAvatar;
    }

    public String getThumbnailView() {
        return thumbnailView;
    }

    public void setThumbnailView(String thumbnailView) {
        this.thumbnailView = thumbnailView;
    }
}
