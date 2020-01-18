package bank.repository;

import bank.models.Role;

import org.springframework.data.repository.PagingAndSortingRepository;


public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {

    Role findByRole(String role);
}
