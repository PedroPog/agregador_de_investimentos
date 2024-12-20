package com.codehive.agregador.service;

import com.codehive.agregador.client.BrapiClient;
import com.codehive.agregador.client.dto.StockDto;
import com.codehive.agregador.controller.dto.AccountResponseDto;
import com.codehive.agregador.controller.dto.AccountStockResponseDto;
import com.codehive.agregador.controller.dto.AssociateAccountStockDto;
import com.codehive.agregador.entity.AccountStock;
import com.codehive.agregador.entity.AccountStockId;
import com.codehive.agregador.repository.AccountRepository;
import com.codehive.agregador.repository.AccountStockRepository;
import com.codehive.agregador.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private AccountStockRepository accountStockRepository;

    @Autowired
    private BrapiClient brapiClient;

    @Value("#{environment.TOKEN}")
    private String TOKEN;


    public void associateStock(String accountId, AssociateAccountStockDto dto) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        var stock = stockRepository.findById(dto.getStockId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new AccountStockId(account.getAccountId(),stock.getStockId());
        var entity = new AccountStock(
                id,
                account,
                stock,
                dto.getQuantity()
        );

        accountStockRepository.save(entity);
    }

    public List<AccountStockResponseDto> listStocks(String accountId) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStocks()
                .stream()
                .map(as ->{
                    var stockDto = getTotal(as.getQuantity(),as.getStock().getStockId());
                    return new AccountStockResponseDto(
                            as.getStock().getStockId(),
                            as.getQuantity(),
                            stockDto.getRegularMarketPrice(),
                            stockDto.getLogourl()
                    );
                })
                .toList();
                /*.map(as -> new AccountStockResponseDto(
                        as.getStock().getStockId(),
                        as.getQuantity(),
                        getTotal(as.getQuantity(),as.getStock().getStockId()).regularMarketPrice(),
                        getTotal(as.getQuantity(),as.getStock().getStockId()).logourl()
                ))
                .toList();*/
    }

    private StockDto getTotal(int quantity, String stockId) {
        var response = brapiClient.getQuote(TOKEN,stockId);
        var price = response.getResults().get(0).getRegularMarketPrice();
        return new StockDto(
                quantity * price,
                response.getResults().get(0).getLogourl()
        );
    }
}
