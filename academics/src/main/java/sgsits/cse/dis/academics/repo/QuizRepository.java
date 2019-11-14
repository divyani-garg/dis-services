package sgsits.cse.dis.academics.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sgsits.cse.dis.academics.model.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
	
}
