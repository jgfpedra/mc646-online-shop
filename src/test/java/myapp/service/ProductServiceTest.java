package myapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;
import myapp.domain.Product;
import myapp.domain.enumeration.ProductStatus;
import myapp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
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
    private ProductService productService; // Injects the mock into the service

    // Helper method to create a sample product with flexible parameters
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
        Instant dateAdded
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
            .dateAdded(dateAdded);

        return product;
    }

    @Test
    public void testTitleEquivalencePartitionTitle() {
        /*
         * Valid Cases (TC1, TC2, TC3, TC4)
         */

        //Valid case Product Title == 3 char - TC1
        Product productWithValidTitleMin = createProductSample(
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
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithValidTitleMin);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidTitleMin)).thenReturn(productWithValidTitleMin);
        Product savedProduct = productService.save(productWithValidTitleMin);
        assertEquals(productWithValidTitleMin, savedProductMin);

        //Valid case Product Title == 4 char - TC2
        Product productWithValidTitleMinOne = createProductSample(
            1L,
            "SNES",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidTitleMinOne);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidTitleMinOne)).thenReturn(productWithValidTitleMinOne);
        savedProduct = productService.save(productWithValidTitleMinOne);
        assertEquals(productWithValidTitleMinOne, savedProduct);

        //Valid case Product Title == 99 char - TC3
        String title_tc3 = "A".repeat(99);
        Product productWithValidTitleMaxOne = createProductSample(
            1L,
            title_tc3,
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidTitleMaxOne);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidTitleMaxOne)).thenReturn(productWithValidTitleMaxOne);
        savedProduct = productService.save(productWithValidTitleMaxOne);
        assertEquals(productWithValidTitleMaxOne, savedProduct);

        //Valid case Product Title == 100 char - TC4
        String title_tc4 = "A".repeat(100);
        Product productWithValidTitleMax = createProductSample(
            1L,
            title_tc4,
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidTitleMax);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidTitleMax)).thenReturn(productWithValidTitleMax);
        savedProduct = productService.save(productWithValidTitleMax);
        assertEquals(productWithValidTitleMax, savedProduct);

        /*
         * Invalid Cases (TC5, TC6, TC7)
         */
        // Invalid case Product Title = 2 - TC5
        Product productWithTwoCharTitle = createProductSample(
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
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productWithTwoCharTitle);
        // Assert
        assertEquals("title", violations_invalid.iterator().next().getPropertyPath().toString());

        // Invalid case Product Title = 101 - TC6
        String title_tc6 = "A".repeat(101);
        Product productWithOneHundredOneCharTitle = createProductSample(
            1L,
            title_tc6,
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_invalid = validator.validate(productWithOneHundredOneCharTitle);
        // Assert
        assertEquals("title", violations_invalid.iterator().next().getPropertyPath().toString());

        // Invalid case Product Title = null - TC7
        Product productWithNullCharTitle = createProductSample(
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
            Instant.now()
        );
        violations_invalid = validator.validate(productWithNullCharTitle);
        // Assert
        assertEquals("title", violations_invalid.iterator().next().getPropertyPath().toString());
    }

    // KeyWord

    @Test
    public void testKeyWordEquivalencePartitionTitle() {
        /*
         * Valid Cases (TC8, TC9, TC10, TC11, TC12)
         */

        //Valid case Key Word == null char - TC8
        Product productWithValidKeyWordNull = createProductSample(
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
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithValidKeyWordNull);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidKeyWordNull)).thenReturn(productWithValidKeyWordNull);
        Product savedProduct = productService.save(productWithValidKeyWordNull);
        assertEquals(productWithValidKeyWordNull, savedProduct);

        //Valid case Key Word == 1 char - TC9
        Product productWithValidKeyWordMin = createProductSample(
            1L,
            "NES",
            "a",
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidKeyWordMin);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidKeyWordMin)).thenReturn(productWithValidKeyWordMin);
        savedProduct = productService.save(productWithValidKeyWordMin);
        assertEquals(productWithValidKeyWordMin, savedProduct);

        //Valid case Key Word == 2 char - TC10
        Product productWithValidKeyWordMinOne = createProductSample(
            1L,
            "NES",
            "aa",
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidKeyWordMinOne);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidKeyWordMinOne)).thenReturn(productWithValidKeyWordMinOne);
        savedProduct = productService.save(productWithValidKeyWordMinOne);
        assertEquals(productWithValidKeyWordMinOne, savedProduct);

        //Valid case Key Word == 199 char - TC11
        String keywordTC11 = "A".repeat(199);
        Product productWithValidKeyWordMaxOne = createProductSample(
            1L,
            "NES",
            keywordTC11,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidKeyWordMaxOne);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidKeyWordMaxOne)).thenReturn(productWithValidKeyWordMaxOne);
        savedProduct = productService.save(productWithValidKeyWordMaxOne);
        assertEquals(productWithValidKeyWordMaxOne, savedProduct);

        //Valid case Key Word == 200 char - TC12
        String keywordTC12 = "A".repeat(200);
        Product productWithValidKeyWordMax = createProductSample(
            1L,
            "NES",
            keywordTC12,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidKeyWordMax);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidKeyWordMax)).thenReturn(productWithValidKeyWordMax);
        savedProduct = productService.save(productWithValidKeyWordMax);
        assertEquals(productWithValidKeyWordMax, savedProduct);

        /*
         * Invalid Cases (TC13)
         */
        // Invalid case Key Word = 201 - TC13
        String keywordTC13 = "A".repeat(201);
        Product productWithInvalidKeyWordMax = createProductSample(
            1L,
            "NES",
            keywordTC13,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productWithInvalidKeyWordMax);
        // Assert
        assertEquals("keywords", violations_invalid.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testDescriptionEquivalencePartitionTitle() {
        /*
         * Valid Cases (TC14, TC15, TC16)
         */

        //Valid case Description == null char - TC14
        Product productWithValidDescriptionNull = createProductSample(
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
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithValidDescriptionNull);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidDescriptionNull)).thenReturn(productWithValidDescriptionNull);
        Product savedProduct = productService.save(productWithValidDescriptionNull);
        assertEquals(productWithValidDescriptionNull, savedProduct);

        //Valid case Description == 50 char - TC15
        String descriptionTC15 = "A".repeat(50);
        Product productWithValidDescriptionMax = createProductSample(
            1L,
            "NES",
            null,
            descriptionTC15,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidDescriptionMax);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidDescriptionMax)).thenReturn(productWithValidDescriptionMax);
        savedProduct = productService.save(productWithValidDescriptionMax);
        assertEquals(productWithValidDescriptionMax, savedProduct);

        //Valid case Description == 51 char - TC16
        String descriptionTC16 = "A".repeat(51);
        Product productWithValidDescriptionMaxOne = createProductSample(
            1L,
            "NES",
            null,
            descriptionTC16,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidDescriptionMaxOne);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidDescriptionMax)).thenReturn(productWithValidDescriptionMaxOne);
        savedProduct = productService.save(productWithValidDescriptionMaxOne);
        assertEquals(productWithValidDescriptionMaxOne, savedProduct);

        /*
         * Invalid Cases (TC17, TC18, TC19, TC20)
         */
        // Invalid case Description = 1 - TC17
        Product productWithInvalidDescriptionMin = createProductSample(
            1L,
            "NES",
            null,
            "A",
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productWithInvalidDescriptionMin);
        // Assert
        assertEquals("description", violations_invalid.iterator().next().getPropertyPath().toString());

        // Invalid case Description = 2 - TC18
        Product productWithInvalidDescriptionMinOne = createProductSample(
            1L,
            "NES",
            null,
            "AA",
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_invalid = validator.validate(productWithInvalidDescriptionMinOne);
        // Assert
        assertEquals("description", violations_invalid.iterator().next().getPropertyPath().toString());

        // Invalid case Description = 49 - TC19
        String descriptionTC19 = "A".repeat(49);
        Product productWithInvalidDescriptioMaxOne = createProductSample(
            1L,
            "NES",
            null,
            descriptionTC19,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_invalid = validator.validate(productWithInvalidDescriptioMaxOne);
        // Assert
        assertEquals("description", violations_invalid.iterator().next().getPropertyPath().toString());
    }
}
