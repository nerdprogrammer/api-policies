package org.mule.ssl.fromName;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.AbstractTemplateTest;
import org.mule.scripts.OutboundSSLPostProcessing;
import org.mule.scripts.SSLPostProcessingWithDownloadFromID;
import org.mule.scripts.SSLPostProcessingWithDownloadFromNames;
import org.xml.sax.SAXException;

/**
 * tests HTTP URL-based endpoint targeting HTTPS API
 * @author Miroslav Rusin
 *
 */
public class WsdlSSLPostProcessingTest extends AbstractTemplateTest {

		
	@Override
	@Before
	public void prepare() throws IOException{
		LOGGER.info("Testing WSDL proxy");
		
		final Properties props = initGatewayParams();    	    	
    	deployHTTPSforWSDL();  
    	    	    	
		prepareToConnectToAP(props, "wsdlApiName", "wsdlApiVersion");
		// may change in future
	    proxyAppZip = props.getProperty("wsdlApiName") + "-v" + props.getProperty("wsdlApiVersion") + "-" + GATEWAY_VERSION + ".zip";
	}

	
	@Test
	public void testProcessing() throws IOException, ParserConfigurationException, SAXException, InterruptedException{
		super.testOutboundProcessing(new SSLPostProcessingWithDownloadFromNames(new SSLPostProcessingWithDownloadFromID(new OutboundSSLPostProcessing())));		
		makeTestRequest(HTTP_PROXY_URL, "/AdmissionService", FileUtils.readFileToString(new File(TEST_RESOURCES_FOLDER + File.separator + "soap-message.xml")));
	}
	
	@Override
	@After
	public void tearDown() throws IOException{
		super.tearDown();
	}
}
