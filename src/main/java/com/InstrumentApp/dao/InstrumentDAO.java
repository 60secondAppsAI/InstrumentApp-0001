package com.InstrumentApp.dao;

import java.util.List;
import java.util.Date;

import com.InstrumentApp.dao.GenericDAO;
import com.InstrumentApp.domain.Instrument;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface InstrumentDAO extends GenericDAO<Instrument, Integer> {
  
	List<Instrument> findAll();
	


}

