package com.ntagungira.app.ws.ui.controller;

import com.ntagungira.app.ws.sevice.AddressService;
import com.ntagungira.app.ws.sevice.UserService;
import com.ntagungira.app.ws.shared.dto.AddressDTO;
import com.ntagungira.app.ws.shared.dto.UserDto;
import com.ntagungira.app.ws.ui.model.request.UserDetailsRequestModel;
import com.ntagungira.app.ws.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @GetMapping(path = "/{id}", produces = {
             MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserResp getUser(@PathVariable String id) {
        //UserResp returnValue = new UserResp();
        UserDto userDto = userService.getUserByUserId(id);
        //BeanUtils.copyProperties(userDto, returnValue);
        ModelMapper modelMapper = new ModelMapper();
        UserResp returnValue = modelMapper.map(userDto, UserResp.class);
        return returnValue;
    }

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
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
                    MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public UserResp updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
        UserResp returnVal = new UserResp();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        UserDto updateUser = userService.updateUser(id, userDto);
        BeanUtils.copyProperties(updateUser, returnVal);
        return returnVal;
    }

    @DeleteMapping(path = "{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String id) {
        OperationStatusModel returnVal = new OperationStatusModel();
        returnVal.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(id);
        returnVal.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnVal;
    }

    //Get all users
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
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

    @GetMapping(path = "/{id}/address", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<AddressResp> getUserAddresses(@PathVariable String id) {
        List<AddressResp> returnValue = new ArrayList<>();
        List<AddressDTO> addressesDTO = addressService.getAddresses(id);

        if (addressesDTO != null && !addressesDTO.isEmpty()) {
            Type listType = new TypeToken<List<AddressResp>>() {}.getType();
            returnValue = new ModelMapper().map(addressesDTO, listType);
        }
        return returnValue;
    }

}
