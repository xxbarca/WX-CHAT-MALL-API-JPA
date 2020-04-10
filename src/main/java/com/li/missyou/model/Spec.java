package com.li.missyou.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Spec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keyId;
    @Column(name = "`key`")
    private String key;
    private Long valueId;
    @Column(name = "`value`")
    private String value;
}
