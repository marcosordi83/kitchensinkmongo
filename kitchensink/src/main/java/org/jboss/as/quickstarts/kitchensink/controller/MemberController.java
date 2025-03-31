package org.jboss.as.quickstarts.kitchensink.controller;

import org.jboss.as.quickstarts.kitchensink.data.MemberRepository;
import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.jboss.as.quickstarts.kitchensink.service.MemberRegistration;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Named
@ViewScoped
@Component
public class MemberController implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(MemberController.class.getName());

    private List<Member> members = new ArrayList<>();
    private Member newMember = new Member();

    private final MemberRepository memberRepository;
    private final MemberRegistration memberRegistration;

    // Constructor-based dependency injection
    @Autowired
    public MemberController(MemberRepository memberRepository, 
                             MemberRegistration memberRegistration) {
        this.memberRepository = memberRepository;
        this.memberRegistration = memberRegistration;
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

    public void register() {
        try {
            memberRegistration.register(newMember);
            loadMembers(); // Refresh the list after registration
            newMember = new Member(); // Reset the form
        } catch (Exception e) {
            logger.severe("Registration failed: " + e.getMessage());
        }
    }

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