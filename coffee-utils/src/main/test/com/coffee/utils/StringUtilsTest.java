package com.coffee.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringUtilsTest {
	@Test
	public void isEmpty() {
		boolean answer = StringUtils.isEmpty("");
		assertEquals(true, answer);
	}

	@Test
	public void isEmpty2() {
		boolean answer = StringUtils.isEmpty(null);
		assertEquals(true, answer);
	}

	@Test
	public void isNotEmpty() {
		boolean answer = StringUtils.isNotEmpty("");
		assertEquals(false, answer);
	}

	@Test
	public void isNotEmpty2() {
		boolean answer = StringUtils.isNotEmpty(null);
		assertEquals(false, answer);
	}

	@Test
	public void isBlank() {
		boolean answer = StringUtils.isBlank("     ");
		assertEquals(true, answer);
	}

	@Test
	public void isBlank2() {
		boolean answer = StringUtils.isBlank(null);
		assertEquals(true, answer);
	}

	@Test
	public void isBlank3() {
		boolean answer = StringUtils.isBlank(" gg gg ");
		assertEquals(false, answer);
	}

	@Test
	public void isNotBlank() {
		boolean answer = StringUtils.isNotBlank(" v2    ");
		assertEquals(true, answer);
	}

	@Test
	public void isNotBlank2() {
		boolean answer = StringUtils.isNotBlank(null);
		assertEquals(false, answer);
	}

	@Test
	public void getStringBegin() {
		String answer = StringUtils.getStringBegin(" abc d fgh");
		assertEquals(" abc...", answer);
	}

	@Test
	public void getStringBegin2() {
		String answer = StringUtils.getStringBegin("hello my friend");
		assertEquals("hell...", answer);
	}

	@Test
	public void getStringBegin3() {
		String answer = StringUtils.getStringBegin(" abc d fgh", 9);
		assertEquals(" abc d f...", answer);
	}

	@Test
	public void getStringBegin4() {
		String answer = StringUtils.getStringBegin("hello my friend", 11);
		assertEquals("hello my f...", answer);
	}

	@Test
	public void isNumeric() {
		boolean answer = StringUtils.isNumeric("one");
		assertEquals(false, answer);
	}

	@Test
	public void isNumeric2() {
		boolean answer = StringUtils.isNumeric("1");
		assertEquals(true, answer);
	}

	@Test
	public void isNumeric3() {
		boolean answer = StringUtils.isNumeric("1.0");
		assertEquals(true, answer);
	}

	@Test
	public void EMPTY_STR() {
		String answer = StringUtils.EMPTY_STR;
		assertEquals("", answer);
	}

}
