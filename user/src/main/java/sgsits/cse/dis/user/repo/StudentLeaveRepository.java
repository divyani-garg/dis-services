package sgsits.cse.dis.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.user.model.StudentLeave;

@Repository("studentLeaveRepository")
public interface StudentLeaveRepository extends JpaRepository<StudentLeave, Long>{

	boolean existsByCreatedByAndFrom(long id, String from); 

}
