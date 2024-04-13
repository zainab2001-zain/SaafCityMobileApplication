package com.buiseness.saafcitybuiseness.Model;

public class DataPart {
    private final byte[] data;
    private final String fileName;
    private final String type;

    public DataPart(byte[] data, String fileName, String type) {
        this.data = data;
        this.fileName = fileName;
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public String getFileName() {
        return fileName;
    }

    public String getType() {
        return type;
    }
}
