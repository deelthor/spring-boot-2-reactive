package de.deelthor.whiskybar.producer.service;

import de.deelthor.whiskybar.producer.dto.WhiskyDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface WhiskyService {

    Mono<WhiskyDto> addWhisky(WhiskyDto whisky);

    Mono<WhiskyDto> updateWhisky(WhiskyDto whisky, String id);

    Flux<WhiskyDto> getAllWhiskies();

    Mono<WhiskyDto> getWhiskyById(String id);

    Flux<WhiskyDto> getWhiskiesByName(String name);

    Flux<WhiskyDto> getWhiskiesByDistillery(String distilleryName);

    void delete(String id);
}
