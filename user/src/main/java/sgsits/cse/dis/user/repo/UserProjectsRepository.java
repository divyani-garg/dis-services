package sgsits.cse.dis.user.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.user.model.UserProjects;

@Repository("userProjectsRepository")
public interface UserProjectsRepository extends JpaRepository<UserProjects, Long>{
	List<UserProjects> findByUserId(Long id);
}
