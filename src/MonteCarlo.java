import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

// TODO: Auto-generated Javadoc
/**
 * The Class MonteCarlo.
 */
public class MonteCarlo extends JFrame {

	/** The content pane. */
	private JPanel contentPane;

	/** The num points. */
	private JTextField numPoints;

	/** The lbl error. */
	private JLabel lblResult, lblError;

	/** The progress bar it. */
	private JProgressBar progressBarIt;

	/** The chckbx graph. */
	private JCheckBox chckbxGraph;

	/** The btn view graph. */
	private JButton btnViewGraph;

	/** The values4. */
	private List<Float> values1, values2, values3, values4;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					try {
						UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
					MonteCarlo frame = new MonteCarlo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Instantiates a new monte carlo.
	 */
	public MonteCarlo() {
		setTitle("Monte Carlo Method");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 211, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		numPoints = new JTextField();
		numPoints.setText("10000000");
		numPoints.setBounds(107, 6, 100, 28);
		contentPane.add(numPoints);
		numPoints.setColumns(10);

		JLabel lblRandomPoints = new JLabel("Random Points");
		lblRandomPoints.setBounds(6, 12, 94, 16);
		contentPane.add(lblRandomPoints);

		final JButton btnCalculatePi = new JButton("Calculate Pi");
		btnCalculatePi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Thread t = new Thread() {
					@Override
					public void run() {
						btnViewGraph.setEnabled(chckbxGraph.isSelected());
						if (chckbxGraph.isSelected()) {
							values1 = new ArrayList<Float>();
							values2 = new ArrayList<Float>();
							values3 = new ArrayList<Float>();
							values4 = new ArrayList<Float>();
						}
						calculate();
					}
				};
				t.start();
			}
		});
		btnCalculatePi.setBounds(6, 40, 117, 29);
		contentPane.add(btnCalculatePi);

		lblResult = new JLabel("Result: ");
		lblResult.setBounds(6, 96, 199, 16);
		contentPane.add(lblResult);

		lblError = new JLabel("Error: ");
		lblError.setBounds(6, 113, 199, 16);
		contentPane.add(lblError);

		progressBarIt = new JProgressBar();
		progressBarIt.setStringPainted(true);
		progressBarIt.setBounds(6, 72, 199, 20);
		contentPane.add(progressBarIt);

		btnViewGraph = new JButton("View Graph");
		btnViewGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Graph(values1, values2, values3, values4);
			}
		});
		btnViewGraph.setEnabled(false);
		btnViewGraph.setBounds(6, 134, 199, 29);
		contentPane.add(btnViewGraph);

		chckbxGraph = new JCheckBox("Graph");
		chckbxGraph.setBounds(138, 40, 69, 29);
		contentPane.add(chckbxGraph);

		JButton btnCopyError = new JButton("Copy Error");
		btnCopyError.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				double error = Double.parseDouble(lblError.getText().substring(7));
				error = Math.abs(error);
				DecimalFormat df = new DecimalFormat("#.", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
				df.setMaximumFractionDigits(340);
				String strError = df.format(error);
				strError = strError.substring(0, 11);
				StringSelection stringSelection = new StringSelection(strError);
				Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
				clpbrd.setContents(stringSelection, null);
			}
		});
		btnCopyError.setBounds(6, 168, 201, 29);
		contentPane.add(btnCopyError);
	}

	/**
	 * Calculate.
	 */
	private void calculate() {
		progressBarIt.setValue(0);
		long points = 0;
		try {
			points = Long.parseLong(numPoints.getText());
		} catch (NumberFormatException e) {
			btnViewGraph.setEnabled(false);
			JOptionPane.showMessageDialog(null, "Format Error: Nice try. Enter a number next time.");
		}
		boolean graph = chckbxGraph.isSelected();
		Random random = new Random();
		double in = 0;
		for (long i = 0; i < points; i++) {
			double x = random.nextDouble() * 2 - 1;
			double y = random.nextDouble() * 2 - 1;
			try {
				if (x * x + y * y <= 1) {
					in++;
					if (graph) {
						values1.add((float) x);
						values2.add((float) y);
					}
				} else {
					if (graph) {
						values3.add((float) x);
						values4.add((float) y);
					}
				}
			} catch (OutOfMemoryError e) {
				btnViewGraph.setEnabled(false);
				JOptionPane.showMessageDialog(null, "Memory Error: Try graphing less points.");
				points = i;
				numPoints.setText("" + points);
			}
			progressBarIt.setValue((int) ((double) (i + 1) / points * 100));
		}
		double pi = in / points * 4;
		lblResult.setText("Result: " + Double.toString(pi));
		double error = pi - Math.PI;
		DecimalFormat df = new DecimalFormat("#.", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
		df.setMaximumFractionDigits(340);
		lblError.setText("Error: " + df.format(error));
	}
}