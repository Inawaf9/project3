package com.nawaf.project3.Repository;

import com.nawaf.project3.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findAccountById(Integer id);

    Account findAccountByAccountNumber(String accountNumber);

    List<Account> findAccountsByCustomerId(Integer customerId);

}