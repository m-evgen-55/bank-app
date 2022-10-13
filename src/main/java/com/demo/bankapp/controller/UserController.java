package com.demo.bankapp.controller;

import com.demo.bankapp.controller.request.UserRequest;
import com.demo.bankapp.controller.response.Response;
import com.demo.bankapp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static com.demo.bankapp.utils.UserUtils.mapUserRequestToUser;

@Api(value = "User")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
//    private final SecurityService securityService;
//    private final UserValidator userValidator;

    @ApiOperation(value = "Add new user", response = Response.class)
    @RequestMapping(value = "/add-new-user", method = RequestMethod.POST)
    public Response addNewUser(
            @ApiParam(name = "userRequest", required = true, value = "userRequest")
            @RequestBody final UserRequest userRequest
    ) {
        final Response response = new Response();
        try {
            userService.save(mapUserRequestToUser(userRequest));
            response.setSuccess(true);
        } catch (Exception e) {
            response
                    .setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return response;
    }

//    @PostMapping("/registration")
//    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
//        userValidator.validate(userForm, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return "registration";
//        }
//        userService.save(userForm);
//        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
//        return "redirect:/welcome";
//    }

//    @GetMapping("/login")
//    public String login(Model model, String error, String logout) {
//        if (error != null)
//            model.addAttribute("error", "Your username and password is invalid.");
//        if (logout != null)
//            model.addAttribute("message", "You have been logged out successfully.");
//        return "login";
//    }

//    @GetMapping({"/", "/welcome"})
//    public String welcome(Model model) {
//        return "welcome";
//    }
}
