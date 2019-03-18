package sgsits.cse.dis.academics.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.academics.model.SemesterTimeTable;

@Repository("semesterTimeTableRepository")
public interface SemesterTimeTableRepository extends JpaRepository<SemesterTimeTable, Long> {
	public List<SemesterTimeTable> findByFaculty1OrFaculty2OrFaculty3AndSession(String faculty1, String faculty2, String faculty3, String session);
	public List<SemesterTimeTable> findByLabTechnicianAndSession(String lt, String session);
	public List<SemesterTimeTable> findByFaculty1OrFaculty2OrFaculty3AndSessionAndSemester(String faculty, String faculty2, String faculty3, String session, String semester);
	public List<SemesterTimeTable> findByLabTechnicianAndSessionAndSemester(String lt, String session, String semester);
	public List<SemesterTimeTable> findBySessionAndSemesterAndFaculty1OrFaculty2OrFaculty3(String session,
			String semester, String faculty, String faculty2, String faculty3);
	public List<SemesterTimeTable> findBySessionAndSemesterAndTa(String session, String semester, String ta);
	public List<SemesterTimeTable> findByCourseIdAndSessionAndYearAndSemester(String course, String session,
			String year, String semester);
	public List<SemesterTimeTable> findBySessionAndYearAndSemester(String session, String year, String semester);
	public List<SemesterTimeTable> findByLocationAndSessionAndSemester(String location, String session,
			String semester);
}
