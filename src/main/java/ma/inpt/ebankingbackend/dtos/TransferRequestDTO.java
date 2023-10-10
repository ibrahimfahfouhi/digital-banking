package ma.inpt.ebankingbackend.dtos;

import lombok.Data;

@Data
public class TransferRequestDTO {
    private String accountSource;
    private String accountDestination;
    private double amount;
}
