package com.springapp.mvc.service;

import com.springapp.mvc.domain.User;
import com.springapp.mvc.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.plugin.util.UserProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 05.01.2017.
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        User user = userService.findByName(userName);
        System.out.println("User : "+user);
        if(user == null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getId().toString(), user.getPassword(),
                true, true, true, true, getGrantedAuthorities(user));
    }


    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        String grants = user.getGrantsAdapter();

        for (int i = 0; i < grants.length(); i++) {
            System.out.println("UserGrants : " + grants.charAt(i));
            authorities.add(new SimpleGrantedAuthority(String.valueOf(grants.charAt(i))));
        }
        System.out.print("authorities :"+authorities);
        return authorities;
    }

}
