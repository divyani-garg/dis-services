package sgsits.cse.dis.administration.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.administration.model.ECCWComplaints;

//Engineering Cell and Central Workshop

@Repository("")
public interface ECCWComplaintRepository extends JpaRepository<ECCWComplaints, Long> {

}