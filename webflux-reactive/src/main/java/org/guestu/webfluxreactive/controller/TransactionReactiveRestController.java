package org.guestu.webfluxreactive.controller;

import lombok.extern.slf4j.Slf4j;
import org.guestu.webfluxreactive.dao.SocieteRepository;
import org.guestu.webfluxreactive.dao.TransactionRepository;
import org.guestu.webfluxreactive.entities.Event;
import org.guestu.webfluxreactive.entities.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@RestController
@RequestMapping("/transaction")
@Slf4j
public class TransactionReactiveRestController {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    SocieteRepository societeRepository;

    @GetMapping
    public Flux<Transaction> findAll(){
        Flux<Transaction> transactionFlux=transactionRepository.findAll();
        return transactionRepository.findAll();
    }

    @GetMapping(value = "/streamEvent",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Transaction> transactionStream(@RequestParam String societyId){
        return societeRepository.findById(societyId)
                .flatMapMany(s-> {
                    Flux interval = Flux.interval(Duration.ofSeconds(1L));
                    Flux<Transaction> transactionFlux = Flux.fromStream(Stream.generate(() -> {
                        Transaction transaction = new Transaction();
                        transaction.setInstant(Instant.now());
                        return transaction;
                    }));

            return    Flux.zip(interval,transactionFlux).map(d->{
                   Tuple2 tuple2 = (Tuple2) d;
                   Object obj=tuple2.getT2();
                   Transaction transaction = (Transaction) obj;
                   return transaction;
               }).share();

                });
    }

    @GetMapping(value = "/streamEvent2",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Transaction> transactionStream2(){
        return  Flux.create(transactionFluxSink -> {
            //for(int i=1;i<100;i++)
            while(true)
            {
                Transaction transaction = new Transaction();
                transaction.setInstant(Instant.now());
                transactionFluxSink.next(transaction);
                try{
                    Thread.sleep(Duration.ofSeconds(1).toMillis());
                } catch (InterruptedException interruptedException){
                    log.error(interruptedException.getMessage());
                }
            }
           // transactionFluxSink.complete();
        });
    }


    @GetMapping(value = "/streamEvent3",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Double> transactionStream3(){
        log.info("yup from angular");
        WebClient webClient= WebClient.create("http://localhost:8083");
        Flux<Double> result=webClient.get()
                .uri("/streamEvents/GUESTU")
                .retrieve()
                .bodyToFlux(Event.class)
                .map(event -> event.getValue());
        return result;
    }

    @GetMapping("/{id}")
    public Mono<Transaction> findOne(@PathVariable String id){
        return transactionRepository.findById(id);
    }

    @PostMapping()
    public void save(@RequestBody Transaction transaction,@RequestParam String societeId){
        log.info("Adding a <Transaction>:"+transaction.toString()+" *************************************** ");
       transactionRepository.save(transaction).subscribe(transactionSaved->{
          societeRepository.findById(societeId).subscribe(societe -> {
              try {
                  societe.getTransactions().add(transaction);
              } catch (NullPointerException nullPointerException){
                  societe.setTransactions(new ArrayList<>());
                  societe.getTransactions().add(transaction);
              }
              societeRepository.save(societe).subscribe(savedSocite->{
                  log.info("societeSave:"+savedSocite);
              });
          }) ;
       });
      /*  societeRepository.findById(transaction.getSociete().getId()).subscribe(societe -> {
           try {
               societe.getTransactions().add(transaction);
           }catch (NullPointerException nullPointerException){
              societe.setTransactions(new ArrayList<>());
               societe.getTransactions().add(transaction);
           }
           societeRepository.save(societe);
            transactionRepository.save(transaction).subscribe(result->{
                log.info("resutl:"+result.toString());
            });

        });*/


    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        log.trace("Deleting a <Transaction.id>:" + id + " *************************************** ");
        return transactionRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Mono<Transaction> update(@RequestBody Transaction transaction,@PathVariable String id){
        log.trace("Updating a <Transaction>:"+transaction.toString()+" *************************************** ");
       transaction.setId(id);
        transactionRepository.save(transaction);
        return null;
    }

    @ExceptionHandler
    public void ExHandler(Exception exception){
        log.error(exception.getMessage());
    }

}
