package com.choco.codec;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by choco on 2021/2/16 14:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestBean {
    private String name;
    private Integer age;
    private String[] m;
}
