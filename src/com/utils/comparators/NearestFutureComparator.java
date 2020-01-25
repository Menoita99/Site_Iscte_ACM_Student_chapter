package com.utils.comparators;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class NearestFutureComparator implements Comparator<Date> {

	@Override
	public int compare(Date o1, Date o2) {
		Date now = new Date(System.currentTimeMillis());
		
		if((o1.after(now) && o2.after(now)) || (o1.before(now) && o2.before(now))) {
			long l1 = Math.abs(o1.toInstant().getEpochSecond() - now.toInstant().getEpochSecond());
			long l2 = Math.abs(o2.toInstant().getEpochSecond() - now.toInstant().getEpochSecond());
			return (int)(l1-l2);
		}else
			return o1.after(o2) ? -1 : 1;
	}

	
	public static void main(String[] args) {
		LocalDateTime time1 = LocalDateTime.of(2020, 6, 3, 00, 00);
		LocalDateTime time2 = LocalDateTime.of(2020, 1, 21, 00, 00);
		LocalDateTime time3 = LocalDateTime.of(2020, 2, 3, 00, 00);
		LocalDateTime time4 = LocalDateTime.of(2020, 1, 19, 00, 00);
		LocalDateTime time5 = LocalDateTime.of(2019, 6, 3, 00, 00);
		
		List<Date> dates = new ArrayList<>();
		dates.add(Date.from(time1.atZone(ZoneId.systemDefault()).toInstant()));
		dates.add(Date.from(time2.atZone(ZoneId.systemDefault()).toInstant()));
		dates.add(Date.from(time3.atZone(ZoneId.systemDefault()).toInstant()));
		dates.add(Date.from(time4.atZone(ZoneId.systemDefault()).toInstant()));
		dates.add(Date.from(time5.atZone(ZoneId.systemDefault()).toInstant()));
		
		dates.stream().forEach(d -> System.out.println(d));
		System.out.println("--------------Sorted----------------");
		Collections.sort(dates,new NearestFutureComparator());
		dates.stream().forEach(d -> System.out.println(d));
	}
}
