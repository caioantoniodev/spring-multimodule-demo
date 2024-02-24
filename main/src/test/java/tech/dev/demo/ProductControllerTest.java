package tech.dev.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tech.dev.demo.product.Product;
import tech.dev.demo.product.ProductController;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = Main.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnListOfProducts() throws Exception {
        this.mvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void shouldReturnListOfProductsWithNameQuery() throws Exception {
        var expectedName = "M1";

        var mvcResult = this.mvc.perform(get(String.format("/products?name=%s", expectedName)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        var products = this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Product>>(){});
        var firstProduct = products.get(0);

        Assertions.assertEquals(expectedName, firstProduct.name());
    }

    @Test
    public void shouldReturnListOfProductsWithValueQuery() throws Exception {
        this.mvc.perform(get("/products?price=3000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void shouldRemoveProductsByNameAndReturnTrue() throws Exception {
        var mvcResult = this.mvc.perform(delete("/products/iphone"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        var response = this.objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Boolean.class);

        Assertions.assertTrue(response);
    }
}
