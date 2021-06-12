package com.nictritionist.backend.bff.dtos.responses;

import lombok.Value;

@Value
public class MessageDTO {

    String message;

    public MessageDTO(String message) {
        this.message = message;
    }

}
