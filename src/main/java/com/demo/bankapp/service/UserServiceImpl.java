package com.demo.bankapp.service;

import com.demo.bankapp.model.User;
import com.demo.bankapp.model.entity.UserEntity;
import com.demo.bankapp.repository.RoleRepository;
import com.demo.bankapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import static com.demo.bankapp.utils.UserUtils.mapUserEntityToUser;
import static com.demo.bankapp.utils.UserUtils.mapUserToUserEntity;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(mapUserToUserEntity(user));
    }

    @Override
    public User findByUserName(String userName) {
        final UserEntity userEntity = userRepository.findByUserName(userName);
        return userEntity != null ? mapUserEntityToUser(userEntity) : null;
    }
}
