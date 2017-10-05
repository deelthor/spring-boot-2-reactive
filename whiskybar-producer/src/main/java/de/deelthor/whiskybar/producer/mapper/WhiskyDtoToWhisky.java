package de.deelthor.whiskybar.producer.mapper;

import de.deelthor.whiskybar.producer.entity.Whisky;
import de.deelthor.whiskybar.producer.dto.WhiskyDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WhiskyDtoToWhisky implements Converter<WhiskyDto, Whisky> {

    @Override
    public Whisky convert(WhiskyDto whiskyDto) {
        Whisky whisky = new Whisky();
        whisky.setDistilleryName(whiskyDto.getDistilleryName());
        whisky.setName(whiskyDto.getName());
        whisky.setAge(whiskyDto.getAge());
        whisky.setStrength(whiskyDto.getStrength());
        return whisky;
    }
}
