package com.rabbi.services.impl;

import com.rabbi.payload.dto.PaymentDTO;
import com.rabbi.payload.request.PaymentInitiateRequest;
import com.rabbi.payload.request.PaymentVerifyRequest;
import com.rabbi.payload.response.PaymentInitiateResponse;
import com.rabbi.repo.PaymentRepository;
import com.rabbi.repo.SubscriptionRepository;
import com.rabbi.repo.UserRespository;
import com.rabbi.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final UserRespository userRespository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest req) throws Exception {
        return null;
    }

    @Override
    public PaymentDTO verifyPayment(PaymentVerifyRequest req) {
        return null;
    }

    @Override
    public Page<PaymentDTO> getAllPayments(Pageable pageable) {
        return null;
    }
}
