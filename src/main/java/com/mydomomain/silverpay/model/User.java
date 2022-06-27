package com.mydomomain.silverpay.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class User extends BaseEntity<String> implements UserDetails {

    public User() {
        this.setId(UUID.randomUUID().toString());
    }

    @NotNull
    private String name;

    @NotNull
    private String username;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String Address;

    @NotNull
    private String password;

    private boolean gender;

    private LocalDateTime DateOfBirth;

    private String city;
    @NotNull
    private boolean isActive;
    @NotNull
    private boolean status;

    @OneToMany
    @JoinColumn
    private List<Photo> photos;
//
//    @OneToMany(mappedBy = "users")
//    private List<Role> roles;
//
//    @OneToMany
//    @JoinColumn
//    private List<Notification> notifications;

    @OneToMany
    @JoinColumn
    private List<BankCard> bankCards;



//    @OneToMany
//    @JoinColumn
//    private List<Token> tokens;
//
//    @OneToMany
//    @JoinColumn
//    private List<Document> documents;
//
//    @OneToMany
//    @JoinColumn
//    private List<Wallet> wallets;
//
//    @OneToMany
//    @JoinColumn
//    private List<Ticket> tickets;
//
//    @OneToMany
//    @JoinColumn
//    private List<EasyPay> easyPays;
//
//    @OneToMany
//    @JoinColumn
//    private List<Blog> blogs;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


}
