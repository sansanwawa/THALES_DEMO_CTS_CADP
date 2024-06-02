package com.thales.id.jakarta.entities;


import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="person")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@NoArgsConstructor
public class Person {
	
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@JsonIgnore
	@Column(name = "name")
	private String name;


	@JsonIgnore
	@Column(name = "email")
	private String email;

	@JsonIgnore
	@Column(name = "address")
	private String address;


	@JsonIgnore
	@Column(name = "city")
	private String city;

	@JsonIgnore
	@Column(name = "state")
	private String state;

	@JsonIgnore
	@Column(name = "zip")
	private String zip;


	@JsonIgnore
	@Column(name = "card_name")
	private String cardName;


	@JsonIgnore
	@Column(name = "card_number")
	private String cardNumber;

	@JsonIgnore
	@Column(name = "exp_date")
	private String expDate;


	@JsonIgnore
	@Column(name = "cvv")
	private String cvv;



}
