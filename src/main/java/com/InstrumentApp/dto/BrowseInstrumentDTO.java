package com.InstrumentApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseInstrumentDTO {

	private Integer ownerId;

	private Integer instrumentId;

	private Integer instrumentStatus;
	
	private Integer nextOrPrevious;
}

