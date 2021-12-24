package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class check_columns extends total_check{
	
	public static final String UTF8_BOM = "\uFEFF";
	
	public static void checkcolumns(String path) throws Exception {
		
		File folder = new File(path);
		File files[] = folder.listFiles(); //위의 경로에 있는 파일들을 배열로
		String file_data = path + "column_data.csv";
		List<List<String>> content = new ArrayList<List<String>>(); //column_date의 파일 내용을 list에
		HashMap<String, List<String>> columns = new HashMap<String, List<String>>(); //파일의 아이디와 컬럼들을 해시맵에
		
		try {
			 BufferedReader br = new BufferedReader(new FileReader(file_data));
		     String line = "";
		     while ((line = br.readLine()) != null) {
	        	List<String> tmpList = new ArrayList<String>();
	        	String array[] = line.split(","); //line을 ,로 나누어 array에 넣음
	        	tmpList = Arrays.asList(array); //array 배열을 list로 전환
	            content.add(tmpList); //content에 column_data 파일(tmpList)을 넣음
		     }
		}catch (FileNotFoundException e) {
	    	e.printStackTrace();
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
				for(int j = 1; j < content.size(); j++) { 
					  if(file_id.get(k).equals(content.get(j).get(2))) {
						String url = path + file_id.get(k) + ".csv";
						try {
							BufferedReader br = new BufferedReader(new FileReader(url)); //각 파일의 내용을 읽어옴
							String line = "";
						    while ((line = br.readLine()) != null) {
						    	//line이 UTF8_BOM으로 시작하면
						    	if(line.startsWith(UTF8_BOM)) {
						    		line = line.substring(1);
						    	}
					        	List<String> tmpList = new ArrayList<String>();
					        	String array[] = line.split(","); //line을 ,로 나누어 array에 넣음
					        	tmpList = Arrays.asList(array); //array 배열을 list로 전환
								columns.put(file_id.get(k),tmpList); //columns에 파일 아이디와 컬럼들(tmpList)을 넣음
					        	break;						
						    }
					 	}catch(Exception e) { 
							e.printStackTrace(); 
						}
					 } 
				  }
			}
	/*	//폴더에 있는 파일 이름과 각 파일의 컬럼 출력
		for(int j = 0; j < columns.size(); j++) {
			System.out.println(files[j] + " " + columns.get(files[j]));
		}
		
		//각 파일의 파일 아이디 값 저장
		List<String> file_id = new ArrayList<String>();
		for(int k = 0; k < columns.size(); k++) {
			for(int m = 1; m < content.size(); m++) {
				if(files[k].contains(content.get(m).get(2))) {
					file_id.add(files[k]);		
				}	
			}
		}
		//column_data에 있는 파일id와 폴더에 있는 파일의 이름과 같은것 출력
		List<String> file_list = file_id.stream().distinct().collect(Collectors.toList());
		System.out.println("일치하는 파일 " + file_list);
		System.out.println(" ");
	*/	
		HashMap<String, List<String>> file_columns = new HashMap<String, List<String>>(); //column_data의 파일 아이디와 컬럼들을 해시맵에
		for(int i = 0; i < file_id.size(); i++) {
			List<String> file_name = new ArrayList<String>(); //column_data의 컬럼들을 file_name 리스트에
			for(int n = 1; n < content.size(); n++){
				if(file_id.get(i).equals(content.get(n).get(2))) {	
					file_name.add(content.get(n).get(3));
					file_columns.put(content.get(n).get(2), file_name); 
				}
			}
		}
		//key값에 따라 오름차순 정렬
		TreeMap<String, List<String>> sort_key = new TreeMap<String, List<String>>(file_columns);
		Iterator<String> iteratorKey = sort_key.keySet().iterator(); 
		while(iteratorKey.hasNext()) {
			String key = iteratorKey.next();
			for(int k = 0; k < file_id.size(); k++) {
					if(file_id.get(k).equals(key)) {
					//파일의 컬럼 수와 column_data의 컬럼 개수가 일치하면
					//	System.out.println(columns.get(key));
					//	System.out.println(sort_key.get(key));
					if(columns.get(key).size() == sort_key.get(key).size()) {
						for(int j = 0; j < columns.get(key).size(); j++) {
							//파일의 컬럼명과 column_data의 컬럼명이 일치하면
							if(columns.get(file_id.get(k)).get(j).equalsIgnoreCase(sort_key.get(key).get(j))) {
								//System.out.println("컬럼명 일치 " + files[k] + " " + columns.get(files[k]).get(j));		
							}
							//파일의 컬럼명과 column_data의 컬럼명이 일치하지 않으면
							else {
								System.out.println("* 컬럼명 불일치   " + file_id.get(k) + "   " + "- 파일 컬럼명: " + columns.get(file_id.get(k)).get(j) + "   " + "- column_data 파일 컬럼명: " + sort_key.get(key).get(j));
								//파일의 컬럼에 공백이 있으면
								if(columns.get(file_id.get(k)).get(j).contains(" ")){
									System.out.println("* 공백을 가진 컬럼   - 파일명: " + file_id.get(k) + "   " + "컬럼명: " + columns.get(file_id.get(k)).get(j));
								}
								//column_data의 컬럼에 공백이 있으면
								else if(sort_key.get(key).get(j).contains(" ")) {
									System.out.println("* 공백을 가진 컬럼   - column_data: " + key + "   " + "컬럼명: " + sort_key.get(key).get(j));
								}
							}
						}	
					}
					//파일의 컬럼 수와 column_data의 컬럼 개수가 일치하지 않으면
					else {
						System.out.println("* 컬럼 개수 불일치 " + file_id.get(k));
					}
				}
			}
		}
		System.out.println(" ");
		System.out.println("==================== 컬럼명 검사가 완료되었습니다. ====================");
		System.out.println(" ");
	}
}