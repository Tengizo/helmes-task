package com.helmes.task.services;

import com.helmes.task.exception.AppException;
import com.helmes.task.persistence.model.User;
import com.helmes.task.rest.dto.UserAddDTO;
import com.helmes.task.rest.dto.UserUpdateDTO;

import javax.xml.ws.ServiceMode;

@ServiceMode
public interface UserService {
    User addUser(UserAddDTO userAddDTO) throws AppException;

    User updateUser(UserUpdateDTO userAddDTO) throws AppException;

    User findUserById(Long id);

    User findUserByName(String name);

}
