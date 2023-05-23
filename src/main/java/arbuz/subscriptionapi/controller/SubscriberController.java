package arbuz.subscriptionapi.controller;

import arbuz.subscriptionapi.entities.basket.ReservedProduct;
import arbuz.subscriptionapi.entities.subscription.Subscriber;
import arbuz.subscriptionapi.exceptions.IllegalNumberException;
import arbuz.subscriptionapi.exceptions.ResourceNotSufficientException;
import arbuz.subscriptionapi.exceptions.SubscriberExistsException;
import arbuz.subscriptionapi.exceptions.SubscriberNotFoundException;
import arbuz.subscriptionapi.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/arbuz/delivery/subscriber")
public class SubscriberController {

    private SubscriberService subscriberService;

    @Autowired
    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }


    @PostMapping("/create")
    public boolean createSubscriber(@RequestBody Subscriber subscriber) throws SubscriberExistsException, IllegalNumberException {

        return subscriberService.createSubscriber(subscriber);

    }

    @DeleteMapping ("/delete")
    public boolean deleteSubscriber(@RequestParam("number") Long number) throws IllegalNumberException, SubscriberNotFoundException {

        return subscriberService.deleteSubscriber(number);

    }

    @PutMapping("/update")
    public boolean updateSubscriber(@RequestBody Subscriber subscriber) {

        return true;

    }

    @PostMapping("/basket/add")
    public boolean addToBasket(@RequestBody Subscriber subscriber) throws ResourceNotSufficientException, SubscriberNotFoundException {

        return subscriberService.addToBasket(subscriber);

    }

    @GetMapping("/basket/show")
    public List<ReservedProduct> showBasket(@RequestParam("number") Long number) throws SubscriberNotFoundException, IllegalNumberException {

        return subscriberService.getProductsInBasket(number);

    }
}
