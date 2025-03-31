package org.jboss.as.quickstarts.kitchensink.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.GET;

import org.jboss.as.quickstarts.kitchensink.data.MemberRepository;
import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.jboss.as.quickstarts.kitchensink.service.MemberRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/members")
public class MemberResourceRESTService {

    @Autowired
    private Logger log;

    @Autowired
    private Validator validator;

    // @Autowired
    // private MemberRepository repository;

    @Inject
    private MemberRepository repository;

    @Autowired
    private MemberRegistration registration;

    //CAZZO
    @GetMapping("/allmembers")
    public List<Member> listAllMembers() {
        return repository.findAllByOrderByNameAsc();
    }

    
    public ResponseEntity<Member> lookupMemberById(@PathVariable("id") String id) {
        Optional<Member> member = repository.findById(id);
        if (member.isPresent()) {
        return new ResponseEntity<>(member.get(), HttpStatus.OK);
        } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // @GetMapping("/members")
    // public String listAllMembers() {
    //     return "List of members";
    // }

    @PostMapping
    public ResponseEntity<?> createMember(@RequestBody Member member) {
        try {
            // Validates member using bean validation
            validateMember(member);
            registration.register(member);
            // Create an "ok" response
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ConstraintViolationException ce) {
            // Handle bean validation issues
            return createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            // Handle the unique constraint violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("email", "Email taken");
            return new ResponseEntity<>(responseObj, HttpStatus.CONFLICT);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
        }
    }

    private void validateMember(Member member) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Member>> violations = validator.validate(member);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<>(violations));
        }
        // Check the uniqueness of the email address
        if (emailAlreadyExists(member.getEmail())) {
            throw new ValidationException("Unique Email Violation");
        }
    }

    private ResponseEntity<Map<String, String>> createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validation completed. violations found: " + violations.size());
        Map<String, String> responseObj = new HashMap<>();
        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
    }

    public boolean emailAlreadyExists(String email) {
        Member member = null;
        try {
            member = repository.findByEmail(email);
        } catch (NoResultException e) {
            // ignore
        }
        return member != null;
    }
}