package com.t4.LiveServer.business.imp;

import com.t4.LiveServer.business.interfaze.StreamTypeBusiness;
import com.t4.LiveServer.model.Stream;
import com.t4.LiveServer.model.StreamType;
import com.t4.LiveServer.repository.StreamRepository;
import com.t4.LiveServer.repository.StreamTypeRepository;
import com.t4.LiveServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreamTypeBusinessImp implements StreamTypeBusiness {
	@Autowired
	private StreamTypeRepository streamTypeRepository;
	
	@Override
	public List<StreamType> listStreamTypeByUserID(int userID){
		return streamTypeRepository.getListTypeByUserID(userID);
	}
	

	
}
