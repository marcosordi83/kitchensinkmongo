package org.jboss.as.quickstarts.kitchensink.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jboss.as.quickstarts.kitchensink.data.MemberRepository;
import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.annotation.SessionScope;
// import javax.faces.bean.SessionScoped;
// import jakarta.enterprise.context.SessionScoped;
import org.springframework.web.context.annotation.RequestScope;

@Controller
//@SessionScoped
@RequestScope
public class HomeController implements Serializable{

    @Autowired
    @Qualifier("mongoMemberRepository")
    private MemberRepository memberRepository;

    private List<Member> members = new ArrayList<>();
    


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {

        this.members = memberRepository.findAllByOrderByNameAsc();

        System.out.println("HomeControllerMembers" + this.members);
        model.addAttribute("members", this.members);

        return "index"; // This will resolve to /WEB-INF/views/index.xhtml
    }
}