package qbit.entier.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import qbit.entier.hostel.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {

}
