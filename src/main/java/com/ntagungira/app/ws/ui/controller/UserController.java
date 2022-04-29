 package com.ntagungira.app.ws.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ntagungira.app.ws.sevice.UserService;
import com.ntagungira.app.ws.shared.dto.UserDto;
import com.ntagungira.app.ws.ui.model.request.UserDetailsRequestModel;
import com.ntagungira.app.ws.ui.model.response.UserResp;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping
	public String getUser() {
		return "getUser was called";
	}

	@PostMapping
	public UserResp createUser(@RequestBody UserDetailsRequestModel userDetails) {
		UserResp returnVal = new UserResp();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnVal);
		return returnVal;
	}

	@PutMapping
	public String updateUser() {
		return "updateUser was called";
	}

	@DeleteMapping
	public String deleteUser() {
		return "deleteUser was called";
	}
}
