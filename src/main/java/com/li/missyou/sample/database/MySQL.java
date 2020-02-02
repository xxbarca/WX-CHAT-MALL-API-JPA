package com.li.missyou.sample.database;

import com.li.missyou.sample.IConnect;

public class MySQL implements IConnect {

    private String ip = "localhost";
    private Integer port = 3306;

    public MySQL(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    public MySQL() {
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public void connect() {

        System.out.println(this.ip + this.port);

    }
}
