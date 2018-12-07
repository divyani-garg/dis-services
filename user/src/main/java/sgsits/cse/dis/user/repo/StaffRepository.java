package sgsits.cse.dis.user.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.user.model.StaffProfile;

@Repository("staffRepository")
public interface StaffRepository extends JpaRepository<StaffProfile, Long>{
	Optional<StaffProfile> findByEmail(String email);
	Optional<StaffProfile> findByResetToken(String resetToken);
	Optional<StaffProfile> findByEmployeeId(String employeeId);
}
