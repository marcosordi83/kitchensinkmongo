package org.jboss.as.quickstarts.kitchensink.controller;

import org.jboss.as.quickstarts.kitchensink.data.MemberRepository;
import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.jboss.as.quickstarts.kitchensink.service.MemberRegistration;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Qualifier;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/members")
public class MemberController {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(MemberController.class.getName());

    
    private List<Member> members = new ArrayList<>();
    private Member newMember = new Member();

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
        model.addAttribute("newMember", new Member());
        return "register";
    }

    @GetMapping("/allMembers")
    public List<Member> getAllMembers() {
        System.out.println("I'm here cane");
        return memberRepository.findAllByOrderByNameAsc();
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newMember") Member newMember, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        System.out.println("Entering register method");
        System.out.println("newMember is  " + newMember);
    
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "There were errors with your form submission.");
            return "register";
        }
        try {
            memberRegistration.register(newMember);
            redirectAttributes.addFlashAttribute("message", "Registered! Registration successful");
            return "redirect:/members/register";
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            System.out.println(errorMessage);
            return "redirect:/failed";
        }
    }

    // @PostMapping("/registerjson")
    // public String registerJson(@Valid @RequestBody Member newMember, RedirectAttributes redirectAttributes) {
    //     System.out.println("newMember is  " + newMember);
    //     try {
    //         memberRegistration.register(newMember);
    //         redirectAttributes.addFlashAttribute("message", "Registered! Registration successful");
    //         return "redirect:/register";
    //     } catch (Exception e) {
    //         String errorMessage = getRootErrorMessage(e);
    //         redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
    //         System.out.println(errorMessage);
    //         return "redirect:/failed";
    //     }
    // }

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

     @PostConstruct
    public void init() {
        loadMembers();
    }

    public void loadMembers() {
        try {
            this.members = memberRepository.findAllByOrderByNameAsc();
            logger.info("Total members cazzo found: " + members.size());
        } catch (Exception e) {
            logger.severe("Error loading members: " + e.getMessage());
            this.members = new ArrayList<>();
        }
    }

    // public void register() {
    //     try {
    //         memberRegistration.register(newMember);
    //         loadMembers(); // Refresh the list after registration
    //         newMember = new Member(); // Reset the form
    //     } catch (Exception e) {
    //         logger.severe("Registration failed: " + e.getMessage());
    //     }
    // }

    // Getters and Setters
    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Member getNewMember() {
        return newMember;
    }

    public void setNewMember(Member newMember) {
        this.newMember = newMember;
    }
}