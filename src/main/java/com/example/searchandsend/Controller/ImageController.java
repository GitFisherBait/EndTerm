package com.example.searchandsend.Controller;

import com.example.searchandsend.Model.Image;
import com.example.searchandsend.Service.EmailService;
import com.example.searchandsend.Service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
//setup
public class ImageController {
    private final ImageService imageService;
    private final EmailService emailService;
    public ImageController(ImageService imageService, EmailService emailService) {
        this.imageService = imageService;
        this.emailService = emailService;
    }
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("name", imageService.findByName("MatchBookTexture.png"));
        return "search";
    }
    @GetMapping("/search")
    public String searchImage(Model model, @RequestParam("name") String name) {
        Optional<Image> imageOpt = imageService.findByName(name);
        if (imageOpt.isPresent()) {
            Image image = imageOpt.get();
            model.addAttribute("image", image);
        } else {
            model.addAttribute("error", "Image not found!");
        }
        return "search";
    }
    @PostMapping("/sendImage")
    public String sendImage(@RequestParam(value = "imageId", required = false) Long imageId, Model model) {
        if (imageId == null) {
            model.addAttribute("error", "Image ID is required.");
            return "sendMail";
        }
        Optional<Image> imageOpt = imageService.findById(imageId);
        if (imageOpt.isEmpty()) {
            model.addAttribute("error", "Image not found for ID: " + imageId);
            return "sendMail";
        }
        Image image = imageOpt.get();
        try {
            emailService.sendMessageWithAttachment(image.getEmail(), image.getName());
            model.addAttribute("success", "Image sent to: " + image.getEmail());
        } catch (Exception e) {
            model.addAttribute("error", "Error while sending email: " + e.getMessage());
        }

        return "sendMail";
    }
}
