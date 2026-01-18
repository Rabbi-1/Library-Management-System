package com.rabbi.services;

import com.rabbi.payload.dto.PaymentDTO;
import com.rabbi.payload.request.PaymentInitiateRequest;
import com.rabbi.payload.request.PaymentVerifyRequest;
import com.rabbi.payload.response.PaymentInitiateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest req) throws Exception;

    PaymentDTO verifyPayment(PaymentVerifyRequest req);

    Page<PaymentDTO> getAllPayments(Pageable pageable);


}
