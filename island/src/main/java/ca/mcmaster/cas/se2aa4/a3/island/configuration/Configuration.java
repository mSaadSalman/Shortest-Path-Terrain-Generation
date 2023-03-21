package ca.mcmaster.cas.se2aa4.a3.island.configuration;

import org.apache.commons.cli.*;

public class Configuration {

    public static final String INPUT = "i";
    public static final String OUTPUT = "o";
    public static final String MODE = "mode";
    public static final String SHAPE = "shape";

    private CommandLine cli;

    public Configuration(String[] args) {
        try {
            this.cli = parser().parse(options(), args);

        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }
    }

    private CommandLineParser parser() {
        return new DefaultParser();
    }

    public String input() {
        return this.cli.getOptionValue(INPUT);
    }

    public String output() {
        return this.cli.getOptionValue(OUTPUT, "output.svg");
    }

    public String mode() {
        return this.cli.getOptionValue(MODE);
    }

    public String shape() {
        return this.cli.getOptionValue(SHAPE);
    }

    private Options options() {
        Options options = new Options();
        options.addOption(new Option(INPUT, true, "Input file (MESH)"));
        options.addOption(new Option(OUTPUT, true, "Output file (MESH)"));
        options.addOption(Option.builder()
                .longOpt(MODE)
                .hasArg()
                .argName("mode")
                .desc("Island type")
                .build());
        options.addOption(Option.builder()
                .longOpt(SHAPE)
                .hasArg()
                .argName("shape")
                .desc("Island shape")
                .build());
        return options;
    }
}
