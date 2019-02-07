package sgsits.cse.dis.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sgsits.cse.dis.user.model.StudentProfile;
import sgsits.cse.dis.user.repo.StudentRepository;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public Optional<StudentProfile> findByEmail(String email) {
		return studentRepository.findByEmail(email);
	}

	@Override
	public Optional<StudentProfile> findByResetToken(String resetToken) {
		return studentRepository.findByResetToken(resetToken);
	}

	@Override
	public void save(StudentProfile studentProfile) {
		studentRepository.save(studentProfile);
	}

}