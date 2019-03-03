package sgsits.cse.dis.academics.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.academics.model.Courses;

@Repository("coursesRepository")
public interface CoursesRepository extends JpaRepository<Courses, Long>{
	List<Courses> findByCourseId(String id);
}
