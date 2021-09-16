package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.dao.UserRepository;
import web.model.Role;
import web.model.User;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

//@Service
//public class ServiceDetail implements UserDetailsService {
//    @Autowired
//    private UserRepository userRepository;
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User user =  userRepository.findUserByName(s);
//        if (user == null) {
//            throw new UsernameNotFoundException(String.format("User %s not found",s));
//        }
//        System.out.println(user.getRoles());
//        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), grantedAuthorities(user.getRoles()));
//
//    }
//
//    private Collection<? extends GrantedAuthority> grantedAuthorities (Collection<Role> roles) {
//        return  roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
//    }
//}
