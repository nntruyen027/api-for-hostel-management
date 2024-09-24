package qbit.entier.hostel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import qbit.entier.hostel.dto.ServiceDto;
import qbit.entier.hostel.entity.Service;
import qbit.entier.hostel.dto.ResponseListDto;
import qbit.entier.hostel.dto.ResponseListDto.Meta;
import qbit.entier.hostel.repository.ServiceRepository;

@org.springframework.stereotype.Service
public class ServiceService {
	@Autowired
	private ServiceRepository repository;
	
	public ResponseListDto<ServiceDto> getAll(int limit, int page, String orderBy, boolean descending) {
		Sort sort;
		if(descending)
			sort = Sort.by(Sort.Order.desc(orderBy));
		else
			sort = Sort.by(Sort.Order.asc(orderBy));
		
		Page<Service> servicePage = repository.findAll(PageRequest.of(page - 1, limit, sort));
		List<ServiceDto> categoryDtos = servicePage.getContent().stream().map(ServiceDto::toDto).collect(Collectors.toList());
		ResponseListDto<ServiceDto> res = new ResponseListDto<>();
		res.setData(categoryDtos);
		res.setMeta(new Meta(servicePage.getTotalPages(), servicePage.getTotalElements(), servicePage.getNumber()+1, servicePage.getSize()));
		return res;
	}
	
	public ServiceDto getOne(Long id) {
		return ServiceDto.toDto(repository.findById(id)) ;
	}
	
	public ServiceDto createOne(Service category) {
		Service newCategory = Service.builder()
				.name(category.getName())
				.description(category.getDescription())
				.cost(category.getCost())
				.build();
		return ServiceDto.toDto(repository.save(newCategory));
	}
	
	public ServiceDto updateOne(Long id, Service category) {
		Service update = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
		update.setName(category.getName());
		update.setDescription(category.getDescription());
		update.setCost(category.getCost());
		return ServiceDto.toDto(repository.save(update));
	}
	
	public void deleteOne(Long id) {
		Service delete = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
		repository.delete(delete);
	}
}
