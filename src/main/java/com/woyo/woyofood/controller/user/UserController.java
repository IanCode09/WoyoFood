package com.woyo.woyofood.controller.user;

import com.woyo.woyofood.model.user.UserModel;
import com.woyo.woyofood.response.DataResponse;
import com.woyo.woyofood.response.HandlerResponse;
import com.woyo.woyofood.service.UserService;
import com.woyo.woyofood.shared.dto.ProfileDto;
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
@RequestMapping(value = "/api/users", produces = {"application/json"})
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "", produces = {"application/json"})
    public String index() {
        return "Route Test";
    }

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void register(HttpServletRequest request, HttpServletResponse response,
                         @Valid @NotNull @ModelAttribute("full_name") String fullName,
                         @Valid @NotNull @ModelAttribute("username") String userName,
                         @Valid @NotNull @ModelAttribute("phone_number") String phoneNumber,
                         @Valid @NotNull @ModelAttribute("email") String email,
                         @Valid @NotNull @ModelAttribute("password") String password) throws IOException {

        List<UserModel> userExists = userService.getUserByemail(email);

        if (userExists.size() > 0) {
            HandlerResponse.responseBadRequest(response, "User already registered");
        } else {
            try {
                UserModel userModel = new UserModel();
                userModel.setFullName(fullName);
                userModel.setUserName(userName);
                userModel.setPhoneNumber(phoneNumber);
                userModel.setEmail(email);
                userModel.setPassword(password);

                userService.createUser(userModel);

                HandlerResponse.responseSuccessCreated(response, "USER CREATED");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping(value = "/{userId}")
    public void getUserProfile(HttpServletRequest request, HttpServletResponse response,
                                 @PathVariable("userId") int userId) throws IOException {

        ProfileDto profileDto = userService.getUserProfile(userId);

        if (profileDto != null) {
            DataResponse<ProfileDto> dataResponse = new DataResponse<>();
            dataResponse.setData(profileDto);
            HandlerResponse.responseSuccessWithData(response, dataResponse);
        } else {
            HandlerResponse.responseNotFound(response, "User not found");
        }
    }

    @PutMapping(value = "/{userId}/update", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void updateProfile(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable("userId") int userId,
                              @Valid @NotNull @ModelAttribute("full_name") String fullName,
                              @Valid @NotNull @ModelAttribute("username") String userName,
                              @Valid @NotNull @ModelAttribute("phone_number") String phoneNumber,
                              @Valid @NotNull @ModelAttribute("email") String email,
                              @Valid @NotNull @ModelAttribute("password") String password) throws IOException {

        Optional<UserModel> userExists = userService.getUserById(userId);

        if (userExists.isPresent()) {
            ProfileDto profileDto = userService.updateProfile(userId, fullName, userName, phoneNumber, email, password);

            if (profileDto != null) {
                DataResponse<ProfileDto> dataResponse = new DataResponse<>();
                dataResponse.setData(profileDto);
                HandlerResponse.responseSuccessWithData(response, dataResponse);
            } else {
                HandlerResponse.responseNotFound(response, "User not found");
            }
        } else {
            HandlerResponse.responseNotFound(response, "User not found");
        }
    }
}
