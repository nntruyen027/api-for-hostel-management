package qbit.entier.hostel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import qbit.entier.hostel.dto.ResponseListDto;
import qbit.entier.hostel.dto.UserDto;
import qbit.entier.hostel.entity.User;
import qbit.entier.hostel.service.UserService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseListDto<UserDto> getAllAccounts(
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "id") String orderBy,
            @RequestParam(defaultValue = "false") boolean descending) {

        ResponseListDto<UserDto> users = userService.getAll(limit, page, orderBy, descending);
        return users;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public UserDto getAccountById(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        return userDto;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UserDto createAccount(@RequestBody User user) {
    	System.out.println(user);
        UserDto createdUser = userService.createUser(user);
        return createdUser;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public UserDto updateAccount(@PathVariable Long id, @RequestBody User user) {
        UserDto updatedUser = userService.updateUser(id, user);
        return updatedUser;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
