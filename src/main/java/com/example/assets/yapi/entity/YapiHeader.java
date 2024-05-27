package com.example.assets.yapi.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/03/07 16:27
 **/
@Data
public class YapiHeader {

    private String token;

    private List<String> req_query;

    private List<Map<String, String>> req_headers;

    private List<String> req_body_form;

    private String title;

    private String catid;

    private String path;

    private String status;

    private String res_body_type;

    private YapiInterfaceInfoDTO res_body;

    private Boolean switch_notice;

    private String message;

    private String desc;

    private String method;

    private List<String> req_params;
}
