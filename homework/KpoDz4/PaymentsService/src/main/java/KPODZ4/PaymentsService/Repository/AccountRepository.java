package KPODZ4.PaymentsService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import KPODZ4.PaymentsService.Entity.Account;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {}