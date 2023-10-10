package ma.inpt.ebankingbackend.repositories;

import ma.inpt.ebankingbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
