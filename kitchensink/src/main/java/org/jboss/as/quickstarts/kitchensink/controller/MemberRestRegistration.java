package org.jboss.as.quickstarts.kitchensink.controller;

import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.jboss.as.quickstarts.kitchensink.service.MemberRegistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.logging.Logger;

@RestController
@RequestMapping("/members")
public class MemberRestRegistration {

    private static final Logger logger = Logger.getLogger(MemberRestRegistration.class.getName());
    
    @Autowired
    private MemberRegistration memberRegistration;

    @PostMapping("/registerjson")
    public String registerJson(@Valid @RequestBody Member newMember, RedirectAttributes redirectAttributes) {
        System.out.println("newMember is  " + newMember);
        try {
            memberRegistration.register(newMember);
            redirectAttributes.addFlashAttribute("message", "Registered! Registration successful");
            return "redirect:/register";
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            System.out.println(errorMessage);
            return "redirect:/failed";
        }
    }
    
    private String getRootErrorMessage(Exception e) {
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            return errorMessage;
        }
        
        Throwable t = e;
        while (t != null) {
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        
        return errorMessage;
    }
}