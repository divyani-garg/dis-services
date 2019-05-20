package sgsits.cse.dis.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.user.model.UserCompetitiveExams;

@Repository("userCompetitiveExamsRepository")
public interface UserCompetitiveExamsRepository extends JpaRepository<UserCompetitiveExams, Long>{
	List<UserCompetitiveExams> findByUserId(Long id);

	boolean existsByUserIdAndNameOfExam(long id, String nameOfExam);

	Optional<UserCompetitiveExams> findByUserIdAndNameOfExam(long id, String nameOfExam);
}
