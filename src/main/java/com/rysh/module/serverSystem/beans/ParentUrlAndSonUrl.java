package com.rysh.module.serverSystem.beans;

import java.util.ArrayList;
import java.util.List;

public class ParentUrlAndSonUrl {
    private String parentUrl;
    private List<String> sonUrl=new ArrayList<>();
    private User user;

    public String getParentUrl() {
        return parentUrl;
    }

    public void setParentUrl(String parentUrl) {
        this.parentUrl = parentUrl;
    }

    public List<String> getSonUrl() {
        return sonUrl;
    }

    public void setSonUrl(List<String> sonUrl) {
        this.sonUrl = sonUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
