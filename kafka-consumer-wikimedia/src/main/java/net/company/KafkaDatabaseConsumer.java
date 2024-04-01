package net.company;

import net.company.entity.WikimediaData;
import net.company.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);


    private WikimediaDataRepository wikimediaDataRepository;

    public KafkaDatabaseConsumer(WikimediaDataRepository wikimediaDataRepository) {
        this.wikimediaDataRepository = wikimediaDataRepository;
    }

    @KafkaListener(
            topics = "wikimedia_recent_change" ,
            groupId = "group-id"
    )
    public void consume(String eventMessage){
        LOGGER.info("Event Message received -> {}" + eventMessage);

        WikimediaData wikimedia = new WikimediaData();
        wikimedia.setWikiEventData(eventMessage);

        wikimediaDataRepository.save(wikimedia);
    }
}
