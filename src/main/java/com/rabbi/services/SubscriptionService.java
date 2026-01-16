package com.rabbi.services;

import com.rabbi.exception.SubscriptionException;
import com.rabbi.payload.dto.SubscriptionDTO;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface SubscriptionService {

    SubscriptionDTO subscribe(SubscriptionDTO subscriptionDTO) throws Exception;
    // was SubscriptionDTO getUsersActiveSubscriptions(Long userId) throws Exception;
    SubscriptionDTO getUsersActiveSubscriptions(Long userId) throws Exception;
    SubscriptionDTO cancelSubscription(Long subscriptionId, String reason) throws SubscriptionException;
    SubscriptionDTO activateSubscription(Long subscriptionId, Long paymentId) throws SubscriptionException;
    List<SubscriptionDTO> getAllSubscriptions(Pageable pageable);
    void deactivateSubscription() throws SubscriptionException;


}
