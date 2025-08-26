package com.financetracker.controller;

import com.financetracker.model.User;
import com.financetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

// for registration, login, OTP verification, and password reset
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173") 
public class UserController {

    @Autowired
    private UserService userService;

    //Register new user
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        try {
            String result = userService.registerUser(user);
            return ResponseEntity.ok(Map.of("message", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    //Verify registration OTP 
    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestBody Map<String, String> request) {
        try {
            String result = userService.verifyOtp(request.get("email"), request.get("otp"));
            return ResponseEntity.ok(Map.of("message", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    //login user
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Map<String, String> request) {
        try {
            User user = userService.loginUser(request.get("email"), request.get("password"));
            return ResponseEntity.ok(Map.of(
                "requiresOtp", true,
                "message", "Login OTP sent to email"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    //Verify login OTP
    @PostMapping("/verify-login-otp")
    public ResponseEntity<Map<String, Object>> verifyLoginOtp(@RequestBody Map<String, String> request) {
        try {
            User user = userService.verifyLoginOtp(request.get("email"), request.get("otp"));
            return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "email", user.getEmail(),
                "message", "Login successful"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    //Send forget password OTP
    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) {
        try {
            String result = userService.sendForgotPasswordOtp(request.get("email"));
            return ResponseEntity.ok(Map.of("message", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    //Verify password reset OTP
    @PostMapping("/verify-reset-otp")
    public ResponseEntity<Map<String, String>> verifyResetOtp(@RequestBody Map<String, String> request) {
        try {
            String result = userService.verifyResetOtp(request.get("email"), request.get("otp"));
            return ResponseEntity.ok(Map.of("message", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    //Reset password
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) {
        try {
            String result = userService.resetPassword(
                request.get("email"), 
                request.get("otp"), 
                request.get("newPassword")
            );
            return ResponseEntity.ok(Map.of("message", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}