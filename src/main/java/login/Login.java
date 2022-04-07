package login;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletContext;
import jakarta.servlet.RequestDispatcher;

import java.io.IOException;
import java.time.LocalDateTime;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
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

    public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ServletContext context = req.getServletContext();
		String inputUsername = req.getParameter("username");
		String inputPassword = req.getParameter("password");
		
		HttpSession userSession = req.getSession();
		userSession.setAttribute("loggedIn", false);
		
		LocalDateTime activeDateTime = LocalDateTime.now();
		
		if(login(inputUsername, inputPassword)) {
			// credenciais v�lidas
			
			if (userSession.isNew()) {
				String userSessionId = "umaIDgeradaQualquerAf4387fw39q84d98qd";
				
				userSession.setAttribute("sessionID", userSessionId);
				userSession.setAttribute("lastTimeActive", activeDateTime);
				userSession.setMaxInactiveInterval(3600);
				userSession.setAttribute("loggedIn", true);
				userSession.setAttribute("username", inputUsername);
				
			} else {
				userSession.setAttribute("loggedIn", true);
				userSession.setAttribute("username", inputUsername);
			}
			
		} else {
			// credenciais inv�lidas
			userSession.setAttribute("loggedIn", false);
		}
		
		if (!res.isCommitted()) {
			if ((boolean) userSession.getAttribute("loggedIn")) {
				// vai pra logado
				RequestDispatcher reqDispatcher = context.getRequestDispatcher("/success.do");
				req.setAttribute("inputUsername", inputUsername);
				req.setAttribute("inputPassword", inputPassword);
				
				reqDispatcher.forward(req, res);
				return;
				
			} else {
				res.sendError(400, "Credenciais Inv�lidas");
				return;
			}
		}
	}
}
