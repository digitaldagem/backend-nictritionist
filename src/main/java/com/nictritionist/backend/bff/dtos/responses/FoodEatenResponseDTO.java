package com.nictritionist.backend.bff.dtos.responses;

import lombok.Value;

import java.util.List;

@Value
public class FoodEatenResponseDTO {
    Long id;
    String barcode;
    String brandOwner;
    String foodDescription;
    String amountEaten;
    String servingSize;
    String servingSizeUnit;
    List<Long> nutrientEatenIds;
    String username;
    String date;
}
