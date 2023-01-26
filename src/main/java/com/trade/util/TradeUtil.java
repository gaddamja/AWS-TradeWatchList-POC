package com.trade.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.trade.domain.Currency;
import com.trade.domain.Stocks;

@Component
@lombok.extern.slf4j.Slf4j
public class TradeUtil {
	
	@Value("${stock_start_date}")
	private String startDate;
	
	@Value("${stock_end_date}")
	private String endDate;
	
	public List<Currency> currencyParser(String data) {
			
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
        
	        return currencies;
    	} catch (Exception e) {
    		log.error("Exception while data conversion and persisting to DB : {} ",e.getMessage());
    	}
    	
    	return Collections.emptyList();
    }
	
	
	public List<Currency> xchangeRateParser(String data) {
		
    	try {
	        List<Currency> currencies = new ArrayList<Currency>();
			JsonParser jsonParser = new JsonParser();
	        JsonObject jsonObject = (JsonObject) jsonParser.parse(data);
	        JsonElement currExchgRate = jsonObject.get("Realtime Currency Exchange Rate");
            Currency curr = new Currency();
            curr.setPriceDate(currExchgRate.getAsJsonObject().get("6. Last Refreshed").getAsString());
            curr.setFromSymbol(currExchgRate.getAsJsonObject().get("1. From_Currency Code").getAsString());
            curr.setToSymbol(currExchgRate.getAsJsonObject().get("3. To_Currency Code").getAsString());
            curr.setHigh(currExchgRate.getAsJsonObject().get("9. Ask Price").getAsString());
            curr.setLow(currExchgRate.getAsJsonObject().get("8. Bid Price").getAsString());
            curr.setLast(currExchgRate.getAsJsonObject().get("5. Exchange Rate").getAsString());
            curr.setChangePercent("0.0");
            curr.setChnge("0.0");
            currencies.add(curr);
            //log.info("currency size for fromsymbol : {} is : {}",fromSymbol, currencies.size());
	        return currencies;
    	} catch (Exception e) {
    		log.error("Exception while data conversion and persisting to DB : {} ",e.getMessage());
    	}
    	
    	return Collections.emptyList();
    }
	
	public List<Stocks> stocksParser(String data, String date, String stocksTickers) {
		
    	try {
	        List<Stocks> stocks = new ArrayList<Stocks>();
	        List<String> stocksIndexes = Arrays.asList(stocksTickers.split("\\|"));
			JsonParser jsonParser = new JsonParser();
	        JsonObject jsonObject = (JsonObject) jsonParser.parse(data);
	        JsonElement results = jsonObject.get("results");
	        //String index = metadata.get("2. From Symbol").getAsString();
	        JsonArray resultArr = results.getAsJsonArray();
    	    resultArr.forEach(entry -> {
	            Stocks stock = new Stocks();
	            if(entry.isJsonObject() && entry.getAsJsonObject() != null && entry.getAsJsonObject().get("T") != null && stocksIndexes.contains(entry.getAsJsonObject().get("T").getAsString().replace("\"", ""))) {
		            stock.setIndex(entry.getAsJsonObject().get("T").getAsString());
		            stock.setLastPrice(entry.getAsJsonObject().get("c").getAsString());
		            stock.setWeightedAvg(entry.getAsJsonObject().get("vw").getAsString());
		            stock.setPriceDate(date);
		            stocks.add(stock);
	            }
	        });
        
	        return stocks;
    	} catch (Exception e) {
    		log.error("Exception while data conversion and persisting to DB : {} ",e.getMessage());
    	}
    	
    	return Collections.emptyList();
    }
	
	public List<LocalDate> getFridays() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate start = LocalDate.parse ( startDate , formatter );
		LocalDate stop = LocalDate.parse ( endDate , formatter );
		
		List<LocalDate> fridays = new ArrayList<> ();  
		// Collect each Friday found.
		LocalDate nextOrSameFriday = start.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY ) );
		while ( ( null != nextOrSameFriday ) & (  ! nextOrSameFriday.isAfter ( stop ) ) ) 
		{
		    fridays.add ( nextOrSameFriday );  //  Remember this friday.
		    nextOrSameFriday = nextOrSameFriday.plusWeeks ( 1 );  // Move to the next Friday, setting up for next iteration of this loop.
		}
		return fridays;
	}

}
