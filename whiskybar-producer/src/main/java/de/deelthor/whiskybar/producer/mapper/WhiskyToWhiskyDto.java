package de.deelthor.whiskybar.producer.mapper;

import de.deelthor.whiskybar.producer.entity.Whisky;
import de.deelthor.whiskybar.producer.dto.WhiskyDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class WhiskyToWhiskyDto implements Converter<Whisky, WhiskyDto> {

    @Override
    public WhiskyDto convert(Whisky whisky) {
        WhiskyDto whiskyDto = new WhiskyDto();
        if (whisky.getId() != null && !StringUtils.isEmpty(whisky.getId())) {
            whiskyDto.setId(whisky.getId());
        }
        whiskyDto.setDistilleryName(whisky.getDistilleryName());
        whiskyDto.setName(whisky.getName());
        whiskyDto.setAge(whisky.getAge());
        whiskyDto.setStrength(whisky.getStrength());
        return whiskyDto;
    }
}
