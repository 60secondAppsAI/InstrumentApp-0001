package com.InstrumentApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.InstrumentApp.domain.InstrumentAttribute;
import com.InstrumentApp.dto.InstrumentAttributeDTO;
import com.InstrumentApp.dto.InstrumentAttributeSearchDTO;
import com.InstrumentApp.dto.InstrumentAttributePageDTO;
import com.InstrumentApp.dto.InstrumentAttributeConvertCriteriaDTO;
import com.InstrumentApp.service.GenericService;
import com.InstrumentApp.dto.common.RequestDTO;
import com.InstrumentApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface InstrumentAttributeService extends GenericService<InstrumentAttribute, Integer> {

	List<InstrumentAttribute> findAll();

	ResultDTO addInstrumentAttribute(InstrumentAttributeDTO instrumentAttributeDTO, RequestDTO requestDTO);

	ResultDTO updateInstrumentAttribute(InstrumentAttributeDTO instrumentAttributeDTO, RequestDTO requestDTO);

    Page<InstrumentAttribute> getAllInstrumentAttributes(Pageable pageable);

    Page<InstrumentAttribute> getAllInstrumentAttributes(Specification<InstrumentAttribute> spec, Pageable pageable);

	ResponseEntity<InstrumentAttributePageDTO> getInstrumentAttributes(InstrumentAttributeSearchDTO instrumentAttributeSearchDTO);
	
	List<InstrumentAttributeDTO> convertInstrumentAttributesToInstrumentAttributeDTOs(List<InstrumentAttribute> instrumentAttributes, InstrumentAttributeConvertCriteriaDTO convertCriteria);

	InstrumentAttributeDTO getInstrumentAttributeDTOById(Integer instrumentAttributeId);



	
}
