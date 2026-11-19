package com.InstrumentApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseInstrumentAttributeDTO {

	private Integer ownerId;

	private Integer instrumentAttributeId;

	private Integer instrumentAttributeStatus;
	
	private Integer nextOrPrevious;
}

