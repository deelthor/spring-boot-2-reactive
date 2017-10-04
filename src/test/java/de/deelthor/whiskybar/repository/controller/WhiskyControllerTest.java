package de.deelthor.whiskybar.repository.controller;

import de.deelthor.whiskybar.controller.WhiskyController;
import de.deelthor.whiskybar.dto.WhiskyDto;
import de.deelthor.whiskybar.service.WhiskyService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WhiskyControllerTest {

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
        WhiskyDto whiskyDto = new WhiskyDto();
        whiskyDto.setAge(10);
        whiskyDto.setDistilleryName("Ardbeg");
        whiskyDto.setName("TEN");
        whiskyDto.setStrength(46.0);
        whiskyDtos.add(whiskyDto);
        when(whiskyService.getAllWhiskies()).thenReturn(whiskyDtos);

        mockMvc.perform(get("/whiskies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(whiskyService, times(1)).getAllWhiskies();
    }
}
