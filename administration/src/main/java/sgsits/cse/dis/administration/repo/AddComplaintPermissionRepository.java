package sgsits.cse.dis.administration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.AddComplaintPermission;

@Repository("addComplaintPermissionRepository")
public interface AddComplaintPermissionRepository extends JpaRepository<AddComplaintPermission, Long>{

	List<AddComplaintPermission> findByUser(String user);

}
