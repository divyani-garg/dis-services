package sgsits.cse.dis.infrastructure.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sgsits.cse.dis.infrastructure.model.FacultyRoomAssociation;


@Repository("facultyRoomAssociationRepository")
public interface FacultyRoomAssociationRepository extends JpaRepository<FacultyRoomAssociation, Long> {
	List<FacultyRoomAssociation> findByRoomId(String room_id);
}
