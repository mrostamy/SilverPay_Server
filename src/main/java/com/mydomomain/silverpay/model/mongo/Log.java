package com.mydomomain.silverpay.model.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document
public class Log {

    @Id
    private String id;

    private String name;
    private String level;
    private String message;
    private LocalDateTime time;


}
