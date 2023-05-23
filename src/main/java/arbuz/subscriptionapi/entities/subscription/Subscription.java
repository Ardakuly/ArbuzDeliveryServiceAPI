package arbuz.subscriptionapi.entities.subscription;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Subscriber subscriber;

    private String deliveryDay;

    private String dayPeriod;

    private Short subscriptionDurationInDays;

    private String subscriptionDurationInDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public String getDeliveryDay() {
        return deliveryDay;
    }

    public void setDeliveryDay(String deliveryDay) {
        this.deliveryDay = deliveryDay;
    }


    public String getDayPeriod() {
        return dayPeriod;
    }

    public void setDayPeriod(String dayPeriod) {
        this.dayPeriod = dayPeriod;
    }

    public Short getSubscriptionDurationInDays() {
        return subscriptionDurationInDays;
    }

    public void setSubscriptionDurationInDays(Short subscriptionDurationInDays) {
        this.subscriptionDurationInDays = subscriptionDurationInDays;
    }

    public String getSubscriptionDurationInDate() {
        return subscriptionDurationInDate;
    }

    public void setSubscriptionDurationInDate(String subscriptionDurationInDate) {
        this.subscriptionDurationInDate = subscriptionDurationInDate;
    }
}
