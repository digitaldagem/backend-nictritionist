package com.nictritionist.backend.bff.dtos.requests;

import lombok.Value;

@Value
public class NutrientEatenDTO {
    Long id;
    String name;
    Double amount;
    String amountUnit;
    String barcode;
    String username;
    String date;
}
