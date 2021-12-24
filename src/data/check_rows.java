package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 
public class check_rows extends total_check{
 
	public static void checkrows(String path) throws IOException {
		
		  File folder = new File(path);
		  File files[] = folder.listFiles();
		  String file_data = path + "file_data.csv";
		  List<List<String>> content = new ArrayList<List<String>>();
		  int lineCnt = 0;
		  System.out.println(" ");
		  
		  try {
				 BufferedReader br = new BufferedReader(new FileReader(file_data));
			     String line = "";
			     while ((line = br.readLine()) != null) {
		        	List<String> tmpList = new ArrayList<String>();
		        	String array[] = line.split(",");
		        	tmpList = Arrays.asList(array);
		            content.add(tmpList);
			     }
		  }catch (FileNotFoundException e) {
		    	e.printStackTrace();
		  }
		  List<String> id = new ArrayList<String>();
			for(int k = 1; k < content.size(); k++) {
				id.add(content.get(k).get(0));
			}
		  List<String> file_id = new ArrayList<String>();
		  
		  for(int i = 0; i < files.length; i++) {
			  if(files[i].isFile()) {
				  String filename = files[i].getName();
				  int idx = filename.lastIndexOf(".");
				  filename.substring(0, idx);
				  file_id.add(filename.substring(0, idx));  
			 }  
		  }
		  
		  for(int k = 0; k < file_id.size(); k++) {
			  for(int i = 0; i < id.size(); i++) {
				  if(file_id.get(k).equals(id.get(i))) {
					  String url = path + file_id.get(k) + ".csv";
					  try {
							BufferedReader br = new BufferedReader(new FileReader(url));
						    while(br.readLine() != null) 
						    	lineCnt++;
								    if(lineCnt < 21) {
								    	System.out.println("파일의 행이 20줄 미만입니다. " + file_id.get(k));
								    }
					  }catch(Exception e) { 
							e.printStackTrace(); 
					  }
					  lineCnt = 0;
				  }
			  }
		  }
		  System.out.println(" ");
		  System.out.println("==================== 파일의 행 검사가 완료되었습니다. ====================");
		  System.out.println(" ");
	}
}
