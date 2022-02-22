package com.nictritionist.backend.bff.controllers.logged_in;

import com.nictritionist.backend.bff.dtos.requests.*;
import com.nictritionist.backend.bff.dtos.responses.*;
import com.nictritionist.backend.storage.food_eaten.FoodEaten;
import com.nictritionist.backend.storage.food_eaten.FoodEatenRepository;
import com.nictritionist.backend.storage.nutrient_eaten.*;
import com.nictritionist.backend.storage.user.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/i/users")
public class UserController {

    private final UserRepository userRepository;
    private final NutrientEatenRepository nutrientEatenRepository;
    private final FoodEatenRepository foodEatenRepository;

    @Autowired
    public UserController(UserRepository userRepository, NutrientEatenRepository nutrientEatenRepository, FoodEatenRepository foodEatenRepository) {
        this.userRepository = userRepository;
        this.nutrientEatenRepository = nutrientEatenRepository;
        this.foodEatenRepository = foodEatenRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(User::getUserDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserDTO(@PathVariable("id") Long id) {
        if(userRepository.findById(id).isPresent()) {
            return ResponseEntity.ok(userRepository.findById(id).get().getUserDTO());
        }
        return ResponseEntity
                .badRequest()
                .body("Error: User id not found!");
    }

    @PostMapping("/nutrient_eaten")
    @PreAuthorize("hasRole('USER')")
    public NutrientEatenDTO addNutrientEaten(@RequestBody NutrientEatenDTO nutrientEatenDTO) {
        NutrientEaten nutrientEaten = new NutrientEaten(nutrientEatenDTO.getName(),
                Math.round(nutrientEatenDTO.getAmount() * 100) / 100.0,
                nutrientEatenDTO.getAmountUnit(), nutrientEatenDTO.getBarcode(),
                nutrientEatenDTO.getUsername(), nutrientEatenDTO.getDate());
        System.out.println(nutrientEatenDTO);
        return nutrientEatenRepository.save(nutrientEaten).getNutrientEatenDTO();
    }

    @DeleteMapping("/nutrients_eaten")
    @PreAuthorize("hasRole('USER')")
    public String deleteNutrientsEaten(@RequestBody IdsDTO idsDTO) {
        for(Long id : idsDTO.getIds()) {
            nutrientEatenRepository.deleteById(id);
        }
        return "intakeList";
    }

    @GetMapping("/nutrients_eaten/today_totaled/{username}/{date}")
    @PreAuthorize("hasRole('USER')")
    public List<NutrientEatenForDailyTotalDTO> getAllNutrientsEatenTodayTotaled(
            @PathVariable("username") String username, @PathVariable("date") String date) {
        List<NutrientEatenForDailyTotalDTO> nutrientsEatenToday =
                nutrientEatenRepository.findAll().stream()
                        .filter(nutrientEaten -> nutrientEaten.eatenToday(date))
                        .filter(nutrientEaten -> nutrientEaten.matchesUsername(username))
                        .map(NutrientEaten::getNutrientEatenForDailyTotalDTO).collect(Collectors.toList());
        Set<NutrientEatenForDailyTotalDTO> nutrientsEatenTodayTotaled = new HashSet<>();
        for (var i = 0; i < nutrientsEatenToday.size(); i++) {
            double amount = 0;
            for (NutrientEatenForDailyTotalDTO nutrientEatenForDailyTotalDTO : nutrientsEatenToday) {
                if (nutrientsEatenToday.get(i).getName().equals(nutrientEatenForDailyTotalDTO.getName())) {
                    amount = Math.round((nutrientEatenForDailyTotalDTO.getAmount() + amount) * 100.0) / 100.0;
                }
            }
            nutrientsEatenTodayTotaled.add(
                    new NutrientEatenForDailyTotalDTO(
                            nutrientsEatenToday.get(i).getName(),
                            amount,
                            nutrientsEatenToday.get(i).getAmountUnit(),
                            nutrientsEatenToday.get(i).getUsername()
                    )
            );
        }
        return new ArrayList<>(nutrientsEatenTodayTotaled);
    }

    @PostMapping("/nutrients_from_food_eaten")
    @PreAuthorize("hasRole('USER')")
    public List<NutrientEatenDTO> getAllNutrientsFromFoodEaten(@RequestBody List<Long> nutrientEatenIds) {
        List<NutrientEaten> nutrientsEaten = new ArrayList<>();
        for (Long nutrientEatenId : nutrientEatenIds) {
            if(nutrientEatenRepository.findById(nutrientEatenId).isPresent()) {
                nutrientsEaten.add(nutrientEatenRepository.findById(nutrientEatenId).get());
            }
        }
        return nutrientsEaten.stream().map(NutrientEaten::getNutrientEatenDTO).collect(Collectors.toList());
    }

    @PostMapping("/food_eaten")
    @PreAuthorize("hasRole('USER')")
    public FoodEatenResponseDTO addFoodEaten(@RequestBody FoodEatenRequestDTO foodEatenRequestDTO) {
        FoodEaten foodEaten = new FoodEaten(
                foodEatenRequestDTO.getBarcode(),
                foodEatenRequestDTO.getBrandOwner(),
                foodEatenRequestDTO.getFoodDescription(),
                foodEatenRequestDTO.getAmountEaten(),
                foodEatenRequestDTO.getServingSize(),
                foodEatenRequestDTO.getServingSizeUnit(),
                foodEatenRequestDTO.getNutrientEatenIds(),
                foodEatenRequestDTO.getUsername(),
                foodEatenRequestDTO.getDate()
        );
        FoodEatenResponseDTO foodEatenResponseDTO = foodEatenRepository.save(foodEaten).getFoodEatenResponseDTO();
        System.out.println(foodEatenResponseDTO.toString());
        return foodEatenResponseDTO;
    }

    @GetMapping("/foods_eaten_today/{username}/{date}")
    @PreAuthorize("hasRole('USER')")
    public List<FoodEatenResponseDTO> getFoodsEaten(
            @PathVariable("username") String username, @PathVariable("date") String date) {
        return foodEatenRepository.findAll().stream()
                .filter(foodEaten -> foodEaten.matchesUsername(username) && foodEaten.eatenToday(date))
                .map(FoodEaten::getFoodEatenResponseDTO)
                .collect(Collectors.toList());
    }
}
