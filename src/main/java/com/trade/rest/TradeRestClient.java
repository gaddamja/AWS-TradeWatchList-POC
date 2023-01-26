package com.trade.rest;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trade.domain.Currency;
import com.trade.domain.CurrencyRepository;
import com.trade.domain.Stocks;
import com.trade.domain.StocksRepository;
import com.trade.dto.Stock;
import com.trade.util.TradeUtil;

@Component
@lombok.extern.slf4j.Slf4j
public class TradeRestClient {
	 
	@Value("${api.host.baseurl}")
	private String apiHost;
	
	@Value("${currency.toconvert}")
	private String currencyToConvert;
	
	@Value("${stocks.tickers}")
	private String stocksTickers;
	
	@Autowired 
	private CurrencyRepository currRepository;
	
	@Autowired 
	private StocksRepository stocksRepository;
	
	@Value("${stocks.api.baseurl}")
	private String stocksapiHost;
	
	@Value("${stocks.api.queryparam}")
	private String stocksapiQueryparam;
	
	@Inject
	private TradeUtil tradeUtil;
	
    @Scheduled(cron = "0 0 19 * * FRI",zone = "Europe/London")
	//@Scheduled(fixedRate = 5000)
	public void getTrades() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("lang", "en");
		params.put("region", "US");
		 
		/**
		 * Intraday Currency data consumer logic
		 */
		/*String[] currConvertList = currencyToConvert.split("\\|");
		Arrays.asList(currConvertList).stream().forEach(a -> {
			log.info("currency converter for currency combi : {}",a);
			String[] currencies = a.split("\\-");
			log.info("currency converter for currency : {} {}",currencies[0],currencies[1]);
			ResponseEntity<String> data = restTemplate().exchange(apiHost+"?function=FX_INTRADAY&from_symbol="+currencies[0]+"&to_symbol="+currencies[1]+"&interval=60min&apikey=9YJ6C63O6I7TXBKR", HttpMethod.GET, entity, String.class);
			 List<Currency> currencylist = tradeUtil.currencyParser(data.getBody());
			 currRepository.saveAll(currencylist);
		});*/
		
		/**
		 * Exchange rate - https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=EUR&to_currency=USD&apikey=GTL6YEQ9CZ95QT3V
		 */
		String[] currConvertList = currencyToConvert.split("\\|");
		Arrays.asList(currConvertList).stream().forEach(a -> {
			log.info("currency converter for currency combi : {}",a);
			String[] currencies = a.split("\\-");
			log.info("currency converter for currency : {} {}",currencies[0],currencies[1]);
			ResponseEntity<String> data = restTemplate().exchange(apiHost+"?function=CURRENCY_EXCHANGE_RATE&from_currency="+currencies[0]+"&to_currency="+currencies[1]+"&apikey=GTL6YEQ9CZ95QT3V", HttpMethod.GET, entity, String.class);
			 List<Currency> currencylist = tradeUtil.xchangeRateParser(data.getBody());
			 currRepository.saveAll(currencylist);
			 try {
				Thread.sleep(60000);
			 } catch(InterruptedException e) {
				log.error("Exception : ",e.getMessage());
			}
		});
	
		/**
		 * Stocks data consumer logic 
		 */
		
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date()); //ex 2022-11-04
		ResponseEntity<String> stocks = restTemplate().exchange(stocksapiHost+date+stocksapiQueryparam, HttpMethod.GET, entity, String.class);
		List<Stocks> stockList = tradeUtil.stocksParser(stocks.getBody(), date, stocksTickers);
		stocksRepository.saveAll(stockList);
		System.out.println("stocklist size : "+stockList.size());
		
	}
    
    @Scheduled(fixedRate = 5000)
	public void generateHistoricalStocks() {
    	HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("lang", "en");
		params.put("region", "US");
		/**
		 * Stocks data consumer logic 
		 */
		List<LocalDate> fridays = tradeUtil.getFridays();
		fridays.forEach(date -> {
			
			ResponseEntity<String> stocks = restTemplate().exchange(stocksapiHost+date.toString()+stocksapiQueryparam, HttpMethod.GET, entity, String.class);
			List<Stocks> stockList = tradeUtil.stocksParser(stocks.getBody(), date.toString(), stocksTickers);
			stocksRepository.saveAll(stockList);
			log.info("stocklist size : {}",stockList.size());
			try {
				Thread.sleep(60000);
			} catch(InterruptedException e) {
				log.error("Exception : ",e.getMessage());
			}
		});
		
		
	}
	
	
	@Bean
	public RestTemplate restTemplate() {
	 
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(3000);
		factory.setReadTimeout(3000);
		return new RestTemplate(factory);
	}
	 
	
}
