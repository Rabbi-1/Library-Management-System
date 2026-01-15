package com.rabbi.services.impl;


import com.rabbi.payload.dto.SubscriptionDTO;
import com.rabbi.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionImpl implements SubscriptionService {
    @Override
    public SubscriptionDTO subscribe(SubscriptionDTO subscriptionDTO) {
        return null;
    }

    @Override
    public SubscriptionDTO getUsersActiveSubscriptions(Long userId) {
        return null;
    }

    @Override
    public SubscriptionDTO cancelSubscription(Long subscriptionId, String reason) {
        return null;
    }

    @Override
    public SubscriptionDTO activeSubscription(Long subscriptionId, Long paymentId) {
        return null;
    }

    @Override
    public List<SubscriptionDTO> getAllSubscriptions(Pageable pageable) {
        return List.of();
    }
}
