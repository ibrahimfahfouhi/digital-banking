package ma.inpt.ebankingbackend;

import ma.inpt.ebankingbackend.dtos.BankAccountDTO;
import ma.inpt.ebankingbackend.dtos.CurrentBankAccountDTO;
import ma.inpt.ebankingbackend.dtos.CustomerDTO;
import ma.inpt.ebankingbackend.dtos.SavingBankAccountDTO;
import ma.inpt.ebankingbackend.entities.*;
import ma.inpt.ebankingbackend.enums.AccountStatus;
import ma.inpt.ebankingbackend.enums.OperationType;
import ma.inpt.ebankingbackend.exception.BalanceNotSufficientException;
import ma.inpt.ebankingbackend.exception.BankAccountNotFoundException;
import ma.inpt.ebankingbackend.exception.CustomerNotFoundException;
import ma.inpt.ebankingbackend.repositories.AccountOperationRepository;
import ma.inpt.ebankingbackend.repositories.BankAccountRepository;
import ma.inpt.ebankingbackend.repositories.CustomerRepository;
import ma.inpt.ebankingbackend.service.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
        return args -> {
            Stream.of("Hassan", "Imran", "Khadija").forEach(name -> {
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setName(name);
                customerDTO.setEmail(name + "@gmail.com");
                bankAccountService.saveCustomer(customerDTO);
            });
            bankAccountService.listCustomer().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000, 9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000, 5.5, customer.getId());

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount:bankAccounts) {
                for (int i = 0; i<10; i++) {
                    String accountId;
                    if (bankAccount instanceof SavingBankAccountDTO) {
                        accountId = ((SavingBankAccountDTO) bankAccount).getId();
                    } else {
                        accountId = ((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    try {
                        bankAccountService.credit(accountId, 10000 + Math.random()*120000, "Credit");
                    } catch (BankAccountNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        bankAccountService.debit(accountId, 1000+Math.random()*9000, "Debit");
                    } catch (BankAccountNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (BalanceNotSufficientException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
    }

    // @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository) {
        return args -> {
            Stream.of("Hassan", "Zineb", "Ziko").forEach(name ->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(customer -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*9000);
                currentAccount.setDateCreaction(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(customer);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*70000);
                savingAccount.setDateCreaction(new Date());
                savingAccount.setStatus(AccountStatus.ACTIVATED);
                savingAccount.setCustomer(customer);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });

            bankAccountRepository.findAll().forEach(acc -> {
                for(int i=0; i<10; i++ ){
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12990);
                    accountOperation.setType(Math.random()>0.5?OperationType.DEBIT:OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);

                    accountOperationRepository.save(accountOperation);

                }
            });


            BankAccount bankAccount = bankAccountRepository.findById("acffeb28-51dd-4717-8aa9-16dadf3d21e3").orElse(null);
            //System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getDateCreaction());
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getClass().getSimpleName());

            if (bankAccount instanceof CurrentAccount) {
                System.out.println("over draft => " + ((CurrentAccount)bankAccount).getOverDraft());
            } else if (bankAccount instanceof SavingAccount) {
                System.out.println("Rate => " + ((SavingAccount)bankAccount).getInterestRate());
            }
            bankAccount.getAccountOperations().forEach(op -> {
                System.out.println("=======================");
                System.out.println(op.getOperationDate());
                System.out.println(op.getType());
                System.out.println(op.getAmount());
            });

        };
    }

}
