package KPODZ4.PaymentsService.Controller;

import KPODZ4.PaymentsService.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") 
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/{userId}/create")
    public void create(@PathVariable UUID userId) {
        accountService.createAccount(userId);
    }

    @PostMapping("/{userId}/deposit")
    public void deposit(@PathVariable UUID userId, @RequestParam BigDecimal amount) {
        accountService.deposit(userId, amount);
    }

    @GetMapping("/{userId}/balance")
    public BigDecimal getBalance(@PathVariable UUID userId) {
        return accountService.getBalance(userId);
    }
}