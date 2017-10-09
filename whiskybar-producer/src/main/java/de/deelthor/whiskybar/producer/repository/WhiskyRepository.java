package de.deelthor.whiskybar.producer.repository;

import de.deelthor.whiskybar.producer.entity.Whisky;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface WhiskyRepository extends ReactiveCrudRepository<Whisky, UUID> {

    @Query("SELECT * FROM whiskies WHERE id = ?0 ALLOW FILTERING")
    Mono<Whisky> findById(UUID id);

    @Query("SELECT * FROM whiskies WHERE name = ?0 ALLOW FILTERING")
    Flux<Whisky> findByName(String name);

    @Query("SELECT * FROM whiskies WHERE distilleryName = ?0 ALLOW FILTERING")
    Flux<Whisky> findByDistilleryName(String distilleryName);
}
