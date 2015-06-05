package main;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PatternOptionBuilder;

import algorithm.GraphColoringAlgorithm;
import domain.Graph;

public class Main
{
	private static final String INITIAL_TEMP = "ti";
	private static final String MIN_TEMP = "tm";
	private static final String ALFA = "a";
	private static final String INPUT_FILE = "i";
	private static final String CONSOLE_OUTPUT = "c";
	private static final String OUTPUT_FILE = "o";
	private static final String EPOCH_PARAM = "k";

	public static void main(String[] args)
	{
		Options options = createOptions();
		CommandLineParser parser = new DefaultParser();
		HelpFormatter helpFormatter = new HelpFormatter();
		try
		{
			// Wczytanie parametrów
			CommandLine commandLine = parser.parse(options, args);
			Double alfa = (Double) commandLine.getParsedOptionValue(ALFA);
			Double initialTemp = (Double) commandLine.getParsedOptionValue(INITIAL_TEMP);
			Double minTemp = (Double) commandLine.getParsedOptionValue(MIN_TEMP);
			Long k = (Long) commandLine.getParsedOptionValue(EPOCH_PARAM);
			String inputFile = commandLine.getOptionValue(INPUT_FILE);

			// Inicjacja i uruchomienie
			GraphColoringAlgorithm algorithm = new GraphColoringAlgorithm();
			Graph graph = algorithm.readGraph(new File(inputFile));
			graph = algorithm.colorGraph(graph, initialTemp, minTemp, alfa, k);

			// Wypisanie wyników
			algorithm.printResult(graph, commandLine.getOptionValue(OUTPUT_FILE), commandLine.hasOption(CONSOLE_OUTPUT));
		} catch (ParseException e)
		{
			System.out.println(e);
			helpFormatter.printHelp("GraphColoringAlgorithm", options);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static Options createOptions()
	{
		Options result = new Options();
		result.addOption(createOption(INITIAL_TEMP, "initial_temperature", true, "Initial value of temperature", PatternOptionBuilder.NUMBER_VALUE));
		result.addOption(createOption(MIN_TEMP, "minimum_temperature", true, "Minimum value of temperature", PatternOptionBuilder.NUMBER_VALUE));
		result.addOption(createOption(ALFA, "alfa", true, "cooling factor", PatternOptionBuilder.NUMBER_VALUE));
		result.addOption(createOption(EPOCH_PARAM, "epochParam", true, "epoch param", PatternOptionBuilder.NUMBER_VALUE));
		result.addOption(createOption(INPUT_FILE, "input_file", true,
				"File where input graph is stored. If not specified, graph is taken from standard input.", false));
		result.addOption(createOption(OUTPUT_FILE, "output_file", true, "File where to store output result", false));
		result.addOption(createOption(CONSOLE_OUTPUT, "console_output", false, "If set, output is printed on console", false));
		return result;
	}

	private static Option createOption(String opt, String longOpt, boolean hasArg, String description)
	{
		return createOption(opt, longOpt, hasArg, description, true, String.class);
	}

	private static Option createOption(String opt, String longOpt, boolean hasArg, String description, Class<?> type)
	{
		return createOption(opt, longOpt, hasArg, description, true, type);
	}

	private static Option createOption(String opt, String longOpt, boolean hasArg, String description, boolean isRequired)
	{
		return createOption(opt, longOpt, hasArg, description, isRequired, String.class);
	}

	private static Option createOption(String opt, String longOpt, boolean hasArg, String description, boolean isRequired, Class<?> type)
	{
		Option option = new Option(opt, longOpt, hasArg, description);
		option.setRequired(isRequired);
		option.setType(type);
		return option;
	}
}
