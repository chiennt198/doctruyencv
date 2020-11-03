package jp.co.kcs_grp.base.json;
import spark.ResponseTransformer;

import com.google.gson.Gson;
/**
 * @author kcs
 *
 */
public class JsonTransformer implements ResponseTransformer {
    
	/**.
	 * gson
	 */
	private Gson gson = new Gson();

    @Override
    public String render(Object model) {
    	String json = gson.toJson(model);
        return json;
    }

}
