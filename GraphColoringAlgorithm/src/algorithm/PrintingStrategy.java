package algorithm;

import java.io.BufferedWriter;

import domain.Graph;

public interface PrintingStrategy {
	public void printGraph(Graph g, BufferedWriter writer);
}
