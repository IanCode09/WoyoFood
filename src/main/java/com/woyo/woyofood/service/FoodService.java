package com.woyo.woyofood.service;

import com.woyo.woyofood.model.food.FoodModel;
import com.woyo.woyofood.repository.FoodRepository;
import com.woyo.woyofood.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public List<FoodModel> getAllFood() {
        List<FoodModel> foods = foodRepository.findAll();

        if (foods.size() > 0) {
            return foods.stream().collect(Collectors.toList());
        } else {
            return foods;
        }
    }

    public FoodDto getDetailFood(int id) {
        Optional<FoodModel> foodExists = foodRepository.findById(id);

        if (foodExists.isPresent()) {
            FoodDto foodDto = new FoodDto();
            foodDto.setFoodId(foodExists.get().getFoodId());
            foodDto.setRestoId(foodExists.get().getRestoId());
            foodDto.setFoodName(foodExists.get().getFoodName());
            foodDto.setPrice(foodExists.get().getPrice());
            foodDto.setRate(foodExists.get().getRate());
            foodDto.setDetails(foodExists.get().getDetails());

            return foodDto;
        } else {
            return null;
        }
    }

    public List<FoodModel> filterByPrice(int min, int max) {
        List<FoodModel> foods = foodRepository.filterByPrice(min, max);

        if (foods.size() > 0) {
            return foods.stream().collect(Collectors.toList());
        } else {
            return foods;
        }
    }

    public FoodModel createFood(FoodModel foodModel) {
        return foodRepository.save(foodModel);
    }

    public Optional<FoodModel> getFoodById(int id) {
        Optional<FoodModel> foodExists = foodRepository.findById(id);
        return foodExists;
    }

    public FoodDto updateFood(int foodId, String foodName, int price, String details) {
        Optional<FoodModel> food = foodRepository.findById(foodId);

        FoodModel foodModel = food.get();

        if (foodName != null && !foodName.isEmpty()) {
            foodModel.setFoodName(foodName);
        }

        if (price > 0) {
            foodModel.setPrice(price);
        }

        if (details != null && !details.isEmpty()) {
            foodModel.setDetails(details);
        }

        foodRepository.save(foodModel);

        FoodDto foodDto = new FoodDto();
        foodDto.setFoodId(foodModel.getFoodId());
        foodDto.setRestoId(foodModel.getRestoId());
        foodDto.setFoodName(foodModel.getFoodName());
        foodDto.setPrice(foodModel.getPrice());
        foodDto.setRate(foodModel.getRate());
        foodDto.setDetails(foodModel.getDetails());

        return foodDto;
    }

    public boolean deleteFood(int id) {
        foodRepository.deleteById(id);
        return true;
    }
}
