/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import modelo.Signable;

/**
 *
 * @author 2dam
 */
public class FactorySignable {
    
    
    
    
    
    public static Signable getSignable(){
        return new ClientSocket();
       
    }
}
