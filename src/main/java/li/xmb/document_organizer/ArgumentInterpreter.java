package li.xmb.document_organizer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArgumentInterpreter {
	/**
	 * Defines the logger instance to be used by this class.
	 */
	private final static Logger LOG = LoggerFactory.getLogger(ArgumentInterpreter.class);

	public void parseArguments(final String[] args) throws ParseException {
		final Option help = new Option("h", "help", false, "help");
		final Option version = new Option("v", "version", false, "version");
		final Option silent = new Option("s", "silent", false, "Silent mode");

		final Options options = new Options();
		options.addOption(help);
		options.addOption(version);
		options.addOption(silent);

		// create the parser
		final CommandLineParser parser = new DefaultParser();
		// parse the command line arguments
		final CommandLine cmd = parser.parse(options, args);

		if (cmd.hasOption(help.getOpt())) {
			printHelp(options);
		}

		if (cmd.hasOption(version.getOpt())) {
			printVersion();
		}
	}

	private void printHelp(final Options options) {
		final HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("help", options);

		System.exit(0);
	}

	private void printVersion() {
		LOG.info("alpha");
		// Terminate application
		System.exit(0);
	}

}
