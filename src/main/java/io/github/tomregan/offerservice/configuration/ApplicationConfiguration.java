package io.github.tomregan.offerservice.configuration;

import io.github.tomregan.offerservice.datastore.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.Instant;

import static io.github.tomregan.offerservice.datastore.OfferData.offerBuilder;

@SuppressWarnings("unused")
@Configuration class ApplicationConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Bean CommandLineRunner loadOffers(OfferRepository repository) {
        return args -> {
            logger.info("Loading data {}", repository.save(offerBuilder()
                    .id(1)
                    .description("firstOffer")
                    .expiry(Instant.now().plus(Duration.ofDays(1)))
                    .valid(true)
                    .currency(5, "GBP")
                    .build()));
            logger.info("Loading data {}", repository.save(offerBuilder()
                    .id(2)
                    .description("secondOffer")
                    .expiry(Instant.now().plus(Duration.ofDays(1)))
                    .valid(true)
                    .currency(8, "GBP")
                    .build()));
        };
    }

}
