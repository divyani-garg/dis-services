package sgsits.cse.dis.user.service;

import java.util.List;
import java.util.Optional;
import sgsits.cse.dis.user.model.StudentProfile;

public interface StudentService {
	public Optional<StudentProfile> findByEmail(String email);
	public Optional<StudentProfile> findByResetToken(String resetToken);
	public void save(StudentProfile studentProfile);

}
