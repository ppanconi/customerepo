/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.panks.customerepo.service;

import static it.panks.customerepo.util.EntityManagerFactoryHolder.*;

import it.panks.customerepo.model.ClientProspet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author paolo.panconi
 */
@WebService(serviceName = "CustomerService")
public class CustomerService {

    
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    @WebMethod(operationName = "fetchClientProspect")
    public ClientProspet fetchClientProspect(@WebParam(name = "id") Long id) throws CrmException {
        
        try {
            beginTransaction();

            ClientProspet c = currentEntityManager().find(ClientProspet.class, id);

            commitTransaction();

            return c;
        } catch (Exception ex) {
            Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            throw new CrmException(ex.getMessage(), new CrmFault(1, "1"), ex);
        } finally {
            closeEntityManager();
        }
    } 
    
    @WebMethod(operationName = "searchClientProspects")
    public List<ClientProspet> searchClientProspects(@WebParam(name = "name") String name, 
            @WebParam(name = "surname") String surname) throws CrmException {
        try {
            beginTransaction();
            List<ClientProspet> l = new ArrayList<>();
            
            //TODO make the right query here
            ClientProspet c = new ClientProspet();
            c.setDenomination(name + " " + surname);
            c.setId(2L);
            c.setFiscalcode("26348762819");
            c.setVersion(1L);
            l.add(c);
            
            commitTransaction();
            
            return l;
        } catch (Exception ex) {
            Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            throw new CrmException(ex.getMessage(), new CrmFault(2, "2"), ex);
        } finally {
            closeEntityManager();
        }

    }
}
