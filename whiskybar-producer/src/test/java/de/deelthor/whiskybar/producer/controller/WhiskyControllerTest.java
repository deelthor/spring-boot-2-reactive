package de.deelthor.whiskybar.producer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.deelthor.whiskybar.producer.dto.WhiskyDto;
import de.deelthor.whiskybar.producer.service.WhiskyService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WhiskyControllerTest {

    private static final String DISTILLERY_NAME = "Ardbeg";
    private static final int AGE = 10;
    private static final String NAME = "TEN";
    private static final double STRENGTH = 46.0;

    @Mock
    private WhiskyService whiskyService;

    private WhiskyController whiskyController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        whiskyController = new WhiskyController(whiskyService);
        mockMvc = MockMvcBuilders.standaloneSetup(whiskyController).build();
    }

    @Test
    public void getAllWhiskies() throws Exception {
        Set<WhiskyDto> whiskyDtos = new HashSet<>();
        whiskyDtos.add(getWhiskyDto());
        when(whiskyService.getAllWhiskies()).thenReturn(whiskyDtos);

        mockMvc.perform(get("/whiskies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(whiskyService, times(1)).getAllWhiskies();
    }

    @Test
    public void getWhiskyById() throws Exception {
        WhiskyDto whiskyDto = getWhiskyDto();
        when(whiskyService.getWhiskyById("12345")).thenReturn(whiskyDto);

        mockMvc.perform(get("/whiskies/12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", not(nullValue())));

        verify(whiskyService, times(1)).getWhiskyById("12345");
    }

    @Test
    public void getWhiskiesByName() throws Exception {
        Set<WhiskyDto> whiskyDtos = new HashSet<>();
        WhiskyDto whiskyDto = getWhiskyDto();
        whiskyDtos.add(whiskyDto);
        when(whiskyService.getWhiskiesByName(NAME)).thenReturn(whiskyDtos);

        mockMvc.perform(get("/whiskies?name=" + NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(whiskyService, times(1)).getWhiskiesByName(NAME);
    }

    @Test
    public void getWhiskiesByDistilleryName() throws Exception {
        Set<WhiskyDto> whiskyDtos = new HashSet<>();
        WhiskyDto whiskyDto = getWhiskyDto();
        whiskyDtos.add(whiskyDto);
        when(whiskyService.getWhiskiesByDistillery(DISTILLERY_NAME)).thenReturn(whiskyDtos);

        mockMvc.perform(get("/whiskies?distilleryName=" + DISTILLERY_NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(whiskyService, times(1)).getWhiskiesByDistillery(DISTILLERY_NAME);
    }

    @Test
    public void deleteWhisky() throws Exception {
        mockMvc.perform(delete("/whiskies/12345"))
                .andExpect(status().isOk());

        verify(whiskyService, times(1)).delete("12345");
    }

    @Test
    public void addWhisky() throws Exception {
        WhiskyDto whiskyDto = getWhiskyDto();
        when(whiskyService.addWhisky(whiskyDto)).thenReturn(whiskyDto);

        mockMvc.perform(post("/whiskies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(whiskyDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", not(nullValue())));

        verify(whiskyService, times(1)).addWhisky(whiskyDto);
    }

    @Test
    public void updateWhisky() throws Exception {
        WhiskyDto whiskyDto = getWhiskyDto();
        when(whiskyService.updateWhisky(whiskyDto, "12345")).thenReturn(whiskyDto);

        mockMvc.perform(put("/whiskies/12345")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(whiskyDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", not(nullValue())));

        verify(whiskyService, times(1)).updateWhisky(whiskyDto, "12345");
    }

    private WhiskyDto getWhiskyDto() {
        WhiskyDto whiskyDto = new WhiskyDto();
        whiskyDto.setAge(AGE);
        whiskyDto.setDistilleryName(DISTILLERY_NAME);
        whiskyDto.setName(NAME);
        whiskyDto.setStrength(STRENGTH);
        return whiskyDto;
    }
}
