package com.demo.bankapp.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserRequest {

    private String name;
    private String password;
}
