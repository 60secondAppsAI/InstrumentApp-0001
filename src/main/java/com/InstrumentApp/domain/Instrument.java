package com.InstrumentApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="instruments")
@Getter @Setter @NoArgsConstructor
public class Instrument {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="instrument_id")
	private Integer instrumentId;
    
  	@Column(name="symbol")
	private String symbol;
    
  	@Column(name="name")
	private String name;
    
  	@Column(name="instrument_type")
	private String instrumentType;
    
  	@Column(name="currency")
	private String currency;
    
  	@Column(name="status")
	private String status;
    

}
