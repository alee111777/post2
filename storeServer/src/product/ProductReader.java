/*
 * CSC 668 SFSU
 * Project POST1
 * Team Ziga
 */
package product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * product reader reads in a catalog from a file.
 * @author Team Ziga
 */
public class ProductReader {
    private static HashMap productCatalog;
    private static StringTokenizer productTokens;
    private static BufferedReader source;
    private static String UPC, Description, createPrice;
    private static double Price;
    
    
    public ProductReader(String productFile)throws IOException {
        
        source = new BufferedReader(new FileReader(productFile));
        productCatalog = new HashMap<String, ProductSpec>();
        
    }
    
    /**
     * takes a catalog(file) and returns a hashmap of ProductSpecs with key
     * being the upc
     * @param fileName
     * @return HashMap<ProductSpec>
     * @throws IOException 
     */
    public static HashMap getCatalog(String fileName) throws IOException{
        ProductReader read = new ProductReader(fileName);   
        ProductReader.init();
        ProductReader.close();
        
        return productCatalog;
        
    }
    
    /**
     * use to test product reader
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException{
        
        HashMap catalog = ProductReader.getCatalog("productCatalog.txt");
        
        System.out.println("It worked");
        
    }

    /**
     * close the file stream
     */
    static void close() {
        
        try {
            source.close();
        } catch (Exception e) {}
        
    }
    
    /**
     * populates catalog hashmap
     * @throws IOException 
     */
    static void init() throws IOException{
        
        String newItem;
        
        while((newItem = source.readLine()) != null){
            
            productTokens = new StringTokenizer(newItem);
            
            UPC = productTokens.nextToken();
            Description = productTokens.nextToken();
            createPrice = productTokens.nextToken();
            Price = Double.parseDouble(createPrice);
            
            productCatalog.put(UPC, new ProductSpec(UPC,Description,Price));
        }
        
    }
        
    boolean hasMoreProducts(){
        return true;
    }
    
    ProductSpec getNextProduct(){
        ProductSpec productSpec = new ProductSpec("0000", "Giraffe", 10.00);
        return productSpec;
    }
    
}
