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
@Table(name="instrument_identifiers")
@Getter @Setter @NoArgsConstructor
public class InstrumentIdentifier {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="instrument_identifier_id")
	private Integer instrumentIdentifierId;
    
  	@Column(name="instrument_id")
	private Integer instrumentId;
    
  	@Column(name="identifier_type")
	private String identifierType;
    
  	@Column(name="identifier_value")
	private String identifierValue;
    

}
