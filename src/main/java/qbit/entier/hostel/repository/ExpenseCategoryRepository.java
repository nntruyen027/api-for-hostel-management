package qbit.entier.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import qbit.entier.hostel.entity.ExpenseCategory;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {

}
