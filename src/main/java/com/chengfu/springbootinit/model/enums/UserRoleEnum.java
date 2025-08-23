package com.chengfu.springbootinit.model.enums;

import lombok.Getter;

/**
 * 用户角色枚举
 *
 * @author: cheng fu
 **/
@Getter
public enum UserRoleEnum {

    USER("用户", "user"),
    ADMIN("管理员", "admin");

    private final String text;
    private final String value;

    UserRoleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /*
     * 根据值获得枚举
     *
     * @author: cheng fu
     * @param: value 枚举值
     * @return: 枚举
     **/
    public static UserRoleEnum getEnumByValue(String value) {

        if (value.isBlank()) {
            return null;
        }
        for (UserRoleEnum anEnum : UserRoleEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
