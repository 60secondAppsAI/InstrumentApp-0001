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

import com.InstrumentApp.domain.InstrumentClassification;
import com.InstrumentApp.dto.InstrumentClassificationDTO;
import com.InstrumentApp.dto.InstrumentClassificationSearchDTO;
import com.InstrumentApp.dto.InstrumentClassificationPageDTO;
import com.InstrumentApp.service.InstrumentClassificationService;
import com.InstrumentApp.dto.common.RequestDTO;
import com.InstrumentApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/instrumentClassification")
@RestController
public class InstrumentClassificationController {

	private final static Logger logger = LoggerFactory.getLogger(InstrumentClassificationController.class);

	@Autowired
	InstrumentClassificationService instrumentClassificationService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<InstrumentClassification> getAll() {

		List<InstrumentClassification> instrumentClassifications = instrumentClassificationService.findAll();
		
		return instrumentClassifications;	
	}

	@GetMapping(value = "/{instrumentClassificationId}")
	@ResponseBody
	public InstrumentClassificationDTO getInstrumentClassification(@PathVariable Integer instrumentClassificationId) {
		
		return (instrumentClassificationService.getInstrumentClassificationDTOById(instrumentClassificationId));
	}

 	@RequestMapping(value = "/addInstrumentClassification", method = RequestMethod.POST)
	public ResponseEntity<?> addInstrumentClassification(@RequestBody InstrumentClassificationDTO instrumentClassificationDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = instrumentClassificationService.addInstrumentClassification(instrumentClassificationDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/instrumentClassifications")
	public ResponseEntity<InstrumentClassificationPageDTO> getInstrumentClassifications(InstrumentClassificationSearchDTO instrumentClassificationSearchDTO) {
 
		return instrumentClassificationService.getInstrumentClassifications(instrumentClassificationSearchDTO);
	}	

	@RequestMapping(value = "/updateInstrumentClassification", method = RequestMethod.POST)
	public ResponseEntity<?> updateInstrumentClassification(@RequestBody InstrumentClassificationDTO instrumentClassificationDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = instrumentClassificationService.updateInstrumentClassification(instrumentClassificationDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
