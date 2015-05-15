package graph.generation;

import graph.Graph;

public interface GenerationStrategy
{
	Graph generate(int vertexNumber, double probability);
}
