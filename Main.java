package arba.kz.gps.gps.arbagps.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tc_positions")
@JsonIgnoreProperties({ "attributes" })
public class Position /* extends BaseModel */ {

	@Getter
	@Setter
	@Id
	private Long id;

	private String attributes;

	public Map<String, Object> getAttributesJson() {
		if (this.attributes == null)
			return null;
		Gson gson = new Gson();
		return gson.fromJson(this.attributes, Map.class);
	}

	@Getter
	private Long deviceid;

	@Getter
	private String protocol;

	@Getter
	private Date servertime = new Date();

	@Getter
	private Calendar devicetime;

	@Getter
	private Boolean valid;

	@Getter
	private Double latitude;

	@Getter
	private Double longitude;

	@Getter
	private Double altitude; // value in meters

	// если приходит в узлах то: узлы * 1.852 = км
	@Getter
	private Double speed;

	@Getter
	private Double course;

	@Getter
	private String address;

	@Getter
	private Double accuracy;

	public static void main(String[] args) {

		Map<Long, Boolean> data = new TreeMap<>();
		data.put(1561530131156L, false);
		data.put(1561530183179L, true);
		data.put(1561530219518L, false);
		data.put(1561530309217L, true);
		data.put(1561530326751L, true);
		data.put(1561530348394L, false);
		data.put(1561530360000L, false);
		data.put(1561530378002L, false);
		
		
		Map<Boolean, List<List<Long>>> sorted = new HashMap<>();
		sorted.put(true, new ArrayList<>());
		sorted.put(false, new ArrayList<>());
		
		Integer trueIndex = null;
		Integer falseIndex = null;
		
		Boolean prevObj = null;
		
		Iterator<Map.Entry<Long, Boolean>> it = data.entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry<Long, Boolean> currentPair = it.next();
		    
		    long key = currentPair.getKey();
		    boolean val = currentPair.getValue();
		    
		    if(val) {
		    	// reset indexes
		    	falseIndex = null;
		    	if(prevObj != null && prevObj) {
		    		trueIndex = sorted.get(true).size()-1;
		    	} else {
		    		trueIndex = null;
		    	}
		    		
		    	if(trueIndex != null) {
		    		// insert into previous true list
		    		sorted.get(true).get(trueIndex).add(key);
		    	} else {
		    		// create new list
		    		List<Long> listItem = new ArrayList<>();
			    	listItem.add(key);
			    	sorted.get(true).add(listItem);
		    	}
		    	
		    } else {
		    	// reset indexes
		    	trueIndex = null;
		    	if(prevObj != null && !prevObj) {
		    		falseIndex = sorted.get(false).size()-1;
		    	} else {
		    		falseIndex = null;
		    	}
		    		
		    	if(falseIndex != null) {
		    		// insert into previous true list
		    		sorted.get(false).get(falseIndex).add(key);
		    	} else {
		    		// create new list
		    		List<Long> listItem = new ArrayList<>();
			    	listItem.add(key);
			    	sorted.get(false).add(listItem);
		    	}
		    }
			prevObj = currentPair.getValue();
		}
		
		
		
		
		
		
		
		
		
		System.out.println(sorted);
		
		
		
		//String s = dateAndTimeDifferent(new Date(), new Date(new Date().getTime()+29608));
		//System.out.println(s);
		


		
		Date currentDate = null;
		Iterator<Map.Entry<Boolean, List<List<Long>>>> it2 = sorted.entrySet().iterator();
		while(it2.hasNext()) {
			
			currentDate = new Date();
			long currentDateTimestamp = currentDate.getTime();
			 Map.Entry<Boolean, List<List<Long>>> currentPair = it2.next();
			
			 Boolean key = currentPair.getKey();
			 List<List<Long>> val = currentPair.getValue();
			 
			 
			 if(key) {
				 
				int differenceOfNumbersMotionTrue = 0;
				 
				 
				 for(int i = 0; i < val.size(); i++) {
					 List<Long> valItem = val.get(i);
					 
					 if(valItem.size() > 0) {
						 
						 long diff = valItem.get(valItem.size()-1) - valItem.get(0);
						 differenceOfNumbersMotionTrue += diff;
						 
						 // плючаем разницу к текущей дате
					/*	 Date d2 = new Date(currentDateTimestamp + diff);
						 Date d1 = currentDate;
						 
						System.out.println(dateAndTimeDifferent(d1, d2));*/
						 
					 } else {
						 //currentDateTimestamp
						 long diff = valItem.get(valItem.size()-1) - valItem.get(0);
						 //val.get(0);
					 }
					
					 
					 
				 }
				 
				 
				
				 
				 
				 
			 } else {
					int differenceOfNumbersMotionFalse = 0;
					
					
			 }
			 
			 
			 
			 
			 currentDate = null;
		}
		
		

	}

	
	
	public static String dateAndTimeDifferent(Date d1, Date d2) {
		try {
			//in milliseconds
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			String differense = diffDays + " дней, " + diffHours + " часов, " + diffMinutes + " минут, " + diffSeconds + " секунд.";
			return differense;
		} catch (Exception e) {
			e.printStackTrace();
			return "Неизвестно";
		}
	}
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
















