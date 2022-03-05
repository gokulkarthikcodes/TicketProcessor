package com.sync.ticketprocessor.dto;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

@Getter
@Setter
public class ProcessDTO {

    String id;

    String processName;

    String createdBy;

    DateTime created;

    String updatedBy;

    @Override
    public String toString() {
        return "ProcessDTO{" +
                "id='" + id + '\'' +
                ", processName='" + processName + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", created=" + created +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
