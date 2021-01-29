package moe.fab.sfacgreader;

import com.alibaba.fastjson.JSONObject;
import moe.fab.sfacgreader.ApiAnnotation.BODY;
import moe.fab.sfacgreader.ApiAnnotation.PATH;
import moe.fab.sfacgreader.ApiAnnotation.QUERY;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Map;

public class ApiMethod
{
	private Method method;
	public String httpMethod,path,queries,requestBody;
	private Object[] args;
	public ApiMethod(Method method,Object[] args)
	{
		this.method=method;
		this.args=args;
		Annotation methodAnnotation=method.getDeclaredAnnotations()[0];
		httpMethod=methodAnnotation.annotationType().getSimpleName();
		path=getAnnotationValue(methodAnnotation);
		queries="";
		Annotation[][] pAnnotations=method.getParameterAnnotations();
		for (int i=0;i<pAnnotations.length;i++)
		{
			Annotation pAnnotation=pAnnotations[i][0];

			if(pAnnotation instanceof PATH)
				setPath(getAnnotationValue(pAnnotation),args[i]);
			else if(pAnnotation instanceof QUERY)
				setQuery(getAnnotationValue(pAnnotation),args[i]);
			else if (pAnnotation instanceof BODY)
				requestBody=new JSONObject((Map<String, Object>)args[i]).toJSONString();
		}
		if (requestBody==null)requestBody="";
		if (!queries.equals(""))
			path+="?"+queries;
	}

	private void setPath(String name,Object value)
	{

		path=path.replaceFirst("\\{"+name+"\\}",value.toString());
	}
	private void setQuery(String name,Object value)
	{
		if (!queries.equals(""))queries+="&";
		queries+=name+"="+value.toString();
	}

	private String getAnnotationValue(Annotation annotation)
	{
		try {
			return (String)annotation.annotationType().getMethod("value").invoke(annotation);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
}
