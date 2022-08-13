package com.mydomomain.silverpay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mydomomain.silverpay.model.blog.Blog;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "users")
public class User extends BaseEntity<String> implements UserDetails, Serializable {

    public User() {
        this.setId(UUID.randomUUID().toString());
    }

    @NotNull
    @Length(min = 0, max = 100)
    private String name;

    @NotNull
    private String username;

    @NotNull
    @Length(min = 0, max = 100)
    private String phoneNumber;

    @NotNull
    @Length(min = 0, max = 500)
    private String Address;

    @NotNull
    private String password;

    private String gender;

    private String DateOfBirth;

    @Length(min = 0, max = 100)
    private String city;

    @NotNull
    private boolean isActive;

    @NotNull
    private boolean status;

    private String lastActive;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonManagedReference
    private List<Photo> photos;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns =@JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonManagedReference
    private List<Role> roles=new ArrayList<>();

    @OneToMany
    @JoinColumn
    private List<Notification> notifications;

    @Transient
    private List<Photo> testPhotos;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private List<BankCard> bankCards;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    @OneToMany
    @JoinColumn
    private List<Token> tokens;

    @OneToMany
    @JoinColumn
    private List<Document> documents;

    @OneToMany
    @JoinColumn
    private List<Wallet> wallets;

    @OneToMany
    @JoinColumn
    private List<Ticket> tickets;


    @OneToMany
    @JoinColumn
    private List<EasyPay> easyPays;

    @OneToMany
    @JoinColumn
    private List<Blog> blogs;

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {


        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }


        return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return false;
    }


}



























