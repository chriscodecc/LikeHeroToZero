/***package Charts;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;

import jakarta.annotation.PostConstruct;
//import javax.inject.Inject;
import jakarta.inject.Inject;

//import javax.inject.Named;
import org.primefaces.model.charts.line.LineChartModel;
import com.project.CountryManager;


import jakarta.inject.Named;

@Named
@RequestScoped
public class ChartViewManager implements Serializable{
	private static final long serialVersionUID = 1L;

	ChartView chartView;
	
	@Inject
	private CountryManager countryManager;
	
	public ChartViewManager() {
		
	}
	
	@PostConstruct
	public void init() {
		System.out.println("CVM Initialzed...");
		//chartView = new ChartView();
	}
	
	public void updateChart() {
		System.out.println("CVM updateChart:");
		//chartView.updateLineModel(countryManager.getSelectedCountry());
	}
	
	public LineChartModel getLineChartModel() {
		System.out.println("CVM getLineChart");
		return this.chartView.getLineModel();
	}
	
}***/
package charts;


