/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package errores;

/**
 *
 * @author 2dam
 */
public class ValidationError extends Exception {
        public ValidationError(String message) {
            super(message);
        }
}
