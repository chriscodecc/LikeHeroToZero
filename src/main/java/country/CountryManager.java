package country;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.inject.Named;
import jakarta.persistence.PersistenceException;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.charts.line.LineChartModel;

import ch.qos.logback.classic.Logger;
import charts.ChartView;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.AjaxBehaviorEvent;
import hibernate.*;


@Named
@SessionScoped
public class CountryManager implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DBConnectionManager dbCon;
	//private Collection<Country> countryList = new ArrayList<Country>();
	private List<Country> countryList = new ArrayList<Country>();
	private List<CountryChange> countryList_changes = new ArrayList<CountryChange>();
	private String selectedCountryCode;
	private String addselectedCountry;
	private int addselectedYear;
	private double addnewdata;
	


	private String newCountry;
	private Integer newYear;
	
	
	public double getAddnewdata() {
		return addnewdata;
	}

	public void setAddnewdata(double addnewdata) {
		this.addnewdata = addnewdata;
	}

	private List<Integer> allYear;
	
	public List<Integer> getAllYear() {
		return allYear;
	}

	public void setAllYear(List<Integer> allYear) {
		this.allYear = allYear;
	}

	public String getAddselectedCountry() {
		return addselectedCountry;
	}

	public void setAddselectedCountry(String addselectCountry) {
		this.addselectedCountry = addselectCountry;
	}

	public Integer getAddselectedYear() {
		return addselectedYear;
	}

	public void setAddselectedYear(Integer addselectYear) {
		this.addselectedYear = addselectYear;
	}

	private ChartView chartView;
	//private LineChartModel lineChartModel;

	public CountryManager() {
	}
	
	
	@PostConstruct
	public void init() {
		System.out.println("CM Initialzed...");
		dbCon = new DBConnectionManager();
		countryList = (List<Country>) dbCon.getAllCountries();	
		countryList_changes = (List<CountryChange>) dbCon.getAllCountries_changes();
		chartView = new ChartView(countryList);
		
		allYear = new ArrayList<Integer>();
		
		
		allYear.add(1990);
		allYear.add(2000);
		allYear.add(2014);
		allYear.add(2015);
		allYear.add(2016);
		allYear.add(2017);
		allYear.add(2018);
		allYear.add(2019);
		allYear.add(2020);
		allYear.add(2021);
		allYear.add(2022);
		allYear.add(2023);
		allYear.add(2024);
		addnewdata = countryList.get(0).getYr1990();
		newYear = 1990;
		newCountry = countryList.get(0).countryName;
		selectedCountryCode = countryList.get(0).getCountryCode();
	}
	
	public List<CountryChange> getCountryList_changes() {
		return countryList_changes;
	}

	public void setCountryList_changes(List<CountryChange> countryList_changes) {
		this.countryList_changes = countryList_changes;
	}

	public LineChartModel getLineChartModel() {
		return chartView.getLineModel();
	}


	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountryCode = selectedCountry;
	}
	
	public String getSelectedCountry() {
		return selectedCountryCode;
	}

	public Collection<Country> getCountryList() {
		return countryList;
	}
	
	public LineChartModel lineModel() {
		System.out.println("geLine " + selectedCountryCode);
		return chartView.getLineModel();
	}
	
	public void updateChart() {
		chartView.loadChartFromDB(selectedCountryCode);
	}
	
	public void onCountryChange(AjaxBehaviorEvent  event){
		  newCountry = (String) ((UIInput) event.getComponent()).getValue();
		  try {
			changeValuesInForm();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onYearChange(AjaxBehaviorEvent  event){
		newYear = (Integer) ((UIInput) event.getComponent()).getValue();
		try {
			changeValuesInForm();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void commitChangesInForm() throws Exception {
		Country c = getCountry(newCountry);
		CountryChange cc = new CountryChange(c.getCountryName(), 
											 c.getCountryCode(), 
											 newYear, 
											 c.getDynamicYear(newYear), 
											 addnewdata);
		dbCon.commitToChangesTable(cc);
		countryList_changes = (List<CountryChange>) dbCon.getAllCountries_changes();
	}
	
	public void changeValuesInForm() throws Exception{
		Country c = getCountry(newCountry);
		//CountryChange cc = new CountryChange(c.getCountryName(), c.getCountryCode(), newYear, c.getDynamicYear(newYear), addnewdata);
		//dbCon.commitToChangesTable(cc);
		//countryList_changes = (List<CountryChange>) dbCon.getAllCountries_changes();
		
		addnewdata = c.getDynamicYear(newYear);
	}
	
	public void commitData() throws Exception {
		Country c = getCountry(newCountry);
		c.setDynamicYear(newYear, addnewdata);
		
		dbCon.commitChanges(c);
	}
	
	/**
	 * takes a name returns a country
	 * @param countryname
	 * @return
	 */
	public Country getCountry(String countryname) {
		for(Country c : countryList) {
			if(c.countryName.equals(countryname)) {
				return c;
			}
		}
		return null;
	}
	
	public Country getCountry() {
		for(Country c : countryList) {
			if(c.getCountryCode().equals(selectedCountryCode)) {
				return c;
			}
		}
		return null;
	}
	
	public void add(ActionEvent event) {
	    UIComponent component = event.getComponent();
	    DataTable dataTable = (DataTable) component.getParent().getParent();
	    CountryChange countrychange = (CountryChange) dataTable.getRowData();  
	   try {
		   Country commitCountry = ccToCountryConverter(countrychange);
		   dbCon.commitChanges(commitCountry);
		   dbCon.removeFromChangeTable(countrychange);
		   countryList_changes = (List<CountryChange>) dbCon.getAllCountries_changes();
	   } catch (IllegalArgumentException e) {
	        // null or invalied
	        e.printStackTrace();
	    } catch (PersistenceException e) {
	        // JPA Error
	        e.printStackTrace();
	    } catch (Exception e) {
	        // others
	        e.printStackTrace();
	    }
	    
	}

	public void remove(ActionEvent event) {
	    UIComponent component = event.getComponent();
	    DataTable dataTable = (DataTable) component.getParent().getParent();
	    CountryChange countrychange = (CountryChange) dataTable.getRowData();
	    
	    dbCon.removeFromChangeTable(countrychange);
	    countryList_changes = (List<CountryChange>) dbCon.getAllCountries_changes();
	}
	
	public Country ccToCountryConverter(CountryChange cc) throws Exception{
			Country c = getCountry(cc.countryName);
			c.setDynamicYear(cc.getYear(), cc.getNew_data());
			return c;
	}
}
