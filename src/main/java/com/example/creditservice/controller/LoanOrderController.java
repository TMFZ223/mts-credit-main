package com.example.creditservice.controller;

import com.example.creditservice.model.request.CreateOrder;
import com.example.creditservice.model.request.DeleteOrder;
import com.example.creditservice.model.request.TariffDTO;
import com.example.creditservice.model.response.*;
import com.example.creditservice.service.LoanOrderService;
import com.example.creditservice.service.TariffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/loan-service")
@RequiredArgsConstructor
@Slf4j
public class LoanOrderController {
    private final TariffService tariffService;
    private final LoanOrderService loanOrderService;

    @PostMapping("/addTariff")
    public ResponseEntity<Integer> addTariff(@Valid @RequestBody TariffDTO tariffDTO) {
        return ResponseEntity.ok(tariffService.save(tariffDTO));
    }

    @GetMapping("/getTariffs")
    public ResponseEntity<DataResponse> getTariffs() {
        return ResponseEntity.ok(
                new DataResponse(
                        new DataResponseTariff(tariffService.getTariffs()))
        );
    }

    @PostMapping("/order")
    public ResponseEntity<DataResponse> addOrder(@RequestBody CreateOrder order) {
        return ResponseEntity.ok(
                new DataResponse(
                        new DataResponseLoanOrder(loanOrderService.save(order)))
        );
    }

    @GetMapping("/getStatusOrder")
    public ResponseEntity<DataResponse> getStatusOrder(@RequestParam UUID orderId) {
        return ResponseEntity.ok(
                new DataResponse(
                        new DataResponseStatus(loanOrderService.getStatusByOrderId(orderId)))
        );
    }

    @DeleteMapping("/deleteOrder")
    public void deleteOrder(@RequestBody DeleteOrder order) {
        loanOrderService.deleteByOrderIdAndUserId(order.getUserId(), order.getOrderId());
    }

    @DeleteMapping("/deleteTariff")
    public void deleteTariff(@RequestParam long id) {
        tariffService.deleteById(id);
    }

    @GetMapping("/getOrdersByUserId")
    public ResponseEntity<DataResponse> getOrdersByUserId(@RequestParam long userId) {
        var result = loanOrderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(
                new DataResponse(
                        new DataResponseUserOrders(result.firstname(), result.orders()))
        );
    }
}
