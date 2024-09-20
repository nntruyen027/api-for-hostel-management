package qbit.entier.hostel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import qbit.entier.hostel.entity.RoomImage;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomImageDto {
	private Long id;
	private String image;
	
	public static RoomImageDto toDto(RoomImage image) {
		if(image == null)
			return null;
		return new RoomImageDto(image.getId(), image.getImage());
	}
}
