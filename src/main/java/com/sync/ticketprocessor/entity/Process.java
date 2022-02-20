package com.sync.ticketprocessor.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "process")
@Getter
@Setter
public class Process {

    @Id
    String id;

    @Indexed(unique=true)
    Long processId;

    String processName;

    Integer processOrder;

    String createdBy;

}
