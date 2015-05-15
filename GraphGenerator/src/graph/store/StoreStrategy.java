package graph.store;

import graph.Graph;

import java.io.IOException;
import java.io.Writer;

public interface StoreStrategy
{
	void store(Writer writer, Graph graph) throws IOException;
}
