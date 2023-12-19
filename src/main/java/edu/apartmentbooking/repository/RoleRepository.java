package edu.apartmentbooking.repository;

import edu.apartmentbooking.model.Role;
import edu.apartmentbooking.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleType(RoleType roleType);
}
