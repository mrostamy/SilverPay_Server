package com.mydomomain.silverpay.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.time.LocalDateTime;


@Getter
@Setter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})//barrasy shavad
public class BaseEntity<T extends Serializable> implements Serializable {

    @Id
    private T id;


    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @PrePersist
    private void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void modifiedAt() {
        this.modifiedAt = LocalDateTime.now();
    }

}
