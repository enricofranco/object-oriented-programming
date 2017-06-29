package applications;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Applicant implements Comparable<Applicant> {
	private String name;
	private Map<Skill, Integer> capabilities = new HashMap<>();
	private Position position;
	
	public Applicant(String name) {
		this.name = name;
	}
	
	public void addCapability(Skill skill, int level) {
		capabilities.put(skill, level);
		skill.addApplicant();
	}
	
	public boolean checkCapability(Skill s) {
		return capabilities.containsKey(s);
	}
	
	public void setPosition(Position p) {
		this.position = p;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public String getCapabilities() {
		return capabilities.entrySet().stream()
				.sorted((e1,e2) -> e1.getKey().compareTo(e2.getKey()))
				.map(e -> e.getKey().getName() + ":" + e.getValue())
				.collect(Collectors.joining(","));
	}
	
	public int getTotalLevel(List<Skill> skills) {
		int sum = 0;
		for(Skill s : skills) {
			if(! capabilities.containsKey(s))
				throw new RuntimeException();
			int level = capabilities.get(s);
			sum += level;
		}
		return sum;
	}
	
	public int getLevel(Skill s) {
		Integer level = capabilities.get(s);
		return level != null ? level : -1;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Applicant a) {
		return name.compareTo(a.getName());
	}
}
