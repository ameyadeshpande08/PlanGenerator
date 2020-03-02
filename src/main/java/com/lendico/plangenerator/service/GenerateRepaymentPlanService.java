package com.lendico.plangenerator.service;

import java.util.List;

import com.lendico.plangenerator.model.GeneratePlan;
import com.lendico.plangenerator.model.RepaymentPlan;

public interface GenerateRepaymentPlanService {

	List<RepaymentPlan> generateRepaymentPlan(GeneratePlan generatePlan);

}
