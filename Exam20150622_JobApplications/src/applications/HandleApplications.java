package applications;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class HandleApplications {
	private Map<String, Skill> skills = new HashMap<>();
	private Map<String, Position> positions = new HashMap<>();
	private Map<String, Applicant> applicants = new HashMap<>();

	public void addSkills(String... names) throws ApplicationException {
		for(String s : names)
			if(skills.containsKey(s))
				throw new ApplicationException();
			else
				skills.put(s, new Skill(s));
	}

	public void addPosition(String name, String... skillNames) throws ApplicationException {
		if(positions.containsKey(name))
			throw new ApplicationException();
		Position p = new Position(name);
		for(String s : skillNames) {
			Skill skill = skills.get(s);
			if(skill == null)
				throw new ApplicationException();
			else {
				p.addSkill(skill);
				skill.addPosition(p);
			}
		}
		positions.put(name, p);
	}

	public Skill getSkill(String name) {
		return skills.get(name);
	}

	public Position getPosition(String name) {
		return positions.get(name);
	}

	public void addApplicant(String name, String capabilities) throws ApplicationException {
		if(applicants.containsKey(name))
			throw new ApplicationException();
		Applicant a = new Applicant(name);
		String[] arrayCapability = capabilities.split(",");
		for(String s : arrayCapability) {
			String[] capability = s.split(":");
			Skill skill = skills.get(capability[0]);
			if(skill == null)
				throw new ApplicationException();
			int level = Integer.parseInt(capability[1]);
			if(level < 1 || level > 10)
				throw new ApplicationException();
			a.addCapability(skill, level);
		}
		applicants.put(name, a);
	}

	public String getCapabilities(String applicantName) throws ApplicationException {
		Applicant a = applicants.get(applicantName);
		if(a == null)
			throw new ApplicationException();
		return a.getCapabilities();
	}

	public void enterApplication(String applicantName, String positionName) throws ApplicationException {
		Applicant a = applicants.get(applicantName);
		Position p = positions.get(positionName);
		if(a == null || p == null || a.getPosition() != null)
			throw new ApplicationException();
		List<Skill> skillsRequired = p.getSkills();
		for(Skill s : skillsRequired)
			if(! a.checkCapability(s))
				throw new ApplicationException();
		a.setPosition(p);
		p.addApplicant(a);
	}

	public int setWinner(String applicantName, String positionName) throws ApplicationException {
		Applicant a = applicants.get(applicantName);
		Position p = positions.get(positionName);
		if(a == null || p == null || 
				a.getPosition() != p || p.getWinner() != null)
			throw new ApplicationException();
		int numReqSkills = p.getSkills().size();
		int levelsSum = a.getTotalLevel(p.getSkills());
		if(levelsSum < 6*numReqSkills)
			throw new ApplicationException();
		p.setWinner(a);
		return levelsSum;
	}

	public SortedMap<String, Long> skill_nApplicants() {
		return applicants.values().stream()
				.flatMap(a -> a.getCapabilitiesSet().stream())
				.collect(Collectors.groupingBy(Skill::getName,
						TreeMap::new,
						Collectors.counting()));
	}

	public String maxPosition() {
		return positions.values().stream()
				.sorted((e1, e2) -> e2.getNumberApplicants() - e1.getNumberApplicants())
				.map(Position::getName)
				.findFirst().orElse(null);
	}
}
