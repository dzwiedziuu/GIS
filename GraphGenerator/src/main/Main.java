package main;

import graph.GraphGenerator;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main
{
	private static final String OUTPUT_FILE = "of";
	private static final String OUTPUT_DIR = "od";
	private static final String PROBABILITY = "p";
	private static final String VERTEX_NUMBER = "v";
	private static final String CONSOLE_OUTPUT = "c";

	public static void main(String[] args)
	{
		Options options = createOptions();
		CommandLineParser parser = new DefaultParser();
		HelpFormatter helpFormatter = new HelpFormatter();
		try
		{
			CommandLine commandLine = parser.parse(options, args);
			GraphGenerator graphGenerator = new GraphGenerator();
			int vNum = Integer.parseInt(commandLine.getOptionValue(VERTEX_NUMBER));
			double prob = Double.parseDouble(commandLine.getOptionValue(PROBABILITY));
			graphGenerator.generateGraph(vNum, prob);
			graphGenerator.storeGraph(
					createFile(commandLine.getOptionValue(OUTPUT_DIR), commandLine.getOptionValue(OUTPUT_FILE)),
					commandLine.hasOption(CONSOLE_OUTPUT));
		} catch (ParseException e)
		{
			System.out.println(e.getMessage());
			helpFormatter.printHelp("GraphGenerator", options);
		} catch (NumberFormatException e)
		{
			System.out.println("One of parameter -" + VERTEX_NUMBER + " or -" + PROBABILITY + " could not be parsed!");
			helpFormatter.printHelp("GraphGenerator", options);
		}
	}

	private static File createFile(String outputDir, String outputFile)
	{
		if (outputFile == null)
			return null;
		return outputDir == null ? new File(outputFile) : new File(outputDir, outputFile);
	}

	private static Options createOptions()
	{
		Options result = new Options();
		result.addOption(createOption(VERTEX_NUMBER, "vertex_number", true, "Number of vertex in created graph"));
		result.addOption(createOption(PROBABILITY, "probability", true,
				"Probability of creating edge between every two vertex"));
		result.addOption(createOption(OUTPUT_DIR, "output_direcroty", true, "Directory where to store output graph",
				false));
		result.addOption(createOption(OUTPUT_FILE, "output_file", true, "File where to store output graph", false));
		result.addOption(createOption(CONSOLE_OUTPUT, "console_output", false, "If set, output is printed on console",
				false));
		return result;
	}

	private static Option createOption(String opt, String longOpt, boolean hasArg, String description)
	{
		return createOption(opt, longOpt, hasArg, description, true);
	}

	private static Option createOption(String opt, String longOpt, boolean hasArg, String description,
			boolean isRequired)
	{
		Option option = new Option(opt, longOpt, hasArg, description);
		option.setRequired(isRequired);
		return option;
	}
}
