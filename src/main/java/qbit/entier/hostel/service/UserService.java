package qbit.entier.hostel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import qbit.entier.hostel.dto.UserDto;
import qbit.entier.hostel.entity.User;
import qbit.entier.hostel.repository.UserRepository;

@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserInfo() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        
        String username;
        
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername(); 
        } else {
            username = principal.toString(); 
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserDto.toDto(user);
    }
}
