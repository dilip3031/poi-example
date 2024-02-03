package com.dileep.poiexample.services;

import com.dileep.poiexample.entity.EmployeeEntity;
import com.dileep.poiexample.entity.RootEntity;
import com.dileep.poiexample.helper.EmployeeHelper;
import com.dileep.poiexample.model.EmployeeExcelResponseDTO;
import com.dileep.poiexample.repositorys.EmployeesRepository;
import com.dileep.poiexample.utils.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class EmployeeService extends RootService {
    private final EmployeesRepository employeesRepository;
    private final EmployeeHelper employeeHelper;
    @Value("${excel.path}")
    private String basePath;

    public EmployeeEntity save(EmployeeEntity employeeEntity) throws GeneralException {
        return (EmployeeEntity) checkSaveAndUpdateRequest(employeeEntity, employeesRepository);
    }

    public EmployeeEntity findEmployeeById(Long id) {
        return employeesRepository.findById(id).orElse(new EmployeeEntity());
    }

    public List<EmployeeEntity> findAllEmployee() {
        return employeesRepository.findAll();
    }

    public EmployeeEntity update(EmployeeEntity employeeEntity) throws GeneralException {
        return (EmployeeEntity) checkSaveAndUpdateRequest(employeeEntity, employeesRepository);
    }

    public List<EmployeeExcelResponseDTO> uploadFile(MultipartFile file) throws IOException {
        List<EmployeeEntity> employessData = employeeHelper.getDataFromExcel(file);
        List<EmployeeExcelResponseDTO> response = new ArrayList<>();
        AtomicReference<Long> count = new AtomicReference<>(0L);
        employessData.forEach(employeeEntity -> {
            count.getAndSet(count.get() + 1);
            try {
                RootEntity rootEntity = checkSaveAndUpdateRequest(employeeEntity, employeesRepository);
                response.add(new EmployeeExcelResponseDTO(employeeEntity, null, null));
            } catch (GeneralException e) {
                response.add(new EmployeeExcelResponseDTO(employeeEntity, count.get(), e.getMassage()));
            }
        });
        return response;
    }

    public ByteArrayOutputStream getExcelofEmployees(Boolean isDataRequested) throws IOException {
        List<EmployeeEntity> allEmployess = (isDataRequested) ? employeesRepository.findAll() : Collections.emptyList();
        ByteArrayOutputStream stream = employeeHelper.getEmployeeExcel(allEmployess, isDataRequested);
        return stream;
    }
}
