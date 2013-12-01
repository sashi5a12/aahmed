/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.rest;

import com.sample.springjersey.TransactionBo;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/payment")
public class PaymentService {

    @Autowired
    TransactionBo transactionBo;

    @GET
    @Path("/save")
    public Response savePayment() {
        String result = transactionBo.save();
        return Response.status(200).entity(result).build();
    }

    public TransactionBo getTransactionBo() {
        return transactionBo;
    }

    public void setTransactionBo(TransactionBo transactionBo) {
        this.transactionBo = transactionBo;
    }
    
}