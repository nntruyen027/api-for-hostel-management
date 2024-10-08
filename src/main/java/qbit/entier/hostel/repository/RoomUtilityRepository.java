package qbit.entier.hostel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import qbit.entier.hostel.entity.RoomUtility;

public interface RoomUtilityRepository extends JpaRepository<RoomUtility, Long> {
	Page<RoomUtility> findByRoomId(Long id, Pageable pageable);
	Page<RoomUtility> findByServiceId(Long id, Pageable pageable);
}
