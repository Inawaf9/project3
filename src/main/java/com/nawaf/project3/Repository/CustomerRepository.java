package com.nawaf.project3.Repository;

import com.nawaf.project3.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findCustomerById(Integer id);

    Customer findCustomerByUserId(Integer userId);

    Customer findCustomerByPhoneNumber(String phoneNumber);

}