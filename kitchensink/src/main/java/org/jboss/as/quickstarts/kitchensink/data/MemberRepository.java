package org.jboss.as.quickstarts.kitchensink.data;

import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;


@Repository("mongoMemberRepository")
@Primary
public interface MemberRepository extends MongoRepository<Member, String> {
    
    Member findByEmail(String email);
    List<Member> findAllByOrderByNameAsc();

    // @Query("SELECT COUNT(m) FROM Member m")
    // long countAllMembers();
}