package org.guestu.webfluxreactive.dao;

import org.guestu.webfluxreactive.entities.Societe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SocieteRepository extends ReactiveMongoRepository<Societe,String> {
}
