package sgsits.cse.dis.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.user.model.UserAddress;

@Repository("userAddressRepository")
public interface UserAddressRepository extends JpaRepository<UserAddress, Long>{
	List<UserAddress> findByUserId(Long id);

	Optional<UserAddress> findByUserIdAndType(long id, String type);

	boolean existsByUserIdAndType(long id, String type);
	
	
}