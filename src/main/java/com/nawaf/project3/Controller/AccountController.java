package com.nawaf.project3.Controller;

import com.nawaf.project3.Api.ApiResponse;
import com.nawaf.project3.Model.User;
import com.nawaf.project3.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/get-all")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<?> getAccounts() {
        return ResponseEntity.status(200).body(accountService.getAccounts());
    }

    @GetMapping("/get-by-id/{accountId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<?> getAccountById(@PathVariable Integer accountId) {
        return ResponseEntity.status(200).body(accountService.getAccountById(accountId));
    }

    @GetMapping("/details/{accountId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getAccountDetails(@AuthenticationPrincipal User user, @PathVariable Integer accountId) {
        return ResponseEntity.status(200).body(accountService.getAccountDetails(user.getId(), accountId));
    }

    @GetMapping("/my-accounts")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getUserAccounts(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(accountService.getUserAccounts(user.getId()));
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> addAccount(@AuthenticationPrincipal User user, @RequestParam String accountNumber) {

        accountService.addAccount(user.getId(), accountNumber);

        return ResponseEntity.status(201).body(new ApiResponse("Account created successfully"));
    }

    @PutMapping("/update/{accountId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<?> updateAccount(@PathVariable Integer accountId, @RequestParam String accountNumber) {

        accountService.updateAccount(accountId, accountNumber);

        return ResponseEntity.status(200).body(new ApiResponse("Account updated successfully"));
    }

    @DeleteMapping("/delete/{accountId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAccount(@PathVariable Integer accountId) {

        accountService.deleteAccount(accountId);

        return ResponseEntity.status(200).body(new ApiResponse("Account deleted successfully"));
    }

    @PutMapping("/activate/{accountId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<?> activateAccount(@PathVariable Integer accountId) {

        accountService.activateAccount(accountId);

        return ResponseEntity.status(200).body(new ApiResponse("Account activated successfully"));
    }

    @PutMapping("/block/{accountId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<?> blockAccount(@PathVariable Integer accountId) {

        accountService.blockAccount(accountId);

        return ResponseEntity.status(200).body(new ApiResponse("Account blocked successfully"));
    }

    @PutMapping("/deposit/{accountId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> deposit(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @RequestParam BigDecimal amount) {

        accountService.deposit(user.getId(), accountId, amount);

        return ResponseEntity.status(200).body(new ApiResponse("Money deposited successfully"));
    }

    @PutMapping("/withdraw/{accountId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal User user, @PathVariable Integer accountId, @RequestParam BigDecimal amount) {

        accountService.withdraw(user.getId(), accountId, amount);

        return ResponseEntity.status(200).body(new ApiResponse("Money withdrawn successfully"));
    }

    @PutMapping("/transfer/{fromAccountId}/{toAccountId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> transfer(@AuthenticationPrincipal User user, @PathVariable Integer fromAccountId, @PathVariable Integer toAccountId, @RequestParam BigDecimal amount) {

        accountService.transfer(user.getId(), fromAccountId, toAccountId, amount);

        return ResponseEntity.status(200).body(new ApiResponse("Money transferred successfully"));
    }
}