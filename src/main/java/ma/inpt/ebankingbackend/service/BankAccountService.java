package ma.inpt.ebankingbackend.service;

import ma.inpt.ebankingbackend.dtos.*;
import ma.inpt.ebankingbackend.entities.BankAccount;
import ma.inpt.ebankingbackend.entities.CurrentAccount;
import ma.inpt.ebankingbackend.entities.Customer;
import ma.inpt.ebankingbackend.entities.SavingAccount;
import ma.inpt.ebankingbackend.exception.BalanceNotSufficientException;
import ma.inpt.ebankingbackend.exception.BankAccountNotFoundException;
import ma.inpt.ebankingbackend.exception.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    public List<CustomerDTO> listCustomer();
    public BankAccountDTO getBankAccount(String customerId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accontId, int page, int pageSize) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomers(String keyword);
}
