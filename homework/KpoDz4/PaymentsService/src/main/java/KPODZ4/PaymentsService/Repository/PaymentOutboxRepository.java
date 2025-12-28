package KPODZ4.PaymentsService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import KPODZ4.PaymentsService.Entity.PaymentOutbox;

import java.util.UUID;

public interface PaymentOutboxRepository extends JpaRepository<PaymentOutbox, UUID> {}