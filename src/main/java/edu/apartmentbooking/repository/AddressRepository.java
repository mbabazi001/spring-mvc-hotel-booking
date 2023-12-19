package edu.viviane.apartmentbookingapp.repository;

import edu.viviane.apartmentbookingapp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
