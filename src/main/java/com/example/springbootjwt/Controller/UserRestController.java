package com.example.springbootjwt.Controller;

import com.example.springbootjwt.Entity.User;
import com.example.springbootjwt.Service.JwtService;
import com.example.springbootjwt.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class UserRestController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    /* ---------------- GET ALL USER ------------------------ */
    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUser() {
        return new ResponseEntity<List<User>>(userService.finAll(), HttpStatus.OK);
    }
    /* GET USER BY ID  */
    @GetMapping(value = "/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable int id) {
        User user = userService.findById(id);
        if (user != null) {
            return new ResponseEntity<Object>(user, HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Not Found User", HttpStatus.NO_CONTENT);
    }
    /* ---------------- CREATE NEW USER ------------------------ */
    @PostMapping(value = "/users")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (userService.add(user)) {
            return new ResponseEntity<String>("Created!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("User Existed!", HttpStatus.BAD_REQUEST);
        }
    }
    /* ---------------- DELETE USER ------------------------ */
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable int id) {
        userService.delete(id);
        return new ResponseEntity<String>("Deleted!", HttpStatus.OK);
    }
    @PostMapping(value = "/login")
    public ResponseEntity<String> login(HttpServletRequest request, @RequestBody User user) {
        String result = "";
        HttpStatus httpStatus = null;
        try {
            if (userService.checkLogin(user)) {
                result = jwtService.generateTokenLogin(user.getUsername());
                httpStatus = HttpStatus.OK;
            } else {
                result = "Wrong userId and  password";
                httpStatus = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception ex) {
            result = "Server Error";
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<String>(result, httpStatus);
    }

}
