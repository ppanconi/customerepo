/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.panks.customerepo.service;

/**
 *
 * @author paolo.panconi
 */
public class CrmFault {
    
    private int id;
    private String code;

    public CrmFault() {
    }

    public CrmFault(int id, String code) {
        this.id = id;
        this.code = code;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
}
