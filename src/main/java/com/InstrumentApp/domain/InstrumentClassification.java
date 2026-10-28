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
@Table(name="instrument_classifications")
@Getter @Setter @NoArgsConstructor
public class InstrumentClassification {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="instrument_classification_id")
	private Integer instrumentClassificationId;
    
  	@Column(name="instrument_id")
	private Integer instrumentId;
    
  	@Column(name="sector")
	private String sector;
    
  	@Column(name="industry")
	private String industry;
    
  	@Column(name="asset_class")
	private String assetClass;
    

}
