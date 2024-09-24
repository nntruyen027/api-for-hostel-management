package qbit.entier.hostel.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import qbit.entier.hostel.entity.Service;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceDto {
	private Long id;
	private String name;
	private String description;
	private Long cost;
	
	public static ServiceDto toDto(Service entity) {
		return ServiceDto.builder()
				.id(entity.getId())
				.name(entity.getName())
				.description(entity.getDescription())
				.cost(entity.getCost())
				.build();
	}
	
	public static ServiceDto toDto(Optional<Service> entityOptional) {
		Service entity = entityOptional.get();
		return toDto(entity);
	}
}
