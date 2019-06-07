package sgsits.cse.dis.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.user.model.StaffDuty;
//import sgsits.cse.dis.user.model.StaffLeave;

@Repository("staffDutiesRepository")
public interface StaffDutyRepository extends JpaRepository<StaffDuty, Long> {

	List<StaffDuty> findByUserId(Long id);

	boolean existsByUserIdAndPlace(long id, String place);

	Optional<StaffDuty> findByUserIdAndPlace(long id, String place);

}
