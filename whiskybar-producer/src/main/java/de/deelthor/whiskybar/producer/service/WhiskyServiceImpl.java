package de.deelthor.whiskybar.producer.service;

import de.deelthor.whiskybar.producer.dto.WhiskyDto;
import de.deelthor.whiskybar.producer.entity.Whisky;
import de.deelthor.whiskybar.producer.mapper.WhiskyDtoToWhisky;
import de.deelthor.whiskybar.producer.mapper.WhiskyToWhiskyDto;
import de.deelthor.whiskybar.producer.repository.WhiskyRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<WhiskyDto> addWhisky(WhiskyDto whiskyDto) {
        Whisky whisky = whiskyDtoToWhisky.convert(whiskyDto);
        return whiskyRepository.save(whisky).map(whiskyToWhiskyDto::convert);
    }

    @Override
    public Mono<WhiskyDto> updateWhisky(WhiskyDto whiskyDto, String id) {
        Whisky whisky = whiskyDtoToWhisky.convert(whiskyDto);
        whisky.setId(UUID.fromString(id));
        return whiskyRepository.save(whisky).map(whiskyToWhiskyDto::convert);
    }

    @Override
    public Flux<WhiskyDto> getAllWhiskies() {
        return whiskyRepository.findAll().map(whiskyToWhiskyDto::convert);
    }

    @Override
    public Mono<WhiskyDto> getWhiskyById(String id) {
        return whiskyRepository.findById(UUID.fromString(id)).map(whiskyToWhiskyDto::convert);
    }

    @Override
    public Flux<WhiskyDto> getWhiskiesByName(String name) {
        return whiskyRepository.findByName(name).map(whiskyToWhiskyDto::convert);
    }

    @Override
    public Flux<WhiskyDto> getWhiskiesByDistillery(String distilleryName) {
        return whiskyRepository.findByDistilleryName(distilleryName).map(whiskyToWhiskyDto::convert);
    }

    @Override
    public void delete(String id) {
        Whisky whisky = whiskyRepository.findById(UUID.fromString(id)).block();
        whiskyRepository.delete(whisky);
    }
}
