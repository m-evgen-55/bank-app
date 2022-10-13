package com.demo.bankapp.utils;

import com.demo.bankapp.controller.request.UserRequest;
import com.demo.bankapp.model.User;
import com.demo.bankapp.model.entity.UserEntity;

public final class UserUtils {

    private UserUtils() {
    }

    public static UserEntity mapUserToUserEntity(final User user) {
        return new UserEntity()
                .setUserName(user.getName())
                .setPassword(user.getPassword())
                .setRoles(user.getRoles());
    }

    public static User mapUserEntityToUser(final UserEntity userEntity) {
        return new User()
                .setName(userEntity.getUserName())
                .setPassword(userEntity.getPassword())
                .setPasswordConfirm(userEntity.getPasswordConfirm())
                .setRoles(userEntity.getRoles());
    }

    public static User mapUserRequestToUser(final UserRequest userRequest) {
        return new User()
                .setName(userRequest.getName())
                .setPassword(userRequest.getPassword());
    }
}
