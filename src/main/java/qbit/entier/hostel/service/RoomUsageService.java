package qbit.entier.hostel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import qbit.entier.hostel.dto.RequestRoomServiceUsageDto;
import qbit.entier.hostel.dto.ResponseListDto;
import qbit.entier.hostel.dto.ResponseListDto.Meta;
import qbit.entier.hostel.dto.RoomServiceUsageDto;
import qbit.entier.hostel.entity.Invoice;
import qbit.entier.hostel.entity.RoomServiceUsage;
import qbit.entier.hostel.entity.Service;
import qbit.entier.hostel.repository.InvoiceRepository;
import qbit.entier.hostel.repository.RoomServiceUsageRepository;
import qbit.entier.hostel.repository.ServiceRepository;

@org.springframework.stereotype.Service
public class RoomUsageService {
	@Autowired
	private RoomServiceUsageRepository repository;
	
	@Autowired
	private InvoiceRepository invoidRepo;
	
	@Autowired
	private ServiceRepository serviceRepo;
	
	
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
	
	public RoomServiceUsageDto getOne(Long id) {
		return RoomServiceUsageDto.toDto(repository.findById(id));
	}
	
	public RoomServiceUsageDto createOne(RequestRoomServiceUsageDto newObject) {
		Invoice invoice = invoidRepo.findById(newObject.getInvoice()).orElseThrow(() -> new RuntimeException("Not found invoice"));
		Service service = serviceRepo.findById(newObject.getService()).orElseThrow(() -> new RuntimeException("Not found service"));
		RoomServiceUsage object = RoomServiceUsage.builder()
				.cost(newObject.getCost())
				.service(service)
				.invoice(invoice)
				.oldIndex(newObject.getOldIndex())
				.newIndex(newObject.getNewIndex())
				.build();
		return RoomServiceUsageDto.toDto(repository.save(object));
	}
	
	public RoomServiceUsageDto updateOne(Long id, RequestRoomServiceUsageDto updatedObject) {
		Invoice invoice = invoidRepo.findById(updatedObject.getInvoice()).orElseThrow(() -> new RuntimeException("Not found invoice"));
		Service service = serviceRepo.findById(updatedObject.getService()).orElseThrow(() -> new RuntimeException("Not found service"));
		RoomServiceUsage object = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found room service usage"));
		object.setCost(updatedObject.getCost());
		object.setInvoice(invoice);
		object.setNewIndex(updatedObject.getNewIndex());
		object.setOldIndex(updatedObject.getOldIndex());
		object.setService(service);
		return RoomServiceUsageDto.toDto(repository.save(object));
	}
	
	public void deleteOne(Long id) {
		RoomServiceUsage object = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found room service usage"));
		repository.delete(object);
	}
	
}
