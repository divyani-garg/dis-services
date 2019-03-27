package sgsits.cse.dis.administration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.OverviewDetails;

@Repository("overviewDetailsRepository")
public interface OverviewDetailsRepository extends JpaRepository<OverviewDetails, Long> {

	List<OverviewDetails> findBySession(String session);
	
	@Query(value="SELECT id from overview_details WHERE created_date = (SELECT max(created_date) from overview_details) LIMIT 1" , nativeQuery = true) 
	long findMax();

}
