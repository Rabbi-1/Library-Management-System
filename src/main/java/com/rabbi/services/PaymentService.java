package com.rabbi.services;

import com.rabbi.payload.dto.PaymentDTO;
import com.rabbi.payload.request.PaymentInitiateRequest;
import com.rabbi.payload.request.PaymentVerifyRequest;
import com.rabbi.payload.response.PaymentInitiateResponse;

public interface PaymentService {

    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest req);

    PaymentDTO verifyPayment(PaymentVerifyRequest req);


}
