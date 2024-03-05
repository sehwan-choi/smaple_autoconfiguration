package com.mylib.user.client.dto;

public class UserCompany {

    private String name;

    private String catchPhrase;

    private String bs;

    @Override
    public String toString() {
        return "UserCompany{" +
                "name='" + name + '\'' +
                ", catchPhrase='" + catchPhrase + '\'' +
                ", bs='" + bs + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public String getBs() {
        return bs;
    }
}
