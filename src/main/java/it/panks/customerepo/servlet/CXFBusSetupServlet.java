/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.panks.customerepo.servlet;

import it.panks.customerepo.service.CustomerService;
import javax.servlet.ServletConfig;
import javax.xml.ws.Endpoint;
import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

/**
 *
 * @author paolo.panconi
 */
public class CXFBusSetupServlet extends CXFNonSpringServlet  {
    
    @Override
    public void loadBus(ServletConfig servletConfig) 
//            throws ServletException 
                                    {
        super.loadBus(servletConfig);        
         
        // You could add the endpoint publish codes here
//        Endpoint.publish("/CustomerService", new CustomerService());
         
        // You can als use the simple frontend API to do this
        ServerFactoryBean factory = new ServerFactoryBean();
        factory.setBus(bus);
        factory.setServiceClass(CustomerService.class);
        factory.setAddress("/CustomerService");
        factory.setDataBinding(new AegisDatabinding());
        factory.create();              
        
    }
    
}
