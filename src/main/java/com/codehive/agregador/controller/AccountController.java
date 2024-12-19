package com.codehive.agregador.controller;

import com.codehive.agregador.controller.dto.AccountStockResponseDto;
import com.codehive.agregador.controller.dto.AssociateAccountStockDto;
import com.codehive.agregador.controller.dto.CreateAccountDto;
import com.codehive.agregador.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @PostMapping("/{accountId}/stock")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
                                              @RequestBody AssociateAccountStockDto dto) {

        accountService.associateStock(accountId, dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stock")
    public ResponseEntity<List<AccountStockResponseDto>> associateStock(@PathVariable("accountId") String accountId) {

        var stocks = accountService.listStocks(accountId);

        return ResponseEntity.ok(stocks);
    }


}
