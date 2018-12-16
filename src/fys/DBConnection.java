package fys;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;	

public class DBConnection {
	
	public static void main(String[] args) throws Exception {
	        String payload = "{" +
	                "\"function\": \"List\", " +
	                "\"teamId\": \"IC106-5\", " +
	                "\"teamKey\": \"61d3f4b1959805acf5dafcd3dca7f7a6\", " +
	                "\"requestId\": \"1\"" +
	                "}";
	        StringEntity entity = new StringEntity(payload,
	                ContentType.APPLICATION_FORM_URLENCODED);

	        HttpClient httpClient = HttpClientBuilder.create().build();
	        HttpPost request = new HttpPost("http://fys.securidoc.nl:11111/Passenger");
	        request.setEntity(entity);

	        HttpResponse response = httpClient.execute(request);
	        System.out.println(response.getStatusLine().getStatusCode());
	    }
	}	



