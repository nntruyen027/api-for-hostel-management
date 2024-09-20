package qbit.entier.hostel.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import qbit.entier.hostel.entity.Room;
import qbit.entier.hostel.entity.RoomType;

public interface RoomRepository extends JpaRepository<Room, Long> {
	Room findByName(String name);

	Page<Room> findByRoomTypeId(Long id, Pageable pageable);
}
