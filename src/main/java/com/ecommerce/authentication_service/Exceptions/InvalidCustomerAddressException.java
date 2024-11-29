package com.ecommerce.authentication_service.Exceptions;

public class InvalidCustomerAddressException extends Exception{
	InvalidCustomerAddressException(String msg)
	{
		super(msg);
	}
}
