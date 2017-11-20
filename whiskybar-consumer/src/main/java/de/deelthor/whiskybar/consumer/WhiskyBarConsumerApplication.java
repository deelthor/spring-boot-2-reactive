package de.deelthor.whiskybar.consumer;

import de.deelthor.whiskybar.consumer.dto.WhiskyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootApplication
public class WhiskyBarConsumerApplication implements ApplicationListener<ContextRefreshedEvent> {

    private Logger LOG = LoggerFactory.getLogger(WhiskyBarConsumerApplication.class);

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    private URI whiskyProducerUri = new URI("http://localhost:8080/whiskies");

    public WhiskyBarConsumerApplication() throws URISyntaxException {
    }

    public static void main(String[] args) {
        SpringApplication.run(WhiskyBarConsumerApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        // Produce whiskies
        processInputFile("whiskies.csv").forEach(whiskyDto -> restTemplate.postForEntity(whiskyProducerUri, whiskyDto, WhiskyDto.class));

        // Consume all whiskies
        WhiskyDto[] whiskies = restTemplate.getForObject(whiskyProducerUri, WhiskyDto[].class);
        LOG.info("========================================");
        LOG.info("Whiskies in database:");
        Arrays.stream(whiskies).forEach(whisky -> LOG.info(whisky.toString()));

    }

    private List<WhiskyDto> processInputFile(String inputFilePath) {
        List<WhiskyDto> inputList = new ArrayList<>();
        try {
            URI path = getClass().getClassLoader().getResource(inputFilePath).toURI();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
            inputList = br.lines().map(mapToWhiskyDto).collect(Collectors.toList());
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return inputList;
    }

    private Function<String, WhiskyDto> mapToWhiskyDto = (line) -> {
        String[] whiskyLine = line.split(",");
        WhiskyDto whisky = new WhiskyDto();
        whisky.setDistilleryName(whiskyLine[0]);
        whisky.setName(whiskyLine[1]);
        if (whiskyLine[2] != null && whiskyLine[2].trim().length() > 0) {
            whisky.setAge(Integer.valueOf(whiskyLine[2]));
        }
        if (whiskyLine[3] != null && whiskyLine[3].trim().length() > 0) {
            whisky.setStrength(Double.valueOf(whiskyLine[3]));
        }
        return whisky;
    };
}
