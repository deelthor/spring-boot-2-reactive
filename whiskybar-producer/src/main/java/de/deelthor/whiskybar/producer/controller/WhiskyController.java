package de.deelthor.whiskybar.producer.controller;

import de.deelthor.whiskybar.producer.dto.WhiskyDto;
import de.deelthor.whiskybar.producer.service.WhiskyService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
            return whiskyService.getWhiskiesByName(name);
        }
        if (!StringUtils.isEmpty(distilleryName)) {
            return whiskyService.getWhiskiesByDistillery(distilleryName);
        }
        return whiskyService.getAllWhiskies();
    }

    @GetMapping("/whiskies/{id}")
    public WhiskyDto getWhisky(@PathVariable String id) {
        return whiskyService.getWhiskyById(id);
    }

    @DeleteMapping("/whiskies/{id}")
    public void deleteWhisky(@PathVariable String id) {
        whiskyService.delete(id);
    }

    @PostMapping("/whiskies")
    public WhiskyDto newWhisky(@RequestBody WhiskyDto whiskyDto) {
        return whiskyService.addWhisky(whiskyDto);
    }

    @PutMapping("/whiskies/{id}")
    public WhiskyDto updateWhisky(@RequestBody WhiskyDto whiskyDto, @PathVariable String id) {
        return whiskyService.updateWhisky(whiskyDto, id);
    }

}
