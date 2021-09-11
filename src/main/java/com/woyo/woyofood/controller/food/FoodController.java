package com.woyo.woyofood.controller.food;

import com.woyo.woyofood.model.food.FoodModel;
import com.woyo.woyofood.response.DataResponse;
import com.woyo.woyofood.response.HandlerResponse;
import com.woyo.woyofood.service.FoodService;
import com.woyo.woyofood.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
}
