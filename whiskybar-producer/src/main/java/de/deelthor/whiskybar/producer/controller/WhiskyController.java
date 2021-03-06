package de.deelthor.whiskybar.producer.controller;

import de.deelthor.whiskybar.producer.dto.WhiskyDto;
import de.deelthor.whiskybar.producer.service.WhiskyService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class WhiskyController {

    private final WhiskyService whiskyService;

    public WhiskyController(WhiskyService whiskyService) {
        this.whiskyService = whiskyService;
    }

    @GetMapping("/whiskies")
    public Set<WhiskyDto> getAllWhiskies(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) String distilleryName) {
        if (!StringUtils.isEmpty(name)) {
            return whiskyService.getWhiskiesByName(name).collect(Collectors.toSet()).block();
        }
        if (!StringUtils.isEmpty(distilleryName)) {
            return whiskyService.getWhiskiesByDistillery(distilleryName).collect(Collectors.toSet()).block();
        }
        return whiskyService.getAllWhiskies().collect(Collectors.toSet()).block();
    }

    @GetMapping("/whiskies/{id}")
    public WhiskyDto getWhisky(@PathVariable String id) {
        return whiskyService.getWhiskyById(id).block();
    }

    @DeleteMapping("/whiskies/{id}")
    public void deleteWhisky(@PathVariable String id) {
        whiskyService.delete(id);
    }

    @PostMapping("/whiskies")
    public WhiskyDto newWhisky(@RequestBody WhiskyDto whiskyDto) {
        return whiskyService.addWhisky(whiskyDto).block();
    }

    @PutMapping("/whiskies/{id}")
    public WhiskyDto updateWhisky(@RequestBody WhiskyDto whiskyDto, @PathVariable String id) {
        return whiskyService.updateWhisky(whiskyDto, id).block();
    }

}
