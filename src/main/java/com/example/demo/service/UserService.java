package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    User getUser(String userName, String password);
}
