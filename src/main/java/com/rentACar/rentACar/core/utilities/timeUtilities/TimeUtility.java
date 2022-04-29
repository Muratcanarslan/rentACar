package com.rentACar.rentACar.core.utilities.timeUtilities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TimeUtility {
	
	public static int calculateTotalRentDays(LocalDate rentDate, LocalDate confirmedPaidDate) {
		return (int) ChronoUnit.DAYS.between(rentDate, confirmedPaidDate);
	}
}
