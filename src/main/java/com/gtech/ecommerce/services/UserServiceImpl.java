package com.gtech.ecommerce.services;

import com.gtech.ecommerce.domain.UpdateUser;
import com.gtech.ecommerce.domain.User;
import com.gtech.ecommerce.exceptions.EcAuthException;
import com.gtech.ecommerce.exceptions.EcBadRequestException;
import com.gtech.ecommerce.exceptions.EcResourceNotFoundException;
import com.gtech.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String identity, String password) throws EcAuthException {
        Pattern patternPhone = Pattern.compile("\\d+");
        Pattern patternEmail = Pattern.compile("^(.+)@(.+)$");
        if(identity != null) identity = identity.toLowerCase();
        if(!patternPhone.matcher(identity).matches() && !patternEmail.matcher(identity).matches())
            throw new EcAuthException("Invalid phone/email format");
        return userRepository.findByIdentityAndPassword(identity, password);
    }

    @Override
    public User registerUser(String firstName, String lastName, String gender, String birthday, String phone, String email, String password) throws EcAuthException {
        if(email != null) email = email.toLowerCase();
        // Pattern patternEmail = Pattern.compile("^(.+)@(.+)$");
        // if(email != null) email = email.toLowerCase();
        // if(!patternEmail.matcher(email).matches())
        // throw new EcAuthException("Invalid email format");
        Integer countEmail = userRepository.getCountByEmail(email);
        if(countEmail > 0)
            throw new EcAuthException("Email already in use");
        Integer countPhone = userRepository.getCountByPhone(phone);
        if(countPhone > 0)
            throw new EcAuthException("Phone already in use");
        Integer userId = userRepository.create(firstName, lastName, gender, birthday, phone, email, password);
        return userRepository.findById(userId);
    }

    @Override
    public User fetchUserById(Integer userId) throws EcResourceNotFoundException {
        return userRepository.findById(userId);
    }

    @Override
    public void updateUser(Integer userId, UpdateUser updateUser) throws EcBadRequestException {
        userRepository.update(userId, updateUser);
    }
}
