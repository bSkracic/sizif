package hr.bskracic.sizif.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Vector;

@Slf4j
public class SvarogRpcClient {

    private class SvarogRequest implements Serializable {
         public String NodeId;
         public String Image;
    }

    private XmlRpcClient client;
    public SvarogRpcClient(XmlRpcClient client) throws MalformedURLException {
        this.client = client;
    }

    public Object createContainer(Long nodeId, String name, String image) {
        Vector params = new Vector();
        params.addElement(nodeId.toString());
        params.addElement(name);
        params.addElement(image);
        Object reply = null;
        try {
            reply = client.execute("containerService.create", params);
        } catch (XmlRpcException | IOException e) {
            log.error(e.getMessage());
        }
        params.clear();
        return reply;
    }


}
