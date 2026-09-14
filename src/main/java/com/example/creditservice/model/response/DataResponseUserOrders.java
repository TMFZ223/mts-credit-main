package com.example.creditservice.model.response;

import com.example.creditservice.model.order.LoanOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DataResponseUserOrders {
    private String firstname;
    private List<LoanOrder> orders;
}
