package com.rabbi.services.impl;

import com.rabbi.payload.dto.SubscriptionPlanDTO;
import com.rabbi.services.SubscriptionPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {
    @Override
    public SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO) {
        return null;
    }

    @Override
    public SubscriptionPlanDTO updateSubscriptionPlan(Long planId, SubscriptionPlanDTO planDTO) {
        return null;
    }

    @Override
    public void deleteSubscriptionPlan(Long planId) {

    }

    @Override
    public List<SubscriptionPlanDTO> getAllSubscriptionPlans() {
        return List.of();
    }
}
