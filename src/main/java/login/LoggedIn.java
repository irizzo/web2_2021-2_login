package login;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.time.LocalDateTime;

public class LoggedIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public LoggedIn() {
        super();
    }
    
	LocalDateTime activeDateTime = LocalDateTime.now();
	
    private static boolean login(String inputUsername, String inputPassword) {
        if (
				((inputUsername.equals("web2_user")) && (inputPassword.equals("sabado_pass"))) |
				((inputUsername.equals("mendigo_user")) && (inputPassword.equals("pegador_pass"))) |
				((inputUsername.equals("rural_user")) && (inputPassword.equals("pap_pass"))) |
				((inputUsername.equals("irizzo")) && (inputPassword.equals("irizzo"))) 
		) {
        	 return true;
        } else return false;
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession userSession = req.getSession();
		userSession.setAttribute("authorized", false);
		
		String dispatcherType = req.getDispatcherType().toString(); // se � forward tem os atributos sen�o n tem
		System.out.println("[LoggedIn.java] dispatcherType: "+ dispatcherType);
		
		if (dispatcherType == "FORWARD" | dispatcherType == "INCLUDE") {
			// veio pelo dispatcher
			
			System.out.println("[LoggedIn.java] dispatcher - verificar credenciais");
			String reqUsername = (String) req.getAttribute("inputUsername");
			String reqPassword = (String) req.getAttribute("inputPassword");
			
			if (userSession.isNew()) {
				// dispatcher - sess�o nova
				if(login(reqUsername, reqPassword)) {
					// dispatcher - sess�o nova - credenciais batem com alguma salva
					System.out.println("[LoggedIn.java] credenciais ok");
					
					userSession.setAttribute("authorized", true);
					
					String userSessionId = "q9809190umaIDgeradaQualquerd98qd";
					
					userSession.setAttribute("sessionID", userSessionId);
					userSession.setAttribute("lastTimeActive", activeDateTime);
					userSession.setMaxInactiveInterval(3600);
					userSession.setAttribute("loggedIn", true);
					userSession.setAttribute("username", reqUsername);
					
				} else {
					// dispatcher - sess�o nova - credenciais n�o batem
					System.out.println("[LoggedIn.java] n�o logado");
					userSession.setAttribute("loggedIn", false);
					userSession.setAttribute("authorized", false);
				}	
			} else {
				// dispatcher - sess�o n�o � nova
				boolean loginAuth = (boolean) userSession.getAttribute("loggedIn");
				
				if (loginAuth) {
					// dispatcher - sess�o n�o � nova - est� logado
					userSession.setAttribute("authorized", true);
					userSession.setAttribute("username", reqUsername);
				}
			}
			
		} else {
			// n�o veio pelo dispatcher, ou seja, acessou success.do direto
			
			if (userSession.isNew()) {
				userSession.setAttribute("authorized", false);
				userSession.setAttribute("loggedIn", false);
				
			} else if ((boolean) userSession.getAttribute("loggedIn")) {
					System.out.println("[LoggedIn.java] logado");
					userSession.setAttribute("authorized", true);
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
