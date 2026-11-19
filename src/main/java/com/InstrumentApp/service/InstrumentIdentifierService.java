package com.InstrumentApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.InstrumentApp.domain.InstrumentIdentifier;
import com.InstrumentApp.dto.InstrumentIdentifierDTO;
import com.InstrumentApp.dto.InstrumentIdentifierSearchDTO;
import com.InstrumentApp.dto.InstrumentIdentifierPageDTO;
import com.InstrumentApp.dto.InstrumentIdentifierConvertCriteriaDTO;
import com.InstrumentApp.service.GenericService;
import com.InstrumentApp.dto.common.RequestDTO;
import com.InstrumentApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface InstrumentIdentifierService extends GenericService<InstrumentIdentifier, Integer> {

	List<InstrumentIdentifier> findAll();

	ResultDTO addInstrumentIdentifier(InstrumentIdentifierDTO instrumentIdentifierDTO, RequestDTO requestDTO);

	ResultDTO updateInstrumentIdentifier(InstrumentIdentifierDTO instrumentIdentifierDTO, RequestDTO requestDTO);

    Page<InstrumentIdentifier> getAllInstrumentIdentifiers(Pageable pageable);

    Page<InstrumentIdentifier> getAllInstrumentIdentifiers(Specification<InstrumentIdentifier> spec, Pageable pageable);

	ResponseEntity<InstrumentIdentifierPageDTO> getInstrumentIdentifiers(InstrumentIdentifierSearchDTO instrumentIdentifierSearchDTO);
	
	List<InstrumentIdentifierDTO> convertInstrumentIdentifiersToInstrumentIdentifierDTOs(List<InstrumentIdentifier> instrumentIdentifiers, InstrumentIdentifierConvertCriteriaDTO convertCriteria);

	InstrumentIdentifierDTO getInstrumentIdentifierDTOById(Integer instrumentIdentifierId);



	
}
