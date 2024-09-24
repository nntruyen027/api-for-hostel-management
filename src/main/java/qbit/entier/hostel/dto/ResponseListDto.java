package qbit.entier.hostel.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseListDto<T> {
    private List<T> data;
    private Meta meta;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Meta {
        private int totalPages;
        private Long totalElements;
        private int page;
        private int limit;
        
    }
}
