package com.trade.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trade.domain.Currency;
import com.trade.domain.CurrencyRepository;

@Component
@lombok.extern.slf4j.Slf4j
public class TradeRestClient {
	 
	@Value("${api.host.baseurl}")
	private String apiHost;
	
	@Value("${currency.toconvert}")
	private String currencyToConvert;
	
	@Autowired 
	private CurrencyRepository currRepository;
	
	
	@Scheduled(cron = "0 0 7 * * ?",zone = "Europe/London")
	//@Scheduled(fixedRate = 1000)
	public void getTrades() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("lang", "en");
		params.put("region", "US");
		 
		//System.out.println(restTemplate().exchange("https://yfapi.net/v6/finance/quote/marketSummary", HttpMethod.GET, entity, String.class, params));;
		
		
		
		String[] currConvertList = currencyToConvert.split("\\|");
		Arrays.asList(currConvertList).stream().forEach(a -> {
			log.info("currency converter for currency combi : {}",a);
			String[] currencies = a.split("\\-");
			log.info("currency converter for currency : {} {}",currencies[0],currencies[1]);
			ResponseEntity<String> data = restTemplate().exchange(apiHost+"?function=FX_INTRADAY&from_symbol="+currencies[0]+"&to_symbol="+currencies[1]+"&interval=60min&apikey=9YJ6C63O6I7TXBKR", HttpMethod.GET, entity, String.class);
			jsonParser(data.getBody()); 
		});
		
	}
	
	@Bean
	public RestTemplate restTemplate() {
	 
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(3000);
		factory.setReadTimeout(3000);
		return new RestTemplate(factory);
	}
	 
	private void jsonParser(String data) {
		
    	try {
	        List<Currency> currencies = new ArrayList<Currency>();
			JsonParser jsonParser = new JsonParser();
	        JsonObject jsonObject = (JsonObject) jsonParser.parse(data);
	        JsonElement metadata = jsonObject.get("Meta Data");
	        //String index = metadata.get("2. From Symbol").getAsString();
	        String fromSymbol = metadata.getAsJsonObject().get("2. From Symbol").toString();
	        String toSymbol = metadata.getAsJsonObject().get("3. To Symbol").toString();
	        JsonElement timeSeriesData = jsonObject.get("Time Series FX (60min)");
	        Set<Map.Entry<String, JsonElement>> entrySet = timeSeriesData.getAsJsonObject().entrySet();
	        entrySet.parallelStream().forEach(entry -> {
	            Currency curr = new Currency();
	            curr.setPriceDate(entry.getKey());
	            curr.setFromSymbol(fromSymbol.replace("\"", ""));
	            curr.setToSymbol(toSymbol.replace("\"", ""));
	            curr.setHigh(entry.getValue().getAsJsonObject().get("2. high").getAsString());
	            curr.setLow(entry.getValue().getAsJsonObject().get("3. low").getAsString());
	            curr.setLast(entry.getValue().getAsJsonObject().get("4. close").getAsString());
	            curr.setChangePercent("0.0");
	            curr.setChnge("0.0");
	            currencies.add(curr);
	            //log.info("currency size for fromsymbol : {} is : {}",fromSymbol, currencies.size());
	        });
	        
	        currRepository.saveAll(currencies);
    	} catch (Exception e) {
    		log.error("Exception while data conversion and persisting to DB : {} ",e.getMessage());
    	}
	}
}
