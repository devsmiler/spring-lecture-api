package member.week1.invitation;

import member.week1.invitation.domain.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    @Query("select i from Invitation i where i.isExpired = false")
    Optional<Invitation> findInvitationByUuid(String uuid);

}
