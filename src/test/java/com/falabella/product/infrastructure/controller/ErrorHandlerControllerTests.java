package com.falabella.product.infrastructure.controller;

import com.falabella.product.application.ApplicationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ErrorHandlerControllerTests {

    @Mock
    private BindingResult bindingResult;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ErrorHandlerController errorHandlerController;

    @Test
    void shouldValidateMethodArgumentNotValidExceptionHandler() {
        List<FieldError> listaErrores = new ArrayList<>();
        FieldError error = new FieldError("test", "test", "test");
        listaErrores.add(error);
        when(bindingResult.getFieldErrors()).thenReturn(listaErrores);

        Assertions.assertDoesNotThrow(() -> errorHandlerController
                .methodArgumentNotValidExceptionHandler(request,new MethodArgumentNotValidException(null,bindingResult)));
    }

    @Test
    void shouldValidatePsqlExceptionExceptionHandler() {
        Assertions.assertEquals(400, errorHandlerController
                .psqlExceptionExceptionHandler(request, new PSQLException("", PSQLState.UNKNOWN_STATE)).getStatusCodeValue());
    }

    @Test
    void shouldValidateApplicationExceptionHandler() {
        Assertions.assertEquals(400, errorHandlerController
                .applicationExceptionHandler(request, new ApplicationException("test")).getStatusCodeValue());
    }
}
