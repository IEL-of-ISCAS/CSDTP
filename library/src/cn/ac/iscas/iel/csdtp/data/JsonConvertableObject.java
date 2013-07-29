/**
 * VR.IEL @ ISCAS
 * Copyright reserved.
 */
package cn.ac.iscas.iel.csdtp.data;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * JsonConvertableObject 
 * 
 * @author VoidMain
 *
 */
public class JsonConvertableObject {
	
	private ObjectMapper mMapper = new ObjectMapper();
	
	public String toJsonString() {
		StringWriter sw = new StringWriter();
		try {
			mMapper.writeValue(sw, this);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sw.toString();
	}
	
}
