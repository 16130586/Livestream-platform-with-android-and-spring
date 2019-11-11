package com.t4.LiveServer.business.interfaze.wowza;

import com.t4.LiveServer.entryParam.base.Stream.CreatingStreamEntryParams;
import com.t4.LiveServer.entryParam.base.Wowza.AdditionOutputStreamTargetToTransCoderEntryParam;
import com.t4.LiveServer.model.wowza.*;

import java.util.List;

public interface WOWZAStreamBusiness {
    WowzaStream create(CreatingStreamEntryParams entryParams);
    List<WowzaStream> fetchAll();
    WowzaStream fetchOne(String id);
    String update(WowzaStream entry);
    String delete(String id);
    WowzaStream start(String id);
    WowzaStream stop(String id);
    String reset(String id);
    String regenerate(String id);
    String fetchThumbnail(String id);
    String fetchState(String id);
    String fetchMetrics(String id);
    String fetchVersions();

    String fetchAllCustomStreamTargets();
    String fetchCustomStreamTarget(String id);
    String updateCustomStreamTarget(String id);
    String deleteCustomStreamTarget(String id);
    String regenerateCodeForAnyStreamTarget(String id);


    StreamTarget createCustomStreamTarget(StreamTarget entry);
    TransCoder getDefaultTransCoder(String id);
    String addOutputStreamTargetToTransCoderOfAStream(
            AdditionOutputStreamTargetToTransCoderEntryParam entry);
    List<StreamOutput> fetchAllOutputOfATransCoder(String id);

    StreamOutput getStreamOutput(String streamId, String name);
}
