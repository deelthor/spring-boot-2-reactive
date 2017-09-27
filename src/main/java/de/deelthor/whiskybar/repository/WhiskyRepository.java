package de.deelthor.whiskybar.repository;

import de.deelthor.whiskybar.domain.Whisky;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface WhiskyRepository extends CassandraRepository<Whisky> {
}
