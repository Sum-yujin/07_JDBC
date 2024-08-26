package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample3 {

	public static void main(String[] args) {
		
		// 입력 받은 최소 급여 이상
		// 입력 받은 최대 급여 이하를 받는 
		// 사원의 사번, 이름, 급여를 급여 내림차순으로 조회
		
		/* 1. JDBC 객체 참조용 변수 선언 */
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null; 
		
		try {
			/* 2. DriverManager 객체를 이용해서 Connection 객체 생성하기 */
			/* 2-1) Oracle JDBC Driver 객체를 메모리에 로드(적재) 하기*/
			Class.forName("oracle.jdbc.driver.OracleDriver");
	
			/* 2-2) DB 연결 정보(ip, port, id,... ) 작성 */
			String type = "jdbc:oracle:thin:@"; // 드라이버의 종류
			String host = "khj-1.xyz"; // DB 서버 컴퓨터의 IP 또는 도메인 주소
									   // localhost == 현재 컴퓨터
			String port = ":10000"; // 프로그램 연결을 위한 구분 번호
			String dbName = ":XE"; // DBMS 이름(XE == eXpress Edition)
			String userName = "KH_COMMON"; // 사용자 계정명 
			String password = "KH1234"; // 계정 비밀번호
			
			/* 2-3) DB 연결 정보와 DriverManager를 이용해서 
			 * Connection 객체 생성*/
			conn = DriverManager.getConnection(type + host + port + dbName, userName, password);
			
			/* 3. SQL 작성 */
			Scanner sc = new Scanner(System.in);
			System.out.print("최소 급여 : ");
			int min = sc.nextInt();
			
			System.out.print("최대 급여 : ");
			int max = sc.nextInt();
			
			String sql = """
					SELECT EMP_ID, EMP_NAME, SALARY
					FROM EMPLOYEE
					WHERE SALARY BETWEEN  
					 """ 
					+ min + " AND " + max
					+ " ORDER BY SALARY DESC";
			
			/* 4. Statement 객체 생성 */
			stmt = conn.createStatement();
			
			/* 5. SQL 수행 후 결과 반환받기 */
			rs = stmt.executeQuery(sql);
			
			/* 6. 커서를 이용해서 1행씩 접근해 컬럼 값 얻어오기 */
			int count = 0;
			
			while(rs.next()) {
				count++;
				
				String empId   = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int    salary  = rs.getInt("SALARY");
				
				System.out.printf("%s / %s / %d \n", empId, empName, salary);
			}
			
			System.out.println("총원 : " + count + " 명");
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
}
