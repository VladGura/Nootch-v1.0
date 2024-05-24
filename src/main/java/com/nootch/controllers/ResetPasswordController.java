package com.nootch.controllers;

import com.nootch.entities.NootchUser;
import com.nootch.repositories.UserRepository;
import com.nootch.services.EmailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResetPasswordController {

    private final EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    public ResetPasswordController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/forgot_password")
    public String forgot_password() {
        return "forgot_password";
    }
    @PostMapping("/reset_password")
    public String sendCode(@RequestParam(name = "email") String email, Model model) {

        NootchUser user = userRepository.getUserByEmail(email);
        if(user == null) {
            model.addAttribute("alert", "Invalid email!");
            model.addAttribute("dataSubmitted", true);
            return "forgot_password";
        }

        String code = generateVerificationCode();
        user.setVerificationCode(code);
        userRepository.saveAndFlush(user);

        String subject = "Reset Your Password";
        String body = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <title>Password Reset</title>\n" +
                "  <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f6f6f6;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 50px auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: white;\n" +
                "            box-shadow: 0 2px 8px rgba(0,0,0,0.1);\n" +
                "            border-radius: 25px;\n" +
                "            border: 2px solid #ddd;\n" +
                "        }\n" +
                "        .header {\n" +
                "            text-align: center;\n" +
                "            border-bottom: 1px solid #ddd;\n" +
                "            padding-bottom: 20px;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        .header img {\n" +
                "            width: 80px;\n" +
                "            border-radius: 50%;\n" +
                "        }\n" +
                "        .content {\n" +
                "            font-size: 16px;\n" +
                "            line-height: 1.6;\n" +
                "        }\n" +
                "        .content p {\n" +
                "            margin: 0 0 20px;\n" +
                "        }\n" +
                "        .content .recovery-code {\n" +
                "            display: inline-block;\n" +
                "            padding: 10px 20px;\n" +
                "            background-color: #007BFF;\n" +
                "            color: white;\n" +
                "            font-weight: bold;\n" +
                "            border-radius: 4px;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "        .content .bold-text {\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            border-top: 1px solid #ddd;\n" +
                "            padding-top: 20px;\n" +
                "            margin-top: 20px;\n" +
                "            font-size: 12px;\n" +
                "            color: #888;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "  <div class=\"header\">\n" +
                "    <img src=\"https://vladgura.com/NuciLogov1.jpg\" alt=\"Nootch Logo\">\n" +
                "    <h2>Reset Your Password</h2>\n" +
                "  </div>\n" +
                "  <div class=\"content\">\n" +
                "    <p>Hello,</p>\n" +
                "    <p>You requested a <span class=\"bold-text\">password reset</span> for your Nootch account!</p>\n" +
                "    <p>This is your <span class=\"bold-text\">recovery code</span>:</p>\n" +
                "    <p class=\"recovery-code\">".concat(code) + "</p>\n" +
                "    <p>If you haven't requested a password reset, ignore this email.</p>\n" +
                "    <p class=\"bold-text\">Best regards,<br>The Nootch Team</p>\n" +
                "  </div>\n" +
                "  <div class=\"footer\">\n" +
                "    <p>&copy; 2024 Nootch. All rights reserved.</p>\n" +
                "  </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>\n";
        emailService.sendMail(email, subject, body);

        model.addAttribute("alert", "You've been sent a recovery code!");
        model.addAttribute("dataSubmitted", true);

        return "reset_password";
    }

    @PostMapping("/submit_reset_password")
    public String resetPassword(@RequestParam(name = "recoveryCode") String code, @RequestParam(name = "newPassword") String password, Model model) {

        NootchUser user = userRepository.getUserByVerificationCode(code);

        if(user == null) {
            model.addAttribute("alert", "Invalid recovery code!");
            model.addAttribute("dataSubmitted", true);
            return "reset_password";
        }

        user.setPassword(password.hashCode());
        user.setVerificationCode(null);
        userRepository.saveAndFlush(user);

        model.addAttribute("alert", "Successfully updated your password, ".concat(user.getUsername()).concat("!"));
        model.addAttribute("dataSubmitted", true);
        return "index";
    }

    private String generateVerificationCode() {

        int length = 6;
        boolean useLetters = false;
        boolean useNumbers = true;

        return RandomStringUtils.random(length, useLetters, useNumbers);
    }
}
