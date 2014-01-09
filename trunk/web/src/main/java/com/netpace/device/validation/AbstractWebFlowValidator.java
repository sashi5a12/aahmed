/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.netpace.device.validation;

import com.netpace.device.vo.Record;
import java.util.List;

import java.util.Map;
import java.util.Set;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;

/**
 *
 * @author Content5
 */
public abstract class AbstractWebFlowValidator<T extends Record, E extends MessageContext> extends BaseValidator<T> implements WebFlowValidator<T , E> {

    @Override
    public void validate(T o, E mc) {
        this.validateJSR303Fields(o, mc);
        this.customValidation(o, mc);
    }

    private final void validateJSR303Fields(T obj, E context) {

        Map<String, List<AnnotationBean> > invalidFields = super.validateJSR303Fields(obj);
        
        if(!invalidFields.isEmpty()) {
            this.populateMessageContext(invalidFields, context);
        }
    }

    private void populateMessageContext( Map<String, List<AnnotationBean> > invalidFields, MessageContext context) {

        MessageBuilder contextMessageBuilder = new MessageBuilder();
        Set<String> keys = invalidFields.keySet();
        List<AnnotationBean> fieldBeans = null;

        for(String key : keys){
            fieldBeans = invalidFields.get(key);
            for (AnnotationBean bean : fieldBeans) {
                contextMessageBuilder.error().defaultText(bean.getMessage());
                context.addMessage(contextMessageBuilder.build());
            }
        }
    }

    protected final void populateMessageContext( MessageBuilder messageBuilder, MessageContext context, String message) {

        if(messageBuilder == null)
            messageBuilder = new MessageBuilder();
        
        messageBuilder.error().defaultText(message);
        context.addMessage(messageBuilder.build());
    }

    protected abstract void customValidation(T elem, E context);
    
}
