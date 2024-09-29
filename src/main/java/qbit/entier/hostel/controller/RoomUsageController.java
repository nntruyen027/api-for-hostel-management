package qbit.entier.hostel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import qbit.entier.hostel.dto.RequestRoomServiceUsageDto;
import qbit.entier.hostel.dto.ResponseListDto;
import qbit.entier.hostel.dto.RoomServiceUsageDto;
import qbit.entier.hostel.service.RoomUsageService;

@RestController
@RequestMapping("room-usages")
@RequiredArgsConstructor
public class RoomUsageController {
	@Autowired
	private RoomUsageService service;
	
	@GetMapping
	public ResponseListDto<RoomServiceUsageDto> getAll(
			  	@RequestParam(defaultValue = "10") int limit,
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "id") String orderBy,
	            @RequestParam(defaultValue = "false") boolean descending) {
		return service.getAll(limit, page, orderBy, descending);
	}
	
	@GetMapping("/{id}")
	public RoomServiceUsageDto getOne(@PathVariable Long id) {
		return service.getOne(id);
	}
	
	@PostMapping
	public RoomServiceUsageDto createOne(@RequestBody RequestRoomServiceUsageDto body) {
		return service.createOne(body);
	}
	
	@PutMapping("/{id}")
	public RoomServiceUsageDto createOne(@PathVariable Long id, @RequestBody RequestRoomServiceUsageDto body) {
		return service.updateOne(id, body);
	}
	
	@DeleteMapping("/{id}")
	public void deleteOne(@PathVariable Long id) {
		service.deleteOne(id);
	}
	
	//dsadd
}
