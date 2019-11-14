package sgsits.cse.dis.moodle.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sgsits.cse.dis.moodle.model.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
	
}