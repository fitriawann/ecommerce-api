package com.gtech.ecommerce.services;

import com.gtech.ecommerce.domain.UpdateUser;
import com.gtech.ecommerce.domain.User;
import com.gtech.ecommerce.exceptions.EcAuthException;
import com.gtech.ecommerce.exceptions.EcBadRequestException;
import com.gtech.ecommerce.exceptions.EcResourceNotFoundException;

public interface UserService {

    User validateUser(String identity, String password) throws EcAuthException;

    User registerUser(String firstName, String lastName, String gender, String birthday, String phone, String email, String password) throws EcAuthException;

    User fetchUserById(Integer userId) throws EcResourceNotFoundException;

    void updateUser(Integer userId, UpdateUser updateUser) throws EcBadRequestException;
}
