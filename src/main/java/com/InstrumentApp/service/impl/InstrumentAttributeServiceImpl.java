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
import com.InstrumentApp.dao.InstrumentAttributeDAO;
import com.InstrumentApp.domain.InstrumentAttribute;
import com.InstrumentApp.dto.InstrumentAttributeDTO;
import com.InstrumentApp.dto.InstrumentAttributeSearchDTO;
import com.InstrumentApp.dto.InstrumentAttributePageDTO;
import com.InstrumentApp.dto.InstrumentAttributeConvertCriteriaDTO;
import com.InstrumentApp.dto.common.RequestDTO;
import com.InstrumentApp.dto.common.ResultDTO;
import com.InstrumentApp.service.InstrumentAttributeService;
import com.InstrumentApp.util.ControllerUtils;


@Service
public class InstrumentAttributeServiceImpl extends GenericServiceImpl<InstrumentAttribute, Integer> implements InstrumentAttributeService {

    private final static Logger logger = LoggerFactory.getLogger(InstrumentAttributeServiceImpl.class);

	@Autowired
	InstrumentAttributeDAO instrumentAttributeDao;

	

	@Override
	public GenericDAO<InstrumentAttribute, Integer> getDAO() {
		return (GenericDAO<InstrumentAttribute, Integer>) instrumentAttributeDao;
	}
	
	public List<InstrumentAttribute> findAll () {
		List<InstrumentAttribute> instrumentAttributes = instrumentAttributeDao.findAll();
		
		return instrumentAttributes;	
		
	}

	public ResultDTO addInstrumentAttribute(InstrumentAttributeDTO instrumentAttributeDTO, RequestDTO requestDTO) {

		InstrumentAttribute instrumentAttribute = new InstrumentAttribute();

		instrumentAttribute.setInstrumentAttributeId(instrumentAttributeDTO.getInstrumentAttributeId());

		instrumentAttribute.setInstrumentId(instrumentAttributeDTO.getInstrumentId());

		instrumentAttribute.setAttributeName(instrumentAttributeDTO.getAttributeName());

		instrumentAttribute.setAttributeValue(instrumentAttributeDTO.getAttributeValue());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		instrumentAttribute = instrumentAttributeDao.save(instrumentAttribute);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<InstrumentAttribute> getAllInstrumentAttributes(Pageable pageable) {
		return instrumentAttributeDao.findAll(pageable);
	}

	public Page<InstrumentAttribute> getAllInstrumentAttributes(Specification<InstrumentAttribute> spec, Pageable pageable) {
		return instrumentAttributeDao.findAll(spec, pageable);
	}

	public ResponseEntity<InstrumentAttributePageDTO> getInstrumentAttributes(InstrumentAttributeSearchDTO instrumentAttributeSearchDTO) {
	
			Integer instrumentAttributeId = instrumentAttributeSearchDTO.getInstrumentAttributeId(); 
 			Integer instrumentId = instrumentAttributeSearchDTO.getInstrumentId(); 
 			String attributeName = instrumentAttributeSearchDTO.getAttributeName(); 
 			String attributeValue = instrumentAttributeSearchDTO.getAttributeValue(); 
 			String sortBy = instrumentAttributeSearchDTO.getSortBy();
			String sortOrder = instrumentAttributeSearchDTO.getSortOrder();
			String searchQuery = instrumentAttributeSearchDTO.getSearchQuery();
			Integer page = instrumentAttributeSearchDTO.getPage();
			Integer size = instrumentAttributeSearchDTO.getSize();

	        Specification<InstrumentAttribute> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, instrumentAttributeId, "instrumentAttributeId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, instrumentId, "instrumentId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, attributeName, "attributeName"); 
			
			spec = ControllerUtils.andIfNecessary(spec, attributeValue, "attributeValue"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("attributeName")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("attributeValue")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<InstrumentAttribute> instrumentAttributes = this.getAllInstrumentAttributes(spec, pageable);
		
		//System.out.println(String.valueOf(instrumentAttributes.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(instrumentAttributes.getTotalPages()));
		
		List<InstrumentAttribute> instrumentAttributesList = instrumentAttributes.getContent();
		
		InstrumentAttributeConvertCriteriaDTO convertCriteria = new InstrumentAttributeConvertCriteriaDTO();
		List<InstrumentAttributeDTO> instrumentAttributeDTOs = this.convertInstrumentAttributesToInstrumentAttributeDTOs(instrumentAttributesList,convertCriteria);
		
		InstrumentAttributePageDTO instrumentAttributePageDTO = new InstrumentAttributePageDTO();
		instrumentAttributePageDTO.setInstrumentAttributes(instrumentAttributeDTOs);
		instrumentAttributePageDTO.setTotalElements(instrumentAttributes.getTotalElements());
		return ResponseEntity.ok(instrumentAttributePageDTO);
	}

	public List<InstrumentAttributeDTO> convertInstrumentAttributesToInstrumentAttributeDTOs(List<InstrumentAttribute> instrumentAttributes, InstrumentAttributeConvertCriteriaDTO convertCriteria) {
		
		List<InstrumentAttributeDTO> instrumentAttributeDTOs = new ArrayList<InstrumentAttributeDTO>();
		
		for (InstrumentAttribute instrumentAttribute : instrumentAttributes) {
			instrumentAttributeDTOs.add(convertInstrumentAttributeToInstrumentAttributeDTO(instrumentAttribute,convertCriteria));
		}
		
		return instrumentAttributeDTOs;

	}
	
	public InstrumentAttributeDTO convertInstrumentAttributeToInstrumentAttributeDTO(InstrumentAttribute instrumentAttribute, InstrumentAttributeConvertCriteriaDTO convertCriteria) {
		
		InstrumentAttributeDTO instrumentAttributeDTO = new InstrumentAttributeDTO();

		instrumentAttributeDTO.setInstrumentAttributeId(instrumentAttribute.getInstrumentAttributeId());

		instrumentAttributeDTO.setInstrumentId(instrumentAttribute.getInstrumentId());

		instrumentAttributeDTO.setAttributeName(instrumentAttribute.getAttributeName());

		instrumentAttributeDTO.setAttributeValue(instrumentAttribute.getAttributeValue());
		
		return instrumentAttributeDTO;
	}

	public ResultDTO updateInstrumentAttribute(InstrumentAttributeDTO instrumentAttributeDTO, RequestDTO requestDTO) {
		
		InstrumentAttribute instrumentAttribute = instrumentAttributeDao.getById(instrumentAttributeDTO.getInstrumentAttributeId());
		
		instrumentAttribute.setInstrumentAttributeId(ControllerUtils.setValue(instrumentAttribute.getInstrumentAttributeId(), instrumentAttributeDTO.getInstrumentAttributeId()));
		
		instrumentAttribute.setInstrumentId(ControllerUtils.setValue(instrumentAttribute.getInstrumentId(), instrumentAttributeDTO.getInstrumentId()));
		
		instrumentAttribute.setAttributeName(ControllerUtils.setValue(instrumentAttribute.getAttributeName(), instrumentAttributeDTO.getAttributeName()));
		
		instrumentAttribute.setAttributeValue(ControllerUtils.setValue(instrumentAttribute.getAttributeValue(), instrumentAttributeDTO.getAttributeValue()));

        instrumentAttribute = instrumentAttributeDao.save(instrumentAttribute);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public InstrumentAttributeDTO getInstrumentAttributeDTOById(Integer instrumentAttributeId) {
	
		InstrumentAttribute instrumentAttribute = instrumentAttributeDao.getById(instrumentAttributeId);
		
		InstrumentAttributeConvertCriteriaDTO convertCriteria = new InstrumentAttributeConvertCriteriaDTO();
		return(this.convertInstrumentAttributeToInstrumentAttributeDTO(instrumentAttribute,convertCriteria));
	}

}
