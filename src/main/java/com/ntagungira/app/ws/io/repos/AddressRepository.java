package com.ntagungira.app.ws.io.repos;

import com.ntagungira.app.ws.io.entity.AddressEntity;
import com.ntagungira.app.ws.io.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressEntity, String> {
    List<AddressEntity> findAllByUserDetails(UserEntity userEntity);
    AddressEntity findByAddressId(String addressId);
}

