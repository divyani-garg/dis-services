package sgsits.cse.dis.administration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.StudentNotification;

@Repository("studentNotificationRepository")
public interface StudentNotificationRepository  extends JpaRepository<StudentNotification, Long>{

	List<StudentNotification> findByUserIdContaining(String id);

	List<StudentNotification> findByCourseAndAdmissionYearAndUserId(String course, int admissionYear, String userId);




}
