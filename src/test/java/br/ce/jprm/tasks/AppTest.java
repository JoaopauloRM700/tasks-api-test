package br.ce.jprm.tasks;


import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * Unit test for simple App.instborz
 */
public class AppTest {
    
    @BeforeClass
    public static void Setup(){
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas(){
        RestAssured.given()
                    .when()
                        .get("/todo")
                    .then()
                        .statusCode(200)
                    ;
    }

    @Test
    public void deveAdicionarTarefaComSucesso(){
        RestAssured.given()
                        .body("{\"task\":\"Teste via API\", \"dueDate\":\"2022-12-30\"}")
                        .contentType(ContentType.JSON)
                    .when()
                        .post("/todo")
                    .then()
                        .log().all()
                        .statusCode(201)
                    ;
    }

    @Test
    public void NaoDeveAdicionarTarefaInvalida(){
        RestAssured.given()
                        .body("{\"task\":\"Teste via API\", \"dueDate\":\"2021-12-30\"}")
                        .contentType(ContentType.JSON)
                    .when()
                        .post("/todo")
                    .then()
                        .log().all()
                        .statusCode(400)
                        .body("message", CoreMatchers.is("Due date must not be in past"))
                    ;
    }
}

