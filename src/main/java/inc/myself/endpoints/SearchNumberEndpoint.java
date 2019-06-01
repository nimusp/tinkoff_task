package inc.myself.endpoints;

import inc.myself.models.FilesWithNumberRequest;
import inc.myself.models.FilesWithNumberResponse;
import inc.myself.models.Result;
import inc.myself.services.SearchNumberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SearchNumberEndpoint {

    private static final String NAMESPACE_URI = "http://inc/myself/models";
    private static final String FIND_NUMBER_REQUEST = "filesWithNumberRequest";

    @Autowired
    private SearchNumberServiceImpl service;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = FIND_NUMBER_REQUEST)
    @ResponsePayload
    public FilesWithNumberResponse findNumber(@RequestPayload FilesWithNumberRequest request) {
        Integer numberToSearch = request.getNumberToSearch();
        Result searchResult = service.findNumber(numberToSearch);
        FilesWithNumberResponse response = new FilesWithNumberResponse();
        response.setResult(searchResult);
        return response;
    }
}
