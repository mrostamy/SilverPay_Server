package com.mydomain.silverpay.data.dto.site.panel.bank_card;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BankCardUpdateDto {

    @NotNull(message = "bank card name is required")
    @Length(min=0,max = 50,message = "bank name must be between 0 to 50 character")
    private String bankName;

    @NotNull(message = "owner name  is required")
    @Length(min=0,max = 50,message = "owner name must be between 0 to 50 character")
    private String ownerName;

    private String shaba;

    private String accountNumber;

    @NotNull(message = "card number  is required")
    @Length(min = 16,max = 16,message = "card number must be 16 character")
    private String cardNumber;

    @NotNull(message = "expire month  is required")
    @Length(min = 2,max = 2,message = "expire month must be 2 character")
    private String expireMonth;

    @NotNull(message = "expire year  is required")
    @Length(min = 4,max = 4,message = "expire month must be 4 character")
    private String expireYear;
}
