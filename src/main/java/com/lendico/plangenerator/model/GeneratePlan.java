package com.lendico.plangenerator.model;

import java.util.Date;

import lombok.Data;

@Data
public class GeneratePlan {

	private double loanAmount;
	private double nominalRate;
	private double duration;
	private String startDate;

}
