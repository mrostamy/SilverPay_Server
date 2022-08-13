package com.mydomomain.silverpay.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@Setter
public class TicketContent extends BaseEntity<String> {

    public TicketContent() {
        this.setId(UUID.randomUUID().toString());
    }

    @NotNull(message = "isAdminSide is required")
    private boolean isAdminSide;

    @NotNull(message = "text is required")
    @Length(min = 0,max = 1000,message = "text is between 0 to 1000 character")
    private String text;

    @Length(min = 0,max = 1000,message = "fileUrl is between 0 to 1000 character")
    private String fileUrl;

    @OneToOne
    private Ticket ticket;
}
