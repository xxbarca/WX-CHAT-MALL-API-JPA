package com.li.missyou.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.lang.management.LockInfo;

@Entity
@Setter
@Getter
@Where(clause = "delete_time is null")
public class GridCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String img;
    private String name;

    private Long categoryId;
    private Long rootCategoryId;

}
