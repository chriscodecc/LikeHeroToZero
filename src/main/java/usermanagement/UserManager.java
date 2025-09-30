package usermanagement;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hibernate.DBConnectionManager;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@SessionScoped
public class UserManager implements Serializable{

	private static final long serialVersionUID = -3365863312972543871L;
	
	private static final Logger LOGGER =  LoggerFactory.getLogger(UserManager.class);
	
	public static final String HOME_PAGE_REDIRECT = "/secured/home.xhtml?faces-redirect=true"; 	
	public static final String LOGOUT_PAGE_REDIRECT = "/logout.xhtml?faces-redirect=true";		
	public static final String ADDDATA_PAGE_REDIRECT = "/secured/addData.xhtml?faces-redirect=true";	
	
	private String userId;
	private String userPassword;
	private User currentUser;
	
	public String login() {
		//lookup the user base to find user and check pw
		
		currentUser = find(userId, userPassword);
		if(currentUser != null) {
			LOGGER.info("login successful for '{}'", userId);
			System.out.println(HOME_PAGE_REDIRECT);
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userManager", this);
			
			return HOME_PAGE_REDIRECT;
		} else {
			LOGGER.info("login faild for '{}'", userId);
			//bib??
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login failed","Invalid or unkown credentials."));
			return null;
		}
	}
	
	public String logout() {
		String identifier = userId;
		
		LOGGER.debug("invalidating session for '{}'}", identifier);
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		LOGGER.info("logout successful for '{}'", identifier);
		return LOGOUT_PAGE_REDIRECT;
	}
	
	public boolean isLoggedIn() {
		return currentUser != null;
	}
	
	public String isLoggedInForwardHome() {
		if(isLoggedIn()) {
			return HOME_PAGE_REDIRECT;
		}
		return null;
	}
	
	public String isLoggedInForwardAddData() {
		if(isLoggedIn()) {
			return ADDDATA_PAGE_REDIRECT;
		}
		return null;
	}

	//NEED DATA AND DB CONNECTION
	private User find(String userName, String password) {
		return DBConnectionManager.checkUsers(userName, password);
	}
	
	  public String getUserId() {
		    return userId;
		  }

	  public void setUserId(String userId) {
	    this.userId = userId;
	  }

	  public String getUserPassword() {
	    return userPassword;
	  }

	  public void setUserPassword(String userPassword) {
	    this.userPassword = userPassword;
	  }

	  public User getCurrentUser() {
	    return currentUser;
	  }
}
