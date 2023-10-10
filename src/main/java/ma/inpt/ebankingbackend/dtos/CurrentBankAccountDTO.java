package ma.inpt.ebankingbackend.dtos;

import lombok.Data;
import ma.inpt.ebankingbackend.enums.AccountStatus;

import java.util.Date;


@Data
public class CurrentBankAccountDTO extends BankAccountDTO{
    private String id;
    private double balance;
    private Date dateCreaction;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;
}
