package com.nictritionist.backend.storage.food_eaten;

import com.nictritionist.backend.bff.dtos.responses.FoodEatenResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FoodEaten {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String barcodeOrItem;
    private String brandOwner;
    private String foodDescription;
    private String amountEaten;
    private String servingSize;
    private String servingSizeUnit;
    @ElementCollection
    private List<Long> nutrientEatenIds;
    private String username;
    private String _date;

    public FoodEaten(String barcodeOrItem, String brandOwner, String foodDescription, String amountEaten,
                     String servingSize, String servingSizeUnit, List<Long> nutrientEatenIds,
                     String username, String _date) {
        this.barcodeOrItem = barcodeOrItem;
        this.brandOwner = brandOwner;
        this.foodDescription = foodDescription;
        this.amountEaten = amountEaten;
        this.servingSize = servingSize;
        this.servingSizeUnit = servingSizeUnit;
        this.nutrientEatenIds = nutrientEatenIds;
        this.username = username;
        this._date = _date;
    }

    public FoodEaten(String barcodeOrItem, String brandOwner, String foodDescription, String servingSize,
                     String servingSizeUnit, List<Long> nutrientEatenIds, String username, String _date) {
        this.barcodeOrItem = barcodeOrItem;
        this.brandOwner = brandOwner;
        this.foodDescription = foodDescription;
        this.servingSize = servingSize;
        this.servingSizeUnit = servingSizeUnit;
        this.nutrientEatenIds = nutrientEatenIds;
        this.username = username;
        this._date = _date;
    }

    public boolean eatenToday(String date) {
        return this._date.equals(date);
    }

    public boolean matchesUsername(String username) {
        return this.username.equals(username);
    }

    public FoodEatenResponseDTO getFoodEatenResponseDTO() {
        return new FoodEatenResponseDTO(
                id,
                barcodeOrItem,
                brandOwner,
                foodDescription,
                amountEaten,
                servingSize,
                servingSizeUnit,
                nutrientEatenIds,
                username,
                _date
        );
    }

}
