package com.pin91.hrm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pin91.hrm.transferobject.KeyValueResponse;

@RestController
public class AppController {

	@RequestMapping(value = "/healthcheck", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getHealthCheck() {
		KeyValueResponse response = new KeyValueResponse();
		response.setValue("success");
		return new ResponseEntity<KeyValueResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/version", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> version() {
		KeyValueResponse response = new KeyValueResponse();
		response.setValue("success");
		return new ResponseEntity<KeyValueResponse>(response, HttpStatus.OK);
	}
}
