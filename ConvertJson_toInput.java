package maven.SPAB.upb;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



public class ConvertJson_toInput {
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {		
		File file=new File("C://Users/harsh/eclipse-workspace/maven.SPAB.upb/zoo.json");
		
		JsonFactory jsonFactory = new JsonFactory();
		@SuppressWarnings("deprecation")
		JsonParser jsonParser = jsonFactory.createJsonParser(file);
		
		
		ObjectMapper objectMapper = new ObjectMapper(jsonFactory);
		
		objectMapper.configure(
			    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //to ignore unknown JSON fields
		objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true); //to check for primitive datatypes
		
		JsonNode rootNode= objectMapper.readTree(jsonParser); //to get rootnode of every jsonRecord
		
		Iterator<Map.Entry<String, JsonNode>> fieldsIterator = rootNode.fields();
		while(fieldsIterator.hasNext()) {
			Map.Entry<String, JsonNode> field = fieldsIterator.next();

            if (!field.getValue().isObject())
            System.out.println(field.getKey() + ": " + field.getValue());
		}
		
		//Zoo zoo =objectMapper.readValue(file,Zoo.class);
		
		
		//System.out.println(rootNode);
		
		System.out.println("done");		
	}
	
}