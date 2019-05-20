package sgsits.cse.dis.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sgsits.cse.dis.user.model.UserCulturalActivityAchievements;

@Repository("userCulturalActivityAchievementsRepository")
public interface UserCulturalActivityAchievementsRepository extends JpaRepository<UserCulturalActivityAchievements, Long>{
	List<UserCulturalActivityAchievements> findByUserId(Long id);

	boolean existsByUserIdAndNameOfActivity(long id, String nameOfActivity);

	Optional<UserCulturalActivityAchievements> findByUserIdAndNameOfActivity(long id, String nameOfActivity);
}
