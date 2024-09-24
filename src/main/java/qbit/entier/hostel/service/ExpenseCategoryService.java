package qbit.entier.hostel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import qbit.entier.hostel.dto.ExpenseCategoryDto;
import qbit.entier.hostel.dto.ResponseListDto;
import qbit.entier.hostel.dto.ResponseListDto.Meta;
import qbit.entier.hostel.entity.ExpenseCategory;
import qbit.entier.hostel.repository.ExpenseCategoryRepository;

@Service
public class ExpenseCategoryService {
	@Autowired
	private ExpenseCategoryRepository repository;
	
	public ResponseListDto<ExpenseCategoryDto> getAll(int limit, int page, String orderBy, boolean descending) {
		Sort sort;
		if(descending)
			sort = Sort.by(Sort.Order.desc(orderBy));
		else
			sort = Sort.by(Sort.Order.asc(orderBy));
		
		Page<ExpenseCategory> categoryPage = repository.findAll(PageRequest.of(page - 1, limit, sort));
		List<ExpenseCategoryDto> categoryDtos = categoryPage.getContent().stream().map(ExpenseCategoryDto::toDto).collect(Collectors.toList());
		ResponseListDto<ExpenseCategoryDto> res = new ResponseListDto<>();
		res.setData(categoryDtos);
		res.setMeta(new Meta(categoryPage.getTotalPages(), categoryPage.getTotalElements(), categoryPage.getNumber()+1, categoryPage.getSize()));
		return res;
	}
	
	public ExpenseCategoryDto getOne(Long id) {
		return ExpenseCategoryDto.toDto(repository.findById(id)) ;
	}
	
	public ExpenseCategoryDto createOne(ExpenseCategory category) {
		ExpenseCategory newCategory = ExpenseCategory.builder()
				.name(category.getName())
				.description(category.getDescription())
				.cost(category.getCost())
				.build();
		return ExpenseCategoryDto.toDto(repository.save(newCategory));
	}
	
	public ExpenseCategoryDto updateOne(Long id, ExpenseCategory category) {
		ExpenseCategory update = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
		update.setName(category.getName());
		update.setDescription(category.getDescription());
		update.setCost(category.getCost());
		return ExpenseCategoryDto.toDto(repository.save(update));
	}
	
	public void deleteOne(Long id) {
		ExpenseCategory delete = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
		repository.delete(delete);
	}
}
