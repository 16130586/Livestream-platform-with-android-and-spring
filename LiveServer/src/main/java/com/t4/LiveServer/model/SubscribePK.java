package com.t4.LiveServer.model;


import lombok.Data;

import javax.persistence.*;

@Embeddable
public class SubscribePK {
	@Id
	private String subscriber_id;
	@Id
	private String publisher_id;
	
}
