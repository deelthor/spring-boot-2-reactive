package de.deelthor.whiskybar.service;

import de.deelthor.whiskybar.dto.WhiskyDto;
import de.deelthor.whiskybar.entity.Whisky;
import de.deelthor.whiskybar.mapper.WhiskyDtoToWhisky;
import de.deelthor.whiskybar.mapper.WhiskyToWhiskyDto;
import de.deelthor.whiskybar.repository.WhiskyRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

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
        when(whiskyRepository.findAll()).thenReturn(Arrays.asList(whisky));
        when(whiskyToWhiskyDto.convert(whisky)).thenCallRealMethod();

        Set<WhiskyDto> allWhiskies = whiskyService.getAllWhiskies();

        assertThat(allWhiskies, hasSize(1));
        verify(whiskyRepository, times(1)).findAll();
        verify(whiskyToWhiskyDto, times(1)).convert(whisky);
    }

    @Test
    public void getWhiskyById() throws Exception {
        Whisky whisky = getWhisky();
        when(whiskyRepository.findById(ID)).thenReturn(whisky);
        when(whiskyToWhiskyDto.convert(whisky)).thenCallRealMethod();

        WhiskyDto whiskyDto = whiskyService.getWhiskyById(ID.toString());

        assertThat(whiskyDto, not(nullValue()));
        verify(whiskyRepository, times(1)).findById(ID);
        verify(whiskyToWhiskyDto, times(1)).convert(whisky);
    }

    @Test
    public void getWhiskiesByName() throws Exception {
        Whisky whisky = getWhisky();
        when(whiskyRepository.findByName(NAME)).thenReturn(Arrays.asList(whisky));
        when(whiskyToWhiskyDto.convert(whisky)).thenCallRealMethod();

        Set<WhiskyDto> allWhiskies = whiskyService.getWhiskiesByName(NAME);

        assertThat(allWhiskies, hasSize(1));
        verify(whiskyRepository, times(1)).findByName(NAME);
        verify(whiskyToWhiskyDto, times(1)).convert(whisky);
    }

    @Test
    public void getWhiskiesByDistilleryName() throws Exception {
        Whisky whisky = getWhisky();
        when(whiskyRepository.findByDistilleryName(DISTILLERY_NAME)).thenReturn(Arrays.asList(whisky));
        when(whiskyToWhiskyDto.convert(whisky)).thenCallRealMethod();

        Set<WhiskyDto> allWhiskies = whiskyService.getWhiskiesByDistillery(DISTILLERY_NAME);

        assertThat(allWhiskies, hasSize(1));
        verify(whiskyRepository, times(1)).findByDistilleryName(DISTILLERY_NAME);
        verify(whiskyToWhiskyDto, times(1)).convert(whisky);
    }

    @Test
    public void addWhisky() throws Exception {
        Whisky whisky = getWhisky();
        WhiskyDto whiskyDto = getWhiskyDto();
        when(whiskyRepository.save(whisky)).thenReturn(whisky);
        when(whiskyToWhiskyDto.convert(whisky)).thenCallRealMethod();
        when(whiskyDtoToWhisky.convert(whiskyDto)).thenCallRealMethod();

        WhiskyDto newWhisky = whiskyService.addWhisky(whiskyDto);

        assertThat(newWhisky, is(whiskyDto));
        verify(whiskyRepository, times(1)).save(whisky);
        verify(whiskyToWhiskyDto, times(1)).convert(whisky);
        verify(whiskyDtoToWhisky, times(1)).convert(whiskyDto);
    }

    @Test
    public void updateWhisky() throws Exception {
        Whisky whisky = getWhisky();
        WhiskyDto whiskyDto = getWhiskyDto();
        when(whiskyRepository.save(whisky)).thenReturn(whisky);
        when(whiskyToWhiskyDto.convert(whisky)).thenCallRealMethod();
        when(whiskyDtoToWhisky.convert(whiskyDto)).thenCallRealMethod();

        WhiskyDto newWhisky = whiskyService.updateWhisky(whiskyDto, ID.toString());

        assertThat(newWhisky, is(whiskyDto));
        verify(whiskyRepository, times(1)).save(whisky);
        verify(whiskyToWhiskyDto, times(1)).convert(whisky);
        verify(whiskyDtoToWhisky, times(1)).convert(whiskyDto);
    }

    @Test
    public void deleteWhisky() throws Exception {
        Whisky whisky = getWhisky();
        when(whiskyRepository.findById(ID)).thenReturn(whisky);

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

