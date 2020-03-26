package co.com.bancolombia.reactivas.controller;

import co.com.bancolombia.reactivas.model.Test;
import co.com.bancolombia.reactivas.model.dto.TestDto;
import co.com.bancolombia.reactivas.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/test")
public class ReactivasController {

    @Autowired
    private TestRepository repository;

    @PostMapping
    public Mono<Test> save(TestDto testDto){
        return repository.save(new Test(testDto));
    }

    @GetMapping(value = "/list")
    public Flux<Test> list(){
        return repository.findAll().take(5);
    }

    @GetMapping(value = "/steam",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux <Test> steam() throws InterruptedException {
        return repository.findAll().take(5);
    }
}
