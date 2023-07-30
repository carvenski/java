package com.finebi.plugin.biproxy.db;

import com.fr.stable.AssistUtils;
import com.fr.stable.db.entity.BaseEntity;
import com.fr.third.javax.persistence.Column;
import com.fr.third.javax.persistence.Entity;
import com.fr.third.javax.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "fine_plugin_biproxy_url_map")
public class SystemConfigEntity extends BaseEntity {

    // 还有个自带的id字段用来存 插件给报表生成的发布id
    // 报表访问路径 /webroot/decision/publish/id

    @Column(name = "name", nullable = false, length=255)
    private String name;

    @Column(name = "url", nullable = false, length=255)
    private String url;

    @Column(name = "time")
    private Date time;

    public SystemConfigEntity() {

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

    @Override
    public String toString() {
        return AssistUtils.toString(this);
    }
}
