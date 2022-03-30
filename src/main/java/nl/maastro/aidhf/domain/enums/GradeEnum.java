package nl.maastro.aidhf.domain.enums;

public enum GradeEnum {
	GRADE_0("0"),
	GRADE_1("1"),
	GRADE_2("2"),
	GRADE_3("3");

	public final String value;

	GradeEnum(String value) {
		this.value = value;
	}
}
