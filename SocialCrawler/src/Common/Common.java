package Common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
 
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * @author hussein.hazimeh
 * @HES-SO, Switzerland
 *
 */
public class Common {
	
	private String inputFile;
	
	 public void setInputFile(String inputFile) {
	    	
	        this.inputFile = inputFile;
	    }
	    public List<String> read(int start, int end) throws IOException  {
	    	List<String> urls = new ArrayList<>();
	        File inputWorkbook = new File(inputFile);
	        Workbook w;
	        try {
	            w = Workbook.getWorkbook(inputWorkbook);
	            Sheet sheet = w.getSheet(0);
	            
	            for (int j = 0; j < sheet.getColumns(); j++) {
	                for (int i = start; i < end; i++) {
	                    Cell cell = sheet.getCell(j, i);
	                    CellType type = cell.getType();
	                    if (type == CellType.LABEL) {
	                        urls.add(cell.getContents());
	                    }
	                    if (type == CellType.NUMBER) {
	                    }
	                   }
	                }
	        } catch (BiffException e) {
	            e.printStackTrace();
	        }
	        return urls;
	    }
	    
	    @SuppressWarnings("resource")
		public void buildDataset(String title, JSONArray list, String file_path) throws IOException{
	    	
	    	FileWriter file;
	    	JSONObject o;
	    	o = new JSONObject();
        	file = new FileWriter(file_path);
        	file.write("["); 
        	o.put(title+": ", list);
	        file.write(o.toJSONString() + ","); 
	        System.out.println(o.toJSONString() + "]"); 
	        file.flush(); 
	    }
}
