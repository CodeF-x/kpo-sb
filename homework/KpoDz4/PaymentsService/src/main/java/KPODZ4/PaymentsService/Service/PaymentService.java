package KPODZ4.PaymentsService.Service;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import KPODZ4.PaymentsService.Dto.OrderEvent;
import KPODZ4.PaymentsService.Entity.Account;
import KPODZ4.PaymentsService.Entity.PaymentOutbox;
import KPODZ4.PaymentsService.Repository.AccountRepository;
import KPODZ4.PaymentsService.Repository.PaymentOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final AccountRepository accountRepository;
    private final PaymentOutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void processPayment(OrderEvent event) {
        
        log.info("Processing payment for order: {}", event.getOrderId());
        try {
            Account account = accountRepository.findById(event.getUserId())
                    .orElseThrow(() -> new RuntimeException("Счет не найден для пользователя: " + event.getUserId()));
            String status = "FAILED";
            if (account.getBalance().compareTo(event.getAmount()) >= 0) {
                account.setBalance(account.getBalance().subtract(event.getAmount()));
                accountRepository.save(account);
                status = "PAID";
            }

            sendResponse(event.getOrderId(), status);

        } catch (Exception e) {
            String status = "FAILED";
            sendResponse(event.getOrderId(), status);
        }
    }

    @SneakyThrows
    private void sendResponse(UUID orderId, String status) {
        Map<String, Object> response = Map.of("orderId", orderId, "status", status);
        String json = objectMapper.writeValueAsString(response);
        outboxRepository.save(new PaymentOutbox("PAYMENT_UPDATED", json));
    }
}