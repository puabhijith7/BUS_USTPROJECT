//package com.example.busservice1.Exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//@ControllerAdvice
//public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//
//	@ExceptionHandler(BusNotFoundException.class)
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//	public ResponseEntity<String> handleBusNotFoundException(BusNotFoundException ex) {
//
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
//	}
//
//	@ExceptionHandler(BusAlreadyException.class)
//	@ResponseStatus(HttpStatus.CONFLICT)
//	public ResponseEntity<String> handleBusAlreadyException(BusAlreadyException ex) {
//
//		return ResponseEntity.status(HttpStatus.CONFLICT).body("already exist");
//	}
//
//	@ExceptionHandler(RouteNotFoundException.class)
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//	public ResponseEntity<String> handleRouteNotFoundException(RouteNotFoundException ex) {
//
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
//	}
//
//	@ExceptionHandler(RouteAlreadyExistException.class)
//	@ResponseStatus(HttpStatus.CONFLICT)
//	public ResponseEntity<String> handleRouteAlreadyExistException(RouteAlreadyExistException ex) {
//
//		return ResponseEntity.status(HttpStatus.CONFLICT).body("already exist");
//	}
//
//	@ExceptionHandler(RouteDetialsNotFoundException.class)
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//	public ResponseEntity<String> handleRouteDetialsNotFoundException(RouteDetialsNotFoundException ex) {
//
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
//	}
//
//	@ExceptionHandler(RouteDetailsAlreadyExistException.class)
//	@ResponseStatus(HttpStatus.CONFLICT)
//	public ResponseEntity<String> handleRouteDetailsAlreadyExistException(RouteDetailsAlreadyExistException ex) {
//
//		return ResponseEntity.status(HttpStatus.CONFLICT).body("already exist");
//	}
//
//}