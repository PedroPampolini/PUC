package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.AlunoDAO;
import model.Aluno;
import spark.Request;
import spark.Response;

public class AlunoService {
	private AlunoDAO alunoDao = new AlunoDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_NOME = 2;
	private final int FORM_ORDERBY_EMAIL = 3;
	
	public AlunoService() {
		makeForm();
	}
	
	public void makeForm() {
		makeForm(FORM_INSERT, new Aluno(),FORM_ORDERBY_NOME);
	}
	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Aluno(), orderBy);
	}
	
	public void makeForm(int tipo, Aluno aluno,int orderBy) {
		String fileName = "form.html";
		form = "";
		try {
			Scanner entrada = new Scanner(new File(fileName));
			while(entrada.hasNext()) {
				form += (entrada.nextLine() + "\n");
			}
			entrada.close();
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		String umAluno = "";
		if(tipo != FORM_INSERT) {
			umAluno += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/aluno/list/1\">Novo Aluno</a></b></font></td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t</table>";
			umAluno += "\t<br>";
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/aluno/";
			String acao,nome,buttonLabel;
			
			if(tipo == FORM_INSERT) {
				action += "insert";
				acao = "Inserir Aluno";
				nome = "Nome do Aluno...";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + aluno.getId();
				acao = "Atualizar Produto (ID " + aluno.getId() + ")";
				nome = aluno.getNome();
				buttonLabel = "Atualizar";
			}
			umAluno += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umAluno += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + acao + "</b></font></td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ nome +"\"></td>";
			umAluno += "\t\t\t<td>E-mail: <input class=\"input--register\" type=\"text\" name=\"email\" value=\""+ aluno.getEmail() +"\"></td>";
			umAluno += "\t\t\t<td>Senha: <input class=\"input--register\" type=\"text\" name=\"senha\" value=\""+ aluno.getSenha() +"\"></td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td>&nbsp;Idade: <input class=\"input--register\" type=\"text\" name=\"idade\" value=\""+ aluno.getIdade() + "\"></td>";
			umAluno += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t</table>";
			umAluno += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umAluno += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Aluno (ID " + aluno.getId() + ")</b></font></td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td>&nbsp;Nome: "+ aluno.getNome() +"</td>";
			umAluno += "\t\t\t<td>Email: "+ aluno.getEmail() +"</td>";
			umAluno += "\t\t\t<td>Senha: "+ aluno.getSenha() +"</td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td>&nbsp;Idade: "+ aluno.getIdade() + "</td>";
			umAluno += "\t\t\t<td>&nbsp;</td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-ALUNO>", umAluno);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Alunos</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/aluno/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/aluno/list/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
        		"\t<td><a href=\"/aluno/list/" + FORM_ORDERBY_EMAIL + "\"><b>Email</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Aluno> alunos = alunoDao.get();
		/*if (orderBy == FORM_ORDERBY_ID) {
			alunos = alunoDao.getOrderById();
		} else if (orderBy == FORM_ORDERBY_NOME) {
			alunos = alunoDao.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_EMAIL) {
			alunos = alunoDao.getOrderByEmail();
		}*/
		
		String bgColor = "";
		int i = 0;
		for(Aluno a : alunos) {
			bgColor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgColor +"\">\n" + 
          		  	"\t<td>" + a.getId() + "</td>\n" +
          		  	"\t<td>" + a.getNome() + "</td>\n" +
          		  	"\t<td>" + a.getEmail() + "</td>\n" +
          		  	"\t<td align=\"center\" valign=\"middle\"><a href=\"/aluno/" + a.getId() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
          		  	"\t<td align=\"center\" valign=\"middle\"><a href=\"/aluno/update/" + a.getId() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
          		  	"\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteProduto('" + a.getId() + "', '" + a.getNome() + "', '" + a.getEmail() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
          		  	"</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-ALUNO>", list);			
	}
	
	public Object insert(Request request, Response response) {
		String nome = request.queryParams("nome");
		String email = request.queryParams("email");
		String senha = request.queryParams("senha");
		int idade = Integer.parseInt(request.queryParams("idade"));
		
		String resp = "";
		Aluno max = alunoDao.get().get(alunoDao.get().size() - 1);
		int posicao = max.getId() + 1;
		Aluno aluno = new Aluno(posicao,nome,email,senha,idade);
		
		if(alunoDao.insert(aluno) == true) {
			resp = "Aluno (" + nome + ") inserido!";
			response.status(201); // 201 Created
		}
		else {
			resp = "Aluno (" + nome + ") não inserido !";
			response.status(404);	// 404 Not Found
		}
		
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Aluno aluno = (Aluno) alunoDao.get(id);
		
		if (aluno != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, aluno, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Aluno " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Aluno aluno = (Aluno) alunoDao.get(id);
		
		if (aluno != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, aluno, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Nome " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Aluno aluno = alunoDao.get(id);
        String resp = "";       

        if (aluno != null) {
        	aluno.setNome(request.queryParams("nome"));
        	aluno.setEmail(request.queryParams("email"));
        	aluno.setSenha(request.queryParams("senha"));
        	aluno.setIdade(Integer.parseInt(request.queryParams("idade")));
        	alunoDao.update(aluno);
        	response.status(200); // success
            resp = "Aluno (ID " + aluno.getId() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Aluno (ID " + aluno.getId() + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Aluno aluno = alunoDao.get(id);
        String resp = "";       

        if (aluno != null) {
        	alunoDao.delete(id);
            response.status(200); // success
            resp = "Aluno (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Aluno (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
	
}
