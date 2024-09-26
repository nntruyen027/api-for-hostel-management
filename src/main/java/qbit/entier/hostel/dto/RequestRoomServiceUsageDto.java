package qbit.entier.hostel.dto;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.*;
import qbit.entier.hostel.entity.RoomServiceUsage;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestRoomServiceUsageDto {
    private Long id;

    private Long service;  
    
    private Long invoice;

    private int oldIndex;
    
    private int newIndex;
    
    private BigDecimal cost;
    
    public static RequestRoomServiceUsageDto toDto(RoomServiceUsage entity) {
    	return RequestRoomServiceUsageDto.builder()
    			.id(entity.getId())
    			.invoice(entity.getInvoice().getId())
    			.service(entity.getService().getId())
    			.oldIndex(entity.getOldIndex())
    			.newIndex(entity.getNewIndex())
    			.cost(entity.getCost())
    			.build();
    }
    
    public static RequestRoomServiceUsageDto toDto(Optional<RoomServiceUsage> entity) {
    	return toDto(entity.get());
    }
}
