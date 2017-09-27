package de.deelthor.whiskybar.service;

import de.deelthor.whiskybar.dto.WhiskyDto;
import de.deelthor.whiskybar.mapper.WhiskyToWhiskyDto;
import de.deelthor.whiskybar.repository.WhiskyRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;

@Service
public class WhiskyServiceImpl implements WhiskyService {

    private final WhiskyRepository whiskyRepository;

    private final WhiskyToWhiskyDto whiskyToWhiskyDto;

    public WhiskyServiceImpl(WhiskyRepository whiskyRepository, WhiskyToWhiskyDto whiskyToWhiskyDto) {
        this.whiskyRepository = whiskyRepository;
        this.whiskyToWhiskyDto = whiskyToWhiskyDto;
    }

    @Override
    public Set<WhiskyDto> getAllWhiskies() {
        return StreamSupport.stream(whiskyRepository.findAll()
                .spliterator(), false)
                .map(whiskyToWhiskyDto::convert)
                .collect(toSet());
    }
}
