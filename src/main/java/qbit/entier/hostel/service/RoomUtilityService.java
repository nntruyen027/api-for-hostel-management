package qbit.entier.hostel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import qbit.entier.hostel.dto.RequestRoomUtilityDto;
import qbit.entier.hostel.dto.ResponseListDto;
import qbit.entier.hostel.dto.ResponseListDto.Meta;
import qbit.entier.hostel.dto.RoomUtilityDto;
import qbit.entier.hostel.entity.ExpenseCategory;
import qbit.entier.hostel.entity.Room;
import qbit.entier.hostel.entity.RoomUtility;
import qbit.entier.hostel.repository.ExpenseCategoryRepository;
import qbit.entier.hostel.repository.RoomRepository;
import qbit.entier.hostel.repository.RoomUtilityRepository;

@Service
public class RoomUtilityService {
	@Autowired
	RoomUtilityRepository repository;
	
	@Autowired
	RoomRepository roomRep;
	
	@Autowired
	ExpenseCategoryRepository categoryRep;
	
	public ResponseListDto<RoomUtilityDto> getAll(int limit, int page, String orderBy, boolean descending) {
		Sort sort;
		if(descending)
			sort = Sort.by(Sort.Order.desc(orderBy));
		else
			sort = Sort.by(Sort.Order.asc(orderBy));
		Page<RoomUtility> resPage = repository.findAll(PageRequest.of(page, limit, sort));
		List<RoomUtilityDto> dtos = resPage.getContent().stream().map(RoomUtilityDto::toDto).collect(Collectors.toList());
		ResponseListDto<RoomUtilityDto> res = new ResponseListDto<>();
		res.setData(dtos);
		res.setMeta(Meta.builder()
				.limit(resPage.getSize())
				.page(resPage.getNumber())
				.totalPages(resPage.getTotalPages())
				.totalElements(resPage.getTotalElements())
				.build());
		return res;
	}
	
	public ResponseListDto<RoomUtilityDto> findByRoom(Long room ,int limit, int page, String orderBy, boolean descending) {
		Sort sort;
		if(descending)
			sort = Sort.by(Sort.Order.desc(orderBy));
		else
			sort = Sort.by(Sort.Order.asc(orderBy));
		Page<RoomUtility> resPage = repository.findByRoomId(room, PageRequest.of(page, limit, sort));
		List<RoomUtilityDto> dtos = resPage.getContent().stream().map(RoomUtilityDto::toDto).collect(Collectors.toList());
		ResponseListDto<RoomUtilityDto> res = new ResponseListDto<>();
		res.setData(dtos);
		res.setMeta(Meta.builder()
				.limit(resPage.getSize())
				.page(resPage.getNumber())
				.totalPages(resPage.getTotalPages())
				.totalElements(resPage.getTotalElements())
				.build());
		return res;
	}
	
	public ResponseListDto<RoomUtilityDto> findByCategory(Long category ,int limit, int page, String orderBy, boolean descending) {
		Sort sort;
		if(descending)
			sort = Sort.by(Sort.Order.desc(orderBy));
		else
			sort = Sort.by(Sort.Order.asc(orderBy));
		Page<RoomUtility> resPage = repository.findByExpenseCategoryId(category, PageRequest.of(page, limit, sort));
		List<RoomUtilityDto> dtos = resPage.getContent().stream().map(RoomUtilityDto::toDto).collect(Collectors.toList());
		ResponseListDto<RoomUtilityDto> res = new ResponseListDto<>();
		res.setData(dtos);
		res.setMeta(Meta.builder()
				.limit(resPage.getSize())
				.page(resPage.getNumber())
				.totalPages(resPage.getTotalPages())
				.totalElements(resPage.getTotalElements())
				.build());
		return res;
	}
	
	public RoomUtilityDto findById(Long id) {
		return RoomUtilityDto.toDto(repository.findById(id));
	}
	
	public RoomUtilityDto createOne(RequestRoomUtilityDto newObject) {
		Room room = roomRep.findById(newObject.getRoom()).orElseThrow(() -> new RuntimeException("Not found room"));
		ExpenseCategory category = categoryRep.findById(newObject.getExpenseCategory()).orElseThrow(() -> new RuntimeException("Not found category"));
		
		RoomUtility object = RoomUtility.builder()
				.expenseCategory(category)
				.room(room)
				.build();
		
		return RoomUtilityDto.toDto(repository.save(object));
	}
	
	public RoomUtilityDto updateOne(Long id, RequestRoomUtilityDto updatedObject) {
		RoomUtility object = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
		Room room = roomRep.findById(updatedObject.getRoom()).orElseThrow(() -> new RuntimeException("Not found room"));
		ExpenseCategory category = categoryRep.findById(updatedObject.getExpenseCategory()).orElseThrow(() -> new RuntimeException("Not found category"));
		object.setExpenseCategory(category);
		object.setRoom(room);
		return RoomUtilityDto.toDto(repository.save(object));
	}
	
	public void deleteOne(Long id) {
		RoomUtility object = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
		repository.delete(object);
	}
}
