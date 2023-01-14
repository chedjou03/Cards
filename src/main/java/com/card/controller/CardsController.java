package com.card.controller;

import com.card.config.CardsServiceConfig;
import com.card.model.Cards;
import com.card.model.Customer;
import com.card.model.Properties;
import com.card.repository.CardsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class CardsController {

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private CardsServiceConfig cardsServiceConfig;

    private static final Logger logger = LoggerFactory.getLogger(CardsController.class);

    @PostMapping("/myCards")
    public List<Cards> getCardDetails(@RequestHeader("ChedjouBank-correlation-id") String correlationId,@RequestBody Customer customer) {
        logger.debug("ChedjouBank-correlation-id : {}. ", correlationId);
        List<Cards> cards = cardsRepository.findByCustomerId(customer.getCustomerId());
        if (cards != null) {
            return cards;
        } else {
            return null;
        }
    }

    @GetMapping("/cards/properties")
    public String getCardsProperties() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Properties properties = new Properties(cardsServiceConfig.getMsg(), cardsServiceConfig.getBuildVersion(), cardsServiceConfig.getActiveBranches(), cardsServiceConfig.getMailDetails());
        String jsonStr = objectMapper.writeValueAsString(properties);
        return jsonStr;
    }

}
