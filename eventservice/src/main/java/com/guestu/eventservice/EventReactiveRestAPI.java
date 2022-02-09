package com.guestu.eventservice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
public class EventReactiveRestAPI {
    @GetMapping(value = "/streamEvents/{id}",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<Event> listEvents(@PathVariable String id){
        Flux<Long> interval= Flux.interval(Duration.ofSeconds(1));
    Flux<Event> eventFlux=Flux.fromStream(
      Stream.generate(()->{
          Event event=new Event();
          event.setInstant(Instant.now());
          event.setValue(100+Math.random()*1000);
          event.setSocieteId(id);
          return event;
      } )
    );
    return Flux.zip(interval,eventFlux).map(d->{
        return d.getT2();
    }).share();
  }
}
