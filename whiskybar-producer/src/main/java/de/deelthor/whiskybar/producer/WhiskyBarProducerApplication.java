package de.deelthor.whiskybar.producer;

import de.deelthor.whiskybar.producer.entity.Whisky;
import de.deelthor.whiskybar.producer.repository.WhiskyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class WhiskyBarProducerApplication implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private WhiskyRepository whiskyRepository;

	public static void main(String[] args) {
		SpringApplication.run(WhiskyBarProducerApplication.class, args);
	}

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Whisky whisky1 = new Whisky();
		whisky1.setDistilleryName("Ardbeg");
		whisky1.setName("Ten");
		whisky1.setAge(10);
		whisky1.setStrength(46.0);
		whiskyRepository.save(whisky1);

		Whisky whisky2 = new Whisky();
		whisky2.setDistilleryName("Laphroaig");
		whisky2.setName("Quarter Cask");
		whisky2.setStrength(48.0);
		whiskyRepository.save(whisky2);

		Whisky whisky3 = new Whisky();
		whisky3.setDistilleryName("Bruichladdich");
		whisky3.setName("Octomore 07.2");
		whisky3.setStrength(58.5);
		whisky3.setAge(5);
		whiskyRepository.save(whisky3);
	}
}
