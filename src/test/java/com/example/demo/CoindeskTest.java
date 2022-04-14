package com.example.demo;

import org.junit.jupiter.api.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

class CoindeskTest {
	
	@Test
	public void testMain() {
		
		/* 建立 資料表
		 * CREATE TABLE bpi (
		 *	   id INT NOT NULL,
		 *	   code VARCHAR(50) NOT NULL,
		 *	   symbol VARCHAR(20) NOT NULL,
		 *	   rate VARCHAR(20) NOT NULL,
		 *	   description VARCHAR(50) NOT NULL,
		 *	   rate_float number NOT NULL
		 *	);
		 */
		
		// 1. 測試呼叫查詢幣別對應表資料 API，並顯示其內容。
			// saveDataByDB();
			// getDataByDB();
		
		// 2. 測試呼叫新增幣別對應表資料 API。
			// saveDataByDB();
		
		
		// 3. 測試呼叫更新幣別對應表資料 API，並顯示其內容。
			// upDataByDB();
			// getDataByDB();
		
		
		// 4. 測試呼叫刪除幣別對應表資料 API。
			// deleteDataByDB();
		
		
		// 5. 測試呼叫 coindesk API，並顯示其內容。
			// getCoindeskAPI();
		
		
		// 6. 測試呼叫資料轉換的 API，並顯示其內容。
			// getCoindeskAPI();
			// getDataByDB();
	}
	
	/**
	 * 取得 Coindesk API 資料
	 */
	public void getCoindeskAPI() {
		try {
			String path = "http://localhost:8080/getCoindeskAPI";
			HttpResponse<JsonNode> response = Unirest.get(path).asJson();
			System.out.printf("Test 取得 Coindesk API 回應：%s 回傳資料：%s \n",response.getStatus(), response.getBody());
			System.out.println();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 將 Coindesk API 存到 DB
	 */
	public void saveDataByDB() {
		try {
			String path = "http://localhost:8080/changetodb";
			HttpResponse<String> response = Unirest.get(path).asString();
			System.out.printf("Test 儲存資料到 DB... 回應：%s  回傳資料：%s \n",response.getStatus(), response.getBody());
			System.out.println();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查詢 DB 資料
	 */
	public void getDataByDB() {
		try {
			String path = "http://localhost:8080/getdata";
			HttpResponse<JsonNode> response = Unirest.get(path).asJson();
			System.out.printf("Test 查詢 DB... 回應：%s  回傳資料：%s \n",response.getStatus(), response.getBody());
			System.out.println();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新 DB 資料
	 */
	public void upDataByDB() {
		try {
			String path = "http://localhost:8080/updata";
			HttpResponse<String> response = Unirest.post(path)
					.header("Content-Type", "application/json")
					.body("{"
						+ "  \"id\":null,"
						+ "  \"幣別\":\"USD\","
						+ "  \"代號\":\"222\","
						+ "  \"匯率\":\"333\","
						+ "  \"說明\":\"444\","
						+ "  \"匯率_浮點數\":333"
						+ "}")
					.asString();
			System.out.printf("Test 更新資料... 回應：%s  回傳資料：%s \n",response.getStatus(), response.getBody());
			System.out.println();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 刪除 DB 資料
	 */
	public void deleteDataByDB() {
		try {
			String path = "http://localhost:8080/deletedata";
			HttpResponse<String> response = Unirest.delete(path)
					.header("Content-Type", "application/json")
					.body("{"
						+ "  \"幣別\":\"USD\""
						+ "}")
					.asString();
			System.out.printf("Test 刪除資料... 回應：%s  回傳資料：%s \n",response.getStatus(), response.getBody());
			System.out.println();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

}
