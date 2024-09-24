package qbit.entier.hostel.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import qbit.entier.hostel.entity.ExpenseCategory;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseCategoryDto {
	private Long id;
	private String name;
	private String description;
	private Long cost;
	
	public static ExpenseCategoryDto toDto(ExpenseCategory entity) {
		return ExpenseCategoryDto.builder()
				.id(entity.getId())
				.name(entity.getName())
				.description(entity.getDescription())
				.cost(entity.getCost())
				.build();
	}
	
	public static ExpenseCategoryDto toDto(Optional<ExpenseCategory> entityOptional) {
		ExpenseCategory entity = entityOptional.get();
		return toDto(entity);
	}
}
