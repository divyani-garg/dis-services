package sgsits.cse.dis.user.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.user.model.StaffLeave;
//import sgsits.cse.dis.user.model.StaffProfile;

@Repository("staffLeaveRepository")
public interface StaffLeaveRepository extends JpaRepository<StaffLeave, Long>{

	List<StaffLeave> findByCreatedBy(long id);

}
