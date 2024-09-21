package qbit.entier.hostel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import qbit.entier.hostel.dto.ResponseListDto;
import qbit.entier.hostel.dto.ResponseListDto.Meta;
import qbit.entier.hostel.dto.RoomTypeDto;
import qbit.entier.hostel.entity.RoomType;
import qbit.entier.hostel.repository.RoomTypeRepository;

@Service
public class RoomTypeService {

	@Autowired
	RoomTypeRepository roomTypeRepository;
	
	public ResponseListDto<RoomTypeDto> getAll(int limit, int page, String orderBy, boolean descending) {
		Sort sort;
	    if (descending) {
	        sort = Sort.by(Sort.Order.desc(orderBy));
	    } else {
	        sort = Sort.by(Sort.Order.asc(orderBy));
	    }
		
		Pageable pageable = PageRequest.of(page-1, limit, sort);
		Page<RoomType> typePage = roomTypeRepository.findAll(pageable);
		List<RoomTypeDto> typeDtos = typePage.getContent().stream().map(RoomTypeDto::toDto).collect(Collectors.toList());
		ResponseListDto<RoomTypeDto> res = new ResponseListDto<RoomTypeDto>();
		res.setData(typeDtos);
		res.setMeta(new Meta(typePage.getTotalPages(), typePage.getTotalElements(), typePage.getNumber() + 1, typePage.getSize()));
		return res;
	}
	
	public RoomTypeDto getOne(Long id) {
		return RoomTypeDto.toDto(roomTypeRepository.findById(id));
	}
	
	public RoomTypeDto createOne(RoomType newType) {
		return RoomTypeDto.toDto(roomTypeRepository.save(newType));
	}
	
	public RoomTypeDto updateOne(Long id, RoomType updatedType) {
		RoomType type = roomTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found")); 
		type.setName(updatedType.getName());
		type.setBasePrice(updatedType.getBasePrice());
		type.setDescription(updatedType.getDescription());
		return RoomTypeDto.toDto(roomTypeRepository.save(type));
	}
	
	public void deleteOne(Long id) {
		RoomType type = roomTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found")); 
		roomTypeRepository.delete(type);
	}
}
