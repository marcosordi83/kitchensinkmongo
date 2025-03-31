package org.jboss.as.quickstarts.kitchensink.controller;

import org.jboss.as.quickstarts.kitchensink.data.MemberRepository;
import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.jboss.as.quickstarts.kitchensink.service.MemberRegistration;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Qualifier;

import jakarta.validation.Valid;

@SpringBootApplication
@RestController
@RequestMapping("/members")
@ComponentScan(basePackages = "org.jboss.as.quickstarts.kitchensink")
//@EnableJpaRepositories(basePackages = "org.jboss.as.quickstarts.kitchensink.data")
public class MemberRestController {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(MemberController.class.getName());

    private List<Member> members = new ArrayList<>();

    @Autowired
    @Qualifier("mongoMemberRepository")
    private MemberRepository memberRepository;

    @Autowired
    private MemberRegistration memberRegistration;

        //added for trobleshooting
        @GetMapping("/count")
        public long countMembers() {
            return memberRepository.count();
        }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        return "register";
    }

    @GetMapping("/allMembers")
    public List<Member> getAllMembers() {
        System.out.println("I'm here cane");
        return memberRepository.findAllByOrderByNameAsc();
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody Member newMember, RedirectAttributes redirectAttributes) {
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