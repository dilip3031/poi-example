package com.dileep.poiexample.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class EmployeeEntity extends RootEntity {
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private Integer age;
    @Column
    private Long salary;
    @Column
    private Double experience;


}
