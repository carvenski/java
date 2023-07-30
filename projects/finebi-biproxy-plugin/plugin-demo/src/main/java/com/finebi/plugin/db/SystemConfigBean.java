package com.finebi.plugin.biproxy.db;
import java.util.Date;

public class SystemConfigBean {

    private String id;
    private String name;
    private String url;
    private Date time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        // 这里处理下,从url中取id的值存进db
        if (id.contains("id=")) {
            id = id.split("id=")[1];
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}


