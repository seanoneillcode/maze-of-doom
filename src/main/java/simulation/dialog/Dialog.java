package simulation.dialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dialog {

	private String currentText;
	private static final float CHAR_PAUSE = 0.025f;
	private float charTimer = 0;
	private static final String SPACE_STRING = " ";
	private static final String NEWLINE_STRING = "/n";
	private List<String> fullLines;
	private static final int MAX_LINE_LENGTH = 24;
	private static final int MAX_NUMBER_OF_LINES = 2;
	private int startLine, endLine;
	private int cursor;
	private boolean isWaitingToContinue;
	private boolean isDone;
	
	public Dialog(String text) {
		currentText = "";
		fullLines = getLines(text, MAX_LINE_LENGTH);
		startLine = 0;
		endLine = 0;
		cursor = 0;
		isWaitingToContinue = false;
		isDone = false;
	}
	
	private List<String> getLines(String fullText, int lineLength) {
		List<String> words = Arrays.asList(fullText.split(SPACE_STRING));
		List<String> lines = new ArrayList<String>();
		StringBuilder line = new StringBuilder();
		for (String word : words) {
			if ((line.length() + word.length() > lineLength) || (word.equals(NEWLINE_STRING))) {
				lines.add(line.toString());
				line = new StringBuilder();
			}
			if (!word.equals(NEWLINE_STRING)) {
				line.append(word + " ");
			}
		}
		lines.add(line.toString());
		return lines;
	}
	
	public List<String> getLines() {
		List<String> currentLines = new ArrayList<String>();
		for (int index = startLine; index < fullLines.size() && index <= endLine; index++) {
			String next = fullLines.get(index);
			if (index == endLine) {
				next = next.substring(0, cursor);
			}
			currentLines.add(next);
		}
		
		return currentLines;
	}
	
	public String getCurrentText() {
		return currentText;
	}
	
	public void reset() {
		charTimer = 0;
		currentText = "";
		startLine = 0;
		endLine = 0;
		cursor = 0;
	}
	
	public boolean isDone() {
		return isDone;
	}
	
	public void continueProgress() {
		if (isWaitingToContinue) {
			isWaitingToContinue = false;
			startLine++;			
		}
		if (endLine >= fullLines.size()) {
			isDone = true;
		}
	}
	
	public void update(float delta) {
		if (!isWaitingToContinue) {
			charTimer += delta;			
		}
		if (charTimer > CHAR_PAUSE && !isDone()) {
			cursor++;
			if (cursor > fullLines.get(endLine).length()) {
				cursor = 0;
				endLine++;
			}
			if (endLine - startLine > MAX_NUMBER_OF_LINES) {
				isWaitingToContinue = true;
			}
			charTimer = 0;
		}
	}
}
