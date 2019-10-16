package com.tomaszkyc.app.core.services.apiconnection;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomaszkyc.app.model.APIClaims;
import com.tomaszkyc.app.model.ImageInformation;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;

public class AzureAPIConnectionServiceImpl implements APIConnectionService {

    private static final Logger log = LogManager.getLogger(AzureAPIConnectionServiceImpl.class);


    private static final String AZURE_ANALIZE_IMAGE_URL_PATTERN = "@endpoint@@version@?@request_parameters@";
    private static final String AZURE_ANALIZE_IMAGE_API_VERSION = "vision/v2.0/analyze";
    private static final String AZURE_ANALIZE_IMAGE_PARAMETERS = "visualFeatures=Categories,Description,Color";

    public String getServiceUrl() {
        return serviceUrl;
    }

    public ImageInformation getImageInformation() {
        return imageInformation;
    }

    public void setImageInformation(ImageInformation imageInformation) {
        this.imageInformation = imageInformation;
    }

    private ImageInformation imageInformation;



    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    private String serviceUrl;

    private APIClaims apiClaims;

    public AzureAPIConnectionServiceImpl(APIClaims apiClaims) {
        this.apiClaims = apiClaims;
    }


    private void buildUrl() throws Exception {
        log.debug("Started building url...");
        Map<String, String> urlParameters = new HashMap<>();
        urlParameters.put("@endpoint@", getAPIClaims().getApiEndpoint());
        urlParameters.put("@version@", AZURE_ANALIZE_IMAGE_API_VERSION);
        urlParameters.put("@request_parameters@", AZURE_ANALIZE_IMAGE_PARAMETERS);

        this.serviceUrl = URLBuilder.build(AZURE_ANALIZE_IMAGE_URL_PATTERN, urlParameters);

        log.debug("Finished building url... Result: " + this.serviceUrl);
    }

    private void sendRequestToAPIToAnalizeImage(File imageFile) throws Exception {

        log.debug("Before creating data to send");
        HttpEntity data = MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addBinaryBody("application/octet-stream", imageFile)
                .build();

        log.debug("Starting creating request headers.");
        Header header = new BasicHeader("Ocp-Apim-Subscription-Key", getAPIClaims().getApiKey());


        List<Header> headers = Collections.singletonList(header);
        log.debug("Starting creating http client");
        HttpClient client = HttpClients.custom().setDefaultHeaders(headers).build();

        HttpUriRequest request = RequestBuilder.post( getServiceUrl() )
                                                .setEntity(data)
                                                .build();
        log.debug("Starting call request.");
        HttpResponse response = client.execute( request );
        int status = response.getStatusLine().getStatusCode();

        log.debug("response code: " + status);
        if ( status != 200 ) {
            String errorDetails = String.format("%s , message from API: %s",String.valueOf( status )
                                                                         , response.getStatusLine().getReasonPhrase());
            throw new BadStatusOnAPIConnectionException( errorDetails );
        }
        HttpEntity entity = response.getEntity();
        String entityStr = EntityUtils.toString(entity);
        log.debug("Got the info from API: ");
        log.debug( entityStr );
        ObjectMapper objectMapper = new ObjectMapper();
        this.imageInformation = objectMapper.readValue(entityStr, ImageInformation.class);


    }

    @Override
    public APIClaims getAPIClaims() throws Exception {
        return apiClaims;
    }

    @Override
    public void setAPIClaims(APIClaims apiClaims) throws Exception {
        this.apiClaims = apiClaims;
    }

    @Override
    public ImageInformation analizeImage(File imageFile) throws Exception {

        //build url for the service
        buildUrl();

        //send request
        sendRequestToAPIToAnalizeImage( imageFile );

        //return image information object
        return this.getImageInformation();
    }
}
