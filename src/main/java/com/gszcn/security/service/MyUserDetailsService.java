package com.gszcn.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gszcn.security.entity.Users;
import com.gszcn.security.exception.ServiceException;
import com.gszcn.security.mapper.UsersMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leiyunlong
 * @version 1.0
 * @since 2020/11/30 下午2:29
 */
@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
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
        log.info(String.valueOf(users));
        // 权限、角色列表
        List<GrantedAuthority> auths =
                AuthorityUtils.commaSeparatedStringToAuthorityList("admins,manager,ROLE_sale,ROLE_role");
        // 给用户添加一个权限
        return new User(users.getUsername(),
//                new BCryptPasswordEncoder().encode(users.getPassword())
                users.getPassword()
                ,auths);
    }

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
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String password1=passwordEncoder.encode(password);
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        Users users1 = new Users();
        users1.setUsername(userDetails.getUsername());
        queryWrapper.setEntity(users1);
        Users users = usersMapper.selectOne(queryWrapper);
        // 密码对比
        if(!passwordEncoder.matches(oldPassword,users.getPassword())){
            throw new ServiceException("201","原密码输入不正确！");
        } else {
            users.setPassword(password1);
            usersMapper.updateById(users);
        }
    }

}
