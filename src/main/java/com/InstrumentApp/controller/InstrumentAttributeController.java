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

import com.InstrumentApp.domain.InstrumentAttribute;
import com.InstrumentApp.dto.InstrumentAttributeDTO;
import com.InstrumentApp.dto.InstrumentAttributeSearchDTO;
import com.InstrumentApp.dto.InstrumentAttributePageDTO;
import com.InstrumentApp.service.InstrumentAttributeService;
import com.InstrumentApp.dto.common.RequestDTO;
import com.InstrumentApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/instrumentAttribute")
@RestController
public class InstrumentAttributeController {

	private final static Logger logger = LoggerFactory.getLogger(InstrumentAttributeController.class);

	@Autowired
	InstrumentAttributeService instrumentAttributeService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<InstrumentAttribute> getAll() {

		List<InstrumentAttribute> instrumentAttributes = instrumentAttributeService.findAll();
		
		return instrumentAttributes;	
	}

	@GetMapping(value = "/{instrumentAttributeId}")
	@ResponseBody
	public InstrumentAttributeDTO getInstrumentAttribute(@PathVariable Integer instrumentAttributeId) {
		
		return (instrumentAttributeService.getInstrumentAttributeDTOById(instrumentAttributeId));
	}

 	@RequestMapping(value = "/addInstrumentAttribute", method = RequestMethod.POST)
	public ResponseEntity<?> addInstrumentAttribute(@RequestBody InstrumentAttributeDTO instrumentAttributeDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = instrumentAttributeService.addInstrumentAttribute(instrumentAttributeDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/instrumentAttributes")
	public ResponseEntity<InstrumentAttributePageDTO> getInstrumentAttributes(InstrumentAttributeSearchDTO instrumentAttributeSearchDTO) {
 
		return instrumentAttributeService.getInstrumentAttributes(instrumentAttributeSearchDTO);
	}	

	@RequestMapping(value = "/updateInstrumentAttribute", method = RequestMethod.POST)
	public ResponseEntity<?> updateInstrumentAttribute(@RequestBody InstrumentAttributeDTO instrumentAttributeDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = instrumentAttributeService.updateInstrumentAttribute(instrumentAttributeDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
