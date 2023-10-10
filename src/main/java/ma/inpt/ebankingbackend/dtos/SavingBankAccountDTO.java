package ma.inpt.ebankingbackend.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.inpt.ebankingbackend.entities.AccountOperation;
import ma.inpt.ebankingbackend.entities.Customer;
import ma.inpt.ebankingbackend.enums.AccountStatus;

import java.util.Date;
import java.util.List;


@Data
public class SavingBankAccountDTO extends BankAccountDTO{
    private String id;
    private double balance;
    private Date dateCreaction;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;
}
