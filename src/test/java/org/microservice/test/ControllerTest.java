package org.microservice.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.microservice.controller.Controller;
import org.microservice.dao.CacheService;
import org.microservice.model.Pair;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class})
public class ControllerTest {

	private MockMvc mockMvc;
    String key = "key1";
    String value = "val1";
    Pair pair = new Pair(key,value);
    
    String key2 = "key2";
    Pair pair404 = new Pair(key2);
    
    String pairJson,pair404Json;

	

    @InjectMocks
    private Controller dataController;

    
    @Mock
	private CacheService cacheService;


    /**
     * In setup the behavior of mocked classes is defined
     * @throws JsonProcessingException 
     */

    @Before
    public void setUp() throws JsonProcessingException{
        MockitoAnnotations.initMocks(this);
        
        ObjectMapper mapper = new ObjectMapper();
      mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
      ObjectWriter ow = mapper.writer();
      pairJson = ow.writeValueAsString(pair); 
      pair404Json = ow.writeValueAsString(pair404); 
        
        Mockito.when(cacheService.retrievePair(key)).thenReturn(pair);
        Mockito.when(cacheService.retrievePair(key2)).thenReturn(pair404);
        Mockito.when(cacheService.addPair(Mockito.any(Pair.class))).thenReturn(true);

        mockMvc = MockMvcBuilders.standaloneSetup(dataController).build();
    }
    
    @Test
    public void test404() throws Exception {

      mockMvc.perform(get("/key2"))
      .andExpect(status().isNotFound())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(content().string(pair404Json));
    }

    @Test
    public void test200() throws Exception {

      mockMvc.perform(get("/key1"))
      .andExpect(status().is2xxSuccessful())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(content().string(pairJson));
    }

    @Test
    public void testAdd() throws Exception {
    	
      System.out.println(pairJson);	
      mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON_UTF8).content(pairJson))
      .andExpect(status().is2xxSuccessful())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(content().string(pairJson));
    }



}
