package sgsits.cse.dis.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.user.model.UserPlacement;
//import sgsits.cse.dis.user.model.UserProjects;
///import sgsits.cse.dis.user.model.UserProjects;

@Repository("userPlacementRepository")
public interface UserPlacementRepository extends JpaRepository<UserPlacement, Long>{

	List<UserPlacement> findByUserId(Long id);

	Optional<UserPlacement> findByUserIdAndCompanyName(long id, String companyName);

	boolean existsByUserIdAndCompanyName(long id, String companyName);
	

}
