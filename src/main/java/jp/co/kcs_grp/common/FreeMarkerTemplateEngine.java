package jp.co.kcs_grp.common;
import java.io.File;
import java.io.StringWriter;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jp.co.kcs_grp.utils.AppParams;
import spark.ModelAndView;
import spark.TemplateEngine;

public class FreeMarkerTemplateEngine extends TemplateEngine {

    @Override
    public String render(ModelAndView modelAndView) {
        try {
        	Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        	configuration.setDefaultEncoding("UTF-8");
        	File file = new File(AppParams.getValue("parameterpath", "EXTERNAL_STATIC_FILE_LOCATION") + "/html/");
        	configuration.setDirectoryForTemplateLoading(file);
        	
            StringWriter stringWriter = new StringWriter();
            Template template = configuration.getTemplate(modelAndView.getViewName(), "UTF-8");
            template.process(modelAndView.getModel(), stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
		return null;
    }

}
