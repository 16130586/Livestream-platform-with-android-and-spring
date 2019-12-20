package com.t4.LiveServer.business.imp;

import com.t4.LiveServer.business.interfaze.SubscribeBusiness;
import com.t4.LiveServer.model.Subscribe;
import com.t4.LiveServer.repository.SubcribleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscribeBusinessImp implements SubscribeBusiness {
	@Autowired
	private SubcribleRepository subscibleRepository;
	
	@Override
	public boolean doSubscribe(int subscriberID, int publisherID) {
		int condition1 = subscibleRepository.repoDoSubscribe(subscriberID,publisherID);
		int condition2 = subscibleRepository.repoAddSubTotal(publisherID);
		if(condition1>0&&condition2>0){
		 	return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean undoSubscribe(int subscriberID, int publisherID) {
		int condition1 = subscibleRepository.repoUndoSubscribe(subscriberID,publisherID);
		int condition2 = subscibleRepository.repoRemoveSubTotal(publisherID);
		if(condition1>0&&condition2>0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean checkSubscribe(int subscriberID, int publisherID) {
		Subscribe checkSubscribe = subscibleRepository.repoCheckSubscribe(subscriberID,publisherID);
		if((checkSubscribe)==null){
			return true;
		}else{
			return false;
		}
	}
}
