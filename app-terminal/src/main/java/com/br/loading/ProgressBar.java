package com.br.loading;

import com.br.config.ShellHelper;
import org.jline.terminal.Terminal;

public class ProgressBar {
	 private static final String CUU = "\u001B[A";
	    private static final String DL = "\u001B[1M";
	    
	    private String doneMarker = "=";
	    private String remainsMarker = "-";
	    private String leftDelimiter = "<";
	    private String rightDelimiter = ">";
	    
	    ShellHelper shellHelper;
	    
	    private boolean started = false;
		
	    public ProgressBar(ShellHelper shellHelper) {
	        this.shellHelper = shellHelper;
	    }
	    public void display(int percentage) {
	        if (!started) {
	            started = true;
	            shellHelper.getTerminal().writer().println();
	        }
	        int x = (percentage/5);
	        int y = 20-x;
	        String done = shellHelper.getSuccessMessage(new String(new char[x]).replace("\0", doneMarker));
	        String remains = new String(new char[y]).replace("\0", remainsMarker);
	        String progressBar = String.format("%s%s%s%s %d", leftDelimiter, done, remains, rightDelimiter, percentage);
	        shellHelper.getTerminal().writer().println(CUU + "\r" + DL + progressBar + "% ");
	        shellHelper.getTerminal().flush();
	    }
	public void display() {
		if (!started) {
			started = true;
			shellHelper.getTerminal().writer().println();
		}
		String done = shellHelper.getSuccessMessage(new String(new char[0]).replace("\0", doneMarker));
		String remains = new String(new char[0]).replace("\0", remainsMarker);
		String progressBar = String.format("%s%s%s%s", leftDelimiter, done, remains, rightDelimiter);
		shellHelper.getTerminal().writer().println(CUU + "\r" + DL + progressBar + "% ");
		shellHelper.getTerminal().flush();
	}
	    public void reset() {
	        started = false;
	    }
}
