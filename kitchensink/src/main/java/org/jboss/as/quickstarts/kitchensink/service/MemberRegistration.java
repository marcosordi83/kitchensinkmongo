package org.jboss.as.quickstarts.kitchensink.service;
import org.jboss.as.quickstarts.kitchensink.data.MemberRepository;
import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;



import java.util.logging.Logger;

@Service
public class MemberRegistration {

    @Autowired
    private Logger log;

    @Autowired
    private MemberRepository memberRepository;  // Using MongoDB repository instead of EntityManager

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void register(Member member) throws Exception {
        log.info("Registering " + member.getName());
        
        // Save to MongoDB using repository instead of EntityManager
        memberRepository.save(member);
        
        // Publish event after successful save
        eventPublisher.publishEvent(member);
    }

}