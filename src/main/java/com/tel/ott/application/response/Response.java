package com.tel.ott.application.response;

import java.util.List;

import com.tel.ott.application.collection.Subscriptions;

public class Response {
	private int status;
	private String message;

	private Object output;

	public Response() {
	}

	public Response(int status, String message, Object output) {
		this.status = status;
		this.message = message;
		this.output = output;
	}


	public Response(String message, Object output) {
		this.message = message;
		this.output = output;
	}

	public Response(Object output) {
		this.output = output;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getOutput() {
		return output;
	}

	public void setOutput(Object output) {
		this.output = output;
	}
}
