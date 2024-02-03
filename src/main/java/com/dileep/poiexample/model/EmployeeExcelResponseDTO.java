package com.dileep.poiexample.model;

import com.dileep.poiexample.entity.EmployeeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeExcelResponseDTO {
    private String name;
    private String email;
    private Integer age;
    private Long salary;
    private Double experience;
    private Long id;
    private Long rowNumber;
    private String massage;

    public EmployeeExcelResponseDTO(EmployeeEntity employeeEntity, Long rowNumber, String massage) {
        this.name = employeeEntity.getName();
        this.email = employeeEntity.getEmail();
        this.age = employeeEntity.getAge();
        this.salary = employeeEntity.getSalary();
        this.experience = employeeEntity.getExperience();
        this.id = employeeEntity.getId();
        this.rowNumber = rowNumber;
        this.massage = massage;
    }
}
