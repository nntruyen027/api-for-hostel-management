package qbit.entier.hostel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import qbit.entier.hostel.entity.RoomType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeDto {
	private Long id;
	private String name;
	private String description;
	private Long basePrice;
	
	public static RoomTypeDto toDto(RoomType type) {
		if(type == null)
			return null;
		return new RoomTypeDto(type.getId(), type.getName(), type.getDescription(), type.getBasePrice());
	}
}
