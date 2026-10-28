package com.InstrumentApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.InstrumentApp.domain.InstrumentClassification;
import com.InstrumentApp.dto.InstrumentClassificationDTO;
import com.InstrumentApp.dto.InstrumentClassificationSearchDTO;
import com.InstrumentApp.dto.InstrumentClassificationPageDTO;
import com.InstrumentApp.dto.InstrumentClassificationConvertCriteriaDTO;
import com.InstrumentApp.service.GenericService;
import com.InstrumentApp.dto.common.RequestDTO;
import com.InstrumentApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface InstrumentClassificationService extends GenericService<InstrumentClassification, Integer> {

	List<InstrumentClassification> findAll();

	ResultDTO addInstrumentClassification(InstrumentClassificationDTO instrumentClassificationDTO, RequestDTO requestDTO);

	ResultDTO updateInstrumentClassification(InstrumentClassificationDTO instrumentClassificationDTO, RequestDTO requestDTO);

    Page<InstrumentClassification> getAllInstrumentClassifications(Pageable pageable);

    Page<InstrumentClassification> getAllInstrumentClassifications(Specification<InstrumentClassification> spec, Pageable pageable);

	ResponseEntity<InstrumentClassificationPageDTO> getInstrumentClassifications(InstrumentClassificationSearchDTO instrumentClassificationSearchDTO);
	
	List<InstrumentClassificationDTO> convertInstrumentClassificationsToInstrumentClassificationDTOs(List<InstrumentClassification> instrumentClassifications, InstrumentClassificationConvertCriteriaDTO convertCriteria);

	InstrumentClassificationDTO getInstrumentClassificationDTOById(Integer instrumentClassificationId);



	
}
