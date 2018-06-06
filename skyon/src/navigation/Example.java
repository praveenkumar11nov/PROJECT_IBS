package navigation;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Example extends Item implements Serializable 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;    
    private String role;    
    
    public ThirdLevel[] getThirdLevel() {
		return thirdLevel;
	}

	public void setThirdLevel(ThirdLevel[] thirdLevel) {
		this.thirdLevel = thirdLevel;
	}

	private ThirdLevel[] thirdLevel; 

    public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    } 
}
