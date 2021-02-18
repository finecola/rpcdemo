package com.choco.proto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by choco on 2021/2/16 13:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    /**
     * 0-成功, 非0-失败
     */
    private Integer code;
    /**
     * 每个code对应的信息
     */
    private String message;
    /**
     * 具体返回的内容
     */
    private Object data;
}
