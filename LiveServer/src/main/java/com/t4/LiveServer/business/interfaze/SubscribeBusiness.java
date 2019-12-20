package com.t4.LiveServer.business.interfaze;
import com.t4.LiveServer.model.Subscribe;

public interface SubscribeBusiness {
	boolean doSubscribe(int subscriberID,int publisherID);
	boolean undoSubscribe(int subscriberID,int publisherID);
	boolean checkSubscribe(int subscriberID,int publisherID);
}
