package com.farm.buddy.controller.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BuddyControllerImplTest {
	private static final int Ok_Response = 200;
	private static final int Bad_Response = 400;

	@Autowired
	private MockMvc mvc;

	@Test
	void predictTest() throws Exception {
		ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/predict?crop=Paddy&mobile=1234567890")
				.accept("application/vnd.farm.buddy.api.v1+json"))
				.andExpect(status().isOk());
		Assert.assertEquals(Ok_Response, resultActions.andReturn().getResponse().getStatus());

	}

	@Test
	void invalidMobileTest() throws Exception {
		ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/predict?crop=Paddy&mobile=123456")
				.accept("application/vnd.farm.buddy.api.v1+json"))
				.andExpect(status().isBadRequest());
		Assert.assertEquals(Bad_Response, resultActions.andReturn().getResponse().getStatus());
	}

	@Test
	void invalidCropTest() throws Exception {
		ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/predict?crop=india&mobile=1234567890")
				.accept("application/vnd.farm.buddy.api.v1+json"))
				.andExpect(status().isBadRequest());
		Assert.assertEquals(Bad_Response, resultActions.andReturn().getResponse().getStatus());
	}
}
