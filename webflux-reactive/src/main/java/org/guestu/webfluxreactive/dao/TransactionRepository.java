package org.guestu.webfluxreactive.dao;

import org.guestu.webfluxreactive.entities.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction,String> {
}
