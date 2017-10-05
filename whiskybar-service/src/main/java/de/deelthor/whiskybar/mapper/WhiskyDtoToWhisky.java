package de.deelthor.whiskybar.mapper;

import de.deelthor.whiskybar.entity.Whisky;
import de.deelthor.whiskybar.dto.WhiskyDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
