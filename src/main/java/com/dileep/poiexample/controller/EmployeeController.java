package com.dileep.poiexample.controller;

import com.dileep.poiexample.entity.EmployeeEntity;
import com.dileep.poiexample.model.EmployeeExcelResponseDTO;
import com.dileep.poiexample.services.EmployeeService;
import com.dileep.poiexample.utils.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api-v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/save")
    public ResponseEntity<EmployeeEntity> save(@RequestBody EmployeeEntity employeeEntity) throws GeneralException {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.save(employeeEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEntity> findEmployeeById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.findEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeEntity>> findAllEmployee() {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.findAllEmployee());
    }

    @PutMapping
    public ResponseEntity<EmployeeEntity> update(@RequestBody EmployeeEntity employeeEntity) throws GeneralException {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.update(employeeEntity));
    }

    @SneakyThrows
    @PostMapping("/upload")
    public ResponseEntity<List<EmployeeExcelResponseDTO>> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.uploadFile(file));
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> getFile(@RequestParam(defaultValue = "FALSE") Boolean isDataRequested) throws IOException {
        ByteArrayOutputStream stream = employeeService.getExcelofEmployees(isDataRequested);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Employee.xlsx");
        return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                header, HttpStatus.CREATED);

    }

}
