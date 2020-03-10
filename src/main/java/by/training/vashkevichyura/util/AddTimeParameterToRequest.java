package by.training.vashkevichyura.util;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AddTimeParameterToRequest {

	public static void addParam(HttpServletRequest request, LocalDateTime dateTime) {

		LocalDateTime dateTo = LocalDateTime.now();

		long difference = ChronoUnit.SECONDS.between(dateTime, dateTo);

		long diffSeconds = difference % 60;
		long diffMinutes = difference / 60 % 60;
		long diffHours = difference / (60 * 60) % 24;
		long diffDays = difference / (24 * 60 * 60);

		request.getSession().setAttribute(RequestParameter.SECONDS.parameter(), diffSeconds);
		request.getSession().setAttribute(RequestParameter.MINUTES.parameter(), diffMinutes);
		request.getSession().setAttribute(RequestParameter.HOURS.parameter(), diffHours);
		request.getSession().setAttribute(RequestParameter.DAYS.parameter(), diffDays);

	}

}
