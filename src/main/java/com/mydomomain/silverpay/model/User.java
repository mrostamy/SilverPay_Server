package com.mydomomain.silverpay.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
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

    @NotNull
    private String lastActive;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private List<Photo> photos;

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    //
//    @OneToMany(mappedBy = "users")
//    private List<Role> roles;
//
//    @OneToMany
//    @JoinColumn
//    private List<Notification> notifications;

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
