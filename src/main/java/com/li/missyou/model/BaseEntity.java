package com.li.missyou.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @JsonIgnore
    @CreatedDate
    private Date createTime;

    @JsonIgnore
    @CreatedDate
    private Date updateTime;

    @JsonIgnore
    private Date deleteTime;
}