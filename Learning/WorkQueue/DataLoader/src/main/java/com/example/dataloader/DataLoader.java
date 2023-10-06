/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.example.dataloader;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class DataLoader {
    private static String fileName;

    public static void main(String[] args) throws ParseException, Exception {
        fileName = args[0];
        
        if(fileName != null && !fileName.equals("")){
            if(args[1].equals("1")){
                XLSXToJSON();
            } else if(args[1].equals("2")){
                loadFileJsonToHTTP();
            }
        }
        
    }
    
    public static void XLSXToJSON() throws Exception{
        
        File inFile = new File("./"+fileName+".xlsx");  //creating a new file instance 
        File outFile = new File("./"+fileName+".json"); //creating a new file instance 
        
        FileInputStream fis = new FileInputStream(inFile);   //obtaining input bytes from a file
 
        XSSFWorkbook wb = new XSSFWorkbook(fis); //creating Workbook instance that refers to .xlsx file  

        XSSFSheet sheet = wb.getSheetAt(0); //creating a Sheet object to retrieve object

        Iterator<Row> itr = sheet.iterator(); //iterating over excel file
        
        JsonFactory factory = new JsonFactory(); // creating a json factory
        
        JsonGenerator generator = factory.createGenerator(outFile, JsonEncoding.UTF8); // generator
        
        generator.useDefaultPrettyPrinter(); // for printing pretty JSON
        
        generator.writeStartObject(); // {        
        
        generator.writeFieldName("cuaas");
        
        generator.writeStartArray(); // [
        
        itr.next(); // to skip the first row beacause it contains the name of the column
        
        while(itr.hasNext()){
            
            Row row = itr.next();
            Iterator<Cell> cellIterator = row.cellIterator(); // iterating over each column
            
            generator.writeStartObject(); // {
            
            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                
                if (cell.getCellType().equals(CellType.NUMERIC)) {
                    // System.out.println(cell.getNumericCellValue() + " ");
                    generator.writeFieldName("campagna");
                    generator.writeNumber((int) cell.getNumericCellValue());
                } else if (cell.getCellType().equals(CellType.STRING)){
                    // System.out.println(cell.getStringCellValue()+ " ");
                    generator.writeFieldName("cuaa");
                    generator.writeString(cell.getStringCellValue());
                }
            }
            
            generator.writeEndObject(); // }
            
            // System.out.println("");
        }
        
        generator.writeEndArray(); // }
        
        generator.writeEndObject(); // }
        
        generator.close(); // to close the generator
    }
    
    public static void loadFileJsonToHTTP() throws ParseException{
        FileReader reader = null;
        
        try {
            File file = new File(fileName+".json");
            JSONParser jsonParser = new JSONParser();
            reader = new FileReader(file);
            Object obj = jsonParser.parse(reader);
            JSONObject jsonFile = (JSONObject) obj;
            
            // System.out.println(jsonFile.toJSONString());
            
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8080/massive_adoption");
            ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, jsonFile.toJSONString());
            
            if(response.getStatus() == 204){
                throw new RuntimeException("Resourse does not exist!");
            } else if(response.getStatus() != 200){
                throw new RuntimeException("Failed: HTTP error code : " + response.getStatus());
            }   String output = response.getEntity(String.class);
            
            System.out.println(output);
            
            reader.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
