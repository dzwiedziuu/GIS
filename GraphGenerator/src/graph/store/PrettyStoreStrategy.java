package graph.store;

import graph.Graph;
import graph.Vertex;

import java.io.IOException;
import java.io.Writer;

public class PrettyStoreStrategy implements StoreStrategy
{
	@Override
	public void store(Writer writer, Graph graph) throws IOException
	{
		writer.write("\t");
		for (int i = 0; i < graph.getSize(); i++)
			writer.write(i + "\t");
		writer.write("\n");
		for (int i = 0; i < graph.getSize(); i++)
		{
			writer.write(i + "\t");
			Vertex v = graph.getVertex(i);
			for (int j = 0; j < v.getSize(); j++)
			{
				String toStr = v.getNeighbour(j) ? "1" : "0";
				writer.write(toStr + "\t");
			}
			writer.write("\n");
		}
		writer.flush();
	}
}
