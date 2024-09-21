package qbit.entier.hostel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import qbit.entier.hostel.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	Room findByName(String name);

	Page<Room> findByRoomTypeId(Long id, Pageable pageable);
}
