package de.deelthor.whiskybar.producer.service;

import de.deelthor.whiskybar.producer.dto.WhiskyDto;
import de.deelthor.whiskybar.producer.entity.Whisky;
import de.deelthor.whiskybar.producer.mapper.WhiskyDtoToWhisky;
import de.deelthor.whiskybar.producer.mapper.WhiskyToWhiskyDto;
import de.deelthor.whiskybar.producer.repository.WhiskyRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;

@Service
public class WhiskyServiceImpl implements WhiskyService {

    private final WhiskyRepository whiskyRepository;

    private final WhiskyToWhiskyDto whiskyToWhiskyDto;

    private final WhiskyDtoToWhisky whiskyDtoToWhisky;

    public WhiskyServiceImpl(WhiskyRepository whiskyRepository, WhiskyToWhiskyDto whiskyToWhiskyDto, WhiskyDtoToWhisky whiskyDtoToWhisky) {
        this.whiskyRepository = whiskyRepository;
        this.whiskyToWhiskyDto = whiskyToWhiskyDto;
        this.whiskyDtoToWhisky = whiskyDtoToWhisky;
    }

    @Override
    public WhiskyDto addWhisky(WhiskyDto whiskyDto) {
        Whisky whisky = whiskyDtoToWhisky.convert(whiskyDto);
        Mono<Whisky> newWhisky = whiskyRepository.save(whisky);
        return whiskyToWhiskyDto.convert(newWhisky.block());
    }

    @Override
    public WhiskyDto updateWhisky(WhiskyDto whiskyDto, String id) {
        Whisky whisky = whiskyDtoToWhisky.convert(whiskyDto);
        whisky.setId(UUID.fromString(id));
        Mono<Whisky> updated = whiskyRepository.save(whisky);
        return whiskyToWhiskyDto.convert(updated.block());
    }

    @Override
    public Set<WhiskyDto> getAllWhiskies() {
        return StreamSupport.stream(whiskyRepository.findAll().toIterable()
                .spliterator(), false)
                .map(whiskyToWhiskyDto::convert)
                .collect(toSet());
    }

    @Override
    public WhiskyDto getWhiskyById(String id) {
        WhiskyDto whisky = whiskyToWhiskyDto.convert(whiskyRepository.findById(UUID.fromString(id)).block());
        return whisky;
    }

    @Override
    public Set<WhiskyDto> getWhiskiesByName(String name) {
        return StreamSupport.stream(whiskyRepository.findByName(name).toIterable()
                .spliterator(), false)
                .map(whiskyToWhiskyDto::convert)
                .collect(toSet());
    }

    @Override
    public Set<WhiskyDto> getWhiskiesByDistillery(String distilleryName) {
        return StreamSupport.stream(whiskyRepository.findByDistilleryName(distilleryName).toIterable()
                .spliterator(), false)
                .map(whiskyToWhiskyDto::convert)
                .collect(toSet());
    }

    @Override
    public void delete(String id) {
        Whisky whisky = whiskyRepository.findById(UUID.fromString(id)).block();
        whiskyRepository.delete(whisky);
    }
}
