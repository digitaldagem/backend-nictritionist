package com.nictritionist.backend.bff.dtos.requests;

import lombok.Value;

import java.util.List;

@Value
public class FoodItemEatenRequestDTO {
    String item;
    String brandOwner;
    String foodDescription;
    String servingSize;
    String servingSizeUnit;
    List<Long> nutrientEatenIds;
    String username;
    String date;
}
