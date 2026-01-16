package com.rabbi.services.impl;


import com.rabbi.exception.SubscriptionException;
import com.rabbi.mapper.SubscriptionMapper;
import com.rabbi.model.Subscription;
import com.rabbi.model.SubscriptionPlan;
import com.rabbi.model.User;
import com.rabbi.payload.dto.SubscriptionDTO;
import com.rabbi.repo.SubscriptionPlanRepository;
import com.rabbi.repo.SubscriptionRepository;
import com.rabbi.services.SubscriptionService;
import com.rabbi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserService userService;
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    @Override
    public SubscriptionDTO subscribe(SubscriptionDTO subscriptionDTO) throws Exception {
        User user = userService.getCurrentUser();

        SubscriptionPlan plan = subscriptionPlanRepository.findById(subscriptionDTO.getPlanId()).orElseThrow(
                () -> new SubscriptionException("Plan Not Found")
        );

        Subscription subscription = subscriptionMapper.toEntity(subscriptionDTO);
        subscription.initializeFromPlan();
        Subscription saved = subscriptionRepository.save(subscription);

        return subscriptionMapper.toDTO(saved);
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
