import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Arc2D;
import java.util.List;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.annotations.XYShapeAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

// TODO: Auto-generated Javadoc
/**
 * The Class Graph.
 */
public class Graph {

	/** The values4. */
	List<Float> values1, values2, values3, values4;

	/**
	 * Instantiates a new graph.
	 *
	 * @param values1
	 *            the values1
	 * @param values2
	 *            the values2
	 * @param values3
	 *            the values3
	 * @param values4
	 *            the values4
	 */
	public Graph(List<Float> values1, List<Float> values2, List<Float> values3, List<Float> values4) {
		this.values1 = values1;
		this.values2 = values2;
		this.values3 = values3;
		this.values4 = values4;
		XYDataset dataSet = createData();
		NumberAxis numberaxis = new NumberAxis();
		numberaxis.setAutoRangeIncludesZero(false);
		NumberAxis numberaxis1 = new NumberAxis();
		numberaxis1.setAutoRangeIncludesZero(false);
		XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(false, true);
		XYPlot xyplot = new XYPlot(dataSet, numberaxis, numberaxis1, xylineandshaperenderer);
		JFreeChart chart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
		XYPlot plot = chart.getXYPlot();
		XYLineAnnotation line1 = new XYLineAnnotation(1, -1, 1, 1, new BasicStroke(2f), Color.blue);
		plot.addAnnotation(line1);
		XYLineAnnotation line2 = new XYLineAnnotation(-1, 1, 1, 1, new BasicStroke(2f), Color.blue);
		plot.addAnnotation(line2);
		XYLineAnnotation line3 = new XYLineAnnotation(-1, -1, 1, -1, new BasicStroke(2f), Color.blue);
		plot.addAnnotation(line3);
		XYLineAnnotation line4 = new XYLineAnnotation(-1, -1, -1, 1, new BasicStroke(2f), Color.blue);
		plot.addAnnotation(line4);
		Arc2D.Double arc = new Arc2D.Double(-1, -1, 2, 2, 0, 360, Arc2D.OPEN);
		plot.addAnnotation(new XYShapeAnnotation(arc, new BasicStroke(2f), Color.red));
		ChartFrame frame = new ChartFrame("Graph", chart);

		frame.pack();
		frame.setSize(600, 600);
		frame.setVisible(true);
	}

	/**
	 * Creates the data.
	 *
	 * @return the XY dataset
	 */
	private XYDataset createData() {
		XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("Inside Circle");
		XYSeries series2 = new XYSeries("Outside Circle");
		for (int i = 0; i < values1.size(); i++) {
			series1.add(values1.get(i), values2.get(i));
		}
		for (int i = 0; i < values3.size(); i++) {
			series2.add(values3.get(i), values4.get(i));
		}
		xySeriesCollection.addSeries(series1);
		xySeriesCollection.addSeries(series2);
		return xySeriesCollection;
	}
}