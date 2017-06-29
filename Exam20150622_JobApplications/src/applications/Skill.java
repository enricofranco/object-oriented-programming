package applications;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Skill implements Comparable<Skill> {
	private String skill;
	private List<Position> positions = new LinkedList<>();
	private int numApplicants = 0;
	
	public Skill(String skill) {
		this.skill = skill;
	}
	
	public void addApplicant() {
		numApplicants++;
	}
	
	public int getNumApplicants() {
		return numApplicants;
	}
	
	public void addPosition(Position p) {
		positions.add(p);
	}

	public String getName() {
		return skill;
	}

	public List<Position> getPositions() {
		return positions.stream().sorted()
				.collect(Collectors.toList());
	}

	@Override
	public int compareTo(Skill s) {
		return skill.compareTo(s.getName());
	}
}