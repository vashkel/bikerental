package by.training.vashkevichyura.tag;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CalculationTimeTag extends TagSupport {

	private static Logger logger = LogManager.getLogger(CalculationTimeTag.class);

	private static final long serialVersionUID = -5306548341656156312L;

	private LocalDateTime startTime;
	private LocalDateTime finishTime;

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public void setFinishTime(LocalDateTime finishTime) {
		this.finishTime = finishTime;
	}

	@Override
	public int doStartTag() {

		try {
			JspWriter out = pageContext.getOut();

			if (startTime != null && finishTime != null) {
				LocalDateTime dateFrom = startTime;
				LocalDateTime dateTo = finishTime;

				long difference = ChronoUnit.MINUTES.between(dateFrom, dateTo);

				out.write(String.valueOf(difference));
			}
		} catch (IOException e) {
			logger.log(Level.ERROR, "Write tag error, " + e);
		}

		return SKIP_BODY;
	}

}
