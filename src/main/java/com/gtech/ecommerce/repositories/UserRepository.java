package com.gtech.ecommerce.repositories;

import com.gtech.ecommerce.domain.UpdateUser;
import com.gtech.ecommerce.domain.User;
import com.gtech.ecommerce.exceptions.EcAuthException;
import com.gtech.ecommerce.exceptions.EcBadRequestException;
import com.gtech.ecommerce.exceptions.EcResourceNotFoundException;

public interface UserRepository {

    Integer create(String firstName, String lastName, String gender, String birthday, String phone, String email, String password) throws EcAuthException;

    User findByIdentityAndPassword(String identity, String password) throws EcAuthException;

    Integer getCountByPhone(String phone);

    Integer getCountByEmail(String email);

    Integer getCountByIdentity(String identity);

    User findById(Integer userId) throws EcResourceNotFoundException;

    void update(Integer userId, UpdateUser updateUser) throws EcBadRequestException;
}
