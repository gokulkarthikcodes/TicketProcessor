package com.sync.ticketprocessor.entity;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "process")
@Getter
@Setter
public class Process {

    @Id
    String id;

    String processName;

    @Indexed
    String createdBy;

    DateTime created;

    String updatedBy;

    DateTime updated;

    @Override
    public String toString() {
        return "Process{" +
                "id='" + id + '\'' +
                ", processName='" + processName + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", created=" + created +
                ", updatedBy='" + updatedBy + '\'' +
                ", updated=" + updated +
                '}';
    }
}
