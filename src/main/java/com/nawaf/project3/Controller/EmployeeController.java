package com.nawaf.project3.Controller;

import com.nawaf.project3.Api.ApiResponse;
import com.nawaf.project3.DTO.EmployeeDTO;
import com.nawaf.project3.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getEmployees() {
        return ResponseEntity.status(200).body(employeeService.getEmployees());
    }

    @GetMapping("/get-by-id/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Integer employeeId) {
        return ResponseEntity.status(200).body(employeeService.getEmployeeById(employeeId));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid EmployeeDTO employeeDTO) {
        employeeService.register(employeeDTO);

        return ResponseEntity.status(201).body(new ApiResponse("Employee registered successfully"));
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer employeeId, @RequestBody @Valid EmployeeDTO employeeDTO) {

        employeeService.updateEmployee(employeeId, employeeDTO);

        return ResponseEntity.status(200).body(new ApiResponse("Employee updated successfully"));
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer employeeId) {
        employeeService.deleteEmployee(employeeId);

        return ResponseEntity.status(200).body(new ApiResponse("Employee deleted successfully"));
    }
}