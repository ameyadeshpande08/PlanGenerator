package com.lendico.plangenerator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.lendico.plangenerator.model.GeneratePlan;
import com.lendico.plangenerator.model.RepaymentPlan;

@Service
public class GenerateRepaymentPlanServiceImpl implements GenerateRepaymentPlanService {

	private static final double NO_OF_DAYS_IN_MONTH = 30;
	private static final double NO_OF_DAYS_IN_YEAR = 360;

	@Override
	public List<RepaymentPlan> generateRepaymentPlan(GeneratePlan generatePlan) {
		double annuityPayment = roundingDecimalto2(calculateAnnuity(generatePlan));
		List<RepaymentPlan> repaymentPlanList = getRepaymentPlan(annuityPayment, generatePlan);
		return repaymentPlanList;
	}

	private List<RepaymentPlan> getRepaymentPlan(double annuityPayment, GeneratePlan generatePlan) {
		List<RepaymentPlan> repaymentPlanList = new ArrayList<RepaymentPlan>();
		double initialOutstandingPrincipal = generatePlan.getLoanAmount();
		double interest = calculateInterest(generatePlan.getNominalRate(), initialOutstandingPrincipal);
		double principle = roundingDecimalto2(annuityPayment - interest);
		double remainingOutstandingPrinciple = initialOutstandingPrincipal;
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
		LocalDateTime date = LocalDateTime.parse(generatePlan.getStartDate(), inputFormatter);

		while (remainingOutstandingPrinciple > 0) {
			interest = calculateInterest(generatePlan.getNominalRate(), initialOutstandingPrincipal);
			principle = roundingDecimalto2(annuityPayment - interest);
			if (principle > initialOutstandingPrincipal) {
				principle = initialOutstandingPrincipal;
			}
			double borrowerPaymentAmount = principle + interest;
			remainingOutstandingPrinciple -= principle;
			// Rounding off all values
			borrowerPaymentAmount = roundingDecimalto2(borrowerPaymentAmount);
			principle = roundingDecimalto2(principle);
			initialOutstandingPrincipal = roundingDecimalto2(initialOutstandingPrincipal);
			remainingOutstandingPrinciple = roundingDecimalto2(remainingOutstandingPrinciple);

			RepaymentPlan repaymentPlan = new RepaymentPlan();
			repaymentPlan.setBorrowerPaymentAmount(borrowerPaymentAmount);
			repaymentPlan.setInterest(interest);
			repaymentPlan.setDate(date);
			repaymentPlan.setPrincipal(principle);
			repaymentPlan.setInitialOutstandingPrincipal(initialOutstandingPrincipal);
			repaymentPlan.setRemainingOutstandingPrincipal(remainingOutstandingPrinciple);
			repaymentPlanList.add(repaymentPlan);
			// Used for next Iteration
			initialOutstandingPrincipal = remainingOutstandingPrinciple;
			date = date.plusDays(30);
		}
		return repaymentPlanList;
	}

	private double calculateInterest(double annualRate, double initialOutstandingPrincipal) {
		annualRate /= 100;
		return roundingDecimalto2(
				(annualRate * NO_OF_DAYS_IN_MONTH * initialOutstandingPrincipal) / NO_OF_DAYS_IN_YEAR);
	}

	private double roundingDecimalto2(double input) {
		BigDecimal bd = new BigDecimal(input).setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	private double calculateAnnuity(GeneratePlan generatePlan) {
		double annualRate = generatePlan.getNominalRate() / 100;
		double monthlyRate = annualRate / 12;
		System.out.println("monthlyRate:- " + monthlyRate);
		double a = 1 + monthlyRate;
		double power = Math.pow(a, -generatePlan.getDuration());
		return (generatePlan.getLoanAmount() * monthlyRate) / (1 - power);
	}
}