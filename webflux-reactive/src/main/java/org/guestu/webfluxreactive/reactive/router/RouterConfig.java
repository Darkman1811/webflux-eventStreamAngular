package org.guestu.webfluxreactive.reactive.router;

import org.guestu.webfluxreactive.reactive.handler.SocieteEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class RouterConfig {

    @Autowired
    SocieteEventHandler societeEventHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(){
        return RouterFunctions
                .route(
                RequestPredicates.GET("/routerSociete"),
                //serverRequest -> ServerResponse.ok().body(Mono.just(10),Integer.class)  //Direct Implementation
                //societeEventHandler::eventExample                                       //External class an injection using method reference
                serverRequest ->societeEventHandler.eventExample(serverRequest)         //External class an injection using Lambda
                        )
                //.andRoute()
                //.andNest()
                //etc ... Like Camel
                ;
    }
}
