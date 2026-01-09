package com.rabbi.services;


import com.rabbi.model.SubscriptionPlan;
import com.rabbi.payload.dto.SubscriptionPlanDTO;

import java.util.List;

public interface SubscriptionPlanService {

    SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO);

    SubscriptionPlanDTO updateSubscriptionPlan(Long planId, SubscriptionPlanDTO planDTO);

    void deleteSubscriptionPlan(Long planId);

    List<SubscriptionPlanDTO> getAllSubscriptionPlans();


}
