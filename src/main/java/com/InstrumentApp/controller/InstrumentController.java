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

import com.InstrumentApp.domain.Instrument;
import com.InstrumentApp.dto.InstrumentDTO;
import com.InstrumentApp.dto.InstrumentSearchDTO;
import com.InstrumentApp.dto.InstrumentPageDTO;
import com.InstrumentApp.service.InstrumentService;
import com.InstrumentApp.dto.common.RequestDTO;
import com.InstrumentApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/instrument")
@RestController
public class InstrumentController {

	private final static Logger logger = LoggerFactory.getLogger(InstrumentController.class);

	@Autowired
	InstrumentService instrumentService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Instrument> getAll() {

		List<Instrument> instruments = instrumentService.findAll();
		
		return instruments;	
	}

	@GetMapping(value = "/{instrumentId}")
	@ResponseBody
	public InstrumentDTO getInstrument(@PathVariable Integer instrumentId) {
		
		return (instrumentService.getInstrumentDTOById(instrumentId));
	}

 	@RequestMapping(value = "/addInstrument", method = RequestMethod.POST)
	public ResponseEntity<?> addInstrument(@RequestBody InstrumentDTO instrumentDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = instrumentService.addInstrument(instrumentDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/instruments")
	public ResponseEntity<InstrumentPageDTO> getInstruments(InstrumentSearchDTO instrumentSearchDTO) {
 
		return instrumentService.getInstruments(instrumentSearchDTO);
	}	

	@RequestMapping(value = "/updateInstrument", method = RequestMethod.POST)
	public ResponseEntity<?> updateInstrument(@RequestBody InstrumentDTO instrumentDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = instrumentService.updateInstrument(instrumentDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
