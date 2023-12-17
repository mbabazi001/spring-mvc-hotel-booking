package edu.viviane.apartmentbookingapp.repository;

import edu.viviane.apartmentbookingapp.model.Role;
import edu.viviane.apartmentbookingapp.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleType(RoleType roleType);
}
