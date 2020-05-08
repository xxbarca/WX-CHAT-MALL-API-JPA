package com.li.missyou.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @JsonIgnore
    @Column(insertable = false, updatable = false)
    public Date createTime;
    @Column(insertable = false, updatable = false)
    @JsonIgnore
    public Date updateTime;
    @JsonIgnore
    public Date deleteTime;

}