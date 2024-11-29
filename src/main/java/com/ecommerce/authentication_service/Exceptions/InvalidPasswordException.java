package com.ecommerce.authentication_service.Exceptions;


public class InvalidPasswordException extends Exception{
	InvalidPasswordException(String msg)
	{
		super(msg);
	}
}