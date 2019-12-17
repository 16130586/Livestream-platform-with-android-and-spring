package com.t4.LiveServer.business.interfaze;

import com.t4.LiveServer.model.StreamType;

import java.util.List;

public interface StreamTypeBusiness {
	List<StreamType> listStreamTypeByUserID(int userID);
}
