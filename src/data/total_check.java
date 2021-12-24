package data;

import java.io.File;
import java.util.Scanner;

public class total_check {

	public static void main(String[] args) throws Exception {
		System.out.print("파일 경로를 입력하세요 : ");
		Scanner sc = new Scanner(System.in);
		String path = sc.nextLine();
		File file = new File(path);
		String files[] = file.list();
				check_rows.checkrows(path);
				check_data.checkdata(path);
				check_columns.checkcolumns(path);
				System.out.println(" ");
				System.out.println("==================== 데이터 검증이 완료되었습니다. ====================");
	}
}
