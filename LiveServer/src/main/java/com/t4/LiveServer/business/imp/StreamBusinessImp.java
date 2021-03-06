package com.t4.LiveServer.business.imp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.t4.LiveServer.business.interfaze.UserBusiness;
import com.t4.LiveServer.business.interfaze.facebook.FacebookLiveBusiness;
import com.t4.LiveServer.business.interfaze.StreamBusiness;
import com.t4.LiveServer.business.interfaze.mail.MailBusiness;
import com.t4.LiveServer.business.interfaze.wowza.WOWZAStreamBusiness;
import com.t4.LiveServer.config.FacebookConfig;
import com.t4.LiveServer.config.ReportConfig;
import com.t4.LiveServer.core.JsonHelper;
import com.t4.LiveServer.entryParam.base.Stream.CreatingStreamEntryParams;
import com.t4.LiveServer.entryParam.base.Stream.StreamingForward;
import com.t4.LiveServer.entryParam.base.Wowza.AdditionOutputStreamTargetToTransCoderEntryParam;
import com.t4.LiveServer.middleware.RestTemplateHandleException;
import com.t4.LiveServer.model.*;
import com.t4.LiveServer.model.wowza.StreamOutput;
import com.t4.LiveServer.model.wowza.StreamTarget;
import com.t4.LiveServer.model.wowza.WowzaStream;
import com.t4.LiveServer.repository.*;
import com.t4.LiveServer.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.*;

public class StreamBusinessImp implements StreamBusiness {
    private RestTemplate restTemplate = new RestTemplateBuilder().errorHandler(new RestTemplateHandleException()).build();

    @Autowired
    MailBusiness mailBusiness;
    @Autowired
    private WOWZAStreamBusiness wowzaStreamBusiness;
    @Autowired
    private FacebookLiveBusiness facebookLiveBusiness;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private UserLikeRepository userLikeRepository;
    @Autowired
    private StreamRepository streamRepository;
    @Autowired
    private StreamTypeRepository streamTypeRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserBusiness userBusiness;

    @Override
    public Stream create(CreatingStreamEntryParams entryParams) {
        WowzaStream liveWowza = null;
        List<StreamTarget> forwardTargets = null;
        User user = userBusiness.getUserById(entryParams.userId);
        try {
            if (user.getWowzaId() != null && !user.getWowzaId().isEmpty()) {
                wowzaStreamBusiness.stop(user.getWowzaId());
                liveWowza = wowzaStreamBusiness.fetchOne(user.getWowzaId());
            } else {
                liveWowza = wowzaStreamBusiness.create(entryParams);
                if (liveWowza != null) {
                    user.setWowzaId(liveWowza.id);
                    userBusiness.saveUser(user);
                }
                if (liveWowza == null) {
                    liveWowza = getReplacementLive(entryParams);
                    System.out.println("Wowza is out of trial!");
                    if (liveWowza == null) {
                        System.out.println("Cannot get replacement");
                        return null;
                    }
                }
            }
            // trong dtb, user chua co wowza stream id, chinh lai cai model, update sql init, sql import
            // khi user yeu cau tao 1 stream tren app cua minh
            // thi m se lay len cai wowza stream cua thang user do
            // neu chua co -> tao moi
            // neu co roi -> stop cai stream do thong qua wowza business
            // stop xong
            // update cai wowza cu thanh wowza voi thong tin tu entryParams
            forwardTargets = new ArrayList<>();
            if (entryParams.forwards != null) {
                for (StreamingForward fw : entryParams.forwards) {
                    StreamingForward.ForwardPlatform platform = StreamingForward
                            .ForwardPlatform.valueOf(fw.platform.toUpperCase());
                    if (StreamingForward.ForwardPlatform.FACEBOOK == platform) {
                        StreamTarget toFacebookOutput = this.createStreamTargetToFacebookStream(fw);
                        toFacebookOutput.setForwardToken(fw.token);
                        toFacebookOutput.setForwardType(StreamingForward.ForwardPlatform.FACEBOOK);
                        forwardTargets.add(toFacebookOutput);
                    }
                    if (StreamingForward.ForwardPlatform.YOUTUBE == platform) {
                        StreamTarget toYoutubeOutput = this.createStreamTargetToYoutubeOutput(fw);
                        toYoutubeOutput.setForwardToken(fw.token);
                        toYoutubeOutput.setForwardType(StreamingForward.ForwardPlatform.FACEBOOK);
                        forwardTargets.add(toYoutubeOutput);
                    }
                    if (StreamingForward.ForwardPlatform.DISCORD == platform) {
                        StreamTarget toDiscordOutput = this.createStreamTargetToDiscordOutput(fw);
                        toDiscordOutput.setForwardToken(fw.token);
                        toDiscordOutput.setForwardType(StreamingForward.ForwardPlatform.FACEBOOK);
                        forwardTargets.add(toDiscordOutput);
                    }
                    if (StreamingForward.ForwardPlatform.TWITCH == platform) {
                        StreamTarget toTwitchOutput = this.createStreamTargetToTwitchOutput(fw);
                        toTwitchOutput.setForwardToken(fw.token);
                        toTwitchOutput.setForwardType(StreamingForward.ForwardPlatform.FACEBOOK);
                        forwardTargets.add(toTwitchOutput);
                    }
                }
            }
            if (!forwardTargets.isEmpty()) {
                StreamOutput passThroughTranCoder = wowzaStreamBusiness.
                        getStreamOutput(liveWowza.id, "Passthrough");
                for (StreamTarget target : forwardTargets) {
                    if (target == null) continue;
                    wowzaStreamBusiness.addOutputStreamTargetToTransCoderOfAStream(
                            new AdditionOutputStreamTargetToTransCoderEntryParam(liveWowza.id,
                                    passThroughTranCoder.id, target.id));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        List<StreamType> streamTypes = streamTypeRepository.findByTypeNameIn(entryParams.genreList);
        Stream rs = new Stream(liveWowza);

        // adding information for another module to get or reply data
        if(forwardTargets != null && forwardTargets.size() > 0){
            List<ForwardStream> forwards = new ArrayList<>(forwardTargets.size());
            ForwardStream forward = null;
           for(StreamTarget target : forwardTargets){
               forward = new ForwardStream();
               forward.forwardTargetToId = target.getTargetPlatformId();
               forward.streamName = target.getStreamName();
               forward.primaryUrl = target.getPrimaryUrl();
               forward.provider = target.provider;
               forward.forwardType = target.getForwardType();
               forward.forwardTargetToken = target.getForwardToken();
               forwards.add(forward);
           }
            rs.setForwards(JsonHelper.serialize(forwards));
        }

        rs.setOwner(user);
        rs.setTitle(entryParams.name);
        rs.setStreamType(streamTypes);
        rs = streamRepository.saveAndFlush(rs);
        return rs;
    }

    private StreamTarget createStreamTargetToTwitchOutput(StreamingForward fw) {
        return null;
    }

    private StreamTarget createStreamTargetToDiscordOutput(StreamingForward fw) {
        return null;
    }

    private StreamTarget createStreamTargetToYoutubeOutput(StreamingForward fw) {
        return null;
    }

    private StreamTarget createStreamTargetToFacebookStream(StreamingForward fw) {
        FacebookConfig fbConfig = new FacebookConfig();
        fbConfig.accessToken = fw.token;
        ForwardStream liveFbData = facebookLiveBusiness.individualCreate(fbConfig);
        if (null == liveFbData) {
            return null;
        }
        StreamTarget target = new StreamTarget(liveFbData);
        target.setTargetPlatformId(liveFbData.forwardTargetToId);
        return wowzaStreamBusiness.createCustomStreamTarget(
                target);
    }


    @Override
    public Stream start(String id) {
        Stream requested = streamRepository.findById(Integer.parseInt(id)).get();
        if (requested == null)
            return null;
        WowzaStream wowzaClosed = wowzaStreamBusiness.start(requested.getWowzaId());
        if (wowzaClosed == null || !"starting".equals(wowzaClosed.state))
            return null;
        try {
            String fullyUrl = WowzaStream.URL_LIVE_STREAM + "/" + requested.getWowzaId() + "/state";
            while (true) {
                Thread.sleep(3000);
                ResponseEntity<String> result = restTemplate.exchange(fullyUrl,
                        HttpMethod.GET, new HttpEntity(WowzaStream.getWowzaConfigHeaders()), String.class);
                WowzaStream stream = JsonHelper.deserialize(DeserializationFeature.UNWRAP_ROOT_VALUE,
                        result.getBody(), WowzaStream.class);
                System.out.println(result.getBody());
                if (stream != null && stream.state.equals("started")) {
                    requested.setStartTime(DateUtil.getCurrentDateInUTC());
                    requested.setStatus(StreamStatus.STARTED);
                    Stream rs = streamRepository.saveAndFlush(requested);
                    System.out.println(rs);
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return requested;
    }

    @Override
    public Object stop(String id) {
        Stream requested = streamRepository.findById(Integer.parseInt(id)).get();
        if (requested == null)
            return null;
        wowzaStreamBusiness.stop(requested.getWowzaId());
        requested.setStatus(StreamStatus.END);
        requested.setEndTime(DateUtil.getCurrentDateInUTC());
        requested = streamRepository.saveAndFlush(requested);
        return requested;
    }


    private WowzaStream getReplacementLive(CreatingStreamEntryParams entry) {
        List<WowzaStream> existedStreams = wowzaStreamBusiness.fetchAll();
        Optional<WowzaStream> replaceStream = existedStreams
                .stream().filter(ws -> !ws.isRecording())
                .findAny();
        if (replaceStream.isPresent()) {
            WowzaStream op = wowzaStreamBusiness.fetchOne(replaceStream.get().id);
            op.name = entry.name;
//            op.posterImage = entry.thumbnail;
            wowzaStreamBusiness.update(op);
            return op;
        }
        return null;
    }

    @Override
    public List<Stream> getRecommendForUser(int userId, int offset, int pageSize) {
        User user = userBusiness.getUserById(userId);
        List<String> streamTypeString = new ArrayList<>();
        for (StreamType streamType : user.getFavouriteType()) {
            streamTypeString.add(streamType.getTypeName());
        }
        Pageable pageable = new PageRequest(offset, pageSize);
        return streamRepository.findAllByStreamTypeInAndStatus(streamTypeString, StreamStatus.STARTED, streamTypeString.size(), pageable);
    }

    @Override
    public List<Stream> getRecommendForCookieUser(int page, int pageSize) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int skip = page*pageSize;
        try {
            return streamRepository.getRecommendForCookieUser(StreamStatus.INIT, month, year, skip, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<StreamType> getAllGenre() {
        return streamTypeRepository.findAll();
    }

    @Override
    public StreamType getGenreByName(String name) {
        return streamTypeRepository.findByTypeName(name);
    }

    @Override
    public List<Stream> getStreamsByName(String streamName, int offset, int pageSize) {
        Pageable pageable = new PageRequest(offset, pageSize);
        return streamRepository.findByTitleContaining(streamName, pageable);
    }

    @Override
    public List<Stream> getStreamsByNameAndType(String streamName, List<String> streamTypes, int offset, int pageSize) {
        System.out.println("stream name: " + streamName);
        Pageable pageable = new PageRequest(offset, pageSize);
        if (streamName != null && !streamName.isEmpty())
            return streamRepository.findByStreamNameAndStreamType(streamName, streamTypes, streamTypes.size(), pageable);
        return streamRepository.findByStreamType(streamTypes, streamTypes.size(), pageable);
    }

    @Override
    public Comment saveComment(Comment comment) {
        Stream requestedStream = streamRepository.getOne(comment.getStreamId());
        if (requestedStream.getStatus() == StreamStatus.STARTED) {
            java.util.Date current = DateUtil.getCurrentDateInUTC();
            java.util.Date started = requestedStream.getStartTime();
            long timeDiff = Math.abs(current.getTime() - started.getTime());
            int atSecondOfLiveStream = (int) timeDiff / 1000;
            comment.setVideoTime(atSecondOfLiveStream);
        }
        return commentRepository.save(comment);
    }

    @Override
    public List<Stream> listStreamByTypeOfUser(int userID, int typeID) {
        return streamRepository.repoListStreamByTypeOfUser(userID, typeID);
    }

    @Override
    public List<Stream> getWatchedStreamsByUserID(int userID) {
        return streamRepository.repoGetWatchedStreamsByUserID(userID);
    }

    @Override
    public List<Stream> getTrendingStreams(int offset, int pageSize) {
        Pageable pageable = new PageRequest(offset, pageSize, Sort.by("totalView").descending());
        return streamRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Comment> getCommentByVideoTime(int streamId, int videoTime) {
        return commentRepository.findAllByStreamIdAndVideoTime(streamId, videoTime);
    }

    public boolean upView(int streamId) {
        try {
            Stream stream = streamRepository.findById(streamId).get();
            stream.upView();
            streamRepository.save(stream);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public Stream likeStream(int userId, int streamId) {
        Stream requestedStream = streamRepository.findById(streamId).get();
        requestedStream.setLikeCount(requestedStream.getLikeCount() + 1);
        UserLike userLike = new UserLike();
        userLike.setUserId(userId);
        userLike.setStreamId(streamId);
        userLikeRepository.save(userLike);
        return streamRepository.save(requestedStream);
    }

    @Override
    @Transactional
    public Stream dislikeStream(int userId, int streamId) {
        Stream requestedStream = streamRepository.findById(streamId).get();
        requestedStream.setLikeCount(requestedStream.getLikeCount() - 1);
//        userLikeRepository.deleteLikeStatus(userId, streamId);
        userLikeRepository.deleteByUserIdAndStreamId(userId, streamId);
        return streamRepository.save(requestedStream);

    }

    @Override
    public int getLikeStatus(int userId, int streamId) {
        UserLike userLike = userLikeRepository.checkingLikeStatus(userId, streamId);
        if (userLike == null) {
            System.out.println("NULLLLLLLL");
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public Report reportStream(int liveId, int ownerId, String reason) {
        Report report = new Report();
        report.setLiveId(liveId);
        report.setOwnerId(ownerId);
        report.setReason(reason);
        report.setCreated(new Date());

        checkingReport(liveId, reason);
        return reportRepository.save(report);
    }

    @Override
    public void checkingReport(int liveId, String reason) {
        List<Report> reports = reportRepository.getReportedCount(liveId, reason);
        System.out.println("COUNT REPORT " + reports.size());
        if (reports.size() >= ReportConfig.FLAGGED_REPORT_TIME) {
            Stream stream = streamRepository.findById(liveId).get();
            if (stream.getStatus() == 1) {
                stop(stream.getStreamId().toString());
                stream.setStatus(StreamStatus.END);
            }
            stream.setIsFlagged(1);
            User user = userBusiness.getUserById(stream.getOwner().getUserId());
            if (user.getIsPublisher() > 0) {
                user.setIsPublisher(user.getIsPublisher() - 1);
            } else {
                user.setIsActivated(-1);
            }
            try {
                mailBusiness.sendMailInformReport("Report", liveId);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            userBusiness.saveUser(user);
            streamRepository.saveAndFlush(stream);
        }
    }
}
