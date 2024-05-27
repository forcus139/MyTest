package com.example.assets.base.service;


import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class RtnResult extends LinkedHashMap<String, Object> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_ERROR = 500;

//    @ParamIgnore
//    private static final long serialVersionUID = 5095439538716185017L;
//    @ParamDesc("返回标识：成功（200）Or失败（500）")
//    private int code;
//    @ParamDesc("失败消息")
//    private String msg;
//    @ParamDesc("返回数据")
//    private T data;
//    @ParamDesc("是否把返回值包装成R")
//    private Boolean decorate;

//    public R() {
//        this.decorate = Boolean.TRUE;
//    }

    public RtnResult() {
    }

    public RtnResult(int code, String msg, Object data) {
        this.setCode(code);
        this.setMsg(msg);
        this.setData(data);
    }

    public RtnResult(Map<String, Object> map) {
        Iterator var2 = map.keySet().iterator();

        while(var2.hasNext()) {
            String key = (String)var2.next();
            this.set(key, map.get(key));
        }

    }

    public Integer getCode() {
        return (Integer)this.get("code");
    }

    public String getMsg() {
        return (String)this.get("msg");
    }

    public Object getData() {
        return this.get("data");
    }

    public RtnResult setCode(int code) {
        this.put("code", code);
        return this;
    }

    public RtnResult setMsg(String msg) {
        this.put("msg", msg);
        return this;
    }

    public RtnResult setData(Object data) {
        this.put("data", data);
        return this;
    }

    public RtnResult set(String key, Object data) {
        this.put(key, data);
        return this;
    }

    public RtnResult setMap(Map<String, ?> map) {
        Iterator var2 = map.keySet().iterator();

        while(var2.hasNext()) {
            String key = (String)var2.next();
            this.put(key, map.get(key));
        }

        return this;
    }

    public static RtnResult success() {
        return new RtnResult(200, "ok", (Object)null);
    }

    public static RtnResult success(String msg, Object data) {
        return new RtnResult(200, msg, (Object)data);
    }

    public static RtnResult success(Object data) {
        return new RtnResult(200, null, (Object)data);
    }

    public static RtnResult fail() {
        return new RtnResult(500, "error", (Object)null);
    }

    public static RtnResult fail(String msg) {
        return new RtnResult(500, msg, (Object)null);
    }

    public static RtnResult fail(Integer code, String msg) {
        return new RtnResult(code, msg, (Object)null);
    }

    public static RtnResult restResult(Integer code, String msg, Object data) {
        return new RtnResult(code, msg, (Object)data);
    }

    public static RtnResult get(int code, String msg, Object data) {
        return new RtnResult(code, msg, data);
    }

//    public String toString() {
//        return "{\"code\": " + this.getCode() + ", \"msg\": " + this.transValue(this.getMsg()) + ", \"data\": " + this.transValue(this.getData()) + "}";
//    }
//
//    private String transValue(Object value) {
//        return value instanceof String ? "\"" + value + "\"" : String.valueOf(value);
//    }
}
