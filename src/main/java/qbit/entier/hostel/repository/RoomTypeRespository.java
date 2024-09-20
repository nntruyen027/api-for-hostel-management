package qbit.entier.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import qbit.entier.hostel.entity.RoomType;

public interface RoomTypeRespository extends JpaRepository<RoomType, Long> {
	RoomType findByName(String name);
}
