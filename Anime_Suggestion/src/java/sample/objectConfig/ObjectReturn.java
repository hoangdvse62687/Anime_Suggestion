/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.objectConfig;

/**
 *
 * @author DELL
 */
public class ObjectReturn {
    private String message;
    private Object data;

    public ObjectReturn() {
    }
    
    
    public ObjectReturn(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
