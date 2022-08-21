package com.mydomomain.silverpay.dto.site.panel.entry;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EntryUpdateDto {

    @NotNull
    @Length(max = 1000)
    private String textForUser;

}
