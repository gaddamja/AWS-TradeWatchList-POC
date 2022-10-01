package com.trade.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Currency {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="SERIALNUMBER", nullable = false, unique = true)
	public Integer serialNumber;
	
	@Column(name="FROMSYMBOL", nullable = true)
	@JsonProperty("FromSymbol") 
    public String fromSymbol;
	@Column(name="TOSYMBOL", nullable = true)
	@JsonProperty("ToSymbol") 
    public String toSymbol;
	@Column(name="LAST", nullable = true)
	@JsonProperty("Last") 
    public String last;
	@Column(name="HIGH", nullable = true)
	@JsonProperty("High") 
    public String high;
	@Column(name="LOW", nullable = true)
	@JsonProperty("Low") 
    public String low;
	@Column(name="CHNGE", nullable = true)
	@JsonProperty("Change") 
    public String chnge;
	@Column(name="CHANGEPERCENT", nullable = true)
	@JsonProperty("ChangePercent") 
    public String changePercent;
	@Column(name="PRICEDATE", nullable = true)
	@JsonProperty("Time") 
    public String priceDate;
    


	public Integer getSerialNumber() {
		return serialNumber;
	}



	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}



	public String getFromSymbol() {
		return fromSymbol;
	}



	public void setFromSymbol(String fromSymbol) {
		this.fromSymbol = fromSymbol;
	}



	public String getToSymbol() {
		return toSymbol;
	}



	public void setToSymbol(String toSymbol) {
		this.toSymbol = toSymbol;
	}



	public String getLast() {
		return last;
	}



	public void setLast(String last) {
		this.last = last;
	}



	public String getHigh() {
		return high;
	}



	public void setHigh(String high) {
		this.high = high;
	}



	public String getLow() {
		return low;
	}



	public void setLow(String low) {
		this.low = low;
	}



	public String getChnge() {
		return chnge;
	}



	public void setChnge(String chnge) {
		this.chnge = chnge;
	}



	public String getChangePercent() {
		return changePercent;
	}



	public void setChangePercent(String changePercent) {
		this.changePercent = changePercent;
	}



	public String getPriceDate() {
		return priceDate;
	}



	public void setPriceDate(String priceDate) {
		this.priceDate = priceDate;
	}



	@Override
	public String toString() {
		return "Stock [fromSymbol=" + fromSymbol + ", toSymbol=" + toSymbol + ", last=" + last + ", high=" + high + ", low=" + low
				+ ", change=" + chnge + ", changePercent=" + changePercent + ", time=" + priceDate + "]";
	}
	
	

}
