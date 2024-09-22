package qbit.entier.hostel.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import qbit.entier.hostel.entity.Room;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
	private Long id;
	private String name;
	private Long price;
	private int max;
	
	private RoomTypeDto roomType;
	
	private List<RoomImageDto> images;
	
	private List<UserDto> customers;
	
	public static RoomDto toDto(Room room) {
        if (room == null) {
            return null;
        }
        return new RoomDto(
            room.getId(),
            room.getName(),
            room.getPrice(),
            room.getMax(),
            RoomTypeDto.toDto(room.getRoomType()),
            room.getImages().stream()
                .map(RoomImageDto::toDto)
                .collect(Collectors.toList()),
            room.getCustomer().stream()
            	.map(UserDto::toDto)
            	.collect(Collectors.toList())
            );
    }
	
	public static RoomDto toDto(Optional<Room> room) {
        if (room == null) {
            return null;
        }
        return new RoomDto(
            room.get().getId(),
            room.get().getName(),
            room.get().getPrice(),
            room.get().getMax(),
            RoomTypeDto.toDto(room.get().getRoomType()),
            room.get().getImages().stream()
                .map(RoomImageDto::toDto)
                .collect(Collectors.toList()),
            room.get().getCustomer().stream()
            	.map(UserDto::toDto)
            	.collect(Collectors.toList())
        );
    }
}
