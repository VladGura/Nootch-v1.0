package com.nootch.controllers;


import com.nootch.entities.NootchUser;
import com.nootch.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserControllerTest {

    private final UserRepository userRepository;

    @Autowired
    public UserControllerTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<NootchUser> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public NootchUser addUser(@RequestBody NootchUser user) {
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public NootchUser getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public NootchUser updateUser(@PathVariable Long id, @RequestBody NootchUser updatedUser) {
        if(userRepository.existsById(id)) {
            updatedUser.setId(id);
            return userRepository.save(updatedUser);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
