package arbuz.subscriptionapi.repository;

import arbuz.subscriptionapi.entities.subscription.Subscriber;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SubscriberRepository extends CrudRepository<Subscriber, Long> {

    Optional<Subscriber> findByNumber(Long number);

}
