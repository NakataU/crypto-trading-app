package org.example.controllers;

import org.example.exceptions.EntityNotActive;
import org.example.exceptions.EntityNotFound;
import org.example.services.UserService;
import org.example.views.in.UserInView;
import org.example.views.out.UserOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Page<UserOutView>> getAll(Pageable pageable){
        Page<UserOutView> res = userService.getAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserOutView>> getById(@PathVariable("id") Long id){

        Optional<UserOutView> res = userService.getById(id);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res);

    }

    @PostMapping("/add")
    public ResponseEntity<UserOutView> addOne(@Validated @RequestBody UserInView body){
        UserOutView res = userService.addOne(body);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) throws EntityNotFound, EntityNotActive {
        userService.deleteOne(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserOutView> updateOne(@PathVariable("id") Long id,@Validated @RequestBody UserInView body) throws EntityNotFound {
        UserOutView res = userService.updateOne(id,body);
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/reset_user/{id}")
    public ResponseEntity<UserOutView> resetUser(@PathVariable("id") Long id) throws EntityNotFound, EntityNotActive {
        UserOutView res = userService.resetUser(id);
        return ResponseEntity.ok(res);
    }
}
