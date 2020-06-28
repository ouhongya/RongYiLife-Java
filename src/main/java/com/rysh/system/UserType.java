package com.rysh.system;

/**
 *
 * 用户账号类别
 * @return
 * @author Hsiang Sun
 * @date 2019/10/24 10:58
 */
public enum  UserType {

    内部账户(0),
    商铺账户(1),
    农场账户(2),
    农庄账户(3);

    private final Integer typeCode;

    UserType(final int typeCode) {
        this.typeCode = typeCode;
    }

    public int typeCode(){
        return this.typeCode;
    }


}
