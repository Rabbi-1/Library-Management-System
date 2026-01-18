package com.rabbi.services;

import com.rabbi.payload.dto.BookLoanDTO;
import com.rabbi.payload.request.CheckoutRequest;

public interface BookLoanService {

    BookLoanDTO checkoutBok(CheckoutRequest checkoutRequest);
}
