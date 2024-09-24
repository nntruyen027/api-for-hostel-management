package qbit.entier.hostel.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import qbit.entier.hostel.entity.RoomUtility;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomUtilityDto {
	private Long id;
	private RoomDto room;
	private ExpenseCategoryDto expenseCategory;

	public static RoomUtilityDto toDto(RoomUtility entity) {
		return RoomUtilityDto.builder()
				.id(entity.getId())
				.room(RoomDto.toDto(entity.getRoom()))
				.expenseCategory(ExpenseCategoryDto.toDto(entity.getExpenseCategory()))
				.build();
	}

	public static RoomUtilityDto toDto(Optional<RoomUtility> entity) {
		return toDto(entity.get());
	}
}
