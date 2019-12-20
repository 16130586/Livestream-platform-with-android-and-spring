package com.t4.LiveServer.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "types")
public class StreamType {

    private Integer typeId;
	private String typeName;
	private int numberOfType=0;

    @Id
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Column(name = "type_name")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
	
	@Transient
	public int getNumberOfType() {
		return numberOfType;
	}
	public void setNumberOfType(int numberOfType) {
		this.numberOfType = numberOfType;
	}
}
