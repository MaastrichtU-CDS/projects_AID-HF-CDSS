package nl.maastro.aidhf.domain.models;

import nl.maastro.aidhf.domain.enums.GradeEnum;
import nl.maastro.aidhf.domain.enums.SyncopeEnum;

import javax.validation.constraints.NotNull;

public class BNInput {

	@NotNull
	private GradeEnum orthopnea;

	@NotNull
	private GradeEnum cough;

	@NotNull
	private GradeEnum edema;

	@NotNull
	private GradeEnum dizziness;

	@NotNull
	private SyncopeEnum syncope;

	public BNInput(GradeEnum orthopnea, GradeEnum cough, GradeEnum edema, GradeEnum dizziness, SyncopeEnum syncope) {
		this.orthopnea = orthopnea;
		this.cough = cough;
		this.edema = edema;
		this.dizziness = dizziness;
		this.syncope = syncope;
	}

	public GradeEnum getOrthopnea() {
		return orthopnea;
	}

	public void setOrthopnea(GradeEnum orthopnea) {
		this.orthopnea = orthopnea;
	}

	public GradeEnum getCough() {
		return cough;
	}

	public void setCough(GradeEnum cough) {
		this.cough = cough;
	}

	public GradeEnum getEdema() {
		return edema;
	}

	public void setEdema(GradeEnum edema) {
		this.edema = edema;
	}

	public GradeEnum getDizziness() {
		return dizziness;
	}

	public void setDizziness(GradeEnum dizziness) {
		this.dizziness = dizziness;
	}

	public SyncopeEnum getSyncope() {
		return syncope;
	}

	public void setSyncope(SyncopeEnum syncope) {
		this.syncope = syncope;
	}
}
