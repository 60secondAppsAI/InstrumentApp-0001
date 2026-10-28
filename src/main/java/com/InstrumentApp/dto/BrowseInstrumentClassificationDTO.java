package com.InstrumentApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseInstrumentClassificationDTO {

	private Integer ownerId;

	private Integer instrumentClassificationId;

	private Integer instrumentClassificationStatus;
	
	private Integer nextOrPrevious;
}

