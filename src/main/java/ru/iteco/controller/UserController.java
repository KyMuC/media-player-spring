package ru.iteco.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.iteco.controller.dto.UserDto;
import ru.iteco.service.rest.RestUserService;
import ru.iteco.service.UserService;
import ru.iteco.validator.UserDtoValidator;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

/**
 * Registration REST APIs controller.
 *
 * @author Mikhail_Kudimov
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class.getName());

    private final RestUserService restUserService;
    private final UserDtoValidator userDtoValidator;

    public UserController(RestUserService restUserService, UserService userService, UserDtoValidator userDtoValidator) {
        this.restUserService = restUserService;
        this.userDtoValidator = userDtoValidator;
    }

    /**
     * Method for finding user by user name of email.
     * @param query Name or email to find user by
     * @return User with such user name or email or user with error if no user was found
     */
    @GetMapping("{query}")
    public UserDto getUser(@PathVariable String query) {
        UserDto userDto = restUserService.findByUserNameOrEmail(query);

        if (userDto == null) {
            userDto = new UserDto();
            userDto.setErrors(Collections.singletonList(new ObjectError("userDto",
                    new String[]{"user.notFound"}, null,
                    "No user found with such user name or email.")));
        }

        return userDto;
    }

    /**
     * Method for creating a new user.
     * @param body JSON body or user to create
     * @return Newly created user or user with error if JSON format was wrong
     */
    @PostMapping
    public UserDto registerUser(@Validated @RequestBody UserDto body, BindingResult result,
                                HttpServletResponse httpServletResponse) {

        if (result.hasErrors()) {
            body.setErrors(result.getAllErrors());
            httpServletResponse.setStatus(400);
            return body;
        }

        return restUserService.registerUser(body);
    }

    /**
     * Method for updating user.
     * @param query User name or email or user to update
     * @param body JSON body with fields to update
     * @return Updated fields of user or user with error if JSON format was wrong
     */
    @PutMapping("{query}")
    public UserDto updateUserInfo(@PathVariable String query, @RequestBody UserDto body,
                                  BindingResult result, HttpServletResponse httpServletResponse) {

        userDtoValidator.validateUpdate(body, result);
        if (result.hasErrors()) {
            body.setErrors(result.getAllErrors());
            httpServletResponse.setStatus(400);
            return body;
        }

        if (!restUserService.updateUserInfo(query, body)) {
            body.setErrors(Collections.singletonList(new ObjectError("userDto",
                    new String[]{"user.notFound"}, null,
                    "No user found with such user name or email.")));
        }

        return body;
    }

    /**
     * Method for deleting user by username of email.
     * @param query User name or email to delete user by
     * @return Empty user with HTTP status of 204 or user with error if no user was found
     */
    @DeleteMapping("{query}")
    public UserDto removeUser(@PathVariable String query, HttpServletResponse httpServletResponse) {
        boolean deleted = restUserService.removeUser(query);

        UserDto userDto = null;
        if (!deleted) {
            userDto = new UserDto();
            userDto.setErrors(Collections.singletonList(new ObjectError("userDto",
                    new String[]{"user.notFound"}, null,
                    "No user found with such user name or email.")));
        } else {
            httpServletResponse.setStatus(204);
        }

        return userDto;
    }

    @ModelAttribute
    public UserDto userDto() {
        return new UserDto();
    }

    @InitBinder(value = "userDto")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(userDtoValidator);
    }
}
