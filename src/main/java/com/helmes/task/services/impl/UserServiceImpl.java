package com.helmes.task.services.impl;

import com.helmes.task.exception.AppException;
import com.helmes.task.exception.Errors;
import com.helmes.task.persistence.model.User;
import com.helmes.task.persistence.repository.UserRepository;
import com.helmes.task.rest.dto.UserAddDTO;
import com.helmes.task.rest.dto.UserUpdateDTO;
import com.helmes.task.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(UserAddDTO userAddDTO) throws AppException {
        // validate if name exists
        if (userRepository.findByName(userAddDTO.getName()).isPresent()) {
            throw new AppException(Errors.NAME_IS_USED);
        }
        return userRepository.save(userAddDTO.getUser());
    }

    @Override
    public User updateUser(UserUpdateDTO userUpdateDTO) throws AppException {
        User user = userRepository.findById(userUpdateDTO.getId())
                .orElseThrow(() -> new AppException(Errors.INVALID_USER_ID));
        return userRepository.save(userUpdateDTO.getUpdatedUser(user));
    }

    @Override
    public User findUserById(Long id) {
        return null;
    }

    @Override
    public User findUserByName(String name) {
        return userRepository.findByName(name).orElse(null);
    }
}
