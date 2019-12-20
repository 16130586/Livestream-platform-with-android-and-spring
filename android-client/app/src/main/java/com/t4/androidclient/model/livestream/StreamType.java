package com.t4.androidclient.model.livestream;

public class StreamType {
    private int id;
    private String typeName;
    private int numberOfType;



    public StreamType() {

    }

    public StreamType(int id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
