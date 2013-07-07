/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.springjersey;

import org.springframework.stereotype.Service;

/**
 *
 * @author aahmed
 */
@Service(value = "transactionBo")
public class TransactionBoImpl implements TransactionBo {

    @Override
    public String save() {

        return "Jersey + Spring example";

    }
}