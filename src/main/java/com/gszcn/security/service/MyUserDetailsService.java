package com.gszcn.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gszcn.security.entity.Users;
import com.gszcn.security.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leiyunlong
 * @version 1.0
 * @since 2020/11/30 下午2:29
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersMapper usersMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // MP的查询方法
        QueryWrapper<Users> wrapper = new QueryWrapper();
        // 相当于条件语句
        wrapper.eq("username",s);
        Users users = usersMapper.selectOne(wrapper);
        if(users == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        System.out.println(users);
        List<GrantedAuthority> auths =
                AuthorityUtils.commaSeparatedStringToAuthorityList("role");
        return new User(users.getUsername(),
                new BCryptPasswordEncoder().encode(users.getPassword()),auths);
    }
}
