package co.com.bancolombia.reactivas.controller;

import co.com.bancolombia.reactivas.model.Test;
import co.com.bancolombia.reactivas.model.dto.TestDto;
import co.com.bancolombia.reactivas.repository.TestRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;

import java.time.Duration;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/test")
public class ReactivasController {

    final FluxProcessor processor;
    final FluxSink sink;
    public ReactivasController() {
        this.processor = DirectProcessor.create().serialize();
        this.sink = processor.sink();
    }

    @Autowired
    private TestRepository repository;
    private static final Logger LOG = LoggerFactory.getLogger(ReactivasController.class);

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<Test> save(@RequestBody TestDto testDto){
        return repository.save(new Test(testDto)).map(test -> {
            sink.next(test);
            return test;
        });
    }

    @GetMapping(value = "/list")
    public Flux<Test> list(){
        return repository.findAll();
    }

    @GetMapping(value = "/create_steam", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Test> steam(){
        LOG.info("get orientation - HTTP GET CALLED");
        return Flux.interval(Duration.ofSeconds(1))
                .flatMap(l -> repository.save(
                        new Test(null,
                                RandomStringUtils.random(10),
                                RandomStringUtils.random(10))))
                .log();
    }

        @GetMapping(value = "/monitor", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
        public Flux<ServerSentEvent> getSomething() {
            return processor.map(o -> ServerSentEvent.builder(o).build());
        }

}
