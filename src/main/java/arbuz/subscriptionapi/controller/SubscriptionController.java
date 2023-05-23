package arbuz.subscriptionapi.controller;

import arbuz.subscriptionapi.entities.subscription.Subscriber;
import arbuz.subscriptionapi.entities.subscription.Subscription;
import arbuz.subscriptionapi.exceptions.SubscriptionExistsException;
import arbuz.subscriptionapi.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/arbuz/delivery/subscription")
public class SubscriptionController {

    private SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/subscribe")
    public boolean subscribe(@RequestBody Subscription subscription) throws SubscriptionExistsException {

        return subscriptionService.subscribe(subscription);

    }

    @GetMapping("/show")
    public Subscription showSubscription(@RequestBody Subscriber subscriber) throws SubscriptionExistsException {

        return subscriptionService.showSubscription(subscriber);

    }

}
