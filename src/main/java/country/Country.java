package country;

import java.io.Serializable;
import java.lang.reflect.Method;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="worlddataco2")
public class Country implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="id") int id;
	@Column(name="country_name") String countryName;
	@Column(name="country_code")private String countryCode;
	@Column(name="YR1990") private Double yr1990; 
	@Column(name="YR2000") private Double yr2000; 
	@Column(name="YR2014") private Double yr2014; 
	@Column(name="YR2015") private Double yr2015; 
	@Column(name="YR2016") private Double yr2016; 
	@Column(name="YR2017") private Double yr2017; 
	@Column(name="YR2018") private Double yr2018; 
	@Column(name="YR2019") private Double yr2019; 
	@Column(name="YR2020") private Double yr2020; 
	@Column(name="YR2021") private Double yr2021; 
	@Column(name="YR2022") private Double yr2022; 
	@Column(name="YR2023") private Double yr2023; 
	@Column(name="YR2024") private Double yr2024;
	

	public Country() {
	}
	
	public Double getYr1990() {
		return yr1990;
	}

	public void setYr1990(Double yr1990) {
		this.yr1990 = yr1990;
	}

	public Double getYr2000() {
		return yr2000;
	}

	public void setYr2000(Double yr2000) {
		this.yr2000 = yr2000;
	}

	public Double getYr2014() {
		return yr2014;
	}

	public void setYr2014(Double yr2014) {
		this.yr2014 = yr2014;
	}

	public Double getYr2015() {
		return yr2015;
	}

	public void setYr2015(Double yr2015) {
		this.yr2015 = yr2015;
	}

	public Double getYr2016() {
		return yr2016;
	}

	public void setYr2016(Double yr2016) {
		this.yr2016 = yr2016;
	}

	public Double getYr2017() {
		return yr2017;
	}

	public void setYr2017(Double yr2017) {
		this.yr2017 = yr2017;
	}

	public Double getYr2018() {
		return yr2018;
	}

	public void setYr2018(Double yr2018) {
		this.yr2018 = yr2018;
	}

	public Double getYr2019() {
		return yr2019;
	}

	public void setYr2019(Double yr2019) {
		this.yr2019 = yr2019;
	}

	public Double getYr2020() {
		return yr2020;
	}

	public void setYr2020(Double yr2020) {
		this.yr2020 = yr2020;
	}

	public Double getYr2021() {
		return yr2021;
	}

	public void setYr2021(Double yr2021) {
		this.yr2021 = yr2021;
	}

	public Double getYr2022() {
		return yr2022;
	}

	public void setYr2022(Double yr2022) {
		this.yr2022 = yr2022;
	}

	public Double getYr2023() {
		return yr2023;
	}

	public void setYr2023(Double yr2023) {
		this.yr2023 = yr2023;
	}

	public Double getYr2024() {
		return yr2024;
	}

	public void setYr2024(Double yr2024) {
		this.yr2024 = yr2024;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public double getDynamicYear(int year) throws Exception{
		Method method = this.getClass().getMethod("getYr" + year);
		Double result = (method != null) ? (Double) method.invoke(this) : null;
		return (result != null) ? result : 0.0;
	}
	
	public void setDynamicYear(int year, double data) throws Exception{
		System.out.println(year + " " + data);
		Method method = this.getClass().getMethod("setYr" + year, Double.class);
		method.invoke(this, data);
		System.out.println(this + " " + data);
	}
	
	public String getDynamicDisplayValueYear(int year) throws Exception{
		Method method = this.getClass().getMethod("getYr" + year);
		Double result = (method != null) ? (Double) method.invoke(this) : null;
		return (result == null || result == 0.0) ? "N/A " : result.toString();
	}
}
