package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class check_data extends total_check{

	public static void checkdata(String path) throws Exception {
		
		File folder = new File(path);
		File files[] = folder.listFiles();
		String file_data = path + "file_data.csv";
		List<List<String>> content = new ArrayList<List<String>>();
		
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
		List<String> equal_file = new ArrayList<String>();
			for(int i = 0; i < files.length; i++) {
				String filename = files[i].getName();
				if(files[i].isFile()) {
				  int idx = filename.lastIndexOf(".");
				  filename.substring(0, idx);
				  file_id.add(filename.substring(0, idx));
				}
			}

		for(int k = 0; k < file_id.size(); k++) {
			for(int j = 0; j < id.size(); j++) {
				if(file_id.get(k).equals(id.get(j))) {
					equal_file.add(file_id.get(k));
				}
			}
		}

		for(int i = 0; i <  equal_file.size(); i++) {
			System.out.println("일치하는 파일 " + equal_file.get(i));	
		}
		System.out.println(" ");
		for(int i = 0; i < file_id.size(); i++) {
			for(int j = 0; j < equal_file.size(); j++) {
				if(file_id.get(i).equals(equal_file.get(j))) {
					file_id.remove(file_id.get(i));
				}
			}
		}
		List<String> noequal_file = file_id.stream().distinct().collect(Collectors.toList());
		noequal_file.remove("column_data");
		noequal_file.remove("datasets");
		noequal_file.remove("file_data");
		noequal_file.remove("keywords");
		for(int j = 0; j < noequal_file.size(); j++) {
			System.out.println("일치하지 않는 파일 " + noequal_file.get(j));		
		}
		System.out.println(" ");
		System.out.println("==================== 파일명 검사가 완료되었습니다. ====================");
		System.out.println(" ");
	}
}
