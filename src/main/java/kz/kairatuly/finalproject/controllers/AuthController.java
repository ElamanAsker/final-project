package kz.kairatuly.finalproject.controllers;

import kz.kairatuly.finalproject.services.FileService;
import kz.kairatuly.finalproject.services.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class AuthController {

    @Autowired
    private MyUserService myUserService;

    @Autowired
    private FileService fileService;

    @GetMapping(value = "/sign-in")
    public String openLoginPage(Model model) {
        return "loginpage";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/profile")
    public String openProfilePage(Model model) {
        return "profile";
    }

    @GetMapping(value = "/403")
    public String openAccessDeniedPage(Model model) {
        return "accessdenied";
    }

    @GetMapping(value = "/sign-up")
    public String openRegisterPage(Model model) {
        return "register";
    }

    @PostMapping(value = "/to-register")
    public String toRegister(@RequestParam(name = "user_email") String email,
                             @RequestParam(name = "user_password") String password,
                             @RequestParam(name = "user_re_password") String rePassword,
                             @RequestParam(name = "user_full_name") String fullName) {
        Boolean result = myUserService.registerUser(email, password, rePassword, fullName);
        if (result != null) {
            if (result == Boolean.TRUE) {
                return "redirect:/sign-up?success";
            } else {
                return "redirect:/sign-up?passworderror";
            }
        } else {
            return "redirect:/sign-up?usererror";
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/upload-avatar")
    public String uploadAvatar(@RequestParam(name = "user_avatar") MultipartFile file) {
        fileService.uploadAvatar(file);
        return "redirect:/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/view-pic/{url}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getPicture(@PathVariable(name = "url") String url) throws IOException {
        return fileService.getAvatar(url);
    }
}
