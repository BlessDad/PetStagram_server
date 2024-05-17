package com.example.myspringserver.controller;

import com.example.myspringserver.dto.UserDto;
import com.example.myspringserver.entity.User;
import com.example.myspringserver.repository.UserRepository;
import com.example.myspringserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/insert")
    public ResponseEntity<String> insertUser(@RequestBody UserDto userDto) {
        userRepository.save(userService.converToEntity(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 추가 성공");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Integer id,
                                             @RequestBody User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow( () -> new NoSuchElementException("회원을 찾을 수 없음"));

        existingUser.setUser_id(updatedUser.getUser_id());
        existingUser.setUser_nickname(updatedUser.getUser_nickname());
        existingUser.setPet_name(updatedUser.getPet_name());
        existingUser.setPet_age(updatedUser.getPet_age());
        existingUser.setUser_introduce(updatedUser.getUser_introduce());
        existingUser.setUser_follower_count(updatedUser.getUser_follower_count());
        existingUser.setUser_following_count(updatedUser.getUser_following_count());
        existingUser.setUser_post_count(updatedUser.getUser_post_count());

        userRepository.save(existingUser);
        return ResponseEntity.ok("회원 정보 수정 성공");
    }

    @GetMapping("/getUser")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(userService::converToDto).collect(Collectors.toList());

        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUSer(@PathVariable Integer id) {
        try{
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }
}
