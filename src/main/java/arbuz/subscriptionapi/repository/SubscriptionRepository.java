package arbuz.subscriptionapi.repository;

import arbuz.subscriptionapi.entities.subscription.Subscriber;
import arbuz.subscriptionapi.entities.subscription.Subscription;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    Optional<Subscription> findBySubscriber(Subscriber subscriber);

}
