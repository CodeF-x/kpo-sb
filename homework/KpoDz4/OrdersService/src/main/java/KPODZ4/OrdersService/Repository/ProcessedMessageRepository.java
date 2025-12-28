package KPODZ4.OrdersService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import KPODZ4.OrdersService.Entity.ProcessedMessage;


public interface ProcessedMessageRepository extends JpaRepository<ProcessedMessage, String> {}