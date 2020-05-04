package guru.sfg.beer.order.service.services.listeners;

import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.beer.order.service.repositories.BeerOrderRepository;
import guru.sfg.beer.order.service.services.BeerOrderManager;
import guru.sfg.brewery.model.events.AllocateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BeerOrderAllocationResultListener {
    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE)
    public void listener(AllocateOrderResult result){
        log.debug("*********** checking for allocation **********");
        if(!result.getAllocationError() && !result.getInventoryPending()){
            //normal allocation
            beerOrderManager.beerOrderAllocationPassed(result.getBeerOrderDto());
        }
        if(!result.getAllocationError() && result.getInventoryPending()){
            //partial allocation
            beerOrderManager.beerOrderAllocationInventoryPending(result.getBeerOrderDto());
        }
        if(result.getAllocationError()){
            // allocation error
            beerOrderManager.beerOrderAllocationFailed(result.getBeerOrderDto());
        }
    }
}
