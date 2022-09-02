package com.mydomain.silverpay.data.dto.site.panel.entry;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EntryUpdateDto {

    @NotNull
    @Length(max = 1000)
    private String textForUser;

    @NotEmpty
    @Length(max = 200)
    private String bankTrackingCode;

}
