package de.deelthor.whiskybar.producer.service;

import de.deelthor.whiskybar.producer.dto.WhiskyDto;

import java.util.Set;

public interface WhiskyService {

    WhiskyDto addWhisky(WhiskyDto whisky);

    WhiskyDto updateWhisky(WhiskyDto whisky, String id);

    Set<WhiskyDto> getAllWhiskies();

    WhiskyDto getWhiskyById(String id);

    Set<WhiskyDto> getWhiskiesByName(String name);

    Set<WhiskyDto> getWhiskiesByDistillery(String distilleryName);

    void delete(String id);
}
