package com.rysh.module.serverSystem.beans;

import java.util.List;

public class RoleAndFunIds {
    private String roleName;
    private List<String> functions;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<String> getFunctions() {
        return functions;
    }

    public void setFunctions(List<String> functions) {
        this.functions = functions;
    }
}
