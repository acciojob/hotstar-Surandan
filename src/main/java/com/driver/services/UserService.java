package com.driver.services;


import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.model.WebSeries;
import com.driver.repository.UserRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebSeriesRepository webSeriesRepository;


    public Integer addUser(User user){

        //Jut simply add the user to the Db and return the userId returned by the repository
        User user1 = userRepository.save(user);
        return user1.getId();
    }

    public Integer getAvailableCountOfWebSeriesViewable(Integer userId){

        //Return the count of all webSeries that a user can watch based on his ageLimit and subscriptionType
        //Hint: Take out all the Webseries from the WebRepository

        int count = 0;
        User user = userRepository.findById(userId).get();
        SubscriptionType usersType = user.getSubscription().getSubscriptionType();
        for(WebSeries webSeries : webSeriesRepository.findAll()) {
            if(usersType == SubscriptionType.ELITE) count++;
            else if(usersType == webSeries.getSubscriptionType()) count++;
            else if(usersType == SubscriptionType.PRO && webSeries.getSubscriptionType() == SubscriptionType.BASIC) count++;
        }

        return count;
    }


}
