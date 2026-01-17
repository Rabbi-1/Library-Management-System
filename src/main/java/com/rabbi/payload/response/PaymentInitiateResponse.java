package com.rabbi.payload.response;

import com.rabbi.domain.PaymentGateway;

public class PaymentInitiateResponse {

    private Long paymentId;
    private PaymentGateway gateway;
    private String transactionId;
    private String razorpayOrderId;
    private Long amount;
    private String description;
    private String checkoutUrl;
    private String message;
    private Boolean success;

}
