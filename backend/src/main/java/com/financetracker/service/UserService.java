package com.financetracker.service;

import com.financetracker.model.User;
import com.financetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

//business logic for registration, login, and password reset
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    //Register a new user and send OTP for email verification
    public String registerUser(User user) {
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Generate and set OTP
        String otp = generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(System.currentTimeMillis() + 300000); // 5 minutes expiry

        // Save user (not verified yet)
        userRepository.save(user);

        // Send OTP email
        emailService.sendOtpEmail(user.getEmail(), otp, "registration");

        return "User registered. Please verify email with OTP.";
    }

    //Verify email with OTP during registration
    public String verifyOtp(String email, String otp) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();

        // Check if OTP matches and hasn't expired
        if (!otp.equals(user.getOtp()) || System.currentTimeMillis() > user.getOtpExpiry()) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        // Mark user as verified and clear OTP
        user.setVerified(true);
        user.setOtp(null);
        user.setOtpExpiry(0);
        userRepository.save(user);

        return "Email verified successfully";
    }

    //Login user,  password check, then send OTP
    public User loginUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();

        if (!user.isVerified()) {
            throw new RuntimeException("Please verify your email first");
        }

        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        //generate OTP
        String otp = generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(System.currentTimeMillis() + 300000); // 5 minutes
        userRepository.save(user);

        // Send login OTP
        emailService.sendOtpEmail(email, otp, "login");

        return user;
    }

    //verify login otp
    public User verifyLoginOtp(String email, String otp) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();

        if (!otp.equals(user.getOtp()) || System.currentTimeMillis() > user.getOtpExpiry()) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        //clear OTP after verification
        user.setOtp(null);
        user.setOtpExpiry(0);
        userRepository.save(user);

        return user;
    }

    //Send forgot password OTP
    public String sendForgotPasswordOtp(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Email not found");
        }

        User user = userOpt.get();
        String otp = generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(System.currentTimeMillis() + 300000); // 5 minutes
        userRepository.save(user);

        emailService.sendOtpEmail(email, otp, "forgot-password");

        return "Password reset OTP sent to email";
    }

    //Verify reset password OTP
    public String verifyResetOtp(String email, String otp) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();

        if (!otp.equals(user.getOtp()) || System.currentTimeMillis() > user.getOtpExpiry()) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        return "OTP verified. You can reset password.";
    }

    //reset password after OTP verification
    public String resetPassword(String email, String otp, String newPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();

        if (!otp.equals(user.getOtp()) || System.currentTimeMillis() > user.getOtpExpiry()) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        // Update password and clear OTP
        user.setPassword(newPassword);
        user.setOtp(null);
        user.setOtpExpiry(0);
        userRepository.save(user);

        return "Password reset successfully";
    }

    //generating random 6 digit otp
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit number
        return String.valueOf(otp);
    }
}