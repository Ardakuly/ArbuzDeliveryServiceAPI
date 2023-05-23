package arbuz.subscriptionapi.service;

import arbuz.subscriptionapi.entities.basket.ReservedProduct;
import arbuz.subscriptionapi.entities.product.Product;
import arbuz.subscriptionapi.entities.subscription.Subscriber;
import arbuz.subscriptionapi.exceptions.IllegalNumberException;
import arbuz.subscriptionapi.exceptions.ResourceNotSufficientException;
import arbuz.subscriptionapi.exceptions.SubscriberExistsException;
import arbuz.subscriptionapi.exceptions.SubscriberNotFoundException;
import arbuz.subscriptionapi.repository.ProductRepository;
import arbuz.subscriptionapi.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriberService {

    private SubscriberRepository subscriberRepository;
    private ProductRepository productRepository;

    @Autowired
    public SubscriberService(SubscriberRepository subscriberRepository, ProductRepository productRepository) {
        this.subscriberRepository = subscriberRepository;
        this.productRepository = productRepository;
    }

    public boolean createSubscriber(Subscriber subscriber) throws SubscriberExistsException, IllegalNumberException {

        if (!subscriber.getNumber().toString().matches("^(\\+7|8)\\d{10}$")) {
             throw new IllegalNumberException("Number " + subscriber.getNumber() + " has wrong format.");
        } // check number for correctness

        if (subscriberExist(subscriber.getNumber())) {
            throw new SubscriberExistsException("User with "  + subscriber.getNumber() + " number exists.");
        }

        subscriber.setInBasket(new ArrayList<ReservedProduct>());

        subscriberRepository.save(subscriber);

        return true;

    }

    public boolean deleteSubscriber(Long number) throws IllegalNumberException, SubscriberNotFoundException {

        if (!number.toString().matches("^(\\+7|8)\\d{10}$")) {
            throw new IllegalNumberException("Number " + number + " has wrong format.");
        }// check number for correctness

        if (!subscriberExist(number)) {
            throw new SubscriberNotFoundException("User with "  + number + " number does not exist.");
        }

        subscriberRepository.deleteById(number);

        return true;
    }


    public boolean updateSubscriber(Subscriber subscriber) throws IllegalNumberException {

        if (!subscriber.getNumber().toString().matches("^(\\+7|8)\\d{10}$")) {
            throw new IllegalNumberException("Number " + subscriber.getNumber() + " has wrong format.");
        } // check number for correctness

        subscriberRepository.save(subscriber);

        return true;

    }

    public boolean addToBasket(Subscriber subscriber) throws ResourceNotSufficientException, SubscriberNotFoundException {

        // check if products exist and if yes then is sufficient amount

        if (subscriberExist(subscriber.getNumber())) {

            subscriber.setId(subscriberRepository.findByNumber(subscriber.getNumber()).get().getId());

            Subscriber subscriberInDataBase = subscriberRepository.findByNumber(subscriber.getNumber()).get(); // get existing basket

            List<ReservedProduct> basketInDataBase = subscriberInDataBase.getInBasket(); // get existing products in basked

            List<ReservedProduct> updatedBasket = subscriber.getInBasket(); // get a new list of basked came from user

            for (ReservedProduct reservedProduct : updatedBasket) {

                for (ReservedProduct reservedProductInDataBase: basketInDataBase) {

                    if (reservedProduct.equals(reservedProductInDataBase)) { // check for old products to add to an updated basket

                        reservedProduct.setId(reservedProductInDataBase.getId());




                    }

                }

            }

            // subtract from actual amount
            List<Product> products = (List<Product>) productRepository.findAll();

            for (ReservedProduct reservedProduct : updatedBasket) {

                for (Product product : products) {

                    if (product.getName().equals(reservedProduct.getName())) {

                        if (product.getCapacity() < reservedProduct.getWeight()) {
                            throw new ResourceNotSufficientException("Resource " + reservedProduct.getName() + " is not sufficient in stock.");
                        }

                        break;

                    }

                }

            }

            productRepository.saveAll(products); //save reserved product

            subscriberRepository.save(subscriber);


        } else {
            throw new SubscriberNotFoundException("User with "  + subscriber.getNumber() + " number does not exist.");
        }



        return true;

    }

    public List<ReservedProduct> getProductsInBasket(Long number) throws IllegalNumberException, SubscriberNotFoundException {

        if (!number.toString().matches("^(\\+7|8)\\d{10}$")) {
            throw new IllegalNumberException("Number " + number + " has wrong format.");
        } // check number for correctness

        if (!subscriberExist(number)) {
            throw new SubscriberNotFoundException("User with "  + number + " number does not exist.");
        }

        return subscriberRepository.findByNumber(number).get().getInBasket();

    }

    private boolean subscriberExist(Long number) {

        Optional<Subscriber> subscriber = subscriberRepository.findByNumber(number);

        return subscriber.isPresent();

    }



}

//    // subtract from actual amount
//    List<Product> products = (List<Product>) productRepository.findAll();
//
//            for (ReservedProduct reservedProduct : updatedBasket) {
//
//                    for (Product product : products) {
//
//                    if (product.getName().equals(reservedProduct.getName())) {
//
//                    if (product.getCapacity() >= reservedProduct.getWeight()) {
//
//                    product.setCapacity(product.getCapacity() - reservedProduct.getWeight());
//
//                    break;
//                    } else {
//                    throw new ResourceNotSufficientException("Resource " + reservedProduct.getName() + " is not sufficient in stock.");
//                    }
//
//                    }
//
//                    }
//
//                    }
//
//                    productRepository.saveAll(products); //save reserved products