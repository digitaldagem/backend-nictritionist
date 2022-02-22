package com.nictritionist.backend.storage.food_eaten;

import com.nictritionist.backend.bff.dtos.responses.FoodEatenResponseDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class FoodEaten {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String barcode;
    private String brandOwner;
    private String foodDescription;
    private String amountEaten;
    private String servingSize;
    private String servingSizeUnit;
    @ElementCollection
    private List<Long> nutrientEatenIds;
    private String username;
    private String _date;

    public FoodEaten(String barcode, String brandOwner, String foodDescription, String amountEaten,
                     String servingSize, String servingSizeUnit, List<Long> nutrientEatenIds,
                     String username, String _date) {
        this.barcode = barcode;
        this.brandOwner = brandOwner;
        this.foodDescription = foodDescription;
        this.amountEaten = amountEaten;
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
                barcode,
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
