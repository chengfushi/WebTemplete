package com.chengfu.springbootinit.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求类
 *
 * @author: Chengfu Shi
 **/
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}


