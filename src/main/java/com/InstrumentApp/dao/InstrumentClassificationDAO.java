package com.InstrumentApp.dao;

import java.util.List;
import java.util.Date;

import com.InstrumentApp.dao.GenericDAO;
import com.InstrumentApp.domain.InstrumentClassification;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface InstrumentClassificationDAO extends GenericDAO<InstrumentClassification, Integer> {
  
	List<InstrumentClassification> findAll();
	


}

