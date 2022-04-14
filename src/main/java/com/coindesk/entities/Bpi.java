package com.coindesk.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bpi")
public class Bpi {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column
	private String 幣別;
	@Column
	private String 代號;
	@Column
	private String 匯率;
	@Column
	private String 說明;
	@Column
	private Double 匯率_浮點數;

	private String 更新時間;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String get幣別() {
		return 幣別;
	}

	public void set幣別(String 幣別) {
		this.幣別 = 幣別;
	}

	public String get代號() {
		return 代號;
	}

	public void set代號(String 代號) {
		this.代號 = 代號;
	}

	public String get匯率() {
		return 匯率;
	}

	public void set匯率(String 匯率) {
		this.匯率 = 匯率;
	}

	public String get說明() {
		return 說明;
	}

	public void set說明(String 說明) {
		this.說明 = 說明;
	}

	public Double get匯率_浮點數() {
		return 匯率_浮點數;
	}

	public void set匯率_浮點數(Double 匯率_浮點數) {
		this.匯率_浮點數 = 匯率_浮點數;
	}

	public String get更新時間() {
		return 更新時間;
	}

	public void set更新時間(String 更新時間) {
		this.更新時間 = 更新時間;
	}

	@Override
	public String toString() {
		return "Bpi [id=" + id + ", 幣別=" + 幣別 + ", 代號=" + 代號 + ", 匯率=" + 匯率 + ", 說明=" + 說明 + ", 匯率_浮點數=" + 匯率_浮點數
				+ ", 更新時間=" + 更新時間 + "]";
	}

}
