package applications;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Position implements Comparable<Position> {
	private String position;
	private List<Skill> skills = new LinkedList<>();
	private List<Applicant> applicants = new LinkedList<>();
	private Applicant winner;

	public Position(String name) {
		this.position = name;
	}
	
	public String getName() {
		return position;
	}

	public void addSkill(Skill s) {
		skills.add(s);
	}
	
	public List<Skill> getSkills() {
		return skills;
	}
	
	public void addApplicant(Applicant a) {
		applicants.add(a);
	}

	public List<String> getApplicants() {
		return applicants.stream()
				.map(Applicant::getName)
				.sorted().collect(Collectors.toList());
	}
	
	public int getNumberApplicants() {
		return applicants.size();
	}
	
	public void setWinner(Applicant w) {
		this.winner = w;
	}

	public String getWinner() {
		return winner == null ? null : winner.getName();
	}

	@Override
	public int compareTo(Position p) {
		return this.position.compareTo(p.getName());
	}
}