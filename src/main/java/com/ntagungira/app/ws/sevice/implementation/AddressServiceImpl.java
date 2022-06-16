package com.ntagungira.app.ws.sevice.implementation;

import com.ntagungira.app.ws.io.entity.AddressEntity;
import com.ntagungira.app.ws.io.entity.UserEntity;
import com.ntagungira.app.ws.io.repos.AddressRepository;
import com.ntagungira.app.ws.io.repos.UserRepository;
import com.ntagungira.app.ws.sevice.AddressService;
import com.ntagungira.app.ws.shared.dto.AddressDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressDTO> getAddresses(String userId) {
        List<AddressDTO> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity==null) return returnValue;

        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for(AddressEntity addressEntity:addresses)
        {
            returnValue.add( modelMapper.map(addressEntity, AddressDTO.class) );
        }

        return returnValue;
    }
}

