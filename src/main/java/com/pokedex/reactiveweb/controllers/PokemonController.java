package com.pokedex.reactiveweb.controllers;

import com.pokedex.reactiveweb.models.Pokemon;
import com.pokedex.reactiveweb.models.events.PokemonEvent;
import com.pokedex.reactiveweb.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping(value = "/pokemon")
public class PokemonController {

    @Autowired
    private PokemonService service;

    @GetMapping
    public Flux<Pokemon> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public Mono<ResponseEntity<Pokemon>> findById(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Pokemon> createPokemon(@RequestBody Pokemon  request) {
        return service.createPokemon(request);
    }

    @PutMapping(value = "/{id}")
    public Mono<ResponseEntity<Pokemon>> updatePokemon(@PathVariable String id, @RequestBody Pokemon request) {
        return service.update(id, request);
    }

    @DeleteMapping(value = "/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id) {
        return service.deleteById(id);
    }

    @DeleteMapping
    public Mono<Void> deleteAll() {
        return service.deleteAll();
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<PokemonEvent> getPokemonEvents() {
        return Flux.interval(Duration.ofSeconds(5))
                .map(val ->
                        new PokemonEvent(val, "Product Event")
                );
    }
}
