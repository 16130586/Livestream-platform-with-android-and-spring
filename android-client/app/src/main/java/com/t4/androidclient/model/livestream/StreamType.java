package com.t4.androidclient.model.livestream;

public class StreamType {
    private int typeId;
    private String typeName;
    private int numberOfType;



    public StreamType() {

    }

    public StreamType(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    public int getNumberOfType() {
        return numberOfType;
    }

    public void setNumberOfType(int numberOfType) {
        this.numberOfType = numberOfType;
    }
}
