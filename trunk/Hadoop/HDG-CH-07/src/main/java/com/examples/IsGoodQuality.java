/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examples;

import java.io.IOException;
import org.apache.pig.FilterFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.Tuple;

public class IsGoodQuality extends FilterFunc {

    @Override
    public Boolean exec(Tuple tuple) throws IOException {
        if (tuple == null || tuple.size() == 0) {
            return false;
        }
        try {
            Object object = tuple.get(0);
            if (object == null) {
                return false;
            }
            int i = (Integer) object;
            return i == 0 || i == 1 || i == 4 || i == 5 || i == 9;
        } catch (ExecException e) {
            throw new IOException(e);
        }
    }
}
