package com.mydomain.silverpay.data.model.mainDB;


import com.mydomain.silverpay.data.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Ticket extends BaseEntity<String> {

    public Ticket() {
        this.setId(UUID.randomUUID().toString());
    }

    @NotNull(message = "title is required")
    @Length(min = 0, max = 50, message = "title must be between 0 to 50 character")
    private String title;

    @NotNull(message = "department is required")
    @Length(min = 0, max = 50, message = "department must be between 0 to 50 character")
    private String department;

    @NotNull(message = "closed is required")
    private boolean closed;

    @NotNull(message = "level is required")
    private short level;

    @NotNull(message = "isAdminSide is required")
    private boolean isAdminSide;

    @OneToOne
    private User user;

    @OneToMany
    @JoinColumn
    List<TicketContent> ticketContentList;
}
