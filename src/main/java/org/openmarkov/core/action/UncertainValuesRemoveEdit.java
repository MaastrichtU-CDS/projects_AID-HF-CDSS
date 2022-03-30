/*
 * Copyright (c) CISIAD, UNED, Spain,  2019. Licensed under the GPLv3 licence
 * Unless required by applicable law or agreed to in writing,
 * this code is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OF ANY KIND.
 */

package org.openmarkov.core.action;

import org.openmarkov.core.exception.NonProjectablePotentialException;
import org.openmarkov.core.exception.WrongCriterionException;
import org.openmarkov.core.model.network.EvidenceCase;
import org.openmarkov.core.model.network.Node;
import org.openmarkov.core.model.network.Variable;
import org.openmarkov.core.model.network.modelUncertainty.UncertainValue;
import org.openmarkov.core.model.network.potential.ExactDistrPotential;
import org.openmarkov.core.model.network.potential.Potential;
import org.openmarkov.core.model.network.potential.TablePotential;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code UncertainValuesRemoveEdit} is an edit that allow us to removes the uncertain values
 * column for a certain configuration.
 * If all the values in the uncertain values are null after the removal then the uncertain value is set to null.
 *
 * @author mluque
 * @version 1 23/06/11
 */

@SuppressWarnings("serial") public class UncertainValuesRemoveEdit extends SimplePNEdit {

	private List<UncertainValue> oldUncertainColumn;

	private int basePosition;

	private Node node;

	private boolean wasNullOldUncertainColumn;

	/**
	 * Creates a new {@code AddNodeEdit} with the network where the new
	 * new node will be added and basic information about it.
	 *
	 * @param node          the new node
	 * @param configuration Configuration
	 */
	public UncertainValuesRemoveEdit(Node node, EvidenceCase configuration) {
		super(node.getProbNet());

		this.node = node;

		Potential potential = getPotential();

		TablePotential auxProjected = null;
		try {
			auxProjected = potential.tableProject(configuration, null).get(0);
		} catch (WrongCriterionException | NonProjectablePotentialException e) {

			e.printStackTrace();
		}

		UncertainValue[] auxUncertainTable = auxProjected.getUncertainValues();

		wasNullOldUncertainColumn = !hasUncertainValues(auxUncertainTable);

		UncertainValue[] oldUncertainColumnArray = (wasNullOldUncertainColumn ? null : auxUncertainTable);

		oldUncertainColumn = new ArrayList<>();
		for (UncertainValue aux : oldUncertainColumnArray) {
			oldUncertainColumn.add(aux);
		}
		TablePotential auxPotential = getTablePotential(potential);
		this.basePosition = getBasePosition(auxPotential,configuration,potential instanceof ExactDistrPotential);

	}
	
	
	/**
	 * It returns the first position in the table of the consecutive cells where all the values corresponding to a certain configuration are stored. It
	 * assumes that configuration is a complete instantiation of the parents of the variable associated to the table.
	 *
	 * @param configuration Evidence case
	 * @return first position in the table of the consecutive cells where all the values corresponding to a certain configuration are stored
	 */
	public int getBasePosition(TablePotential potential, EvidenceCase configuration, boolean isInAnExactDistrPotential) {
		int[] coordinates;
		int sizeCoordinates;
		int pos;
		int sizeEvi = configuration.getFindings().size();
		sizeCoordinates = sizeEvi+(isInAnExactDistrPotential?0:1);
		coordinates = new int[sizeCoordinates];
		List<Variable> varsTable = potential.getVariables();
		int startLoop;
		startLoop = isInAnExactDistrPotential?0:1;
		if (!isInAnExactDistrPotential) {
			// It is a typical probability potential of a chance variable
			coordinates[0] = 0;
		}
		for (int i = startLoop; i < sizeCoordinates; i++) {
			coordinates[i] = configuration.getFinding(varsTable.get(i)).getStateIndex();
		}
		pos = potential.getPosition(coordinates);
		return pos;
	}
	
	
	
	
	TablePotential getTablePotential(Potential potential) {
		return (potential instanceof TablePotential)?(TablePotential)potential:((ExactDistrPotential)potential).getTablePotential();
	}

	private Potential getPotential() {
		return node.getPotentials().get(0);
	}

	public Variable getVariable() {
		return node.getVariable();
	}

	@Override public void doEdit() {

		TablePotential tablePotential = getTablePotential();
		

		//Remove the column of uncertain values
		if (!wasNullOldUncertainColumn) {
			UncertainValuesEdit.placeUncertainColumn(tablePotential, null, getVariable(), basePosition);
		}
		//If all the elements are null then the uncertain value object is set to null
		if (!hasUncertainValues(tablePotential.getUncertainValues())) {
			tablePotential.setUncertainValues(null);
		}

	}

	public TablePotential getTablePotential() {
		return getTablePotential(getPotential());
	}
	
	public void undo() {
		super.undo();

		TablePotential potential = getTablePotential();

		UncertainValue[] table = potential.getUncertainValues();
		//Create uncertain values table if it is null
		if (table == null) {
			table = new UncertainValue[potential.getTableSize()];
		}
		//Restore the elements of the uncertain column
		UncertainValuesEdit.placeUncertainColumn(potential, oldUncertainColumn, getVariable(), basePosition);
	}

	private boolean hasUncertainValues(UncertainValue[] auxUncertainTable) {
		boolean hasUncertainValues;
		if ((auxUncertainTable == null) || (auxUncertainTable.length == 0)) {
			hasUncertainValues = false;
		} else {
			hasUncertainValues = false;
			for (int i = 0; (i < auxUncertainTable.length) && !hasUncertainValues; i++) {
				hasUncertainValues = (auxUncertainTable[i] != null);
			}
		}
		return hasUncertainValues;
	}

}