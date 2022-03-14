package com.sync.ticketprocessor.dto;

import com.sync.ticketprocessor.entity.Auditable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessDTO extends Auditable {

    String processName;

}
