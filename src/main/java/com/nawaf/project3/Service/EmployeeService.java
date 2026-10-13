package com.nawaf.project3.Service;

import com.nawaf.project3.Api.ApiException;
import com.nawaf.project3.DTO.EmployeeDTO;
import com.nawaf.project3.Model.Employee;
import com.nawaf.project3.Model.User;
import com.nawaf.project3.Repository.AuthRepository;
import com.nawaf.project3.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Integer employeeId) {
        Employee employee = employeeRepository.findEmployeeById(employeeId);

        if (employee == null) throw new ApiException("Employee not found");

        return employee;
    }

    @Transactional
    public void register(EmployeeDTO employeeDTO) {

        if (authRepository.findUserByUsername(employeeDTO.getUsername()) != null) throw new ApiException("Username already taken");

        if (authRepository.findUserByEmail(employeeDTO.getEmail()) != null) throw new ApiException("Email already taken");

        User user = new User(
                null,
                employeeDTO.getUsername(),
                passwordEncoder.encode(employeeDTO.getPassword()),
                employeeDTO.getName(),
                employeeDTO.getEmail(),
                "EMPLOYEE",
                null,
                null
        );

        Employee employee = new Employee(
                null,
                employeeDTO.getPosition(),
                employeeDTO.getSalary(),
                user
        );

        user.setEmployee(employee);
        authRepository.save(user);
    }

    @Transactional
    public void updateEmployee(Integer employeeId, EmployeeDTO employeeDTO) {

        Employee employee = getEmployeeById(employeeId);
        User user = employee.getUser();

        User usernameOwner = authRepository.findUserByUsername(employeeDTO.getUsername());

        if (usernameOwner != null && !usernameOwner.getId().equals(user.getId())) throw new ApiException("Username already taken");

        User emailOwner = authRepository.findUserByEmail(employeeDTO.getEmail());

        if (emailOwner != null && !emailOwner.getId().equals(user.getId())) throw new ApiException("Email already taken");

        user.setUsername(employeeDTO.getUsername());
        user.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        user.setName(employeeDTO.getName());
        user.setEmail(employeeDTO.getEmail());

        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());

        authRepository.save(user);
        employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(Integer employeeId) {

        Employee employee = getEmployeeById(employeeId);

        authRepository.delete(employee.getUser());
    }
}