/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.dto;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class BaseDto {
    private ArrayList<String> columns;
    private ArrayList<String> columnTypes;
    private String tablename;
    
    public ArrayList<String> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<String> columns) {
        this.columns = columns;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public ArrayList<String> getColumnTypes() {
        return columnTypes;
    }

    public void setColumnTypes(ArrayList<String> columnTypes) {
        this.columnTypes = columnTypes;
    }
    
}
