package qbit.entier.hostel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import qbit.entier.hostel.dto.ResponseListDto;
import qbit.entier.hostel.dto.ResponseListDto.Meta;
import qbit.entier.hostel.dto.RoomServiceUsageDto;
import qbit.entier.hostel.entity.RoomServiceUsage;
import qbit.entier.hostel.repository.RoomServiceUsageRepository;

@org.springframework.stereotype.Service
public class RoomUsageService {
	@Autowired
	private RoomServiceUsageRepository repository;
	
	public ResponseListDto<RoomServiceUsageDto> getAll(int limit, int page, String orderBy, boolean descending) {
		Sort sort;
		if(descending)
			sort = Sort.by(Sort.Order.desc(orderBy));
		else
			sort = Sort.by(Sort.Order.asc(orderBy));
		Page<RoomServiceUsage> resPage = repository.findAll(PageRequest.of(page, limit, sort));
		List<RoomServiceUsageDto> dtos = resPage.getContent().stream().map(RoomServiceUsageDto::toDto).collect(Collectors.toList());
		ResponseListDto<RoomServiceUsageDto> res = new ResponseListDto<>();
		res.setData(dtos);
		res.setMeta(Meta.builder()
				.limit(resPage.getSize())
				.page(resPage.getNumber())
				.totalPages(resPage.getTotalPages())
				.totalElements(resPage.getTotalElements())
				.build());
		return res;
		
	}
	
}
