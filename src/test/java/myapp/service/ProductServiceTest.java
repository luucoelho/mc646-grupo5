package myapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import myapp.domain.Product;
import myapp.domain.enumeration.ProductStatus;
import myapp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    public static Product createProductSample(
        Long id,
        String title,
        String keywords,
        String description,
        int rating,
        int quantityInStock,
        String dimensions,
        BigDecimal price,
        ProductStatus status,
        Double weight,
        Instant dateAdded,
        Instant dateModified
    ) {
        Product product = new Product()
            .id(id)
            .title(title)
            .keywords(keywords)
            .description(description)
            .rating(rating)
            .quantityInStock(quantityInStock)
            .dimensions(dimensions)
            .price(price)
            .status(status)
            .weight(weight)
            .dateAdded(dateAdded)
            .dateModified(dateModified);

        return product;
    }

    // Testes Title
    @Test
    @DisplayName("Title 1 - Válido: Tamanho 3")
    void testTitle_1() {
        Product product = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Title 2 - Válido: Tamanho 4")
    void testTitle_2() {
        Product product = createProductSample(
            1L,
            "NESs",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Title 3 - Válido: Tamanho 99")
    void testTitle_3() {
        Product product = createProductSample(
            1L,
            "a".repeat(99),
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Title 4 - Válido: Tamanho 100")
    void testTitle_4() {
        Product product = createProductSample(
            1L,
            "a".repeat(100),
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Title 5 - Inválido: Tamanho 2")
    void testTitle_5() {
        Product product = createProductSample(
            1L,
            "NE",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals("title", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    @DisplayName("Title 6 - Inválido: Tamanho 101")
    void testTitle_6() {
        Product product = createProductSample(
            1L,
            "a".repeat(101),
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals("title", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    @DisplayName("Title 7 - Inválido: Ocupado=False (Nulo)")
    void testTitle_7() {
        Product product = createProductSample(
            1L,
            null,
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals("title", violations.iterator().next().getPropertyPath().toString());
    }

    // Testes Keywords

    @Test
    @DisplayName("Keywords 1 - Válido: 0")
    void testKeywords_1() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            "",
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Keywords 2 - Válido: 1")
    void testKeywords_2() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            "a",
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Keywords 3 - Válido: 199")
    void testKeywords_3() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            "a".repeat(199),
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Keywords 4 - Válido: 200")
    void testKeywords_4() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            "a".repeat(200),
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Keywords 6 - Inválido: 201")
    void testKeywords_6() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            "a".repeat(201),
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals("keywords", violations.iterator().next().getPropertyPath().toString());
    }

    // Testes Description

    @Test
    @DisplayName("Description 1 - Válido: 50")
    void testDescription_1() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            "a".repeat(50),
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Description 2 - Válido: 51")
    void testDescription_2() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            "a".repeat(51),
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Description 3 - Inválido: 49")
    void testDescription_3() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            "a".repeat(49),
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals("description", violations.iterator().next().getPropertyPath().toString());
    }

    // Testes Rating

    @Test
    @DisplayName("Rating 1 - Válido: 1")
    void testRating_1() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Rating 2 - Válido: 2")
    void testRating_2() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            2,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Rating 3 - Válido: 9")
    void testRating_3() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            9,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Rating 4 - Válido: 10")
    void testRating_4() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            10,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Rating 5 - Inválido: 0")
    void testRating_5() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            0,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals("rating", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    @DisplayName("Rating 6 - Inválido: 11")
    void testRating_6() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            11,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals("rating", violations.iterator().next().getPropertyPath().toString());
    }

    // Testes Price

    @Test
    @DisplayName("Price 1 - Válido: 1")
    void testPrice_1() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            new BigDecimal("1"),
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Price 2 - Válido: 1.01")
    void testPrice_2() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            new BigDecimal("1.01"),
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Price 3 - Válido: 9998.99")
    void testPrice_3() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            new BigDecimal("9998.99"),
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Price 4 - Válido: 9999")
    void testPrice_4() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            new BigDecimal("9999"),
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Price 5 - Inválido: 0.99")
    void testPrice_5() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            new BigDecimal("0.99"),
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals("price", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    @DisplayName("Price 6 - Inválido: 9999.01")
    void testPrice_6() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            new BigDecimal("9999.01"),
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals("price", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    @DisplayName("Price 7 - Inválido: Ocupado=False (Nulo)")
    void testPrice_7() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            null,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals("price", violations.iterator().next().getPropertyPath().toString());
    }

    // Testes QuantityInStock

    @Test
    @DisplayName("QuantityInStock 1 - Válido: 0")
    void testQuantityInStock_1() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            0,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("QuantityInStock 2 - Válido: 1")
    void testQuantityInStock_2() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("QuantityInStock 3 - Inválido: -1")
    void testQuantityInStock_3() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            -1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals("quantityInStock", violations.iterator().next().getPropertyPath().toString());
    }

    // Testes Status

    @Test
    @DisplayName("Status 1 - Válido: IN STOCK")
    void testStatus_1() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Status 2 - Válido: OUT OF STOCK")
    void testStatus_2() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.OUT_OF_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Status 3 - Válido: PREORDER")
    void testStatus_3() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.PREORDER,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Status 4 - Válido: DISCONTINUED")
    void testStatus_4() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.DISCONTINUED,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Status 6 - Inválido: Ocupado=False (Nulo)")
    void testStatus_6() {
        Product product = createProductSample(1L, "Título Válido", null, null, 1, 1, null, BigDecimal.TEN, null, null, Instant.now(), null);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals("status", violations.iterator().next().getPropertyPath().toString());
    }

    // Testes Weight

    @Test
    @DisplayName("Weight 1 - Válido: 0")
    void testWeight_1() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            0.0,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Weight 2 - Válido: 0.01")
    void testWeight_2() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            0.01,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Weight 3 - Inválido: -0.01")
    void testWeight_3() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            -0.01,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals("weight", violations.iterator().next().getPropertyPath().toString());
    }

    // Testes Dimensions

    @Test
    @DisplayName("Dimensions 1 - Válido: 0")
    void testDimensions_1() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            "",
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Dimensions 2 - Válido: 1")
    void testDimensions_2() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            "a",
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Dimensions 3 - Válido: 49")
    void testDimensions_3() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            "a".repeat(49),
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Dimensions 4 - Válido: 50")
    void testDimensions_4() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            "a".repeat(50),
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("Dimensions 5 - Inválido: > 50")
    void testDimensions_5() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            "a".repeat(51),
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals("dimensions", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    @DisplayName("Dimensions 6 - Inválido: 51")
    void testDimensions_6() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            "a".repeat(51),
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals("dimensions", violations.iterator().next().getPropertyPath().toString());
    }

    // Testes DateAdded

    @Test
    @DisplayName("DateAdded 1 - Válido: Ontem")
    void testDateAdded_1() {
        Instant data = Instant.now().minus(1, ChronoUnit.DAYS);
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            data,
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("DateAdded 2 - Válido: Hoje")
    void testDateAdded_2() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now(),
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("DateAdded 3 - Inválido: Amanhã")
    void testDateAdded_3() {
        Instant data = Instant.now().plus(1, ChronoUnit.DAYS);
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            data,
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals("dateAdded", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    @DisplayName("DateAdded 4 - Inválido: Ocupado=False (Nulo)")
    void testDateAdded_4() {
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            null,
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals("dateAdded", violations.iterator().next().getPropertyPath().toString());
    }

    // Testes DataModified

    @Test
    @DisplayName("DateModified 1 - Válido: DA=Ontem, DM=DA+1")
    void testDateModified_1() {
        Instant da = Instant.now().minus(1, ChronoUnit.DAYS);
        Instant dm = da.plus(1, ChronoUnit.SECONDS);
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            da,
            dm
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("DateModified 2 - Válido: DA=Hoje, DM=DA")
    void testDateModified_2() {
        Instant da = Instant.now();
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            da,
            da
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("DateModified 3 - Válido: DA=Hoje, DM=Hoje")
    void testDateModified_3() {
        Instant da = Instant.now();
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            da,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("DateModified 4 - Inválido: DA=Amanhã")
    void testDateModified_4() {
        Instant da = Instant.now().plus(1, ChronoUnit.DAYS);
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            da,
            da
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        // Verifica se há violações em dateAdded (data futura) e dateModified (data futura)
        assertFalse(violations.isEmpty());
        boolean hasDateAddedViolation = violations.stream().anyMatch(v -> "dateAdded".equals(v.getPropertyPath().toString()));
        boolean hasDateModifiedViolation = violations.stream().anyMatch(v -> "dateModified".equals(v.getPropertyPath().toString()));
        assertTrue(hasDateAddedViolation || hasDateModifiedViolation);
    }

    @Test
    @DisplayName("DateModified 5 - Válido: Ocupado=False (Nulo)")
    void testDateModified_5() {
        Instant da = Instant.now().minus(1, ChronoUnit.DAYS);
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            da,
            null
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertEquals(product, savedProduct);
    }

    @Test
    @DisplayName("DateModified 6 - Regra de Negócio: DM < DA")
    void testDateModified_6() {
        Instant da = Instant.now();
        Instant dm = da.minus(1, ChronoUnit.SECONDS);
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            da,
            dm
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("DateModified 7 - Inválido: DM no futuro")
    void testDateModified_7() {
        Instant da = Instant.now().minus(1, ChronoUnit.DAYS);
        Instant dm = Instant.now().plus(1, ChronoUnit.DAYS);
        Product product = createProductSample(
            1L,
            "Título Válido",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            da,
            dm
        );
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals("dateModified", violations.iterator().next().getPropertyPath().toString());
    }
}
