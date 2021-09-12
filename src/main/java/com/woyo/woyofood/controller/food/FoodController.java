package com.woyo.woyofood.controller.food;

import com.woyo.woyofood.model.food.FoodModel;
import com.woyo.woyofood.response.DataResponse;
import com.woyo.woyofood.response.HandlerResponse;
import com.woyo.woyofood.service.FoodService;
import com.woyo.woyofood.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/food", produces = {"application/json"})
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping(value = "", produces = {"application/json"})
    public void getAllFood(HttpServletRequest request, HttpServletResponse response) throws IOException {

        DataResponse<List<FoodModel>> foodResponse = new DataResponse<>();
        List<FoodModel> foods = foodService.getAllFood();
        foodResponse.setData(foods);

        HandlerResponse.responseSuccessWithData(response, foodResponse);
    }

    @GetMapping(value = "/{foodId}", produces = {"application/json"})
    public void getDetailFood(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable("foodId") int foodId) throws IOException {

        FoodDto foodDto = foodService.getDetailFood(foodId);

        if (foodDto != null) {
            DataResponse<FoodDto> dataResponse = new DataResponse<>();
            dataResponse.setData(foodDto);
            HandlerResponse.responseSuccessWithData(response, dataResponse);
        } else {
            HandlerResponse.responseNotFound(response, "Food not found");
        }
    }

    @GetMapping(value = "/price", produces = {"application/json"})
    public void getByPrice(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value = "min") int min,
                           @RequestParam(value = "max") int max) throws IOException {

        DataResponse<List<FoodModel>> foodResponse = new DataResponse<>();
        List<FoodModel> foodLists = foodService.filterByPrice(min, max);
        foodResponse.setData(foodLists);

        HandlerResponse.responseSuccessWithData(response, foodResponse);
    }

    @PostMapping(value = "/create", produces = {"application/json"})
    public void createFood(HttpServletRequest request, HttpServletResponse response,
                           @Valid @NotNull @ModelAttribute("resto_id") int restoId,
                           @Valid @NotNull @ModelAttribute("food_name") String foodName,
                           @Valid @NotNull @ModelAttribute("price") int price,
                           @Valid @NotNull @ModelAttribute("rate") double rate,
                           @Valid @NotNull @ModelAttribute("details") String details) throws IOException {

        try {
            FoodModel foodModel = new FoodModel();
            foodModel.setRestoId(restoId);
            foodModel.setFoodName(foodName);
            foodModel.setPrice(price);
            foodModel.setRate(rate);
            foodModel.setDetails(details);

            foodService.createFood(foodModel);

            HandlerResponse.responseSuccessCreated(response, "Food created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PutMapping(value = "/{id}/update", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void updateFood(HttpServletRequest request, HttpServletResponse response,
                           @PathVariable("id") int id,
                           @Valid @NotNull @ModelAttribute("food_name") String foodName,
                           @Valid @NotNull @ModelAttribute("price") int price,
                           @Valid @NotNull @ModelAttribute("details") String details) throws IOException {

        Optional<FoodModel> foodExists = foodService.getFoodById(id);

        if (foodExists.isPresent()) {
            FoodDto foodDto = foodService.updateFood(id, foodName, price, details);

            if (foodDto != null) {
                DataResponse<FoodDto> foodResponse = new DataResponse<>();
                foodResponse.setData(foodDto);
                HandlerResponse.responseSuccessWithData(response, foodResponse);
            } else {
                HandlerResponse.responseNotFound(response, "Food not found");
            }
        } else {
            HandlerResponse.responseNotFound(response, "Food not found");
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteFood(HttpServletRequest request, HttpServletResponse response,
                           @PathVariable("id") int id) throws IOException {

        Optional<FoodModel> foodExists = foodService.getFoodById(id);

        if (foodExists.isPresent()) {
            foodService.deleteFood(id);

            HandlerResponse.responseSuccessOK(response, "Food successfully deleted");
        } else {
            HandlerResponse.responseNotFound(response, "Food not found");
        }
    }
}
