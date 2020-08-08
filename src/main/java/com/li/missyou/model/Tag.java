package com.li.missyou.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Tag extends BaseEntity {
    @Id
    private Long id;

    private Integer type;

    private String title;

    private Integer highlight;

    private String description;
}
