package oi.github.cadnunsdimir;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import oi.github.cadnunsdimir.congespapi.domain.services.TerritoryAssignmentService;

import static io.restassured.RestAssured.given;

// @QuarkusTest
class TerritoryAssignmentRecordResourceTest {

    @InjectMock
    TerritoryAssignmentService service; 


    // @BeforeAll
    void setup() {
    }

    // @Test
    void testgetCurrentSheet() {
        given()
          .when().get("/api/territory/assignment")
          .then()
             .statusCode(200);
    }

}