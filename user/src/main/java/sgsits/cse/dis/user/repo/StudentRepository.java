package sgsits.cse.dis.user.repo;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sgsits.cse.dis.user.model.StudentProfile;

@Repository("studentRepository")
public interface StudentRepository extends JpaRepository<StudentProfile, Long>{
	Optional<StudentProfile> findByEmail(String email);
	Optional<StudentProfile> findByResetToken(String resetToken);
	Optional<StudentProfile> findByEnrollmentId(String enrollmentId);
}
