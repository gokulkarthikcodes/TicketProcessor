package com.sync.ticketprocessor.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "process")
@Getter
@Setter
public class Process {

    @Id
    String id;

    String processName;

    @Indexed
    String createdBy;

    LocalDateTime created;

    String updatedBy;

    LocalDateTime updated;

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
