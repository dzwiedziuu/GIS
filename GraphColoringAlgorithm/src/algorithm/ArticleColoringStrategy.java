package algorithm;

import java.util.Map;

import domain.Graph;

public class ArticleColoringStrategy extends DefaultColoringStrategy
{
	@Override
	protected Integer getObjectiveFunction(Graph graph)
	{
		// E(2CiEi) - E(Ci^2)

		int result = 0;
		for (Integer color : graph.getAllColors())
		{
			int colorCnt = graph.getColorCnt(color);
			Integer edgeCnt = graph.getIncorrectEdgeCnt(color);
			if (edgeCnt == null)
				edgeCnt = 0;
			result += colorCnt * (edgeCnt * 2 - colorCnt);
		}
		return result;
	}

	private void incrementOrCreateNew(Map<Integer, Integer> map, Integer key)
	{
		Integer val = map.get(key);
		if (val == null)
			val = 0;
		val++;
		map.put(key, val);
	}
}
