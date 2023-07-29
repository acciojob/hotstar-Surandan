package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay

        User user = userRepository.findById(subscriptionEntryDto.getUserId()).get();

        int amount = 0;
        SubscriptionType subscriptionType = subscriptionEntryDto.getSubscriptionType();
        if(subscriptionType == SubscriptionType.BASIC) amount = 500 + 200 * subscriptionEntryDto.getNoOfScreensRequired();
        else if (subscriptionType == SubscriptionType.PRO) amount = 800 + 250 * subscriptionEntryDto.getNoOfScreensRequired();
        else amount = 1000 + 350 * subscriptionEntryDto.getNoOfScreensRequired();

        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        subscription.setStartSubscriptionDate(new Date());
        subscription.setUser(user);
        subscription.setNoOfScreensSubscribed(subscriptionEntryDto.getNoOfScreensRequired());
        subscription.setTotalAmountPaid(amount);

        subscriptionRepository.save(subscription);
        return amount;
    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository

        User user = userRepository.findById(userId).get();
        Subscription subscription = user.getSubscription();
        if(subscription == null) throw new Exception("Already the best Subscription");
        SubscriptionType oldType = subscription.getSubscriptionType();
        int difference = 0;
        if( oldType == SubscriptionType.ELITE) throw new Exception("Already the best Subscription");

        if(oldType == SubscriptionType.BASIC) {
            subscription.setSubscriptionType(SubscriptionType.PRO);
            int newAmount = 800 + 250 * subscription.getNoOfScreensSubscribed();
            difference = ( newAmount- subscription.getTotalAmountPaid());
            subscription.setTotalAmountPaid(newAmount);
        }
        else {
            subscription.setSubscriptionType(SubscriptionType.ELITE);
            int newAmount = 1000 + 350 * subscription.getNoOfScreensSubscribed();
            difference = ( newAmount- subscription.getTotalAmountPaid());
            subscription.setTotalAmountPaid(newAmount);
        }

        subscriptionRepository.save(subscription);
        return difference;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb

        int revenue = 0;
        for(Subscription subscription : subscriptionRepository.findAll()) {
            revenue += subscription.getTotalAmountPaid();
        }
        return revenue;
    }

}
