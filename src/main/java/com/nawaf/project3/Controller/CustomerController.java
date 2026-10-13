package com.nawaf.project3.Controller;

import com.nawaf.project3.Api.ApiResponse;
import com.nawaf.project3.DTO.CustomerDTO;
import com.nawaf.project3.Model.User;
import com.nawaf.project3.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/get-all")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<?> getCustomers() {
        return ResponseEntity.status(200).body(customerService.getCustomers());
    }

    @GetMapping("/get-by-id/{customerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer customerId) {
        return ResponseEntity.status(200).body(customerService.getCustomerById(customerId));
    }

    @GetMapping("/my-profile")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getMyCustomer(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(customerService.getMyCustomer(user.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid CustomerDTO customerDTO) {
        customerService.register(customerDTO);

        return ResponseEntity.status(201).body(new ApiResponse("Customer registered successfully"));
    }

    @PutMapping("/update/{customerId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> updateCustomer(@AuthenticationPrincipal User user, @PathVariable Integer customerId, @RequestBody @Valid CustomerDTO customerDTO) {

        customerService.updateCustomer(user.getId(), customerId, customerDTO);

        return ResponseEntity.status(200).body(new ApiResponse("Customer updated successfully"));
    }

    @DeleteMapping("/delete/{customerId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> deleteCustomer(@AuthenticationPrincipal User user, @PathVariable Integer customerId) {

        customerService.deleteCustomer(user.getId(), customerId);

        return ResponseEntity.status(200).body(new ApiResponse("Customer deleted successfully"));
    }
}