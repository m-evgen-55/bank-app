package com.demo.bankapp.model;

import com.demo.bankapp.model.entity.RoleEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class User {

    private String name;
    private String password;
    private String passwordConfirm;
    private Set<RoleEntity> roles;
}
