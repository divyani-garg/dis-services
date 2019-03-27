package sgsits.cse.dis.academics.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sgsits.cse.dis.academics.model.Scheme;

@Repository("schemeRepository")
public interface SchemeRepository extends JpaRepository<Scheme, Long> 
{
	public List<Scheme> findByCourseIdAndSessionAndYearAndSemester(String course, String session, String year,
			String semester);
}
