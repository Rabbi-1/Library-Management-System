package com.rabbi.services;

import com.rabbi.exception.SubscriptionException;
import com.rabbi.payload.dto.SubscriptionDTO;

import java.awt.print.Pageable;
import java.util.List;

public interface SubscriptionService {

    SubscriptionDTO subscribe(SubscriptionDTO subscriptionDTO) throws Exception;
    // was SubscriptionDTO getUsersActiveSubscriptions(Long userId) throws Exception;
    SubscriptionDTO getUsersActiveSubscriptions() throws Exception;
    SubscriptionDTO cancelSubscription(Long subscriptionId, String reason) throws SubscriptionException;
    SubscriptionDTO activeSubscription(Long subscriptionId, Long paymentId) throws SubscriptionException;
    List<SubscriptionDTO> getAllSubscriptions(Pageable pageable);
    void deactivateSubscription(Long userId) throws SubscriptionException;


}
