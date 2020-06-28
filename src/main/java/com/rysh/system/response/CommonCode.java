package com.rysh.system.response;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@ToString
public enum CommonCode implements ResultCode{

    SUCCESS(true,10000,"操作成功！"),
    VERIFICATION_ERROR(false,20001,"验证码错误"),
    FAIL(false,11111,"操作失败！"),
    PHONEREGISTERED(false,30001,"当前手机号码已注册,请直接登陆"),
    UNREGISTERED(false,30002,"未注册,请先注册"),
    HOUSEREGISTERED(false,30003,"当前户号已被注册,如有疑问请点击申诉"),
    UPLOAD_ERROR(false,40000,"图片上传失败"),
    UNAUTHENTICATED(false,10001,"此操作需要登陆系统！"),
    UNAUTHENTIC(false,10002,"权限不足，无权操作！"),
    MAX_SIZE_ERROR(false,60000,"图片过大上传失败"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！"),
    PASSOWRDERROR(false,88888,"用户名或密码错误！"),
    NOT_FIND_CODE(false,10086,"请先发送验证码"),
    NOT_FIND_PHONE(false,22222,"请输入正确的手机号码"),
    UPPER_LIMIT(false,33333,"今日发送验证码次数已达上限"),
    NOT_BINDING_COMMUNITY(true,44444,"登陆成功，但是未绑定社区"),
    TOKEN_ERROR(false,1,"token错误"),
    ORDERS_ERROR(false,2,"提交订单失败"),
    PARAMETER_ERROR(false,3,"参数错误"),
    GET_OUT(false,4,"非法操作"),
    PHONE_ERROR(false,5,"电话号码格式错误"),
    BEYOND_LIMITNUM(false,6,"超出数量限制"),
    DOUBLE_CONTENT(false,7,"重复的内容");
    //    private static ImmutableMap<Integer, CommonCode> codes ;
    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private CommonCode(boolean success,int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }
    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }


}
