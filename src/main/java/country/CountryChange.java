package country;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@jakarta.persistence.Table(name="worlddata_changes")
public class CountryChange {
	@Id  @Column(name="id") int id;
	@Column(name="country_name") String countryName;
	@Column(name="country_code")private String countryCode;
	@Column(name="year") private int year; 
	@Column(name="original_data") private Double original_data;  
	@Column(name="new_data") private Double new_data;
	
	public CountryChange() {

	}
	
	public CountryChange(String name, String code, int year, Double orginal_data, Double new_data) {
		this.countryName = name;
		this.countryCode = code;
		this.year = year;
		this.original_data = orginal_data;
		this.new_data = new_data;
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
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Double getOriginal_data() {
		return original_data;
	}
	public void setOriginal_data(Double original_data) {
		this.original_data = original_data;
	}
	public Double getNew_data() {
		return new_data;
	}
	public void setNew_data(Double new_data) {
		this.new_data = new_data;
	} 

}
