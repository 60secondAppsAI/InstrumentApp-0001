package com.InstrumentApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InstrumentAttributeSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer instrumentAttributeId;
	
	private Integer instrumentId;
	
	private String attributeName;
	
	private String attributeValue;
	
}
