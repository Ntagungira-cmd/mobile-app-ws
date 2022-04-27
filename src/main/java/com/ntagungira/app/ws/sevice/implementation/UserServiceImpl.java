package com.ntagungira.app.ws.sevice.implementation;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ntagungira.app.ws.UserRepository;
import com.ntagungira.app.ws.io.entity.UserEntity;
import com.ntagungira.app.ws.sevice.UserService;
import com.ntagungira.app.ws.shared.Utils;
import com.ntagungira.app.ws.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils utils;

	@Override
	public UserDto createUser(UserDto user) {
		// TODO Auto-generated method stub

		if (userRepository.findByEmail(user.getEmail()) != null)
			throw new RuntimeException("User already exists");

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		String publicUserId = utils.generateUserId(30);
		userEntity.setUserId(publicUserId);
		userEntity.setEncryptedPassword("Ali");

		UserEntity storedUserDetails = userRepository.save(userEntity);
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUserDetails, returnValue);

		return returnValue;
	}

}
