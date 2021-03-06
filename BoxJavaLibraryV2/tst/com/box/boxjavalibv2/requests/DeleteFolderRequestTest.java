package com.box.boxjavalibv2.requests;

import junit.framework.Assert;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.junit.Test;

import com.box.boxjavalibv2.BoxConfig;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.requests.requestobjects.BoxFolderRequestObject;
import com.box.boxjavalibv2.utils.Constants;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class DeleteFolderRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        Assert.assertEquals("/folders/123", DeleteFolderRequest.getUri("123"));
    }

    @Test
    public void testRequestIsWellFormed() throws BoxRestException, AuthFatalFailureException {
        boolean recursive = true;
        String etag = "testetag123";
        String id = "testid456";

        DeleteFolderRequest request = new DeleteFolderRequest(CONFIG, JSON_PARSER, id, (BoxFolderRequestObject) BoxFolderRequestObject
            .deleteFolderRequestObject(recursive).setIfMatch(etag));
        testRequestIsWellFormed(request, BoxConfig.getInstance().getApiUrlAuthority(),
            BoxConfig.getInstance().getApiUrlPath().concat(DeleteFolderRequest.getUri(id)), HttpStatus.SC_NO_CONTENT, RestMethod.DELETE);

        Assert.assertEquals(Boolean.toString(recursive), request.getQueryParams().get(Constants.RECURSIVE));
        Header header = request.getRawRequest().getFirstHeader(Constants.IF_MATCH);
        Assert.assertNotNull(header);
        Assert.assertEquals(etag, header.getValue());

    }
}
