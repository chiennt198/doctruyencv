package jp.co.kcs_grp.page;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class HtmlPage {
	
	
	public TemplateViewRoute getTruyenHtml() {
        return new TemplateViewRoute() {
            @Override
            public ModelAndView handle(Request request, Response response) {
            	return new ModelAndView(null, "truyen.html");
            }
        };
	}
}
