package co.com.bancolombia.reactivas.controller;

import co.com.bancolombia.reactivas.model.Test;
import co.com.bancolombia.reactivas.model.dto.TestDto;
import co.com.bancolombia.reactivas.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/test")
public class ReactivasController {

    @Autowired
    private TestRepository repository;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<Test> save(@RequestBody TestDto testDto){
        return repository.save(new Test(testDto));
    }

    @GetMapping(value = "/list")
    public Flux<Test> list(){
        return repository.findAll();
    }

    @GetMapping(value = "/steam",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux <Test> steam() throws InterruptedException {
        return repository.findAll().publish(1).delayElements(Duration.ofSeconds(1));
    }
}
