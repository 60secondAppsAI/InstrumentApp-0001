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
import com.InstrumentApp.dao.InstrumentDAO;
import com.InstrumentApp.domain.Instrument;
import com.InstrumentApp.dto.InstrumentDTO;
import com.InstrumentApp.dto.InstrumentSearchDTO;
import com.InstrumentApp.dto.InstrumentPageDTO;
import com.InstrumentApp.dto.InstrumentConvertCriteriaDTO;
import com.InstrumentApp.dto.common.RequestDTO;
import com.InstrumentApp.dto.common.ResultDTO;
import com.InstrumentApp.service.InstrumentService;
import com.InstrumentApp.util.ControllerUtils;


@Service
public class InstrumentServiceImpl extends GenericServiceImpl<Instrument, Integer> implements InstrumentService {

    private final static Logger logger = LoggerFactory.getLogger(InstrumentServiceImpl.class);

	@Autowired
	InstrumentDAO instrumentDao;

	

	@Override
	public GenericDAO<Instrument, Integer> getDAO() {
		return (GenericDAO<Instrument, Integer>) instrumentDao;
	}
	
	public List<Instrument> findAll () {
		List<Instrument> instruments = instrumentDao.findAll();
		
		return instruments;	
		
	}

	public ResultDTO addInstrument(InstrumentDTO instrumentDTO, RequestDTO requestDTO) {

		Instrument instrument = new Instrument();

		instrument.setInstrumentId(instrumentDTO.getInstrumentId());

		instrument.setSymbol(instrumentDTO.getSymbol());

		instrument.setName(instrumentDTO.getName());

		instrument.setInstrumentType(instrumentDTO.getInstrumentType());

		instrument.setCurrency(instrumentDTO.getCurrency());

		instrument.setStatus(instrumentDTO.getStatus());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		instrument = instrumentDao.save(instrument);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Instrument> getAllInstruments(Pageable pageable) {
		return instrumentDao.findAll(pageable);
	}

	public Page<Instrument> getAllInstruments(Specification<Instrument> spec, Pageable pageable) {
		return instrumentDao.findAll(spec, pageable);
	}

	public ResponseEntity<InstrumentPageDTO> getInstruments(InstrumentSearchDTO instrumentSearchDTO) {
	
			Integer instrumentId = instrumentSearchDTO.getInstrumentId(); 
 			String symbol = instrumentSearchDTO.getSymbol(); 
 			String name = instrumentSearchDTO.getName(); 
 			String instrumentType = instrumentSearchDTO.getInstrumentType(); 
 			String currency = instrumentSearchDTO.getCurrency(); 
 			String status = instrumentSearchDTO.getStatus(); 
 			String sortBy = instrumentSearchDTO.getSortBy();
			String sortOrder = instrumentSearchDTO.getSortOrder();
			String searchQuery = instrumentSearchDTO.getSearchQuery();
			Integer page = instrumentSearchDTO.getPage();
			Integer size = instrumentSearchDTO.getSize();

	        Specification<Instrument> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, instrumentId, "instrumentId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, symbol, "symbol"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, instrumentType, "instrumentType"); 
			
			spec = ControllerUtils.andIfNecessary(spec, currency, "currency"); 
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("symbol")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("instrumentType")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("currency")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("status")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Instrument> instruments = this.getAllInstruments(spec, pageable);
		
		//System.out.println(String.valueOf(instruments.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(instruments.getTotalPages()));
		
		List<Instrument> instrumentsList = instruments.getContent();
		
		InstrumentConvertCriteriaDTO convertCriteria = new InstrumentConvertCriteriaDTO();
		List<InstrumentDTO> instrumentDTOs = this.convertInstrumentsToInstrumentDTOs(instrumentsList,convertCriteria);
		
		InstrumentPageDTO instrumentPageDTO = new InstrumentPageDTO();
		instrumentPageDTO.setInstruments(instrumentDTOs);
		instrumentPageDTO.setTotalElements(instruments.getTotalElements());
		return ResponseEntity.ok(instrumentPageDTO);
	}

	public List<InstrumentDTO> convertInstrumentsToInstrumentDTOs(List<Instrument> instruments, InstrumentConvertCriteriaDTO convertCriteria) {
		
		List<InstrumentDTO> instrumentDTOs = new ArrayList<InstrumentDTO>();
		
		for (Instrument instrument : instruments) {
			instrumentDTOs.add(convertInstrumentToInstrumentDTO(instrument,convertCriteria));
		}
		
		return instrumentDTOs;

	}
	
	public InstrumentDTO convertInstrumentToInstrumentDTO(Instrument instrument, InstrumentConvertCriteriaDTO convertCriteria) {
		
		InstrumentDTO instrumentDTO = new InstrumentDTO();

		instrumentDTO.setInstrumentId(instrument.getInstrumentId());

		instrumentDTO.setSymbol(instrument.getSymbol());

		instrumentDTO.setName(instrument.getName());

		instrumentDTO.setInstrumentType(instrument.getInstrumentType());

		instrumentDTO.setCurrency(instrument.getCurrency());

		instrumentDTO.setStatus(instrument.getStatus());
		
		return instrumentDTO;
	}

	public ResultDTO updateInstrument(InstrumentDTO instrumentDTO, RequestDTO requestDTO) {
		
		Instrument instrument = instrumentDao.getById(instrumentDTO.getInstrumentId());
		
		instrument.setInstrumentId(ControllerUtils.setValue(instrument.getInstrumentId(), instrumentDTO.getInstrumentId()));
		
		instrument.setSymbol(ControllerUtils.setValue(instrument.getSymbol(), instrumentDTO.getSymbol()));
		
		instrument.setName(ControllerUtils.setValue(instrument.getName(), instrumentDTO.getName()));
		
		instrument.setInstrumentType(ControllerUtils.setValue(instrument.getInstrumentType(), instrumentDTO.getInstrumentType()));
		
		instrument.setCurrency(ControllerUtils.setValue(instrument.getCurrency(), instrumentDTO.getCurrency()));
		
		instrument.setStatus(ControllerUtils.setValue(instrument.getStatus(), instrumentDTO.getStatus()));

        instrument = instrumentDao.save(instrument);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public InstrumentDTO getInstrumentDTOById(Integer instrumentId) {
	
		Instrument instrument = instrumentDao.getById(instrumentId);
		
		InstrumentConvertCriteriaDTO convertCriteria = new InstrumentConvertCriteriaDTO();
		return(this.convertInstrumentToInstrumentDTO(instrument,convertCriteria));
	}

}
