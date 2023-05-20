package com.quest.etna.config;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quest.etna.model.Address;
import com.quest.etna.model.User;
import com.quest.etna.respositories.AddressRepository;
import com.quest.etna.respositories.UserRepository;

@Service
//@Transactional
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Optional<Address> getAddressById(int id) {
    	return addressRepository.findById(id);
    }
    
    public List<Address> getAddressByUser(User user) {
        return addressRepository.findByUser(user);
    }
    
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }
    
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }
    @Transactional
    public boolean deleteAddressById(int id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            addressRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}

