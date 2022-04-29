package app;

import static spark.Spark.*;
import service.AlunoService;

public class Aplicacao {
	
	private static AlunoService alunoService = new AlunoService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/aluno/insert", (request, response) -> alunoService.insert(request, response));

        get("/aluno/:id", (request, response) -> alunoService.get(request, response));
        
        get("/aluno/list/:orderby", (request, response) -> alunoService.getAll(request, response));

        get("/aluno/update/:id", (request, response) -> alunoService.getToUpdate(request, response));
        
        post("/aluno/update/:id", (request, response) -> alunoService.update(request, response));
           
        get("/aluno/delete/:id", (request, response) -> alunoService.delete(request, response));

             
    }
}
