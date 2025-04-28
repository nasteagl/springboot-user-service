package org.cooksApp.controller;


import lombok.RequiredArgsConstructor;
import org.cooksApp.model.User;
import org.cooksApp.service.user.IUserServie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final IUserServie userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User theUser = userService.registerUser(user);
        return ResponseEntity.ok(theUser);
    }

    @GetMapping
    public ResponseEntity<String> findUserByUsername(@RequestParam String username) {
        String theUser = userService.findByUsername(username);
        return ResponseEntity.ok(theUser);

    }

}
