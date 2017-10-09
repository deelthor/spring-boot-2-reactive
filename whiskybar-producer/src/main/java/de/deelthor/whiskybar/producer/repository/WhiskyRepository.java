package de.deelthor.whiskybar.producer.repository;

import de.deelthor.whiskybar.producer.entity.Whisky;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;
import java.util.UUID;

public interface WhiskyRepository extends CassandraRepository<Whisky> {

    @Query("SELECT * FROM whiskies WHERE id = ?0 ALLOW FILTERING")
    Whisky findById(UUID id);

    @Query("SELECT * FROM whiskies WHERE name = ?0 ALLOW FILTERING")
    List<Whisky> findByName(String name);

    @Query("SELECT * FROM whiskies WHERE distilleryName = ?0 ALLOW FILTERING")
    List<Whisky> findByDistilleryName(String distilleryName);
}
