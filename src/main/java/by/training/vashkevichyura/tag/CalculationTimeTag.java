package by.training.vashkevichyura.tag;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CalculationTimeTag extends TagSupport {

	private static Logger logger = LogManager.getLogger();

	private static final long serialVersionUID = -5306548341656156312L;

	private final static String DATE_TIME_PATTERN_TO_VIEW = "dd.MM.yyyy HH:mm:ss";
	private final static DateTimeFormatter VIEW_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_TO_VIEW);

	private String startTime;
	private String finishTime;

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	@Override
	public int doStartTag() {

		try {
			JspWriter out = pageContext.getOut();

			if (!startTime.equals("") && !finishTime.equals("")) {
				LocalDateTime dateFrom = LocalDateTime.parse(startTime, VIEW_FORMATTER);
				LocalDateTime dateTo = LocalDateTime.parse(finishTime, VIEW_FORMATTER);

				long difference = ChronoUnit.MINUTES.between(dateFrom, dateTo);

				out.write(String.valueOf(difference));
			}

		} catch (IOException e) {
			logger.log(Level.ERROR, "Write tag error, " + e);
		}

		return SKIP_BODY;
	}

}
