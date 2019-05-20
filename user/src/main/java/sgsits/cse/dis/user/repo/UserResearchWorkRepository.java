package sgsits.cse.dis.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.user.model.UserResearchWork;

@Repository("userResearchWorkRepository")
public interface UserResearchWorkRepository extends JpaRepository<UserResearchWork, Long>{
	List<UserResearchWork> findByUserId(Long id);

	boolean existsByUserIdAndTitle(long id, String title);

	Optional<UserResearchWork> findByUserIdAndTitle(long id, String title);
}
