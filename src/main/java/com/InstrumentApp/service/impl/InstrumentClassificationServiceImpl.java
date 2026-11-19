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
import com.InstrumentApp.dao.InstrumentClassificationDAO;
import com.InstrumentApp.domain.InstrumentClassification;
import com.InstrumentApp.dto.InstrumentClassificationDTO;
import com.InstrumentApp.dto.InstrumentClassificationSearchDTO;
import com.InstrumentApp.dto.InstrumentClassificationPageDTO;
import com.InstrumentApp.dto.InstrumentClassificationConvertCriteriaDTO;
import com.InstrumentApp.dto.common.RequestDTO;
import com.InstrumentApp.dto.common.ResultDTO;
import com.InstrumentApp.service.InstrumentClassificationService;
import com.InstrumentApp.util.ControllerUtils;


@Service
public class InstrumentClassificationServiceImpl extends GenericServiceImpl<InstrumentClassification, Integer> implements InstrumentClassificationService {

    private final static Logger logger = LoggerFactory.getLogger(InstrumentClassificationServiceImpl.class);

	@Autowired
	InstrumentClassificationDAO instrumentClassificationDao;

	

	@Override
	public GenericDAO<InstrumentClassification, Integer> getDAO() {
		return (GenericDAO<InstrumentClassification, Integer>) instrumentClassificationDao;
	}
	
	public List<InstrumentClassification> findAll () {
		List<InstrumentClassification> instrumentClassifications = instrumentClassificationDao.findAll();
		
		return instrumentClassifications;	
		
	}

	public ResultDTO addInstrumentClassification(InstrumentClassificationDTO instrumentClassificationDTO, RequestDTO requestDTO) {

		InstrumentClassification instrumentClassification = new InstrumentClassification();

		instrumentClassification.setInstrumentClassificationId(instrumentClassificationDTO.getInstrumentClassificationId());

		instrumentClassification.setInstrumentId(instrumentClassificationDTO.getInstrumentId());

		instrumentClassification.setSector(instrumentClassificationDTO.getSector());

		instrumentClassification.setIndustry(instrumentClassificationDTO.getIndustry());

		instrumentClassification.setAssetClass(instrumentClassificationDTO.getAssetClass());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		instrumentClassification = instrumentClassificationDao.save(instrumentClassification);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<InstrumentClassification> getAllInstrumentClassifications(Pageable pageable) {
		return instrumentClassificationDao.findAll(pageable);
	}

	public Page<InstrumentClassification> getAllInstrumentClassifications(Specification<InstrumentClassification> spec, Pageable pageable) {
		return instrumentClassificationDao.findAll(spec, pageable);
	}

	public ResponseEntity<InstrumentClassificationPageDTO> getInstrumentClassifications(InstrumentClassificationSearchDTO instrumentClassificationSearchDTO) {
	
			Integer instrumentClassificationId = instrumentClassificationSearchDTO.getInstrumentClassificationId(); 
 			Integer instrumentId = instrumentClassificationSearchDTO.getInstrumentId(); 
 			String sector = instrumentClassificationSearchDTO.getSector(); 
 			String industry = instrumentClassificationSearchDTO.getIndustry(); 
 			String assetClass = instrumentClassificationSearchDTO.getAssetClass(); 
 			String sortBy = instrumentClassificationSearchDTO.getSortBy();
			String sortOrder = instrumentClassificationSearchDTO.getSortOrder();
			String searchQuery = instrumentClassificationSearchDTO.getSearchQuery();
			Integer page = instrumentClassificationSearchDTO.getPage();
			Integer size = instrumentClassificationSearchDTO.getSize();

	        Specification<InstrumentClassification> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, instrumentClassificationId, "instrumentClassificationId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, instrumentId, "instrumentId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, sector, "sector"); 
			
			spec = ControllerUtils.andIfNecessary(spec, industry, "industry"); 
			
			spec = ControllerUtils.andIfNecessary(spec, assetClass, "assetClass"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("sector")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("industry")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("assetClass")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<InstrumentClassification> instrumentClassifications = this.getAllInstrumentClassifications(spec, pageable);
		
		//System.out.println(String.valueOf(instrumentClassifications.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(instrumentClassifications.getTotalPages()));
		
		List<InstrumentClassification> instrumentClassificationsList = instrumentClassifications.getContent();
		
		InstrumentClassificationConvertCriteriaDTO convertCriteria = new InstrumentClassificationConvertCriteriaDTO();
		List<InstrumentClassificationDTO> instrumentClassificationDTOs = this.convertInstrumentClassificationsToInstrumentClassificationDTOs(instrumentClassificationsList,convertCriteria);
		
		InstrumentClassificationPageDTO instrumentClassificationPageDTO = new InstrumentClassificationPageDTO();
		instrumentClassificationPageDTO.setInstrumentClassifications(instrumentClassificationDTOs);
		instrumentClassificationPageDTO.setTotalElements(instrumentClassifications.getTotalElements());
		return ResponseEntity.ok(instrumentClassificationPageDTO);
	}

	public List<InstrumentClassificationDTO> convertInstrumentClassificationsToInstrumentClassificationDTOs(List<InstrumentClassification> instrumentClassifications, InstrumentClassificationConvertCriteriaDTO convertCriteria) {
		
		List<InstrumentClassificationDTO> instrumentClassificationDTOs = new ArrayList<InstrumentClassificationDTO>();
		
		for (InstrumentClassification instrumentClassification : instrumentClassifications) {
			instrumentClassificationDTOs.add(convertInstrumentClassificationToInstrumentClassificationDTO(instrumentClassification,convertCriteria));
		}
		
		return instrumentClassificationDTOs;

	}
	
	public InstrumentClassificationDTO convertInstrumentClassificationToInstrumentClassificationDTO(InstrumentClassification instrumentClassification, InstrumentClassificationConvertCriteriaDTO convertCriteria) {
		
		InstrumentClassificationDTO instrumentClassificationDTO = new InstrumentClassificationDTO();

		instrumentClassificationDTO.setInstrumentClassificationId(instrumentClassification.getInstrumentClassificationId());

		instrumentClassificationDTO.setInstrumentId(instrumentClassification.getInstrumentId());

		instrumentClassificationDTO.setSector(instrumentClassification.getSector());

		instrumentClassificationDTO.setIndustry(instrumentClassification.getIndustry());

		instrumentClassificationDTO.setAssetClass(instrumentClassification.getAssetClass());
		
		return instrumentClassificationDTO;
	}

	public ResultDTO updateInstrumentClassification(InstrumentClassificationDTO instrumentClassificationDTO, RequestDTO requestDTO) {
		
		InstrumentClassification instrumentClassification = instrumentClassificationDao.getById(instrumentClassificationDTO.getInstrumentClassificationId());
		
		instrumentClassification.setInstrumentClassificationId(ControllerUtils.setValue(instrumentClassification.getInstrumentClassificationId(), instrumentClassificationDTO.getInstrumentClassificationId()));
		
		instrumentClassification.setInstrumentId(ControllerUtils.setValue(instrumentClassification.getInstrumentId(), instrumentClassificationDTO.getInstrumentId()));
		
		instrumentClassification.setSector(ControllerUtils.setValue(instrumentClassification.getSector(), instrumentClassificationDTO.getSector()));
		
		instrumentClassification.setIndustry(ControllerUtils.setValue(instrumentClassification.getIndustry(), instrumentClassificationDTO.getIndustry()));
		
		instrumentClassification.setAssetClass(ControllerUtils.setValue(instrumentClassification.getAssetClass(), instrumentClassificationDTO.getAssetClass()));

        instrumentClassification = instrumentClassificationDao.save(instrumentClassification);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public InstrumentClassificationDTO getInstrumentClassificationDTOById(Integer instrumentClassificationId) {
	
		InstrumentClassification instrumentClassification = instrumentClassificationDao.getById(instrumentClassificationId);
		
		InstrumentClassificationConvertCriteriaDTO convertCriteria = new InstrumentClassificationConvertCriteriaDTO();
		return(this.convertInstrumentClassificationToInstrumentClassificationDTO(instrumentClassification,convertCriteria));
	}

}
