package org.guestu.webfluxreactive.reactive.handler;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class SocieteHandlerImpl implements SocieteEventHandler{
    @Override
    public Mono<ServerResponse> eventExample(ServerRequest serverRequest) {
        return ServerResponse.ok().body(Mono.just(10),Integer.class);
    }
}
