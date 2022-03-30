package nl.maastro.aidhf.controllers;

import nl.maastro.aidhf.domain.models.BNInput;
import nl.maastro.aidhf.services.AdviceService;
import org.openmarkov.core.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/advice")
public class AdviceController {

	private static final Logger logger = LoggerFactory.getLogger(AdviceController.class);

	private final AdviceService adviceService;

	public AdviceController(AdviceService adviceService) {
		this.adviceService = adviceService;
	}

	@PostMapping
	public ResponseEntity<String> calculateAdvice(@Valid @RequestBody BNInput bnInput) {
		logger.info("REST request to calculate advice");
		String response;

		try {
			response = adviceService.calculateAdvice(bnInput);
		} catch (FileNotFoundException | ParserException | NodeNotFoundException | IncompatibleEvidenceException |
				InvalidStateException | NotEvaluableNetworkException | UnexpectedInferenceException e) {
			logger.error("Failed to calculate advice", e);
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok().body(response);
	}


	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
			MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		logger.warn("Handling REST request which invoked MethodArgumentNotValidException, {} ", errors);
		return errors;
	}

}
