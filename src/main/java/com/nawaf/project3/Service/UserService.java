package com.nawaf.project3.Service;

import com.nawaf.project3.Api.ApiException;
import com.nawaf.project3.Model.User;
import com.nawaf.project3.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthRepository authRepository;

    public List<User> getUsers() {
        return authRepository.findAll();
    }

    public User getUserById(Integer userId) {
        User user = authRepository.findUserById(userId);

        if (user == null) throw new ApiException("User not found");

        return user;
    }
}