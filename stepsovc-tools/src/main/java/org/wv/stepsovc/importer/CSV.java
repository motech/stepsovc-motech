package org.wv.stepsovc.importer;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public abstract class CSV<T> extends DataImporter<T> {


    private ColumnPositionMappingStrategy columnPositionMappingStrategy = new ColumnPositionMappingStrategy<T>();

    private CsvToBean csvToBean = new CsvToBean<T>();

    public List<T> parse(String filePath, Class<T> clazz) {
        List<T> beans = new ArrayList<T>();
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(filePath), ',');
            String[] headers = reader.readNext();
            columnPositionMappingStrategy.setType(clazz);
            columnPositionMappingStrategy.setColumnMapping(headers);
            beans = csvToBean.parse(columnPositionMappingStrategy, reader);
        } catch (Exception e) {
        }
        return beans;
    }


}
