package com.youthclub.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;

/**
 * @author Frank
 */
public class ProcessRunner extends Observable {

    public static final int STD = 0;
    public static final int ERR = 1;

    private StringBuffer stdOutput;
    private StringBuffer errOutput;
    private boolean stdOutputCompleted;
    private boolean errOutputCompleted;
    private File directory;
    private String[] command;

    public ProcessRunner(final File directory, final String... command) {
        this.directory = directory;
        this.command = command;
    }

    public ProcessRunner(final String... command) {
        this.command = command;
    }

    public void start() throws IOException {
        stdOutput = new StringBuffer();
        errOutput = new StringBuffer();

        ProcessBuilder pb = new ProcessBuilder(command);
        if (directory != null) {
            pb.directory(directory);
        }
        Process p = null;
        p = pb.start();
        InputStream is = p.getInputStream();
        InputStream es = p.getErrorStream();
        read(is, stdOutput, STD);
        read(es, errOutput, ERR);
    }

    private void read(final InputStream is, final StringBuffer output, final int type) {
        final BufferedInputStream bis = new BufferedInputStream(is);
        new Thread() {
            @Override
            public void run() {
                int c;
                try {
                    while ((c = bis.read()) != -1) {
                        output.append((char) c);
                    }
                } catch (IOException e) {
                    output.append(e.getMessage());
                }
                switch (type) {
                    case STD:
                        stdOutputCompleted = true;
                        break;
                    case ERR:
                        errOutputCompleted = true;
                        break;
                }
                if (completed()) {
                    setChanged();
                    notifyObservers();
                }
            }
        }.start();
    }

    public synchronized String getOutput(int output) {
        StringBuffer sb = null;
        switch (output) {
            case STD:
                sb = stdOutput;
                break;
            case ERR:
                sb = errOutput;
                break;
        }
        return sb == null ? null : sb.toString();
    }

    public synchronized String getOutput(int start, int output) {
        StringBuffer sb = null;
        switch (output) {
            case STD:
                sb = stdOutput;
                break;
            case ERR:
                sb = errOutput;
                break;
        }
        if (sb == null || start < 0 || start >= sb.length()) {
            return null;
        }
        return sb.substring(start);
    }

    public synchronized String getOutput(int start, int end, int output) {
        StringBuffer sb = null;
        switch (output) {
            case STD:
                sb = stdOutput;
                break;
            case ERR:
                sb = errOutput;
                break;
        }
        if (sb == null || start < 0 || start >= end || sb.length() < end) {
            return null;
        }
        return sb.substring(start, end);
    }

    public synchronized boolean completed() {
        return stdOutputCompleted && errOutputCompleted;
    }

    public int length(int output) {
        switch (output) {
            case STD:
                return stdOutput == null ? 0 : stdOutput.length();
            case ERR:
                return errOutput == null ? 0 : errOutput.length();
        }
        return 0;
    }
}
