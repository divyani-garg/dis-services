package sgsits.cse.dis.administration.repo;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.Notification;

@Repository("notificationRepository")
public interface NotificationRepository extends JpaRepository<Notification, Long>{

	Notification findByNotificationId(Long notiId);


}
