package qbit.entier.hostel.dto;

import java.util.Optional;

import lombok.*;
import qbit.entier.hostel.entity.ExpenseCategory;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseCategoryDto {
	private Long id;
	private String name;
	private String description;
	
	public static ExpenseCategoryDto toDto(ExpenseCategory entity) {
		return ExpenseCategoryDto.builder()
				.id(entity.getId())
				.name(entity.getName())
				.description(entity.getDescription())
				.build();
	}
	
	public static ExpenseCategoryDto toDto(Optional<ExpenseCategory> entityOptional) {
		ExpenseCategory entity = entityOptional.get();
		return toDto(entity);
	}
}
