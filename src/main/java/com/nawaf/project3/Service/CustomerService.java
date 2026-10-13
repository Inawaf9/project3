package com.nawaf.project3.Service;

import com.nawaf.project3.Api.ApiException;
import com.nawaf.project3.DTO.CustomerDTO;
import com.nawaf.project3.Model.Customer;
import com.nawaf.project3.Model.User;
import com.nawaf.project3.Repository.AuthRepository;
import com.nawaf.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Integer customerId) {

        Customer customer = customerRepository.findCustomerById(customerId);

        if (customer == null) throw new ApiException("Customer not found");

        return customer;
    }

    public Customer getMyCustomer(Integer userId) {

        Customer customer = customerRepository.findCustomerByUserId(userId);

        if (customer == null) throw new ApiException("Customer not found");

        return customer;
    }

    @Transactional
    public void register(CustomerDTO customerDTO) {

        if (authRepository.findUserByUsername(customerDTO.getUsername()) != null) throw new ApiException("Username already taken");

        if (authRepository.findUserByEmail(customerDTO.getEmail()) != null) throw new ApiException("Email already taken");

        if (customerRepository.findCustomerByPhoneNumber(customerDTO.getPhoneNumber()) != null) throw new ApiException("Phone number already taken");

        User user = new User(
                null,
                customerDTO.getUsername(),
                passwordEncoder.encode(customerDTO.getPassword()),
                customerDTO.getName(),
                customerDTO.getEmail(),
                "CUSTOMER",
                null,
                null
        );

        Customer customer = new Customer(
                null,
                customerDTO.getPhoneNumber(),
                user,
                null
        );

        user.setCustomer(customer);
        authRepository.save(user);
    }

    @Transactional
    public void updateCustomer(Integer userId, Integer customerId, CustomerDTO customerDTO) {

        Customer customer = getCustomerById(customerId);
        User user = customer.getUser();

        if (!user.getId().equals(userId)) throw new ApiException("Cannot update another customer");

        User usernameOwner = authRepository.findUserByUsername(customerDTO.getUsername());

        if (usernameOwner != null && !usernameOwner.getId().equals(userId)) throw new ApiException("Username already taken");

        User emailOwner = authRepository.findUserByEmail(customerDTO.getEmail());

        if (emailOwner != null && !emailOwner.getId().equals(userId)) throw new ApiException("Email already taken");

        Customer phoneOwner = customerRepository.findCustomerByPhoneNumber(customerDTO.getPhoneNumber());

        if (phoneOwner != null && !phoneOwner.getId().equals(customerId)) throw new ApiException("Phone number already taken");

        user.setUsername(customerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        user.setName(customerDTO.getName());
        user.setEmail(customerDTO.getEmail());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        authRepository.save(user);
        customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(Integer userId, Integer customerId) {

        Customer customer = getCustomerById(customerId);
        User user = customer.getUser();

        if (!user.getId().equals(userId)) throw new ApiException("Cannot delete another customer");

        if (!customer.getAccounts().isEmpty()) throw new ApiException("Cannot delete customer with bank accounts");

        authRepository.delete(user);
    }
}