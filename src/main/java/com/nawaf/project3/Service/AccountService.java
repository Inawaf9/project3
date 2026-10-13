package com.nawaf.project3.Service;

import com.nawaf.project3.Api.ApiException;
import com.nawaf.project3.Model.Account;
import com.nawaf.project3.Model.Customer;
import com.nawaf.project3.Repository.AccountRepository;
import com.nawaf.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Integer accountId) {

        Account account = accountRepository.findAccountById(accountId);

        if (account == null) throw new ApiException("Account not found");

        return account;
    }

    public Account getAccountDetails(Integer userId, Integer accountId) {

        Account account = getAccountById(accountId);

        checkAccountOwner(userId, account);

        return account;
    }

    public List<Account> getUserAccounts(Integer userId) {

        Customer customer = customerRepository.findCustomerByUserId(userId);

        if (customer == null) throw new ApiException("Customer not found");

        return accountRepository.findAccountsByCustomerId(customer.getId());
    }

    @Transactional
    public void addAccount(Integer userId, String accountNumber) {

        Customer customer = customerRepository.findCustomerByUserId(userId);

        if (customer == null) throw new ApiException("Customer not found");

        if (accountNumber == null || !accountNumber.matches("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$")) throw new ApiException("Invalid account number");

        if (accountRepository.findAccountByAccountNumber(accountNumber) != null) throw new ApiException("Account number already exists");

        Account account = new Account(
                null,
                accountNumber,
                BigDecimal.ZERO,
                false,
                customer
        );

        accountRepository.save(account);
    }

    @Transactional
    public void updateAccount(Integer accountId, String accountNumber) {

        Account account = getAccountById(accountId);

        if (accountNumber == null || !accountNumber.matches("^\\d{4}-\\d{4}-\\d{4}-\\d{4}-\\d{4}$")) throw new ApiException("Invalid account number");

        Account existingAccount = accountRepository.findAccountByAccountNumber(accountNumber);

        if (existingAccount != null && !existingAccount.getId().equals(accountId)) throw new ApiException("Account number already exists");

        account.setAccountNumber(accountNumber);
    }

    @Transactional
    public void deleteAccount(Integer accountId) {

        Account account = getAccountById(accountId);

        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) throw new ApiException("Cannot delete account with balance");

        accountRepository.delete(account);
    }

    @Transactional
    public void activateAccount(Integer accountId) {

        Account account = getAccountById(accountId);

        if (account.getIsActive()) throw new ApiException("Account is already active");

        account.setIsActive(true);
    }

    @Transactional
    public void blockAccount(Integer accountId) {

        Account account = getAccountById(accountId);

        if (!account.getIsActive()) throw new ApiException("Account is already blocked or inactive");

        account.setIsActive(false);
    }

    @Transactional
    public void deposit(Integer userId, Integer accountId, BigDecimal amount) {

        validateAmount(amount);

        Account account = getAccountById(accountId);

        checkAccountOwner(userId, account);
        checkAccountActive(account);

        account.setBalance(account.getBalance().add(amount));
    }

    @Transactional
    public void withdraw(Integer userId, Integer accountId, BigDecimal amount) {

        validateAmount(amount);

        Account account = getAccountById(accountId);

        checkAccountOwner(userId, account);
        checkAccountActive(account);

        if (account.getBalance().compareTo(amount) < 0) throw new ApiException("Insufficient balance");

        account.setBalance(account.getBalance().subtract(amount));
    }

    @Transactional
    public void transfer(Integer userId, Integer fromAccountId, Integer toAccountId, BigDecimal amount) {

        validateAmount(amount);

        if (fromAccountId.equals(toAccountId)) throw new ApiException("Cannot transfer to the same account");

        Account fromAccount = getAccountById(fromAccountId);
        Account toAccount = getAccountById(toAccountId);

        checkAccountOwner(userId, fromAccount);
        checkAccountActive(fromAccount);
        checkAccountActive(toAccount);

        if (fromAccount.getBalance().compareTo(amount) < 0) throw new ApiException("Insufficient balance");

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
    }

    private void checkAccountOwner(Integer userId, Account account) {

        if (!account.getCustomer().getUser().getId().equals(userId)) throw new ApiException("You do not own this account");
    }

    private void checkAccountActive(Account account) {

        if (!account.getIsActive()) throw new ApiException("Account is not active");
    }

    private void validateAmount(BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new ApiException("Amount must be greater than zero");

        if (amount.scale() > 2) throw new ApiException("Amount cannot have more than two decimal places");
    }
}