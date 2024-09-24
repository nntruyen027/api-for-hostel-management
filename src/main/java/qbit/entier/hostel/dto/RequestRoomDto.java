package qbit.entier.hostel.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import qbit.entier.hostel.entity.Room;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestRoomDto {
	private Long id;
	private String name;
	private Long price;
	private int max;
	
	private Long roomType;
	
	public static RequestRoomDto toDto(Room room) {
        if (room == null) {
            return null;
        }
        return new RequestRoomDto(
            room.getId(),
            room.getName(),
            room.getPrice(),
            room.getMax(),
            room.getRoomType().getId());
    }
	
	public static RequestRoomDto toDto(Optional<Room> room) {
        if (room == null) {
            return null;
        }
        return new RequestRoomDto(
            room.get().getId(),
            room.get().getName(),
            room.get().getPrice(),
            room.get().getMax(),
            room.get().getRoomType().getId());

    }
}
