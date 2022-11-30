package com.trade.rest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.trade.domain.CurrencyRepository;
import com.trade.domain.Stocks;
import com.trade.domain.StocksRepository;
import com.trade.dto.AllCurrency;
import com.trade.dto.AllStocks;
import com.trade.dto.Currency;


@CrossOrigin(origins = "*")
@RestController
@lombok.extern.slf4j.Slf4j
public class TradeController {
	

	@Autowired 
	private CurrencyRepository currRepository;
	
	@Autowired
	private StocksRepository stockRepository;
	
	
	
	
	@GetMapping(value="tradeRequest/currencies", produces = "application/json")
	public List<Currency> getCurrencies() {
		//restClient.getTrades();
		log.info("****** Inside Trading App *******");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("lang", "en");
		params.put("region", "US");
		//ResponseEntity<String> data = restTemplate().exchange("https://www.alphavantage.co/query?function=FX_INTRADAY&from_symbol="+fromSymbol+"&to_symbol="+toSymbol+"&interval=60min&apikey=9YJ6C63O6I7TXBKR", HttpMethod.GET, entity, String.class);
		
		//Root root = jsonParser(data.getBody()); //jsonConverter();
		
		
		//AllCurrency allCurrency = new AllCurrency();
		List<Currency> currencyList = new ArrayList<Currency>();
		currRepository.findAll().forEach(c -> {
			Currency curr = new Currency();
			curr.setIndex(c.getFromSymbol());
			curr.setLast(c.getLast());
			curr.setPriceDate(c.getPriceDate());
			currencyList.add(curr);
		});
		//allCurrency.setCurrency(currencyList);
		return currencyList;
		
	}
	
	@GetMapping(value="tradeRequest/allStocks", produces = "application/json")
	public List<Stocks> getStocks() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("lang", "en");
		params.put("region", "US");
		//ResponseEntity<String> data = restTemplate().exchange("https://www.alphavantage.co/query?function=FX_INTRADAY&from_symbol="+fromSymbol+"&to_symbol="+toSymbol+"&interval=60min&apikey=9YJ6C63O6I7TXBKR", HttpMethod.GET, entity, String.class);
		
		//Root root = jsonParser(data.getBody()); //jsonConverter();
		
		List<Stocks> stocks = stockRepository.findAll();
		log.info("stocks size is {}", stocks.size());
		AllStocks allStocks = new AllStocks();
		allStocks.setStocks(stocks);
		log.info("allStocks :: ",allStocks);
		return stocks;
		
	}
	
	
	
	
	/*private Root jsonConverter() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Root root = mapper.readValue(new File((SystemUtils.getUserDir().getAbsolutePath() + "/src/main/resources/templates/Sample1.json")), Root.class);
            
            System.out.println(root.toString());
            return root;
        } catch (IOException e) {
        	System.out.println(e.getMessage());
        }
		return null;
	}*/

}
