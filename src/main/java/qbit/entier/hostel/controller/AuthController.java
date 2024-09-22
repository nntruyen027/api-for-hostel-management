package qbit.entier.hostel.controller;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import qbit.entier.hostel.dto.ChangePasswordRequest;
import qbit.entier.hostel.dto.LoginRequest;
import qbit.entier.hostel.dto.LoginResponse;
import qbit.entier.hostel.dto.UserDto;
import qbit.entier.hostel.entity.User;
import qbit.entier.hostel.service.UserService;
import qbit.entier.hostel.util.FileUtil;
import qbit.entier.hostel.util.JwtUtil;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	@Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserService service;
    
    @GetMapping(value = "/info")
    public UserDto getInfo() {
    	return service.getUserInfo();
    }
    
	@PutMapping(value = "/info", consumes = "multipart/form-data")
	public ResponseEntity<UserDto> updateInfo(
	    @RequestParam(value = "avatar", required = false) MultipartFile avatar,  
	    @RequestParam(value = "fullname", required = false) String fullname,     
	    @RequestParam(value = "phone", required = false) String phone,     
	    @RequestParam(value = "email", required = false) String email,     
	    @RequestParam(value = "address", required = false) String address,     
	    @RequestParam(value = "birthday", required = false) Date birthday,
	    @RequestParam(value = "cid", required = false) String cid
	) {
	    UserDto currentUser = service.getUserInfo();
	    User updatedUser = new User();
	
	    if (fullname != null) updatedUser.setFullname(fullname);
	    if (phone != null) updatedUser.setPhone(phone);
	    if (email != null) updatedUser.setEmail(email);
	    if (address != null) updatedUser.setAddress(address);
	    if (birthday != null) updatedUser.setBirthday(birthday);  
	    if (cid != null) updatedUser.setCid(cid);
	    
	    if (avatar != null && !avatar.isEmpty()) {
	        try {
	        	 if (currentUser.getAvatar() != null) {
	                FileUtil.deleteFile(currentUser.getAvatar());
	             }

	            updatedUser.setAvatar(FileUtil.saveFile(avatar));  
	        } catch (IOException e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(null);
	        }
	    }
	
	    UserDto updatedUserDto = service.updateUserInfo(updatedUser);
	    return ResponseEntity.ok(updatedUserDto);
	}

    @PutMapping(value = "/password",  produces = "application/json")
    public ResponseEntity<String> updatePassword(@RequestBody ChangePasswordRequest req) {
    	 try {
             service.changePassword(req);
             return ResponseEntity.ok("Password changed successfully");
         } catch (RuntimeException e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
         }
    }
    
    

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid credentials"));
        }

        String token = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
