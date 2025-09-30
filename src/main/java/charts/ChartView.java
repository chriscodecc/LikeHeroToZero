package charts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.primefaces.model.charts.*;
import org.primefaces.model.charts.line.*;
import org.primefaces.model.charts.optionconfig.title.Title;

import country.Country;
import hibernate.DBConnectionManager;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;




@Named
@ViewScoped 
public class ChartView implements Serializable{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LineChartModel lineModel;
	  private List<Country> countryList;
	  private ChartData data;
	  //private DBConnectionManager dbcon;
	  private LineChartDataSet dataSet;
	  private List<String> labels;
	  private LineChartOptions options;
	  private String defaultCountry;
	 
	    
	    public ChartView(Collection<Country> countryList) {
	    	// Log message to indicate that the ChartView constructor is being called
	    	System.out.println("CV Konstruktor...");
	    	
	      	// Initialize the list of countries
	    	this.countryList = (List<Country>) countryList;
	    	
	    	// Set the default country to the first entry in the list
	    	defaultCountry = this.countryList.get(0).getCountryCode();
	    	
	    	// Create a new line chart model for displaying CO2 data
	    	lineModel = new LineChartModel();

	    	// Initialize chart options for customizing the appearance
	    	options  = new LineChartOptions();        
	        Title title = new Title();
	        title.setDisplay(true);
	        title.setText("WORLD Co2 EMISSION");
	        options.setTitle(title);
	        lineModel.setOptions(options);
	        
	        // Load CO2 emission data from the database for the default country and populate the chart
	        loadChartFromDB(defaultCountry); 
		}
	    
	    @PostConstruct
	    public void init() {
	    	System.out.println("ChartView POST");  
	    }
	    
	    public LineChartModel getLineModel() {  
	    	//loadChartFromDB();
	    	return lineModel; 
	    }
	    
	    public void setDefaultCountry(String country) {  
	    	this.defaultCountry = country;
	    }
	    
	    public void loadChartFromDB(String country) {
	    	
	    	// Check if the country list is initialized
	        if(countryList != null) {
	        	
	        	// Iterate through the list of countries
		    	for(int i=0;i<countryList.size();i++) {
		    		
		    		 // Find the country that matches the selected country code
		    		if(countryList.get(i).getCountryCode().equals(country)) {
		    			
		    			// Create a list to hold CO2 emission values for selected years
		    		    List<Object> values = new ArrayList<>();
		    	        values.add(countryList.get(i).getYr1990());
		    	        values.add(countryList.get(i).getYr2000());
		    	        values.add(countryList.get(i).getYr2015());   
		    	        values.add(countryList.get(i).getYr2021());
		    	        values.add(countryList.get(i).getYr2022());
		    	        values.add(countryList.get(i).getYr2023());
		    	        
		    	        // Initialize the dataset for the line chart
		    	        dataSet = new LineChartDataSet();
		    	        dataSet.setFill(false); // Do not fill under the line
		    	        dataSet.setBorderColor("rgb("+(75)+","+(192)+","+(192)+")"); // Set line color
		    	        dataSet.setLineTension(0.3); // Smoothness of the line
		    	        dataSet.setData(values); // Set the data points
		    	        dataSet.setLabel(countryList.get(i).getCountryName()); //lable the data
		    	        
		    	        // Create chart data and set labels
		    	        data = new ChartData();   	
					    	labels = new ArrayList<>();
					        labels.add("1990");
					        labels.add("2000");
					        labels.add("2015");
					        labels.add("2021");
					        labels.add("2022");
					        labels.add("2023");   
				        data.setLabels(labels);
				        
				        // Add the dataset to the chart data
		    	        data.addChartDataSet(dataSet);
		    	        // Set the chart data to the line model
		    	        lineModel.setData(data);   
		    	        // Exit the loop after finding the matching country
		    	        break;
		    		}
		    	}
	        } else {
	        	System.out.println("FA!LED LOADING DATA FROM DATABASE...");
	        }
	    }
	    
	   /* public void updateLineModel(String country) { 
	    	System.out.println("updateChart: " + country);
	    	this.defaultCountry = country;
	    	
	    	for(int i=0;i<countryList.size();i++) {
	    		if(countryList.get(i).getCountryCode().equals(country)) {
	    			
	    			//dataSet = new LineChartDataSet();
	    		    List<Object> values = new ArrayList<>();
	    	        values.add(countryList.get(i).getYr1990());
	    	        values.add(countryList.get(i).getYr2000());
	    	        values.add(countryList.get(i).getYr2015());
	    	        values.add(countryList.get(i).getYr2021());
	    	        values.add(countryList.get(i).getYr2022());
	    	        values.add(countryList.get(i).getYr2023());
      
	    	        dataSet.setData(values);
	    	        dataSet.setFill(false);
	    	        dataSet.setLabel(countryList.get(i).getCountryName());
	    	        dataSet.setBorderColor("rgb("+(75)+","+(192)+","+(192)+")");
	    	        dataSet.setLineTension(0.1);
	    	        data.addChartDataSet(dataSet);	  
	    	        lineModel.setData(data);	
	    	        break;
	    		}
	    		
	    		//PrimeFaces.current().ajax().update("formid:chartOutput"); https://stackoverflow.com/questions/25339056/understanding-primefaces-process-update-and-jsf-fajax-execute-render-attributes
    	}	
	}*/
 }
