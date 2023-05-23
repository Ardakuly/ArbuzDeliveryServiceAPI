package arbuz.subscriptionapi.service;

import arbuz.subscriptionapi.entities.subscription.Subscriber;
import arbuz.subscriptionapi.entities.subscription.Subscription;
import arbuz.subscriptionapi.exceptions.SubscriptionExistsException;
import arbuz.subscriptionapi.repository.SubscriberRepository;
import arbuz.subscriptionapi.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class SubscriptionService {

    private SubscriptionRepository subscriptionRepository;
    private SubscriberRepository subscriberRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, SubscriberRepository subscriberRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriberRepository = subscriberRepository;
    }


    public boolean subscribe(Subscription subscription) throws SubscriptionExistsException {

        Optional<Subscriber> subscriber = subscriberRepository.findByNumber(subscription.getSubscriber().getNumber());

        if (subscriber.isPresent()) {
            subscription.getSubscriber().setId(subscriber.get().getId());
        }

        if (subscriptionExist(subscription.getSubscriber())) {
            throw new SubscriptionExistsException("Subscription for user " + subscription.getSubscriber().getNumber() + " exists.");
        }

        LocalDate date = LocalDate.now();

        LocalDate until = date.plusDays(subscription.getSubscriptionDurationInDays());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy");

        subscription.setSubscriptionDurationInDate(formatter.format(until));

        subscriptionRepository.save(subscription);

        return true;

    }

    public Subscription showSubscription(Subscriber subscriber) {

        Optional<Subscription> subscriptionOptional = subscriptionRepository.findBySubscriber(subscriber);

        if (!subscriptionOptional.isPresent()) return null;

        return subscriptionOptional.get();
    }

    private boolean subscriptionExist(Subscriber subscriber) {

        Optional<Subscription> subscription = subscriptionRepository.findBySubscriber(subscriber);

        return subscription.isPresent();

    }



}
