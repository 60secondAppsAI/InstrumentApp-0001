package com.InstrumentApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InstrumentClassificationSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer instrumentClassificationId;
	
	private Integer instrumentId;
	
	private String sector;
	
	private String industry;
	
	private String assetClass;
	
}
