package sgsits.cse.dis.academics.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.academics.model.ExtraClassTimeTable;

@Repository("extraClassTimeTable")
public interface ExtraClassTimeTableRepository extends JpaRepository<ExtraClassTimeTable, Long> {

	public List<ExtraClassTimeTable> findByCourseIdAndSessionAndYearAndSemesterAndDate(String course, String session,
			String year, String semester, String date);

	public List<ExtraClassTimeTable> findBySessionAndSemesterAndDateAndFaculty1OrFaculty2OrFaculty3(String session,
			String semester, String date, String faculty, String faculty2, String faculty3);

	public List<ExtraClassTimeTable> findBySessionAndSemesterAndDateAndLabTechnician(String session, String semester,
			String date, String lt);

	public List<ExtraClassTimeTable> findBySessionAndSemesterAndTaAndDate(String session, String semester, String ta,
			String date);

	public List<ExtraClassTimeTable> findByLocationAndSessionAndSemesterAndDate(String location, String session,
			String semester, String date);
	
}
