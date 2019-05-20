package sgsits.cse.dis.administration.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.ThesisME;

@Repository("thesisMERepository")
public interface ThesisMERepository extends JpaRepository<ThesisME, Long>{

	boolean existsBySerialNo(Long serialNo);

	Optional<ThesisME> findBySerialNo(Long serialNo);
}
