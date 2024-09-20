package qbit.entier.hostel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import qbit.entier.hostel.entity.RoomImage;

public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {
	List<RoomImage> findByRoomId(String id);
}
