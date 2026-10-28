package com.InstrumentApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.InstrumentApp.domain.Instrument;
import com.InstrumentApp.dto.InstrumentDTO;
import com.InstrumentApp.dto.InstrumentSearchDTO;
import com.InstrumentApp.dto.InstrumentPageDTO;
import com.InstrumentApp.dto.InstrumentConvertCriteriaDTO;
import com.InstrumentApp.service.GenericService;
import com.InstrumentApp.dto.common.RequestDTO;
import com.InstrumentApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface InstrumentService extends GenericService<Instrument, Integer> {

	List<Instrument> findAll();

	ResultDTO addInstrument(InstrumentDTO instrumentDTO, RequestDTO requestDTO);

	ResultDTO updateInstrument(InstrumentDTO instrumentDTO, RequestDTO requestDTO);

    Page<Instrument> getAllInstruments(Pageable pageable);

    Page<Instrument> getAllInstruments(Specification<Instrument> spec, Pageable pageable);

	ResponseEntity<InstrumentPageDTO> getInstruments(InstrumentSearchDTO instrumentSearchDTO);
	
	List<InstrumentDTO> convertInstrumentsToInstrumentDTOs(List<Instrument> instruments, InstrumentConvertCriteriaDTO convertCriteria);

	InstrumentDTO getInstrumentDTOById(Integer instrumentId);



	
}
