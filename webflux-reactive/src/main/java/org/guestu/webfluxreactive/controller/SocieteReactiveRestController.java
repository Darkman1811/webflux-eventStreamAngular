package org.guestu.webfluxreactive.controller;

import lombok.extern.slf4j.Slf4j;
import org.guestu.webfluxreactive.dao.SocieteRepository;
import org.guestu.webfluxreactive.entities.Societe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class SocieteReactiveRestController {
    @Autowired
    SocieteRepository societeRepository;

    @GetMapping("/societes")
    public Flux<Societe> findAll(){
        return societeRepository.findAll();
    }

    @GetMapping("/societes/{id}")
    public Mono<Societe> findOne(@PathVariable String id){
        return societeRepository.findById(id);
    }

    @PostMapping("/societes")
    public Mono<Societe> save(@RequestBody Societe societe){
        log.trace("Adding a <Societe>:"+societe.toString()+" *************************************** ");
        return societeRepository.save(societe);
    }

    @DeleteMapping("/societes/{id}")
    public Mono<Void> update(@PathVariable String id) {
        log.trace("Deleting a <Societe.id>:" + id + " *************************************** ");
        return societeRepository.deleteById(id);
    }

    @PutMapping("/societes/{id}")
    public Mono<Societe> update(@RequestBody Societe societe,@PathVariable String id){
        log.trace("Updating a <Societe>:"+societe.toString()+" *************************************** ");
       societe.setId(id);
        return societeRepository.save(societe);
    }
}
