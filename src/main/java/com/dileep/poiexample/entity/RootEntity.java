package com.dileep.poiexample.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class RootEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_date")
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    @Column(name = "init_version")
    private Integer initVersion;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_date")
    @UpdateTimestamp
    private Date updateDate;
}
