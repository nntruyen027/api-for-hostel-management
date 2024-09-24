package qbit.entier.hostel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import qbit.entier.hostel.dto.ServiceDto;
import qbit.entier.hostel.dto.ResponseListDto;
import qbit.entier.hostel.entity.Service;
import qbit.entier.hostel.service.ServiceService;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {
	
	@Autowired
	ServiceService service;
	
	@GetMapping
	ResponseEntity<ResponseListDto<ServiceDto>> getAll(
			 	@RequestParam(defaultValue = "10", required = false) int limit,
	            @RequestParam(defaultValue = "1", required = false) int page,
	            @RequestParam(defaultValue = "name", required = false) String orderBy,
	            @RequestParam(defaultValue = "false", required = false) boolean descending
	            ) {
		return ResponseEntity.ok(service.getAll(limit, page, orderBy, descending));
	}
	
	@GetMapping("/{id}")
	ResponseEntity<ServiceDto> getOne(@PathVariable Long id) {
		return ResponseEntity.ok(service.getOne(id));
	}
	
	@PostMapping
	ResponseEntity<ServiceDto> createOne(@RequestBody Service body) {
		return ResponseEntity.ok(service.createOne(body));
	}
	
	@PutMapping("/{id}") 
	ResponseEntity<ServiceDto> updateOne(@PathVariable Long id, @RequestBody Service body) {
		return ResponseEntity.ok(service.updateOne(id, body));
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<Void> deleteOne(@PathVariable Long id) {
		service.deleteOne(id);
		return ResponseEntity.noContent().build();
	}
}
