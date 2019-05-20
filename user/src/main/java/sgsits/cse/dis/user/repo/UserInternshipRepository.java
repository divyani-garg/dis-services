package sgsits.cse.dis.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.user.model.UserInternship;

@Repository("userInternshipRepository")
public interface UserInternshipRepository extends JpaRepository<UserInternship, Long>{
	List<UserInternship> findByUserId(Long id);

	boolean existsByUserIdAndCompanyName(long id, String companyName);

	Optional<UserInternship> findByUserIdAndCompanyName(long id, String companyName);
}
