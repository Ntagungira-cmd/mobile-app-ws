package com.ntagungira.app.ws.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ntagungira.app.ws.Exceptions.UserServiceException;
import com.ntagungira.app.ws.sevice.UserService;
import com.ntagungira.app.ws.shared.dto.UserDto;
import com.ntagungira.app.ws.ui.model.request.UserDetailsRequestModel;
import com.ntagungira.app.ws.ui.model.response.ErrorMessages;
import com.ntagungira.app.ws.ui.model.response.OperationStatusModel;
import com.ntagungira.app.ws.ui.model.response.RequestOperationName;
import com.ntagungira.app.ws.ui.model.response.RequestOperationStatus;
import com.ntagungira.app.ws.ui.model.response.UserResp;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}", produces = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserResp getUser(@PathVariable String id) {
        UserResp returnValue = new UserResp();
        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);
        return returnValue;
    }

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {
                    MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserResp createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
        UserResp returnVal = new UserResp();
        //UserDto userDto = new UserDto();
//		if (userDetails.getEmail().isEmpty())
//			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        //BeanUtils.copyProperties(userDetails, userDto);

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);
        returnVal=modelMapper.map(createdUser, UserResp.class);

        return returnVal;
    }

    @PutMapping(path = "/{id}",
            consumes = {
                    MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {
                    MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserResp updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
        UserResp returnVal = new UserResp();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        UserDto updateUser = userService.updateUser(id, userDto);
        BeanUtils.copyProperties(updateUser, returnVal);
        return returnVal;
    }

    @DeleteMapping(path = "{id}", produces = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String id) {
        OperationStatusModel returnVal = new OperationStatusModel();
        returnVal.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(id);
        returnVal.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnVal;
    }

    //Get all users
    @GetMapping(produces = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<UserResp> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit) {

        List<UserResp> returnValue = new ArrayList<>();
        List<UserDto> users = userService.getUsers(page, limit);

        for (UserDto userDto : users) {
            UserResp user = new UserResp();
            BeanUtils.copyProperties(userDto, user);
            returnValue.add(user);
        }
        return returnValue;
    }
}
