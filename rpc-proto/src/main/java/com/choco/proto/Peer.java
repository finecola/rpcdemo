package com.choco.proto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by choco on 2021/2/16 13:28
 */

/**
 * 表示一个端点
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Peer {
    private String host;
    private Integer port;
}
