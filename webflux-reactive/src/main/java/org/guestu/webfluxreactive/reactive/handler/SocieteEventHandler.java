package org.guestu.webfluxreactive.reactive.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface SocieteEventHandler {
    Mono<ServerResponse> eventExample(ServerRequest serverRequest);
}
