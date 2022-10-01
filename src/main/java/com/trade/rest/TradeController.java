package com.trade.rest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trade.domain.Currency;
import com.trade.domain.CurrencyRepository;
import com.trade.dto.Root;


@CrossOrigin(origins = "*")
@RestController
@lombok.extern.slf4j.Slf4j
public class TradeController {
	

	@Autowired 
	private CurrencyRepository currRepository;
	
	
	
	@GetMapping(value="tradeRequest/{fromSymbol}/{toSymbol}")
	public Root getTrades(@PathVariable String fromSymbol,@PathVariable String toSymbol) {
		//restClient.getTrades();
		System.out.println("****** Inside Trading App *******");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("lang", "en");
		params.put("region", "US");
		//ResponseEntity<String> data = restTemplate().exchange("https://www.alphavantage.co/query?function=FX_INTRADAY&from_symbol="+fromSymbol+"&to_symbol="+toSymbol+"&interval=60min&apikey=9YJ6C63O6I7TXBKR", HttpMethod.GET, entity, String.class);
		
		//Root root = jsonParser(data.getBody()); //jsonConverter();
		
		List<Currency> currencies = currRepository.findByFromSymbolAndToSymbol(fromSymbol,toSymbol);
		log.info("currency size for fromsymbol : {} is : {}",fromSymbol, currencies.size());
		Root root = new Root();
		root.setCurrency(currencies);
		return root;
		
	}
	
	private Root jsonConverter() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Root root = mapper.readValue(new File((SystemUtils.getUserDir().getAbsolutePath() + "/src/main/resources/templates/Sample1.json")), Root.class);
            
            System.out.println(root.toString());
            return root;
        } catch (IOException e) {
        	System.out.println(e.getMessage());
        }
		return null;
	}

}
