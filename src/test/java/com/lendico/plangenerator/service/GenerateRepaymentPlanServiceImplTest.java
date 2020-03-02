package com.lendico.plangenerator.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lendico.plangenerator.model.GeneratePlan;
import com.lendico.plangenerator.model.RepaymentPlan;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { GenerateRepaymentPlanServiceImpl.class })
public class GenerateRepaymentPlanServiceImplTest {
	@Autowired
	private GenerateRepaymentPlanService generateRepaymentPlanService;

	@BeforeClass
	public static void setupBeforeClass() {
	}

	@AfterClass
	public static void tearDownContext() {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetStandardCalculator() {
		GeneratePlan generatePlan = new GeneratePlan();
		generatePlan.setDuration(2);
		generatePlan.setLoanAmount(1000);
		generatePlan.setNominalRate(5);
		generatePlan.setStartDate("2018-01-01T00:00:01Z");
		List<RepaymentPlan> repaymentPlanList = generateRepaymentPlanService.generateRepaymentPlan(generatePlan);
		assertFalse(repaymentPlanList.isEmpty());
		assertNotNull(repaymentPlanList.get(0));
		assertThat(repaymentPlanList.get(0), is(getRepaymentPlan1()));
		assertThat(repaymentPlanList.get(1), is(getRepaymentPlan2()));
	}

	private RepaymentPlan getRepaymentPlan1() {
		RepaymentPlan repaymentPlan = new RepaymentPlan();
		repaymentPlan.setBorrowerPaymentAmount(503.13);
		repaymentPlan.setInterest(4.17);
		repaymentPlan.setDate(LocalDateTime.of(2018, 01, 01, 00, 00, 01));
		repaymentPlan.setPrincipal(498.96);
		repaymentPlan.setInitialOutstandingPrincipal(1000.0);
		repaymentPlan.setRemainingOutstandingPrincipal(501.04);
		return repaymentPlan;
	}
	private RepaymentPlan getRepaymentPlan2() {
		RepaymentPlan repaymentPlan = new RepaymentPlan();
		repaymentPlan.setBorrowerPaymentAmount(503.13);
		repaymentPlan.setInterest(2.09);
		repaymentPlan.setDate(LocalDateTime.of(2018, 01, 31, 00, 00, 01));
		repaymentPlan.setPrincipal(501.04);
		repaymentPlan.setInitialOutstandingPrincipal(501.04);
		repaymentPlan.setRemainingOutstandingPrincipal(0.0);
		return repaymentPlan;
	}
}
