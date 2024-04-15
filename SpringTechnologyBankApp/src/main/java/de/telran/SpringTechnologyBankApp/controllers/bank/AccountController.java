package de.telran.SpringTechnologyBankApp.controllers.bank;

import de.telran.SpringTechnologyBankApp.dtos.bank.account.AccountDto;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.services.bank.interf.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "Account Controller API")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/add")
    public ResponseEntity<AccountDto> createAccount(
            @Valid
            @RequestBody AccountDto accountDto) {
        System.out.println(accountDto);
        AccountDto createdAccount = accountService.createAccount(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(
            @Valid
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccountById(
            @Valid
            @PathVariable Long id,
            @RequestBody AccountDto accountDto) {
        AccountDto updatedAccount = accountService.updateAccountById(id, accountDto);
        return ResponseEntity.ok(updatedAccount);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccountByIdPatch(
            @Valid
            @PathVariable("id") Long id,
            @RequestBody AccountDto accountDto) {
        return updateAccountById(id, accountDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteAccountById(
            @Valid
            @PathVariable("id") Long id) {
        accountService.deleteAccountById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", String.format("Счет с id %d успешно удален", id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AccountDto>> getAccountsByStatus(
            @Valid
            @PathVariable StatusType status) {
        List<AccountDto> accounts = accountService.getAllAccountsWhereStatusTypeIs(status);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/clients/{clientId}")
    public ResponseEntity<List<AccountDto>> findAccountsByClientId(
            @Valid
            @PathVariable Long clientId) {
        List<AccountDto> accounts = accountService.getAccountsByClientId(clientId);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/managers/{managerId}")
    public ResponseEntity<List<AccountDto>> findAccountsByManagerId(
            @Valid
            @PathVariable Long managerId) {
        List<AccountDto> accounts = accountService.getAccountsByManagerId(managerId);
        return ResponseEntity.ok(accounts);
    }
}