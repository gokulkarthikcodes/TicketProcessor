package com.sync.ticketprocessor.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "process")
@Getter
@Setter
public class Process extends Auditable {

    String processName;
}
