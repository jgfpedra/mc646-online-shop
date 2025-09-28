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

    @Test
    public void testRatingEquivalencePartitionTitle() {
        /*
         * Valid Cases (TC20, TC21, TC22, TC23, TC24)
         */

        //Valid case Rating == null int - TC20
        Product productWithValidRatingNull = createProductSample(
            1L,
            "NES",
            null,
            null,
            null,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithValidRatingNull);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidRatingNull)).thenReturn(productWithValidRatingNull);
        Product savedProduct = productService.save(productWithValidRatingNull);
        assertEquals(productWithValidRatingNull, savedProduct);

        //Valid case Rating == 1 int - TC21
        Product productWithValidRatingMin = createProductSample(
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
        violations_valid = validator.validate(productWithValidRatingMin);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidRatingMin)).thenReturn(productWithValidRatingMin);
        savedProduct = productService.save(productWithValidRatingMin);
        assertEquals(productWithValidRatingMin, savedProduct);

        //Valid case Rating == 2 int - TC22
        Product productWithValidRatingMinOne = createProductSample(
            1L,
            "NES",
            null,
            null,
            2,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidRatingMinOne);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidRatingMinOne)).thenReturn(productWithValidRatingMinOne);
        savedProduct = productService.save(productWithValidRatingMinOne);
        assertEquals(productWithValidRatingMinOne, savedProduct);

        //Valid case Rating == 9 int - TC23
        Product productWithValidRatingMaxOne = createProductSample(
            1L,
            "NES",
            null,
            null,
            9,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidRatingMaxOne);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidRatingMaxOne)).thenReturn(productWithValidRatingMaxOne);
        savedProduct = productService.save(productWithValidRatingMaxOne);
        assertEquals(productWithValidRatingMaxOne, savedProduct);

        //Valid case Rating == 10 int - TC24
        Product productWithValidRatingMax = createProductSample(
            1L,
            "NES",
            null,
            null,
            10,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidRatingMax);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidRatingMax)).thenReturn(productWithValidRatingMax);
        savedProduct = productService.save(productWithValidRatingMax);
        assertEquals(productWithValidRatingMax, savedProduct);

        /*
         * Invalid Cases (TC25, TC26, TC27)
         */
        // Invalid case Rating = 0 int - TC25
        Product productWithInvalidRatingMin = createProductSample(
            1L,
            "NES",
            null,
            null,
            0,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productWithInvalidRatingMin);
        // Assert
        assertEquals("rating", violations_invalid.iterator().next().getPropertyPath().toString());

        // Invalid case Rating = 11 int - TC26
        Product productWithInvalidRatingMax = createProductSample(
            1L,
            "NES",
            null,
            null,
            11,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_invalid = validator.validate(productWithInvalidRatingMax);
        // Assert
        assertEquals("rating", violations_invalid.iterator().next().getPropertyPath().toString());

        // Invalid case Rating = 1.11 int - TC27
        Product productWithInvalidRatingFloat = createProductSample(
            1L,
            "NES",
            null,
            null,
            1.11,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_invalid = validator.validate(productWithInvalidRatingFloat);
        // Assert
        assertEquals("rating", violations_invalid.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testPriceEquivalencePartitionTitle() {
        /*
         * Valid Cases (TC28, TC29, TC30, TC31)
         */

        //Valid case price == 1.00 BigDecimal - TC28
        BigDecimal priceTC28 = new BigDecimal("1.00");
        Product productWithValidPriceMin = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            priceTC28,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithValidPriceMin);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidPriceMin)).thenReturn(productWithValidPriceMin);
        Product savedProduct = productService.save(productWithValidPriceMin);
        assertEquals(productWithValidPriceMin, savedProduct);

        //Valid case price == 1.01 BigDecimal - TC29
        BigDecimal priceTC29 = new BigDecimal("1.01");
        Product productWithValidPriceMinOne = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            priceTC29,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidPriceMinOne);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidPriceMinOne)).thenReturn(productWithValidPriceMinOne);
        savedProduct = productService.save(productWithValidPriceMinOne);
        assertEquals(productWithValidPriceMinOne, savedProduct);

        //Valid case price == 9998.99 BigDecimal - TC30
        BigDecimal priceTC30 = new BigDecimal("9998.99");
        Product productWithValidPriceMaxOne = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            priceTC30,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidPriceMaxOne);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidPriceMaxOne)).thenReturn(productWithValidPriceMaxOne);
        savedProduct = productService.save(productWithValidPriceMaxOne);
        assertEquals(productWithValidPriceMaxOne, savedProduct);

        //Valid case price == 9999.00 BigDecimal - TC31
        BigDecimal priceTC30 = new BigDecimal("9999.00");
        Product productWithValidPriceMax = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            priceTC31,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidPriceMax);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidPriceMax)).thenReturn(productWithValidPriceMax);
        savedProduct = productService.save(productWithValidPriceMax);
        assertEquals(productWithValidPriceMax, savedProduct);

        /*
         * Invalid Cases (TC32, TC33, TC34, TC35, TC36)
         */
        // Invalid case price == null BigDecimal - TC32
        Product productWithInvalidPriceNull = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            null,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productWithInvalidPriceNull);
        // Assert
        assertEquals("price", violations_invalid.iterator().next().getPropertyPath().toString());

        // Invalid case price == 0.99 BigDecimal - TC33
        BigDecimal priceTC33 = BigDecimal("0.99");
        Product productWithInvalidPriceMin = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            priceTC33,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_invalid = validator.validate(productWithInvalidPriceMin);
        // Assert
        assertEquals("price", violations_invalid.iterator().next().getPropertyPath().toString());

        // Invalid case price  == 9999.01 BigDecimal - TC34
        BigDecimal priceTC34 = BigDecimal("9999.01");
        Product productWithInvalidPriceMax = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            priceTC34,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_invalid = validator.validate(productWithInvalidPriceMax);
        // Assert
        assertEquals("price", violations_invalid.iterator().next().getPropertyPath().toString());

        // Invalid case price == -1 BigDecimal - TC35
        BigDecimal priceTC35 = BigDecimal("-1");
        Product productWithInvalidPriceNegative = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            priceTC35,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_invalid = validator.validate(productWithInvalidPriceNegative);
        // Assert
        assertEquals("price", violations_invalid.iterator().next().getPropertyPath().toString());

        // Invalid case price == "oito" BigDecimal - TC36
        BigDecimal priceTC36 = BigDecimal("oito");
        Product productWithInvalidPriceString = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            priceTC36,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_invalid = validator.validate(productWithInvalidPriceString);
        // Assert
        assertEquals("price", violations_invalid.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testQuantityInStockEquivalencePartitionTitle() {
        /*
         * Valid Cases (TC37, TC38)
         */
        //Valid case quantityInStock == 0 int - TC37
        Product productWithValidQuantityMin = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            0,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithValidQuantityMin);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidQuantityMin)).thenReturn(productWithValidQuantityMin);
        Product savedProduct = productService.save(productWithValidQuantityMin);
        assertEquals(productWithValidQuantityMin, savedProduct);

        //Valid case quantityInStock == 1 int - TC38
        Product productWithValidQuantityMinOne = createProductSample(
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
        violations_valid = validator.validate(productWithValidQuantityMinOne);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidQuantityMinOne)).thenReturn(productWithValidQuantityMinOne);
        savedProduct = productService.save(productWithValidQuantityMinOne);
        assertEquals(productWithValidQuantityMinOne, savedProduct);

        /*
         * Invalid Cases (TC39, TC40, TC41)
         */
        // Invalid case quantityInStock == null int - TC39
        Product productWithInvalidQuantityNull = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            null,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productWithInvalidQuantityNull);
        // Assert
        assertEquals("quantityInStock", violations_invalid.iterator().next().getPropertyPath().toString());

        // Invalid case quantityInStock == -1 int - TC40
        Product productWithInvalidQuantityNegative = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            -1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productWithInvalidQuantityNegative);
        // Assert
        assertEquals("quantityInStock", violations_invalid.iterator().next().getPropertyPath().toString());

        // Invalid case quantityInStock == -1 int - TC41
        Product productWithInvalidQuantityFloat = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1.5,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productWithInvalidQuantityFloat);
        // Assert
        assertEquals("quantityInStock", violations_invalid.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testStatusEquivalencePartitionTitle() {
        /*
         * Valid Cases (TC42, TC43, TC44, TC45)
         */
        //Valid case status == ProductStatus.IN_STOCK ProductStatus - TC42
        Product productWithValidStatusInStock = createProductSample(
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
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithValidStatusInStock);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidStatusInStock)).thenReturn(productWithValidStatusInStock);
        Product savedProduct = productService.save(productWithValidStatusInStock);
        assertEquals(productWithValidStatusInStock, savedProduct);

        //Valid case status == ProductStatus.OUT_OF_STOCK ProductStatus - TC43
        Product productWithValidStatusPreOrder = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.PREORDER,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithValidStatusPreOrder);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidStatusPreOrder)).thenReturn(productWithValidStatusPreOrder);
        savedProduct = productService.save(productWithValidStatusPreOrder);
        assertEquals(productWithValidStatusPreOrder, savedProduct);

        //Valid case status == ProductStatus.DISCONTINUED ProductStatus - TC44
        Product productWithValidStatusDiscontinued = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.DISCONTINUED,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithValidStatusDiscontinued);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidStatusDiscontinued)).thenReturn(productWithValidStatusDiscontinued);
        savedProduct = productService.save(productWithValidStatusDiscontinued);
        assertEquals(productWithValidStatusDiscontinued, savedProduct);

        //Valid case status == ProductStatus.OUT_OF_STOCK ProductStatus - TC45
        Product productWithValidStatusOutOfStock = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.OUT_OF_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithValidStatusOutOfStock);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidStatusOutOfStock)).thenReturn(productWithValidStatusOutOfStock);
        savedProduct = productService.save(productWithValidStatusOutOfStock);
        assertEquals(productWithValidStatusOutOfStock, savedProduct);

        /*
         * Invalid Cases (TC46, TC47)
         */
        // Invalid case status == null ProductStatus - TC46
        Product productWithInvalidStatusNull = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            null,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productWithInvalidStatusNull);
        // Assert
        assertEquals("status", violations_invalid.iterator().next().getPropertyPath().toString());

        // Invalid case status == ProductStatus.AVAILABLE ProductStatus - TC47
        Product productWithInvalidStatusAvailable = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.AVAILABLE,
            null,
            Instant.now()
        );
        violations_invalid = validator.validate(productWithInvalidStatusAvailable);
        // Assert
        assertEquals("status", violations_invalid.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testWeightEquivalencePartitionTitle() {
        /*
         * Valid Cases (TC48, TC49, TC50)
         */
        //Valid case weight == null Double - TC48
        Product productWithValidWeightNull = createProductSample(
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
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithValidWeightNull);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidWeightNull)).thenReturn(productWithValidWeightNull);
        Product savedProduct = productService.save(productWithValidWeightNull);
        assertEquals(productWithValidWeightNull, savedProduct);

        //Valid case weight == 0.00 Double - TC49
        Product productWithValidWeightMin = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            0.00,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidWeightMin);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidWeightMin)).thenReturn(productWithValidWeightMin);
        savedProduct = productService.save(productWithValidWeightMin);
        assertEquals(productWithValidWeightMin, savedProduct);

        //Valid case weight == 0.01 Double - TC50
        Product productWithValidWeightMinOne = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            0.01,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidWeightMinOne);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidWeightMinOne)).thenReturn(productWithValidWeightMinOne);
        savedProduct = productService.save(productWithValidWeightMinOne);
        assertEquals(productWithValidWeightMinOne, savedProduct);

        /*
         * Invalid Cases (TC51, TC52)
         */
        // Invalid case quantityInStock == -0.01 Double - TC51
        Product productWithInvalidWeightNegative = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            -0.01,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productWithInvalidWeightNegative);
        // Assert
        assertEquals("weight", violations_invalid.iterator().next().getPropertyPath().toString());

        // Invalid case quantityInStock == "10 gramas" - TC52
        Product productWithInvalidWeightString = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            null,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            "10 gramas",
            Instant.now()
        );
        violations_invalid = validator.validate(productWithInvalidWeightString);
        // Assert
        assertEquals("weight", violations_invalid.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testDimensionsEquivalencePartitionTitle() {
        /*
         * Valid Cases (TC53, TC54, TC55, TC56, TC57)
         */
        //Valid case dimensions == null String - TC53
        Product productWithValidDimensionsNull = createProductSample(
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
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithValidDimensionsNull);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidDimensionsNull)).thenReturn(productWithValidDimensionsNull);
        Product savedProduct = productService.save(productWithValidDimensionsNull);
        assertEquals(productWithValidDimensionsNull, savedProduct);

        //Valid case dimensions == 1 char String - TC54
        Product productWithValidDimensionsMin = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            "A",
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidDimensionsMin);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidDimensionsMin)).thenReturn(productWithValidDimensionsMin);
        savedProduct = productService.save(productWithValidDimensionsMin);
        assertEquals(productWithValidDimensionsMin, savedProduct);

        //Valid case dimensions == 1 char String - TC55
        Product productWithValidDimensionsMinOne = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            "AA",
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidDimensionsMinOne);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidDimensionsMinOne)).thenReturn(productWithValidDimensionsMinOne);
        savedProduct = productService.save(productWithValidDimensionsMinOne);
        assertEquals(productWithValidDimensionsMinOne, savedProduct);

        //Valid case dimensions == 49 char String - TC56
        String dimensionsTC55 = "A".repeat(49);
        Product productWithValidDimensionsMaxOne = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            dimensionsTC55,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidDimensionsMinOne);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidDimensionsMinOne)).thenReturn(productWithValidDimensionsMinOne);
        savedProduct = productService.save(productWithValidDimensionsMinOne);
        assertEquals(productWithValidDimensionsMinOne, savedProduct);

        //Valid case dimensions == 50 char String - TC57
        String dimensionsTC56 = "A".repeat(50);
        Product productWithValidDimensionsMax = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            dimensionsTC56,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        violations_valid = validator.validate(productWithValidDimensionsMax);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidDimensionsMax)).thenReturn(productWithValidDimensionsMax);
        savedProduct = productService.save(productWithValidDimensionsMax);
        assertEquals(productWithValidDimensionsMax, savedProduct);

        /*
         * Invalid Cases (TC58)
         */
        // Invalid case dimensions == 51 char String - TC58
        String dimensionsTC58 = "A".repeat(51);
        Product productWithInvalidDimensionsMax = createProductSample(
            1L,
            "NES",
            null,
            null,
            1,
            1,
            dimensionsTC58,
            BigDecimal.TEN,
            ProductStatus.IN_STOCK,
            null,
            Instant.now()
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productWithInvalidDimensionsMax);
        // Assert
        assertEquals("dimensions", violations_invalid.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testDateAddedEquivalencePartitionTitle() {
        /*
         * Valid Cases (TC59)
         */
        //Valid case dateAdded == 25/02/2025 Instant - TC59
        LocalDate date = LocalDate.of(2025, 2, 25);
        Instant instantDateTC59 = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Product productWithValidDateAdded = createProductSample(
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
            instantDateTC59
        );
        Set<ConstraintViolation<Product>> violations_valid = validator.validate(productWithValidDateAdded);
        // Assert
        System.err.println(violations_valid);
        assertTrue(violations_valid.isEmpty());
        when(productRepository.save(productWithValidDateAdded)).thenReturn(productWithValidDateAdded);
        Product savedProduct = productService.save(productWithValidDateAdded);
        assertEquals(productWithValidDateAdded, savedProduct);

        /*
         * Invalid Cases (TC60, TC61)
         */
        //Invalid case dateAdded == null Instant - TC60
        Product productWithInvalidDateAddedNull = createProductSample(
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
            null
        );
        Set<ConstraintViolation<Product>> violations_invalid = validator.validate(productWithInvalidDateAddedNull);
        // Assert
        assertEquals("dateAdded", violations_invalid.iterator().next().getPropertyPath().toString());

        //Invalid case dateAdded == 30/02/2025 Instant Instant - TC61
        LocalDate date = LocalDate.of(2025, 2, 30);
        Instant instantDateTC61 = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Product productWithInvalidDateAddedWrong = createProductSample(
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
            instantDateTC61
        );
        violations_invalid = validator.validate(productWithInvalidDateAddedWrong);
        // Assert
        assertEquals("dateAdded", violations_invalid.iterator().next().getPropertyPath().toString());
    }
}
