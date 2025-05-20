package com.codeWithProjects.ecom.dto;

import lombok.Data;

@Data
public class AnalyticsResponseDTO {

public AnalyticsResponseDTO(Long placed, Long shipped, Long delivered, Long currentMonthOrders, Long previousMonthOrders, Long currentMonthEarnings, Long previousMonthEarnings) {
        this.placed = placed;
        this.shipped = shipped;
        this.delivered = delivered;
        this.currentMonthOrders = currentMonthOrders;
        this.previousMonthOrders = previousMonthOrders;
        this.currentMonthEarnings = currentMonthEarnings;
        this.previousMonthEarnings = previousMonthEarnings;
    }

    private Long placed;

    private Long shipped;

    private Long delivered;

    private Long currentMonthOrders;

    private Long previousMonthOrders;

    private Long currentMonthEarnings;

    private Long previousMonthEarnings;

}
