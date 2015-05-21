package algorithm;

import java.io.BufferedWriter;
import java.io.IOException;

import domain.Graph;

public class DefaultPrintingStrategy implements PrintingStrategy {

	@Override
	public void printGraph(Graph g, BufferedWriter writer) {
		try {
			writer.write("" + g.getColorNumber());
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
