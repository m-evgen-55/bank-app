package com.demo.bankapp.service;

import com.demo.bankapp.model.User;

public interface UserService {

    void save(User user);

    User findByUserName(String userName);
}
