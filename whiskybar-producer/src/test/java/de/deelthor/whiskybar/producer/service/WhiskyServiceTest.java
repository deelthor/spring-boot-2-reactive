package de.deelthor.whiskybar.producer.service;

import de.deelthor.whiskybar.producer.dto.WhiskyDto;
import de.deelthor.whiskybar.producer.entity.Whisky;
import de.deelthor.whiskybar.producer.mapper.WhiskyDtoToWhisky;
import de.deelthor.whiskybar.producer.mapper.WhiskyToWhiskyDto;
import de.deelthor.whiskybar.producer.repository.WhiskyRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class WhiskyServiceTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String DISTILLERY_NAME = "Ardbeg";
    private static final int AGE = 10;
    private static final String NAME = "TEN";
    private static final double STRENGTH = 46.0;

    private WhiskyService whiskyService;

    @Mock
    private WhiskyRepository whiskyRepository;

    @Spy
    private WhiskyDtoToWhisky whiskyDtoToWhisky;

    @Spy
    private WhiskyToWhiskyDto whiskyToWhiskyDto;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        whiskyService = new WhiskyServiceImpl(whiskyRepository, whiskyToWhiskyDto, whiskyDtoToWhisky);
    }

    @Test
    public void getAllWhiskies() throws Exception {
        Whisky whisky = getWhisky();
        when(whiskyRepository.findAll()).thenReturn(Flux.just((whisky)));
        when(whiskyToWhiskyDto.convert(whisky)).thenCallRealMethod();

        Long whiskyCount = whiskyService.getAllWhiskies().count().block();

        assertThat(whiskyCount, is(1));
        verify(whiskyRepository, times(1)).findAll();
        verify(whiskyToWhiskyDto, times(1)).convert(whisky);
    }

    @Test
    public void getWhiskyById() throws Exception {
        Whisky whisky = getWhisky();
        when(whiskyRepository.findById(ID)).thenReturn(Mono.just(whisky));
        when(whiskyToWhiskyDto.convert(whisky)).thenCallRealMethod();

        WhiskyDto whiskyDto = whiskyService.getWhiskyById(ID.toString()).block();

        assertThat(whiskyDto, not(nullValue()));
        verify(whiskyRepository, times(1)).findById(ID);
        verify(whiskyToWhiskyDto, times(1)).convert(whisky);
    }

    @Test
    public void getWhiskiesByName() throws Exception {
        Whisky whisky = getWhisky();
        when(whiskyRepository.findByName(NAME)).thenReturn(Flux.just(whisky));
        when(whiskyToWhiskyDto.convert(whisky)).thenCallRealMethod();

        Long whiskyCount = whiskyService.getWhiskiesByName(NAME).count().block();

        assertThat(whiskyCount, is(1));
        verify(whiskyRepository, times(1)).findByName(NAME);
        verify(whiskyToWhiskyDto, times(1)).convert(whisky);
    }

    @Test
    public void getWhiskiesByDistilleryName() throws Exception {
        Whisky whisky = getWhisky();
        when(whiskyRepository.findByDistilleryName(DISTILLERY_NAME)).thenReturn(Flux.just(whisky));
        when(whiskyToWhiskyDto.convert(whisky)).thenCallRealMethod();

        Long allWhiskies = whiskyService.getWhiskiesByDistillery(DISTILLERY_NAME).count().block();

        assertThat(allWhiskies, is(1));
        verify(whiskyRepository, times(1)).findByDistilleryName(DISTILLERY_NAME);
        verify(whiskyToWhiskyDto, times(1)).convert(whisky);
    }

    @Test
    public void addWhisky() throws Exception {
        Whisky whisky = getWhisky();
        WhiskyDto whiskyDto = getWhiskyDto();
        when(whiskyRepository.save(whisky)).thenReturn(Mono.just(whisky));
        when(whiskyToWhiskyDto.convert(whisky)).thenCallRealMethod();
        when(whiskyDtoToWhisky.convert(whiskyDto)).thenCallRealMethod();

        WhiskyDto newWhisky = whiskyService.addWhisky(whiskyDto).block();

        assertThat(newWhisky, is(whiskyDto));
        verify(whiskyRepository, times(1)).save(whisky);
        verify(whiskyToWhiskyDto, times(1)).convert(whisky);
        verify(whiskyDtoToWhisky, times(1)).convert(whiskyDto);
    }

    @Test
    public void updateWhisky() throws Exception {
        Whisky whisky = getWhisky();
        WhiskyDto whiskyDto = getWhiskyDto();
        when(whiskyRepository.save(whisky)).thenReturn(Mono.just(whisky));
        when(whiskyToWhiskyDto.convert(whisky)).thenCallRealMethod();
        when(whiskyDtoToWhisky.convert(whiskyDto)).thenCallRealMethod();

        WhiskyDto newWhisky = whiskyService.updateWhisky(whiskyDto, ID.toString()).block();

        assertThat(newWhisky, is(whiskyDto));
        verify(whiskyRepository, times(1)).save(whisky);
        verify(whiskyToWhiskyDto, times(1)).convert(whisky);
        verify(whiskyDtoToWhisky, times(1)).convert(whiskyDto);
    }

    @Test
    public void deleteWhisky() throws Exception {
        Whisky whisky = getWhisky();
        when(whiskyRepository.findById(ID)).thenReturn(Mono.just(whisky));

        whiskyService.delete(ID.toString());

        verify(whiskyRepository, times(1)).findById(ID);
        verify(whiskyRepository, times(1)).delete(whisky);
    }

    private WhiskyDto getWhiskyDto() {
        WhiskyDto whiskyDto = new WhiskyDto();
        whiskyDto.setAge(AGE);
        whiskyDto.setDistilleryName(DISTILLERY_NAME);
        whiskyDto.setName(NAME);
        whiskyDto.setStrength(STRENGTH);
        return whiskyDto;
    }


    private Whisky getWhisky() {
        Whisky whisky = new Whisky();
        whisky.setId(ID);
        whisky.setAge(AGE);
        whisky.setDistilleryName(DISTILLERY_NAME);
        whisky.setName(NAME);
        whisky.setStrength(STRENGTH);
        return whisky;
    }
}

