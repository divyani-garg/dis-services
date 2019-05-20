package sgsits.cse.dis.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.user.model.UserWorkExperience;

@Repository("userWorkExperienceRepository")
public interface UserWorkExperienceRepository extends JpaRepository<UserWorkExperience, Long>{
	List<UserWorkExperience> findByUserId(Long id);

	boolean existsByUserIdAndOrganizationName(long id, String organizationName);

	Optional<UserWorkExperience> findByUserIdAndOrganizationName(long id, String organizationName);

}
