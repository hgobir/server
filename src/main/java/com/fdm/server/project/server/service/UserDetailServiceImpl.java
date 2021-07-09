package com.fdm.server.project.server.service;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("userDetailServiceImpl")
//public class UserDetailServiceImpl implements UserDetailsService  {
public class UserDetailServiceImpl implements UserDetailsService  {

    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public UserDetailServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        System.out.println("this is encoded username! " + username);

        Optional<ApplicationUser> currentUser = applicationUserRepository.findByUsername(username);

        if(currentUser.isPresent()) {
            currentUser.get().setEnabled(true);
            currentUser.get().setAccountNonExpired(true);
            currentUser.get().setCredentialsNonExpired(true);
            currentUser.get().setAccountNonLocked(true);
            currentUser.get().setAuthorities(AuthorityUtils.createAuthorityList(currentUser.get().getRole().toString()));
            return currentUser.get();
        }
        return null;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(this).passwordEncoder(new BCryptPasswordEncoder());
    }

}
