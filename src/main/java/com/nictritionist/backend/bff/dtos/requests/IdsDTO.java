package com.nictritionist.backend.bff.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class IdsDTO {
    List<Long> ids;
    public IdsDTO(@JsonProperty("ids") List<Long> ids) {
        this.ids = ids;
    }
}
