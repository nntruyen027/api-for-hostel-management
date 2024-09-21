package qbit.entier.hostel.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import qbit.entier.hostel.entity.User;
import qbit.entier.hostel.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
    private UserRepository userRepository;
   
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<GrantedAuthority> authorities = Arrays.stream(user.getRole().split(","))
                .map(SimpleGrantedAuthority::new) 
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
    
    public User createUser(User user) {
    	
    	User currentUser = userRepository.findByUsername(user.getUsername())
    			.orElse(null);
    	if(currentUser!=null) {
    		throw new RuntimeException("User already exists: " + user.getUsername());
    	}
    	
    	try {
    		user.setPassword(encoder.encode(user.getPassword()));
        	User newUser = userRepository.save(user);
        	return newUser;
    	}
    	catch (Exception e) {
			throw new RuntimeException(e);
		}
    
    }
}
