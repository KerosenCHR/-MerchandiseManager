package com.example.utils;

public class CodeCache {
    private String key;  // 前端传来的 uuid，唯一标识
    private String code;  // 验证码
    private long timestamp; // 时间戳

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "CodeCache{" +
                "key='" + key + '\'' +
                ", code='" + code + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}