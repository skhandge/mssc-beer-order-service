package guru.sfg.beer.order.service.services;

import guru.sfg.beer.order.service.domain.BeerOrder;

import java.util.UUID;

public interface BeerOrderManager {

    public BeerOrder newBeerOrder(BeerOrder beerOrder);

    void processValidation(UUID orderId, Boolean isValid);
}
