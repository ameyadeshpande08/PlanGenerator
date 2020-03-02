package com.lendico.plangenerator.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RepaymentPlan {

	private double borrowerPaymentAmount;
	private LocalDateTime date;
	private double initialOutstandingPrincipal;
	private double interest;
	private double principal;
	private double remainingOutstandingPrincipal;
}
