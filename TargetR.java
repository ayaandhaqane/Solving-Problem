package JsTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Scanner;

public class TargetR {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter (YYYY-MM-DD,YYYY-MM-DD,target): ");
        String[] inputs = scanner.nextLine().split(",");
        scanner.close();

        LocalDate startDate = LocalDate.parse(inputs[0].trim());
        LocalDate endDate = LocalDate.parse(inputs[1].trim());
        double totalAnnualTarget = Double.parseDouble(inputs[2].trim());

        int daysInMonthExcludingFridays = calculateDaysInMonthExcludingFridays(startDate);
        int daysWorkedExcludingFridays = calculateDaysExcludingFridays(startDate, endDate);
        double dailyTargetForMonth = calculateDailyTargetForMonth(startDate, totalAnnualTarget);
        double periodTarget = dailyTargetForMonth * daysWorkedExcludingFridays;

        System.out.println("    Days excluding Fridays: " + daysInMonthExcludingFridays);
        System.out.println("    Days worked in the period excluding Fridays: " + daysWorkedExcludingFridays);
        System.out.println("    monthlyTargets: " + periodTarget);
        System.out.println("    totalTarget: " + periodTarget);
    }

    private static int calculateDaysExcludingFridays(LocalDate start, LocalDate end) {
        int count = 0;
        LocalDate date = start;
        while (!date.isAfter(end)) {
            if (date.getDayOfWeek() != DayOfWeek.FRIDAY) {
                count++;
            }
            date = date.plusDays(1);
        }
        return count;
    }

    private static int calculateDaysInMonthExcludingFridays(LocalDate date) {
        YearMonth yearMonth = YearMonth.from(date);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
        int fridaysCount = 0;

        for (LocalDate current = firstDayOfMonth; !current.isAfter(lastDayOfMonth); current = current.plusDays(1)) {
            if (current.getDayOfWeek() == DayOfWeek.FRIDAY) {
                fridaysCount++;
            }
        }
        return yearMonth.lengthOfMonth() - fridaysCount;
    }

    private static double calculateDailyTargetForMonth(LocalDate startDate, double totalAnnualTarget) {
        double monthlyTarget = totalAnnualTarget / 12; // Divide the annual target by 12 to find the monthly target
        int daysInMonthExcludingFridays = calculateDaysInMonthExcludingFridays(startDate);
        return monthlyTarget / daysInMonthExcludingFridays; // Divide the monthly target by the number of days in the month excluding Fridays
    }
}
