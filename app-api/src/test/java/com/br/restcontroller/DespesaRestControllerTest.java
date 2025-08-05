package com.br.restcontroller;

import com.br.business.service.DespesaService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;

@Log4j2
@WebMvcTest(DespesaRestController.class)
public class DespesaRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DespesaService despesaService;

    @Test
    public void deveRetornarNotFoundQuandoUsuarioNaoExiste() throws Exception {


    }

}
