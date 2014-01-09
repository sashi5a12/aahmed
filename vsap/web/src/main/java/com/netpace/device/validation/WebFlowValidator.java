/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.netpace.device.validation;

import com.netpace.device.vo.Record;
import org.springframework.binding.message.MessageContext;

/**
 *
 * @author Content5
 */
public interface WebFlowValidator<T extends Record,E extends MessageContext> {

    public void validate(T o, E mc);

}
