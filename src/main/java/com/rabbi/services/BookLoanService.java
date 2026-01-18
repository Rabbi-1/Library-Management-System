package com.rabbi.services;

import com.rabbi.domain.BookLoanStatus;
import com.rabbi.payload.dto.BookLoanDTO;
import com.rabbi.payload.request.BookLoanSearchRequest;
import com.rabbi.payload.request.CheckinRequest;
import com.rabbi.payload.request.CheckoutRequest;
import com.rabbi.payload.request.RenewalRequest;
import com.rabbi.payload.response.PagesResponse;

public interface BookLoanService {

    BookLoanDTO checkoutBook(CheckoutRequest checkoutRequest);

    BookLoanDTO checkoutBookForUser(Long userId, CheckoutRequest checkoutRequest);

    BookLoanDTO checkinBook(CheckinRequest checkinRequest);

    BookLoanDTO renewCheckout(RenewalRequest renewalRequest);

    PagesResponse<BookLoanDTO> getMyBookLoans(BookLoanStatus status, int page, int size);

    PagesResponse<BookLoanDTO> getBookLoans(BookLoanSearchRequest request);

    int updateOverdueBookLoan();


}
