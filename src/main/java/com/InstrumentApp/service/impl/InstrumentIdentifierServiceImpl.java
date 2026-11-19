package com.InstrumentApp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.InstrumentApp.dao.GenericDAO;
import com.InstrumentApp.service.GenericService;
import com.InstrumentApp.service.impl.GenericServiceImpl;
import com.InstrumentApp.dao.InstrumentIdentifierDAO;
import com.InstrumentApp.domain.InstrumentIdentifier;
import com.InstrumentApp.dto.InstrumentIdentifierDTO;
import com.InstrumentApp.dto.InstrumentIdentifierSearchDTO;
import com.InstrumentApp.dto.InstrumentIdentifierPageDTO;
import com.InstrumentApp.dto.InstrumentIdentifierConvertCriteriaDTO;
import com.InstrumentApp.dto.common.RequestDTO;
import com.InstrumentApp.dto.common.ResultDTO;
import com.InstrumentApp.service.InstrumentIdentifierService;
import com.InstrumentApp.util.ControllerUtils;


@Service
public class InstrumentIdentifierServiceImpl extends GenericServiceImpl<InstrumentIdentifier, Integer> implements InstrumentIdentifierService {

    private final static Logger logger = LoggerFactory.getLogger(InstrumentIdentifierServiceImpl.class);

	@Autowired
	InstrumentIdentifierDAO instrumentIdentifierDao;

	

	@Override
	public GenericDAO<InstrumentIdentifier, Integer> getDAO() {
		return (GenericDAO<InstrumentIdentifier, Integer>) instrumentIdentifierDao;
	}
	
	public List<InstrumentIdentifier> findAll () {
		List<InstrumentIdentifier> instrumentIdentifiers = instrumentIdentifierDao.findAll();
		
		return instrumentIdentifiers;	
		
	}

	public ResultDTO addInstrumentIdentifier(InstrumentIdentifierDTO instrumentIdentifierDTO, RequestDTO requestDTO) {

		InstrumentIdentifier instrumentIdentifier = new InstrumentIdentifier();

		instrumentIdentifier.setInstrumentIdentifierId(instrumentIdentifierDTO.getInstrumentIdentifierId());

		instrumentIdentifier.setInstrumentId(instrumentIdentifierDTO.getInstrumentId());

		instrumentIdentifier.setIdentifierType(instrumentIdentifierDTO.getIdentifierType());

		instrumentIdentifier.setIdentifierValue(instrumentIdentifierDTO.getIdentifierValue());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		instrumentIdentifier = instrumentIdentifierDao.save(instrumentIdentifier);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<InstrumentIdentifier> getAllInstrumentIdentifiers(Pageable pageable) {
		return instrumentIdentifierDao.findAll(pageable);
	}

	public Page<InstrumentIdentifier> getAllInstrumentIdentifiers(Specification<InstrumentIdentifier> spec, Pageable pageable) {
		return instrumentIdentifierDao.findAll(spec, pageable);
	}

	public ResponseEntity<InstrumentIdentifierPageDTO> getInstrumentIdentifiers(InstrumentIdentifierSearchDTO instrumentIdentifierSearchDTO) {
	
			Integer instrumentIdentifierId = instrumentIdentifierSearchDTO.getInstrumentIdentifierId(); 
 			Integer instrumentId = instrumentIdentifierSearchDTO.getInstrumentId(); 
 			String identifierType = instrumentIdentifierSearchDTO.getIdentifierType(); 
 			String identifierValue = instrumentIdentifierSearchDTO.getIdentifierValue(); 
 			String sortBy = instrumentIdentifierSearchDTO.getSortBy();
			String sortOrder = instrumentIdentifierSearchDTO.getSortOrder();
			String searchQuery = instrumentIdentifierSearchDTO.getSearchQuery();
			Integer page = instrumentIdentifierSearchDTO.getPage();
			Integer size = instrumentIdentifierSearchDTO.getSize();

	        Specification<InstrumentIdentifier> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, instrumentIdentifierId, "instrumentIdentifierId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, instrumentId, "instrumentId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, identifierType, "identifierType"); 
			
			spec = ControllerUtils.andIfNecessary(spec, identifierValue, "identifierValue"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("identifierType")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("identifierValue")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<InstrumentIdentifier> instrumentIdentifiers = this.getAllInstrumentIdentifiers(spec, pageable);
		
		//System.out.println(String.valueOf(instrumentIdentifiers.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(instrumentIdentifiers.getTotalPages()));
		
		List<InstrumentIdentifier> instrumentIdentifiersList = instrumentIdentifiers.getContent();
		
		InstrumentIdentifierConvertCriteriaDTO convertCriteria = new InstrumentIdentifierConvertCriteriaDTO();
		List<InstrumentIdentifierDTO> instrumentIdentifierDTOs = this.convertInstrumentIdentifiersToInstrumentIdentifierDTOs(instrumentIdentifiersList,convertCriteria);
		
		InstrumentIdentifierPageDTO instrumentIdentifierPageDTO = new InstrumentIdentifierPageDTO();
		instrumentIdentifierPageDTO.setInstrumentIdentifiers(instrumentIdentifierDTOs);
		instrumentIdentifierPageDTO.setTotalElements(instrumentIdentifiers.getTotalElements());
		return ResponseEntity.ok(instrumentIdentifierPageDTO);
	}

	public List<InstrumentIdentifierDTO> convertInstrumentIdentifiersToInstrumentIdentifierDTOs(List<InstrumentIdentifier> instrumentIdentifiers, InstrumentIdentifierConvertCriteriaDTO convertCriteria) {
		
		List<InstrumentIdentifierDTO> instrumentIdentifierDTOs = new ArrayList<InstrumentIdentifierDTO>();
		
		for (InstrumentIdentifier instrumentIdentifier : instrumentIdentifiers) {
			instrumentIdentifierDTOs.add(convertInstrumentIdentifierToInstrumentIdentifierDTO(instrumentIdentifier,convertCriteria));
		}
		
		return instrumentIdentifierDTOs;

	}
	
	public InstrumentIdentifierDTO convertInstrumentIdentifierToInstrumentIdentifierDTO(InstrumentIdentifier instrumentIdentifier, InstrumentIdentifierConvertCriteriaDTO convertCriteria) {
		
		InstrumentIdentifierDTO instrumentIdentifierDTO = new InstrumentIdentifierDTO();

		instrumentIdentifierDTO.setInstrumentIdentifierId(instrumentIdentifier.getInstrumentIdentifierId());

		instrumentIdentifierDTO.setInstrumentId(instrumentIdentifier.getInstrumentId());

		instrumentIdentifierDTO.setIdentifierType(instrumentIdentifier.getIdentifierType());

		instrumentIdentifierDTO.setIdentifierValue(instrumentIdentifier.getIdentifierValue());
		
		return instrumentIdentifierDTO;
	}

	public ResultDTO updateInstrumentIdentifier(InstrumentIdentifierDTO instrumentIdentifierDTO, RequestDTO requestDTO) {
		
		InstrumentIdentifier instrumentIdentifier = instrumentIdentifierDao.getById(instrumentIdentifierDTO.getInstrumentIdentifierId());
		
		instrumentIdentifier.setInstrumentIdentifierId(ControllerUtils.setValue(instrumentIdentifier.getInstrumentIdentifierId(), instrumentIdentifierDTO.getInstrumentIdentifierId()));
		
		instrumentIdentifier.setInstrumentId(ControllerUtils.setValue(instrumentIdentifier.getInstrumentId(), instrumentIdentifierDTO.getInstrumentId()));
		
		instrumentIdentifier.setIdentifierType(ControllerUtils.setValue(instrumentIdentifier.getIdentifierType(), instrumentIdentifierDTO.getIdentifierType()));
		
		instrumentIdentifier.setIdentifierValue(ControllerUtils.setValue(instrumentIdentifier.getIdentifierValue(), instrumentIdentifierDTO.getIdentifierValue()));

        instrumentIdentifier = instrumentIdentifierDao.save(instrumentIdentifier);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public InstrumentIdentifierDTO getInstrumentIdentifierDTOById(Integer instrumentIdentifierId) {
	
		InstrumentIdentifier instrumentIdentifier = instrumentIdentifierDao.getById(instrumentIdentifierId);
		
		InstrumentIdentifierConvertCriteriaDTO convertCriteria = new InstrumentIdentifierConvertCriteriaDTO();
		return(this.convertInstrumentIdentifierToInstrumentIdentifierDTO(instrumentIdentifier,convertCriteria));
	}

}
