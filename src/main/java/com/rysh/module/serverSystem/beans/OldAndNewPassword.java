package com.rysh.module.serverSystem.beans;

import lombok.Data;

@Data
public class OldAndNewPassword {
    private String oldPassword;   //旧密码
    private String newPassword;   //新密码
}
