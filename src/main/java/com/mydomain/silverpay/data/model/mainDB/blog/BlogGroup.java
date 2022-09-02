package com.mydomain.silverpay.data.model.mainDB.blog;

import com.mydomain.silverpay.data.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class BlogGroup extends BaseEntity<String> {
    public BlogGroup() {
        this.setId(UUID.randomUUID().toString());
    }

    @NotEmpty(message = "parent is required")
    @Length(max = 450,message = "parent max character  is 450")
    private String parent;

    @NotEmpty(message = "blog group name is required")
    @Length(max = 150,message = "blog group name max character  is 150")
    private String name;

    @OneToMany
    @JoinColumn
    private List<Blog> blog;


}
