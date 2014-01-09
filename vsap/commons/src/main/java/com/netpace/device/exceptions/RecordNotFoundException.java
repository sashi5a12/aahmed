package com.netpace.device.exceptions;

public class RecordNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 6597475203957255761L;

	public RecordNotFoundException(String message) {
        super(message);
    }
    
}
