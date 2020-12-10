package com.gszcn.security.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gszcn.security.entity.Users;
import com.gszcn.security.mapper.UsersMapper;
import com.sun.org.apache.xerces.internal.util.EntityResolverWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author leiyunlong
 * @version 1.0
 * @since 2020/12/9 上午11:13
 */
@Service
public class UsersService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 注册
     * @param users
     * @return
     */
    public Integer register(Users users){
        users.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));
        int insert = usersMapper.insert(users);
        return insert;
    }

    /**
     * 修改密码
     */
    public void changePassword(String oldPassword, String password){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String password1=passwordEncoder.encode(password);
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        Users users1 = new Users();
        users1.setUsername(userDetails.getUsername());
        queryWrapper.setEntity(users1);
        Users users = usersMapper.selectOne(queryWrapper);
        if(!passwordEncoder.matches(oldPassword,users.getPassword())){
            throw new RuntimeException("原密码不正确");
        } else {
            users.setPassword(password1);
            usersMapper.updateById(users);
        }
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
//        Users users1 = new Users();
//        users1.setUsername(username);
//        queryWrapper.setEntity(users1);
//        Users users = usersMapper.selectOne(queryWrapper);
//        return users;
//    }
}
