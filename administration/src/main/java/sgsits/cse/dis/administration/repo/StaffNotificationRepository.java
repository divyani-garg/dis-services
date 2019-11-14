package sgsits.cse.dis.administration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.StaffNotification;

@Repository("staffNotificationRepository")
public interface StaffNotificationRepository  extends JpaRepository<StaffNotification, Long>{

	List<StaffNotification> findByUserIdContainingAndStatus(String id, int i);

	List<StaffNotification> findByUserId(String id);

}
