package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.message.request.ChangePassword;
import com.example.demo.message.response.ResultResponse;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    PasswordEncoder encoder;

//    @GetMapping("/account")
//    public User getUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = userRepository.findByUsername(authentication.getName());
//
//        BasicModel basicModel = modelMapper.map(user, BasicModel.class);
//        logger.info(basicModel.getUsername());
//
//        return user;
//    }

    @GetMapping("/account")
    public ResponseEntity<?> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());

        logger.info(user.getUsername());

        return ResponseEntity.ok(new ResultResponse(user,"done!", HttpStatus.OK));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getListUser(@RequestParam(value = "c", defaultValue = "0") Integer offset,
                                         @RequestParam(value = "limit", defaultValue = "1") Integer limit){
        Page<User> list = userRepository.findAll(PageRequest.of(offset, limit));
        ObjectNode rs = JsonNodeFactory.instance.objectNode();
        rs.set("contents",User.toJson(list.getContent()));

//        return ResponseEntity.ok(rs);
        return ResponseEntity.ok(new ResultResponse(rs,"done!",HttpStatus.OK));
    }

    @PostMapping("/changepassword")
    public ResponseEntity<?> changePass(@Valid @RequestBody ChangePassword changepassword){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        logger.info("---------------password change start-------");

        if (!bCryptPasswordEncoder.matches(changepassword.getOldPassword(), user.getPassword())){
            return new ResponseEntity<String>("ERROR -> Password is not correct!", HttpStatus.BAD_REQUEST);
        }

        if (!changepassword.getNewPassword().equals(changepassword.getConfirmPassword())){
            return new ResponseEntity<String>("ERROR -> Confirm password is not same!", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(encoder.encode(changepassword.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(User.toJson(user));
    }
}