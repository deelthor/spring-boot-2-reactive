package de.deelthor.whiskybar.controller;

import de.deelthor.whiskybar.dto.WhiskyDto;
import de.deelthor.whiskybar.service.WhiskyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class WhiskyController {

    private final WhiskyService whiskyService;

    public WhiskyController(WhiskyService whiskyService) {
        this.whiskyService = whiskyService;
    }

    @GetMapping("/")
    public Set<WhiskyDto> getAllWhiskies() {
        return whiskyService.getAllWhiskies();
    }

}
