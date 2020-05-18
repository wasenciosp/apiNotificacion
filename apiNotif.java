import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;

/**
 * Invoking a soap service using the CXF component.
 * Needs camel-cxf dependency.
 */
public class InvokeSoapServiceRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("undertow:http://localhost:8081/test/sms")
                .setBody(constant("<usuario>rimacWsoap</usuario><password>rimacWsoap$2016</password><celular>945266516</celular><mensaje>Test KNATIVE...</mensaje>"))
                .bean(GetBookRequestBuilder.class)
                .setHeader(CxfConstants.OPERATION_NAME, constant("enviarSMS"))
                .setHeader(CxfConstants.OPERATION_NAMESPACE, constant("http://controller.ws.intico.pe/"))

                // Invoke our test service using CXF
                .to("cxf://https://ws.intico.com.pe:8181/soap/sms")
/*                        + "?serviceClass=com.cleverbuilder.bookservice.BookService"
                        + "&wsdlURL=/wsdl/BookService.wsdl")
*/
                // You can retrieve fields from the response using the Simple language
                .log("Log ---> ")
                .log("The title is: ${body[0].book.title}")
                .to("mock:output");

    }
}