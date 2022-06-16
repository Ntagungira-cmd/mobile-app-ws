package com.ntagungira.app.ws.sevice;

import com.ntagungira.app.ws.shared.dto.AddressDTO;

import java.util.List;

public interface AddressService {
    List<AddressDTO> getAddresses(String userId);
}
