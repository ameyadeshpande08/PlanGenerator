package com.lendico.plangenerator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lendico.plangenerator.model.GeneratePlan;
import com.lendico.plangenerator.model.RepaymentPlan;
import com.lendico.plangenerator.service.GenerateRepaymentPlanService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class PlanGeneratorController {

	@Autowired
	private GenerateRepaymentPlanService generateRepaymentPlanService;

	@ResponseStatus(value = HttpStatus.OK)
	@ApiOperation(notes = "This operation takes the Object of GeneratePlan ", tags = "GeneratePlan", value = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 405, message = "Method Not Allowed"),
			@ApiResponse(code = 422, message = "Unprocessable entity Functional error"),
			@ApiResponse(code = 500, message = "Internal Server Error "),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	@PostMapping("/generate-plan")
	public ResponseEntity<List<RepaymentPlan>> generatePlan(@RequestBody GeneratePlan generatePlan) {
		List<RepaymentPlan> repaymentPlanList = generateRepaymentPlanService.generateRepaymentPlan(generatePlan);
		return new ResponseEntity<List<RepaymentPlan>>(repaymentPlanList, HttpStatus.OK);
	}

}
