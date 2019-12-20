package com.t4.LiveServer.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "subscriber")
@IdClass(Subscribe.class)
public class Subscribe implements Serializable {
//	@EmbeddedId
//	private SubscribePK id;
	@Id
	private Integer subscriber_id;
	@Id
	private Integer publisher_id;
	
	@Column(name = "subscriber_id")
	public void setSubscriber_id(Integer subscriber_id) {
		this.subscriber_id = subscriber_id;
	}
	
	public Integer getSubscriber_id() {
		return subscriber_id;
	}
	
	@Column(name = "publisher_id")
	public void setPublisher_id(Integer publisher_id) {
		this.publisher_id = publisher_id;
	}
	
	public Integer getPublisher_id() {
		return publisher_id;
	}
}
