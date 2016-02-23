package com.camel.examples;

public class MyException extends Exception {
	private static final long serialVersionUID = -8487173868744177826L;

	public MyException(String message) {
		super("My Exception : " + message);
	}
}
