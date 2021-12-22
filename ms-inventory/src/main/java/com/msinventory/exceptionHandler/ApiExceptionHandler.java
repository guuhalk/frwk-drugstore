package com.msinventory.exceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.msschemas.exception.EntityAlreadyExistsException;
import com.msschemas.exception.EntityInUseException;
import com.msschemas.exception.EntityNotFoundException;
import com.msschemas.exception.GenericException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(EntityAlreadyExistsException.class)
	public ResponseEntity<?> handleEntityAlreadyExists(EntityAlreadyExistsException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ENTITY_ALREADY_EXISTS;
		String detail = ex.getMessage();
		
		Problem problem = createProblem(status, problemType, detail);
		problem.setObjects(new ArrayList<>());
		
		Problem.Object problemObject = new Problem.Object();
		problemObject.setUserMessage(detail);
		
		problem.getObjects().add(problemObject);
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(GenericException.class)
	public ResponseEntity<?> handleGeneric(GenericException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.GENERIC_ERROR;
		String detail = ex.getMessage();
		
		Problem problem = createProblem(status, problemType, detail);
		problem.setObjects(new ArrayList<>());
		
		Problem.Object problemObject = new Problem.Object();
		problemObject.setUserMessage(detail);
		
		problem.getObjects().add(problemObject);
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		String detail = ex.getMessage();
		
		Problem problem = createProblem(status, problemType, detail);
		problem.setObjects(new ArrayList<>());
		
		Problem.Object problemObject = new Problem.Object();
		problemObject.setUserMessage(detail);
		
		problem.getObjects().add(problemObject);
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntityInUseException.class)
	public ResponseEntity<?> handleEntityInUse(EntityInUseException ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTITY_IN_USE;
		String detail = ex.getMessage();
		
		Problem problem = createProblem(status, problemType, detail);
		problem.setObjects(new ArrayList<>());
		
		Problem.Object problemObject = new Problem.Object();
		problemObject.setUserMessage(detail);
		
		problem.getObjects().add(problemObject);
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	private Problem createProblem(HttpStatus status, ProblemType problemType, String detail) {
		Problem problem = new Problem();
		problem.setTimestamp(OffsetDateTime.now());
		problem.setStatus(status.value());
		problem.setType(problemType.getUri());
		problem.setTitle(problemType.getTitle());
		problem.setDetail(detail);

		return problem;
	}
}
