package de.deelthor.whiskybar.service;

import de.deelthor.whiskybar.dto.WhiskyDto;

import java.util.Set;

public interface WhiskyService {
    Set<WhiskyDto> getAllWhiskies();
}
