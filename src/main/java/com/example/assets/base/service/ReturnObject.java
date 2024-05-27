package com.example.assets.base.service;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReturnObject<T> implements Serializable {

    private T data;

    private Integer page;

    private Integer size;

    private Integer total;
}
