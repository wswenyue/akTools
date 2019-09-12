package com.wswenyue.aktools;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class App {
    private static final List<ICall> mCalls = new ArrayList<>();

    static {
        mCalls.add(new TimeCall());
    }

    public static void main(String[] args) {
        if (args == null || args.length <= 0) {
            printHelp();
            return;
        }
        String cmd = args[0];
        for (ICall mCall : mCalls) {
            if (CommonTools.equals(cmd, mCall.getCallName())) {
                mCall.run(Arrays.copyOfRange(args, 1, args.length));
                return;
            }
        }
        printHelp();
    }

    private static void printHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append("Usage:\tak COMMAND arg...\n");
        sb.append("\n");
        sb.append("COMMAND:\n");
        for (ICall mCall : mCalls) {
            sb.append("\t").append(mCall.getCallName()).append(":\t").append(mCall.getDesc()).append("\n");
        }
        sb.append("\n");
        sb.append("Run 'ak COMMAND --help/-h' for more information on a command.");
        System.out.println(sb.toString());
    }

    private static class TimeCall implements ICall {
        @Parameter(names = {"-f", "--format"}, description = "Format an input time string output.")
        private String formatStr = null;
        @Parameter(names = {"-h", "--help"}, help = true, description = "print this Usage.")
        private boolean help;
        @Parameter(names = {"-c", "--convert"}, arity = 2, description = "convert time form input format.")
        private List<String> convert = null;
        @Parameter(names = {"-q", "--quiet"}, description = "quiet minimal output.")
        private boolean quiet = false;

        @Override
        public String getCallName() {
            return "time";
        }

        @Override
        public String getDesc() {
            return "time tools for print cur time and format time";
        }

        @Override
        public void run(String[] args) {
            try {

                JCommander cmd = JCommander
                        .newBuilder()
                        .addObject(this)
                        .build();
                cmd.setProgramName(getCallName());
                cmd.parse(args);

                if (!quiet) {
                    long curTime = System.currentTimeMillis();
                    System.out.println("curTime(ms)\t:" + curTime);
                    System.out.println("curTime(s)\t:" + TimeUnit.MILLISECONDS.toSeconds(curTime));
                    System.out.println("default format\t:" + TimeTools.PATTERN_DEFAULT);
                    System.out.println(String.format("curTime(format)\t:%s", TimeTools.formatTime(curTime, TimeUnit.MILLISECONDS)));
                    System.out.println();
                }
                if (help) {
                    cmd.usage();
                    return;
                }
                if (formatStr != null) {
                    System.out.println(quiet ? TimeTools.formatTime(formatStr) : ("format time\t:" + TimeTools.formatTime(formatStr)));
                }

                if (convert != null) {
                    if (!quiet) {
                        System.out.println("Convert:");
                    }
                    for (int i = 0; i < convert.size(); i += 2) {
                        String format = convert.get(i);
                        String timeStr = convert.get(i + 1);
                        System.out.println(timeStr + "\t:" + TimeTools.convertTime(format, timeStr));
                    }
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

}
