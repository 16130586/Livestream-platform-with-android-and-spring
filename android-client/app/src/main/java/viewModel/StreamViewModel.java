package viewModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.t4.androidclient.model.livestream.StreamType;
import com.t4.androidclient.model.livestream.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StreamViewModel {
    private Integer streamId;
    private String wowzaId;
    private List<StreamType> streamType;
    private User owner;
    private String thumbnail;
    private Integer totalView;
    private Date startTime;
    private Date endTime;
    private Integer status;
    private String forwards;
    private String forwardsUrl;
    private String storedUrl;
    private String primaryServerURL;
    private int hostPort;
    private String application;
    private String streamName;
    private String title;
    private String hlsPlayBackUrl;
    public StreamViewModel(){}

    @JsonIgnore
    public String getTag() {
        String tag = "";
        for (int i = 0; i < streamType.size(); i++) {
            tag += "#" + streamType.get(i).getTypeName() + " ";
        }
        return tag;
    }

    @JsonIgnore
    public String getDateStartString() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(this.startTime);
        return date;
    }

    @JsonIgnore
    public String getDateStatus() {
        if (status == 1)
            return "Live NOW";
        Date dateNow = new Date();
        if(startTime == null)
            startTime = new Date();
        long diff = Math.abs(startTime.getTime() - dateNow.getTime());
        System.out.println(" =========== " + startTime.toString() + " ========== " + dateNow.toString());

        int days = (int) (diff / (1000*60*60*24));
        System.out.println(this.title + " ====================================================== Days " + days);
        if (days == 1) return "Live " + days + " day ago";
        if (days > 1) return "Live " + days + " days ago";

        int hours = (int) (diff / (1000*60*60));
        System.out.println(this.title + " ====================================================== Days " + hours);
        if (hours == 1) return "Live " + hours + " hour ago";
        if (hours > 1) return "Live " + hours + " hours ago";

        int minutes = (int) (diff / (1000*60));
        System.out.println(this.title + " ====================================================== Days " + minutes);
        if (minutes == 1) return "Live " + minutes + " minute ago";
        if (minutes > 1) return "Live " + minutes + " minutes ago";

        return "Live NOW";
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    public String getHlsPlayBackUrl() {
        return hlsPlayBackUrl;
    }

    public void setHlsPlayBackUrl(String hlsPlayBackUrl) {
        this.hlsPlayBackUrl = hlsPlayBackUrl;
    }
}
