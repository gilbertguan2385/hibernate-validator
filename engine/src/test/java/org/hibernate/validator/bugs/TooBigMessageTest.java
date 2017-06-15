/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.validator.bugs;

import static org.hibernate.validator.testutil.ConstraintViolationAssert.assertThat;
import static org.hibernate.validator.testutil.ConstraintViolationAssert.violationOf;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.testutil.TestForIssue;
import org.hibernate.validator.testutils.ValidatorUtil;

import org.testng.annotations.Test;

/**
 * Ensure large error messages can be interpolated.
 *
 * @author Gunnar Morling
 */
public class TooBigMessageTest {

	/**
	 * Large enough to trigger a stack overflow with the recursive scheme, assuming default settings
	 */
	private static final String LARGE_MESSAGE =
			"12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
			+ "12345678901234567890123456789012345678901234567890123456789012345678901234567890";

	@Test
	@TestForIssue(jiraKey = "HV-1091")
	public void largeMessageCanBeInterpolated() {
		Validator validator = ValidatorUtil.getValidator();
		GoldFish fish = new GoldFish();

		Set<ConstraintViolation<GoldFish>> constraintViolations = validator.validate( fish );
		assertThat( constraintViolations ).containsOnlyViolations(
				violationOf( NotNull.class ).withMessage( LARGE_MESSAGE )
		);
	}

	private static class GoldFish {

		@NotNull(message = LARGE_MESSAGE)
		String name;
	}
}
