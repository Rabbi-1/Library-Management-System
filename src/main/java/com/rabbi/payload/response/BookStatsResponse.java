package com.rabbi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookStatsResponse {
    private Long totalActiveBooks;
    private Long totalAvailableBooks;

}
