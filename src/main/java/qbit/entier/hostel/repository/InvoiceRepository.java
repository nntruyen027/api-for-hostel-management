package qbit.entier.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import qbit.entier.hostel.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
