/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.panks.customerepo.service;

import javax.xml.ws.WebFault;

/**
 *
 * @author paolo.panconi
 */
@WebFault(faultBean = "it.panks.customerepo.service.CrmFault")
public class CrmException extends Exception {

    private CrmFault fault;
    
    /**
     * Creates a new instance of <code>CrmException</code> without detail
     * message.
     */
    public CrmException() {
    }

    /**
     * Constructs an instance of <code>CrmException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public CrmException(String msg, CrmFault fault) {
        super(msg);
        this.fault = fault;
    }
    
    public CrmException(String msg, CrmFault fault,  Throwable cause) {
        super(msg, cause);
        this.fault = fault;
    }
    
    public CrmFault getFaultInfo() {
        return fault;
    }
}
