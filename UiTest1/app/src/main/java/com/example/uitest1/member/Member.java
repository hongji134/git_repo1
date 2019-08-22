package com.example.uitest1.member;

import java.io.Serializable;

public class Member implements Serializable {
    private String name;
    private String tel;
    private String email;
    private String addr;
    private String filePath;

    public Member() {
    }

    public Member(String name, String tel, String email, String addr, String filePath) {
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.addr = addr;
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
