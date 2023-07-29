package com.driver.test;

import com.driver.EntryDto.ProductionHouseEntryDto;
import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import com.driver.repository.WebSeriesRepository;
import com.driver.services.ProductionHouseService;
import com.driver.services.SubscriptionService;
import com.driver.services.UserService;
import com.driver.services.WebSeriesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TestCases {

    WebSeriesService webSeriesService = new WebSeriesService();

    @Test
    public void addWebs() throws Exception {

        WebSeriesEntryDto webSeriesEntryDto = new WebSeriesEntryDto("Mirzapur",14,9.2,SubscriptionType.PRO,3);

        WebSeries actual = webSeriesService.addWebSeries(webSeriesEntryDto);

       assertEquals(14,actual.getAgeLimit());
    }

}
