package bank.repository;



import bank.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByEmail(String email);
}
