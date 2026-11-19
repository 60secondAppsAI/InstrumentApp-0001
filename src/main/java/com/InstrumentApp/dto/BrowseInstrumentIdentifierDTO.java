package com.InstrumentApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseInstrumentIdentifierDTO {

	private Integer ownerId;

	private Integer instrumentIdentifierId;

	private Integer instrumentIdentifierStatus;
	
	private Integer nextOrPrevious;
}

