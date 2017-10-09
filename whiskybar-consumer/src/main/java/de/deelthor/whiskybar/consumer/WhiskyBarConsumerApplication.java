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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@SpringBootApplication
public class WhiskyBarConsumerApplication implements ApplicationListener<ContextRefreshedEvent> {

    private Logger LOG = LoggerFactory.getLogger(WhiskyBarConsumerApplication.class);

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    private URI whiskyProducerUri = new URI("http://localhost:8080/whiskies");
    ;

    public WhiskyBarConsumerApplication() throws URISyntaxException {
    }

    public static void main(String[] args) {
        SpringApplication.run(WhiskyBarConsumerApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        WhiskyDto whisky1 = new WhiskyDto();
        whisky1.setDistilleryName("Ardbeg");
        whisky1.setName("Ten");
        whisky1.setAge(10);
        whisky1.setStrength(46.0);

        restTemplate.postForEntity(whiskyProducerUri, whisky1, WhiskyDto.class);


        WhiskyDto whisky2 = new WhiskyDto();
        whisky2.setDistilleryName("Laphroaig");
        whisky2.setName("Quarter Cask");
        whisky2.setStrength(48.0);
        restTemplate.postForEntity(whiskyProducerUri, whisky2, WhiskyDto.class);

        WhiskyDto whisky3 = new WhiskyDto();
        whisky3.setDistilleryName("Bruichladdich");
        whisky3.setName("Octomore 07.2");
        whisky3.setStrength(58.5);
        whisky3.setAge(5);
        restTemplate.postForEntity(whiskyProducerUri, whisky3, WhiskyDto.class);

        WhiskyDto[] whiskies = restTemplate.getForObject(whiskyProducerUri, WhiskyDto[].class);
        LOG.info("========================================");
        LOG.info("Whiskies in database:");
        for (WhiskyDto whisky : whiskies) {
            LOG.info(whisky.toString());
        }

    }
}
