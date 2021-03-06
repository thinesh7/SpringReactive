package com.bms.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.bms.entities.Account;

public interface AccountRepository extends ReactiveCrudRepository<Account, Long> {

}
