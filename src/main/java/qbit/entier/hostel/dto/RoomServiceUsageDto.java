package qbit.entier.hostel.dto;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.*;
import qbit.entier.hostel.entity.Invoice;
import qbit.entier.hostel.entity.RoomServiceUsage;
import qbit.entier.hostel.entity.Service;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomServiceUsageDto {
    private Long id;

    private Service service;  
    
    private Invoice invoice;

    private int oldIndex;
    
    private int newIndex;
    
    private BigDecimal cost;
    
    public static RoomServiceUsageDto toDto(RoomServiceUsage entity) {
    	return RoomServiceUsageDto.builder()
    			.id(entity.getId())
    			.invoice(entity.getInvoice())
    			.service(entity.getService())
    			.oldIndex(entity.getOldIndex())
    			.newIndex(entity.getNewIndex())
    			.cost(entity.getCost())
    			.build();
    }
    
    public static RoomServiceUsageDto toDto(Optional<RoomServiceUsage> entity) {
    	return toDto(entity.get());
    }
}
