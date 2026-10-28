package com.InstrumentApp.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.InstrumentApp.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.InstrumentApp.domain.InstrumentIdentifier;
import com.InstrumentApp.dto.InstrumentIdentifierDTO;
import com.InstrumentApp.dto.InstrumentIdentifierSearchDTO;
import com.InstrumentApp.dto.InstrumentIdentifierPageDTO;
import com.InstrumentApp.service.InstrumentIdentifierService;
import com.InstrumentApp.dto.common.RequestDTO;
import com.InstrumentApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/instrumentIdentifier")
@RestController
public class InstrumentIdentifierController {

	private final static Logger logger = LoggerFactory.getLogger(InstrumentIdentifierController.class);

	@Autowired
	InstrumentIdentifierService instrumentIdentifierService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<InstrumentIdentifier> getAll() {

		List<InstrumentIdentifier> instrumentIdentifiers = instrumentIdentifierService.findAll();
		
		return instrumentIdentifiers;	
	}

	@GetMapping(value = "/{instrumentIdentifierId}")
	@ResponseBody
	public InstrumentIdentifierDTO getInstrumentIdentifier(@PathVariable Integer instrumentIdentifierId) {
		
		return (instrumentIdentifierService.getInstrumentIdentifierDTOById(instrumentIdentifierId));
	}

 	@RequestMapping(value = "/addInstrumentIdentifier", method = RequestMethod.POST)
	public ResponseEntity<?> addInstrumentIdentifier(@RequestBody InstrumentIdentifierDTO instrumentIdentifierDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = instrumentIdentifierService.addInstrumentIdentifier(instrumentIdentifierDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/instrumentIdentifiers")
	public ResponseEntity<InstrumentIdentifierPageDTO> getInstrumentIdentifiers(InstrumentIdentifierSearchDTO instrumentIdentifierSearchDTO) {
 
		return instrumentIdentifierService.getInstrumentIdentifiers(instrumentIdentifierSearchDTO);
	}	

	@RequestMapping(value = "/updateInstrumentIdentifier", method = RequestMethod.POST)
	public ResponseEntity<?> updateInstrumentIdentifier(@RequestBody InstrumentIdentifierDTO instrumentIdentifierDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = instrumentIdentifierService.updateInstrumentIdentifier(instrumentIdentifierDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
