package ma.inpt.ebankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.inpt.ebankingbackend.dtos.CustomerDTO;
import ma.inpt.ebankingbackend.entities.Customer;
import ma.inpt.ebankingbackend.exception.CustomerNotFoundException;
import ma.inpt.ebankingbackend.service.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*") // pour autoriser au browser l accès aux donnés
public class CustomerRestController {

    private BankAccountService bankAccountService;

    @GetMapping("/customers")  // return the list of customers
    public List<CustomerDTO> customers() {
        return bankAccountService.listCustomer();
    }

    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
        return bankAccountService.searchCustomers(keyword);
    }

    @GetMapping("/customer/{id}")  // faire un recherche sur customer by Id
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return  bankAccountService.getCustomer(customerId);
    }

    @PostMapping("/customers")  // pour ajouter un customer
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return bankAccountService.saveCustomer(customerDTO);
    }
    @PutMapping("/customers/{customerId}") // pour modifier
    public CustomerDTO updateCustomer(@PathVariable Long customerId , @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }
    @DeleteMapping("/customers/{id}")  // pour supprimer
    public void deleteCustomer(@PathVariable Long id) {
        bankAccountService.deleteCustomer(id);
    }
}
