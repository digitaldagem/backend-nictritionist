package com.nictritionist.backend.bff.dtos.responses;

import lombok.Value;

@Value
public class NutrientEatenForDailyTotalDTO {

    String name;
    Double amount;
    String amountUnit;
    String username;

}
