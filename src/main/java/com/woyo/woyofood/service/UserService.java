package com.woyo.woyofood.service;

import com.woyo.woyofood.model.user.UserModel;
import com.woyo.woyofood.repository.UserRepository;
import com.woyo.woyofood.shared.dto.ProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel createUser(UserModel userModel) {
        return userRepository.save(userModel);
    };

    public List<UserModel> getUserByemail(String email) {
        return userRepository.findByEmail(email);
    }

    public ProfileDto getUserProfile(int userId) {
        Optional<UserModel> userExists = userRepository.findById(userId);

        if (userExists.isPresent()) {
            ProfileDto profileDto = new ProfileDto();
            profileDto.setUserId(userExists.get().getUserId());
            profileDto.setFullName(userExists.get().getFullName());
            profileDto.setUsername(userExists.get().getUserName());
            profileDto.setPhoneNumber(userExists.get().getPhoneNumber());
            profileDto.setEmail(userExists.get().getEmail());

            return profileDto;
        } else {
            return null;
        }
    }

    public Optional<UserModel> getUserById(int userId) {
        Optional<UserModel> userExists = userRepository.findById(userId);
        return userExists;
    }

    public ProfileDto updateProfile(int userId, String fullName, String username, String phoneNumber, String email, String password) {
        Optional<UserModel> userExists = userRepository.findById(userId);

        if(userExists.isPresent()) {
            UserModel userModel = userExists.get();

            if (fullName != null && !fullName.isEmpty()) {
                userModel.setFullName(fullName);
            }

            if (username != null && !username.isEmpty()) {
                userModel.setUserName(username);
            }

            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                userModel.setPhoneNumber(phoneNumber);
            }

            if (email != null && !email.isEmpty()) {
                userModel.setEmail(email);
            }

            if (password != null && !password.isEmpty()) {
                userModel.setPassword(password);
            }

            userRepository.save(userModel);

            ProfileDto profileDto = new ProfileDto();
            profileDto.setUserId(userModel.getUserId());
            profileDto.setFullName(userModel.getFullName());
            profileDto.setUsername(userModel.getUserName());
            profileDto.setPhoneNumber(userModel.getPhoneNumber());
            profileDto.setEmail(userModel.getEmail());

            return profileDto;
        } else {
            return null;
        }
    }

}
