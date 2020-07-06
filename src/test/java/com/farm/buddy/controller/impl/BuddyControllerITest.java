package com.farm.buddy.controller.impl;

import java.net.URL;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BuddyControllerITest {

	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/predict");
    }

    @Test
    void predictTest() {
        ResponseEntity<String> response = template.getForEntity(base.toString()+"?crop=Paddy&mobile=1234567890", String.class);
        Assert.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void predictContentTest() {
        ResponseEntity<String> response = template.getForEntity(base.toString()+"?crop=Paddy&mobile=1234567890", String.class);
        Assert.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assert.assertTrue(response.getBody().contains("litres per day for an Acre"));
    }

    @Test
    void invalidMobileTest() {
        ResponseEntity<String> response = template.getForEntity(base.toString()+"?crop=Paddy&mobile=12345", String.class);
        Assert.assertTrue(response.getStatusCode().is4xxClientError());
        Assert.assertTrue(response.getBody().contains("FB-10001"));
    }
}
