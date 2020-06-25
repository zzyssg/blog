package com.zzy.service;

import com.zzy.dao.UserRepository;
import com.zzy.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {

        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user != null) {
            return user;
        }

        return null;
    }

}
