package sgsits.cse.dis.administration.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.ThesisBE;

@Repository("thesisBERepository")
public interface ThesisBERepository extends JpaRepository<ThesisBE, Long>{

	boolean existsBySerialNo(Long serialNo);

}
