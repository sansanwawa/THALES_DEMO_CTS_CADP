package com.thales.id.jakarta.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;


/**
 *
 * Author : sandy.haryono@thalesgroup.com
 *
 */

@Entity
@Table(name="config")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class ThalesConfig {
	

	@Id
	@Column(name = "configName")
	private String configName;

	@Column(name = "configValue")
	private String configValue;

	public ThalesConfig(String configName, String configValue){
		this.configName = configName;
		this.configValue = configValue;
	}
	public ThalesConfig(){}




}
