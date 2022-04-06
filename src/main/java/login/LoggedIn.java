package login;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;

public class LoggedIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public LoggedIn() {
        super();
    }
    
    private static boolean login(String inputUsername, String inputPassword) {
        if (
				((inputUsername.equals("web2_user")) && (inputPassword.equals("sabado_pass"))) |
				((inputUsername.equals("mendigo_user")) && (inputPassword.equals("pegador_pass"))) |
				((inputUsername.equals("rural_user")) && (inputPassword.equals("pap_pass")))
		) {
        	 return true;
        } else return false;
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession userSession = req.getSession();
		userSession.setAttribute("authorized", false);
		
//		String dispatcherType = req.getDispatcherType().toString(); // se é forward tem os atributos senão n tem
//		System.out.println("[LoggedIn.java] dispatcherType: "+ dispatcherType);
		
//		if (dispatcherType == "FORWARD") {
//			System.out.println("[LoggedIn.java] verificar credencias");
//			String reqUsername = (String) req.getAttribute("inputUsername");
//			String reqPassword = (String) req.getAttribute("inputPassword");
//			
//			if(login(reqUsername, reqPassword)) {
//				System.out.println("[LoggedIn.java] credenciais ok");
//				userSession.setAttribute("authorized", true);
//			}
//			
//		} else {
//			
//			if ((boolean) userSession.getAttribute("loggedIn")) {
//				System.out.println("[LoggedIn.java] logado");
//				userSession.setAttribute("authorized", true);
//			} else {
//				System.out.println("[LoggedIn.java] não logado");
//			}
//		}
		
		
		if(userSession.isNew()) {
			System.out.println("[LoggedIn.java] sessão nova");
			
			// quando tento acessar direto, mesmo tendo uma sessão isso aqui dá erro
			if ((boolean) userSession.getAttribute("loggedIn")) {
				System.out.println("[LoggedIn.java] logado");
				userSession.setAttribute("authorized", true);
			} else {
				System.out.println("[LoggedIn.java] não logado");
			}
			
		} else {
			System.out.println("[LoggedIn.java] sessão já existente");
			if ((boolean) userSession.getAttribute("loggedIn")) {
				System.out.println("[LoggedIn.java] logado");
				userSession.setAttribute("authorized", true);
				
			} else {
				System.out.println("[LoggedIn.java] não logado");
				String dispatcherType = req.getDispatcherType().toString(); // se é forward tem os atributos senão n tem
				System.out.println("[LoggedIn.java] dispatcherType: "+ dispatcherType);
				
				if (dispatcherType == "FORWARD") {
					System.out.println("[LoggedIn.java] verificar credencias");
					String reqUsername = (String) req.getAttribute("inputUsername");
					String reqPassword = (String) req.getAttribute("inputPassword");
					
					if(login(reqUsername, reqPassword)) {
						System.out.println("[LoggedIn.java] credenciais ok");
						userSession.setAttribute("authorized", true);
					}
				}
			}
		}
		
		if (!res.isCommitted()) {
			if ((boolean) userSession.getAttribute("authorized")) {
				System.out.println("[LoggedIn.java] authorized");
				String username = (String) userSession.getAttribute("username");
				res.getWriter().println("<h1>Boa Tarde " + username + "</h1>");
				return;
				 
			 } else {
				System.out.println("[LoggedIn.java] not authorized");
				res.sendRedirect("index.html");
				return;
			 }
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
