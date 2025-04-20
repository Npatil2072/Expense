package com.nt.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Expense {

	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private Double amount;
	private String category;
	private String description;
	private LocalDate  date;
	   @Lob
	    @Column(name = "receipt", columnDefinition = "LONGBLOB")
	    private byte[] receipt;

	@ManyToOne
	 @JoinColumn(name = "user_id")
	@JsonIgnore 
	private User user;

	public Expense(LocalDate date, Double amount, String category, String description, byte[] receipt, User user) {
		this.date = date;
		this.amount = amount;
		this.category = category;
		this.description = description;
		this.receipt = receipt;
		this.user = user;
	}
}