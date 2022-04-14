package com.coindesk.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coindesk.entities.Bpi;
import com.coindesk.repository.BpiRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
public class CoindeskController {
	
	@Autowired
	private BpiRepository bpiRepository;
	
	/**
	 * 取得 Coindesk API 資料
	 * https://api.coindesk.com/v1/bpi/currentprice.json
	 */
	@GetMapping("/getCoindeskAPI")
	public String getCoindeskAPI() {
		String path = "https://api.coindesk.com/v1/bpi/currentprice.json";
		String json = null;
		try {
			// 1. 取得 coindesk API 資訊
			URL url = new URL(path);
			InputStreamReader isr = new InputStreamReader(url.openStream(), "utf-8");
			Scanner sc = new Scanner(isr);
			json = sc.useDelimiter("\\A").next();
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 取得 Coindesk API 資料後。轉換到 DB
	 * https://api.coindesk.com/v1/bpi/currentprice.json
	 */
	@GetMapping("/changetodb")
	public String changeToDB() {
		String path = "https://api.coindesk.com/v1/bpi/currentprice.json";
		try {
			// 1. 取得 coindesk API 資訊
			URL url = new URL(path);
			InputStreamReader isr = new InputStreamReader(url.openStream(), "utf-8");
			Scanner sc = new Scanner(isr);
			String json = sc.useDelimiter("\\A").next();
			// 2. 轉 JSN
			JsonObject coindesk = new Gson().fromJson(json, JsonObject.class);
			// 3. 取資料
			String strUSD = Stream.of(coindesk.get("bpi").getAsJsonObject().get("USD").toString())
					.map(e -> e.replace("code", "幣別"))
					.map(e -> e.replace("symbol", "代號"))
					.map(e -> e.replace("rate_float", "匯率_浮點數"))
					.map(e -> e.replace("rate", "匯率"))
					.map(e -> e.replace("description", "說明"))
					.collect(Collectors.joining());
			String strGBP = Stream.of(coindesk.get("bpi").getAsJsonObject().get("GBP").toString())
					.map(e -> e.replace("code", "幣別"))
					.map(e -> e.replace("symbol", "代號"))
					.map(e -> e.replace("rate_float", "匯率_浮點數"))
					.map(e -> e.replace("rate", "匯率"))
					.map(e -> e.replace("description", "說明"))
					.map(e -> e.replace("updated", "更新時間"))
					.collect(Collectors.joining());
			String strEUR = Stream.of(coindesk.get("bpi").getAsJsonObject().get("EUR").toString())
					.map(e -> e.replace("code", "幣別"))
					.map(e -> e.replace("symbol", "代號"))
					.map(e -> e.replace("rate_float", "匯率_浮點數"))
					.map(e -> e.replace("rate", "匯率"))
					.map(e -> e.replace("description", "說明"))
					.map(e -> e.replace("updated", "更新時間"))
					.collect(Collectors.joining());
			String strLastTime = Stream.of(coindesk.get("time").getAsJsonObject().get("updatedISO").toString())
					.map(e -> e.replace("\"", ""))
					.map(e -> e.replace("T", " "))
					.map(e -> e.replace("-", "/"))
					.map(e -> e.replace("0+00:00", "0"))
					.collect(Collectors.joining());
			Bpi usd = new Gson().fromJson(strUSD, Bpi.class);
			usd.set更新時間(strLastTime);
			Bpi gbp = new Gson().fromJson(strGBP, Bpi.class);
			gbp.set更新時間(strLastTime);
			Bpi eur = new Gson().fromJson(strEUR, Bpi.class);
			eur.set更新時間(strLastTime);
			// 4. 儲存
			bpiRepository.save(usd);
			bpiRepository.save(gbp);
			bpiRepository.save(eur);
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
			return "Coindesk API 取得失敗";
		}
		return "Coindesk API 資料存入DB完成...";
	}
	
	/**
	 * 取得 DB 資料
	 * 
	 * @return
	 */
	@GetMapping("/getdata")
	public List<Bpi> getDBData() {
		return bpiRepository.findAll();

	}
	
	/**
	 * 更新 DB 資料
	 * 
	 * @return
	 */
	@PostMapping("/updata")
	public String upData(@RequestBody Map<String, String> jsonMap) {
		// 1.查詢相關資料
		Bpi bpi = getDBData().stream().filter(e -> e.get幣別().contains(jsonMap.get("幣別"))).findAny().get();
		// 2. 給值
		jsonMap.entrySet().forEach(e -> {
			if(e.getKey().contains("幣別")) {
				bpi.set幣別(e.getValue());				
			}
			if(e.getKey().contains("代號")) {
				bpi.set代號(e.getValue());				
			}
			if(e.getKey().contains("匯率")) {
				bpi.set匯率(e.getValue());				
			}
			if(e.getKey().contains("說明")) {
				bpi.set說明(e.getValue());				
			}
			if(e.getKey().contains("匯率_浮點數")) {
				bpi.set匯率_浮點數(Double.valueOf(e.getValue()));				
			}
		});	
		// 更新
		bpiRepository.save(bpi);
		
		return "DB 資料更新完成...";
	}
	
	/**
	 * 刪除 DB 資料
	 * 
	 * @param jsonMap
	 * @return
	 */
	@DeleteMapping("/deletedata")
	public String deleteData(@RequestBody Map<String, String> jsonMap) {
		// 1. 查詢相關資料
		Bpi bpi = getDBData().stream().filter(e -> e.get幣別().contains(jsonMap.get("幣別"))).findAny().get();
		// 2. 刪除
		bpiRepository.delete(bpi);
		
		return "DB 資料刪除完成...";
	}
}
