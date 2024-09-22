package qbit.entier.hostel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import qbit.entier.hostel.dto.ChangePasswordRequest;
import qbit.entier.hostel.dto.ResponseListDto;
import qbit.entier.hostel.dto.ResponseListDto.Meta;
import qbit.entier.hostel.dto.UserDto;
import qbit.entier.hostel.entity.User;
import qbit.entier.hostel.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    
    public UserDto updateUserInfo(User updatedUser) {
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
        
        user.updateNonNullFields(updatedUser);
        return UserDto.toDto(userRepository.save(user));
        	
    }
    
    public boolean changePassword(ChangePasswordRequest changePasswordRequest) {
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

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new RuntimeException("New password and confirm password do not match");
        }
        
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        return true;
    }

    public ResponseListDto<UserDto> getAll(int limit, int page, String orderBy, boolean descending) {
        Sort sort = descending ? Sort.by(Sort.Order.desc(orderBy)) : Sort.by(Sort.Order.asc(orderBy));
        Page<User> userPage = userRepository.findAll(PageRequest.of(page - 1, limit, sort));
        List<UserDto> userDtos = userPage.getContent().stream().map(UserDto::toDto).collect(Collectors.toList());

        ResponseListDto<UserDto> res = new ResponseListDto<>();
        res.setData(userDtos);
        res.setMeta(new Meta(userPage.getTotalPages(), userPage.getTotalElements(), userPage.getNumber() + 1, userPage.getSize()));
        return res;
    }

    public UserDto createUser(User user) {
    	System.out.println(user);
    	
        User newUser = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(user.getRole())
                .fullname(user.getFullname())
                .phone(user.getPhone())
                .cid(user.getCid())
                .email(user.getEmail())
                .address(user.getAddress())
                .birthday(user.getBirthday())
                .build();

        return UserDto.toDto(userRepository.save(newUser));
    }

    public UserDto updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        user.setRole(updatedUser.getRole());
        
        return UserDto.toDto(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        userRepository.delete(user);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Not fount"));
        return UserDto.toDto(user);
    }
}
