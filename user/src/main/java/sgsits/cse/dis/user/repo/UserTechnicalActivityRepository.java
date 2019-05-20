package sgsits.cse.dis.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.user.model.UserTechnicalActivity;

@Repository("userTechnicalActivityRepository")
public interface UserTechnicalActivityRepository extends JpaRepository<UserTechnicalActivity, Long>{
	List<UserTechnicalActivity> findByUserId(Long id);

	boolean existsByUserIdAndSubject(long id, String subject);

	Optional<UserTechnicalActivity> findByUserIdAndSubject(long id, String subject);
}
