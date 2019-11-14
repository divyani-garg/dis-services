package sgsits.cse.dis.user.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.user.model.UserCompetitiveExams;

@Repository("userCompetitiveExamsRepository")
public interface UserCompetitiveExamsRepository extends JpaRepository<UserCompetitiveExams, Long>{
	List<UserCompetitiveExams> findByUserId(Long id);
}
