package navigation;

import java.io.Serializable;

public class ThirdLevel extends Item implements Serializable 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;    
    private String role;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
    
    
}
