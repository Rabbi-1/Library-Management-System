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
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        //Will debug later
        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.initializeFromPlan();
        Subscription saved = subscriptionRepository.save(subscription);

        return subscriptionMapper.toDTO(saved);
    }
    // was public SubscriptionDTO getUsersActiveSubscriptions(Long userId)
    @Override
    public SubscriptionDTO getUsersActiveSubscriptions() throws Exception {
        User user = userService.getCurrentUser();
        Subscription subscription = subscriptionRepository.findActiveSubscriptionByUserId(user.getId(), LocalDate.now())
                .orElseThrow(() -> new SubscriptionException("Subscription Not Found"));
        return subscriptionMapper.toDTO(subscription);
    }

    @Override
    public SubscriptionDTO cancelSubscription(Long subscriptionId, String reason) throws SubscriptionException {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionException("Subscription not found with ID"));

        if(!subscription.getIsActive()) {
            throw new SubscriptionException("Subscription Not Active");
        }
        subscription.setIsActive(false);
        subscription.setCancelledAt(LocalDateTime.now());
        subscription.setCancellationReason(reason != null ? reason : "Cancelled by user");

        subscription = subscriptionRepository.save(subscription);

        return subscriptionMapper.toDTO(subscription);
    }

    @Override
    public SubscriptionDTO activeSubscription(Long subscriptionId, Long paymentId) throws SubscriptionException {

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionException("Subscription not found with ID"));
        //verify payment
        subscription.setIsActive(true);
        subscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDTO(subscription);
    }

    @Override
    public List<SubscriptionDTO> getAllSubscriptions(Pageable pageable) {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        return subscriptionMapper.toDTOList(subscriptions);
    }

    @Override
    public void deactivateSubscription(Long userId) throws SubscriptionException {
        List<Subscription> expiredSubscriptions = subscriptionRepository
                .findExpiredActiveSubscriptions(LocalDate.now());
        for(Subscription subscription : expiredSubscriptions) {
            subscription.setIsActive(false);
            subscriptionRepository.save(subscription);
        }
    }
}
