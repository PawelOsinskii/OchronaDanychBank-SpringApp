package bank.repository;

import bank.models.Transaction;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction,Long> {

}
