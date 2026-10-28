package com.InstrumentApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InstrumentSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer instrumentId;
	
	private String symbol;
	
	private String name;
	
	private String instrumentType;
	
	private String currency;
	
	private String status;
	
}
