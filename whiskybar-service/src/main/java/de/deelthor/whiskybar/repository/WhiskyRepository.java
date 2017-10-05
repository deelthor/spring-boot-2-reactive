package de.deelthor.whiskybar.repository;

import de.deelthor.whiskybar.entity.Whisky;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;
import java.util.UUID;

public interface WhiskyRepository extends CassandraRepository<Whisky> {

    @Query("SELECT * FROM Whiskies WHERE id = ?0 ALLOW FILTERING")
    Whisky findById(UUID id);

    @Query("SELECT * FROM Whiskies WHERE name = ?0 ALLOW FILTERING")
    List<Whisky> findByName(String name);

    @Query("SELECT * FROM Whiskies WHERE distilleryName = ?0 ALLOW FILTERING")
    List<Whisky> findByDistilleryName(String distilleryName);
}
