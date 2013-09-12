package com.sample.ch08;

public class EmployeeFault extends Exception {

    public static final long serialVersionUID = 12324835835L;

    public EmployeeFault() {
        super();
    }

    public EmployeeFault(String message) {
        super(message);
    }

    public EmployeeFault(String message, Throwable cause) {
        super(message, cause);
    }
}
