package nl.maastro.aidhf.services;

import nl.maastro.aidhf.domain.models.BNInput;
import org.openmarkov.core.exception.*;
import org.openmarkov.core.model.network.EvidenceCase;
import org.openmarkov.core.model.network.ProbNet;
import org.openmarkov.core.model.network.State;
import org.openmarkov.core.model.network.potential.StrategyTree;
import org.openmarkov.core.model.network.potential.treeadd.TreeADDBranch;
import org.openmarkov.inference.variableElimination.tasks.VEOptimalIntervention;
import org.openmarkov.io.probmodel.reader.PGMXReader_0_2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@Service
public class AdviceService {
	private static final Logger logger = LoggerFactory.getLogger(AdviceService.class);

	@Value("${aid-hf.model-file}")
	private String modelFilePath;

	public String calculateAdvice(BNInput input) throws FileNotFoundException, ParserException, NodeNotFoundException,
			IncompatibleEvidenceException, InvalidStateException, NotEvaluableNetworkException, UnexpectedInferenceException {
		InputStream file = new FileInputStream(modelFilePath);
		PGMXReader_0_2 pgmxReader = new PGMXReader_0_2();
		ProbNet probNet = pgmxReader.loadProbNet("AID-HF", file);

		EvidenceCase evidence = new EvidenceCase();
		evidence.addFinding(probNet, "Orthopnea", input.getOrthopnea().value);
		evidence.addFinding(probNet, "Cough", input.getCough().value);
		evidence.addFinding(probNet, "Edema", input.getEdema().value);
		evidence.addFinding(probNet, "Dizziness", input.getDizziness().value);
		evidence.addFinding(probNet, "Syncope", input.getSyncope().value);

		VEOptimalIntervention optimalInterventionCalculator = new VEOptimalIntervention(probNet, evidence);
		StrategyTree optimalIntervention = optimalInterventionCalculator.getOptimalIntervention();

		return extractStateFromStrategyTree(optimalIntervention);
	}

	private String extractStateFromStrategyTree(StrategyTree optimalIntervention) {
		StringBuilder strBuffer = new StringBuilder();
		List<TreeADDBranch> branches = optimalIntervention.getBranches();

		int stateCounter = 0;
		for (TreeADDBranch branch : branches) {
			for (State state : branch.getStates()) {
				stateCounter++;
				strBuffer.append(state);
			}
		}

		// There should only be one advice per request, but it is not technically limited and in theory it could be
		// multiple.
		if (stateCounter > 1) {
			logger.warn("Multiple advices have been found.");
		}

		return strBuffer.toString();
	}
}
