package KPODZ4.PaymentsService.Service;

import KPODZ4.PaymentsService.Entity.Account;
import KPODZ4.PaymentsService.Repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public BigDecimal getBalance(UUID userId) {
        return accountRepository.findById(userId)
                .map(Account::getBalance)
                .orElseThrow(() -> new RuntimeException("Счет для пользователя " + userId + " не найден"));
    }

    @Transactional
    public Account createAccount(UUID userId) {
        if (accountRepository.existsById(userId)) {
            throw new RuntimeException("Счет уже существует");
        }
        Account account = new Account();
        account.setUserId(userId);
        account.setBalance(BigDecimal.ZERO);
        return accountRepository.save(account);
    }

    @Transactional
    public Account deposit(UUID userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной");
        }
        
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Счет не найден"));
        
        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }
}