package com.nictritionist.backend.storage.nutrient_eaten;

import com.nictritionist.backend.bff.dtos.requests.NutrientEatenDTO;
import com.nictritionist.backend.bff.dtos.responses.NutrientEatenForDailyTotalDTO;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class NutrientEaten {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Double amount;
    private String amountUnit;
    private String barcode;
    private String username;
    private String _date;

    public NutrientEaten(String name, Double amount, String amountUnit,
                         String barcode, String username, String date) {
        this.name = name;
        this.amount = amount;
        this.amountUnit = amountUnit;
        this.barcode = barcode;
        this.username = username;
        this._date = date;
    }

    public boolean eatenToday(String date) {
        return this._date.equals(date);
    }

    public boolean matchesUsername(String username) {
        return this.username.equals(username);
    }

    public NutrientEatenForDailyTotalDTO getNutrientEatenForDailyTotalDTO() {
        return new NutrientEatenForDailyTotalDTO(name, amount, amountUnit, username);
    }

    public NutrientEatenDTO getNutrientEatenDTO() {
        return new NutrientEatenDTO(id, name, amount, amountUnit, barcode, username, _date);
    }

}
