package com.mitocode.qrpayment;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.h2.tools.Server;

import com.mitocode.qrpayment.bootstrap.BeanConfig;
import com.mitocode.qrpayment.infraestructure.in.web.config.ObjectMapperContextResolver;
import com.mitocode.qrpayment.infraestructure.in.web.controller.MerchantController;
import com.mitocode.qrpayment.infraestructure.in.web.controller.PaymentController;
import com.mitocode.qrpayment.infraestructure.in.web.controller.QRController;
import com.mitocode.qrpayment.infraestructure.in.web.exception.GlobalExceptionMapper;
import com.mitocode.qrpayment.infraestructure.in.web.service.MerchantService;
import com.mitocode.qrpayment.infraestructure.in.web.service.PaymentService;
import com.mitocode.qrpayment.infraestructure.in.web.service.QRService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
	
	//defino donde deslegar el servcio REST que voy a crear luego con JAX-RS 
	public static final String BASE_URI = "http://localhost:8080/";
	
	
    public static void main(String[] args) throws IOException {
    	
    	try {
			Server webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("H2 console running at: http://localhost:8082");

    	MerchantService merchantService = BeanConfig.merchantService();
        QRService qrCodeService = BeanConfig.qrService(); // << NUEVO

        MerchantController merchantController = new MerchantController(merchantService);
        QRController qrCodeController = new QRController(qrCodeService); // << NUEVO


        PaymentService paymentService = BeanConfig.paymentService();
        PaymentController paymentController = new PaymentController(paymentService);

        
        //Porque Jersey no escanea automáticamente todas las clases del proyecto como lo haría Spring. Tú debes decirle explícitamente:
//        Una vez registrada, Jersey:
//        	- Detecta las anotaciones JAX-RS (@Path, @Produces, @Consumes, etc.)
//        	- Usa la clase para procesar solicitudes entrantes
//        	- Aplica lógica de negocio, serialización, manejo de errores, etc.


        final ResourceConfig config = new ResourceConfig()
        	    .register(ObjectMapperContextResolver.class)
        	    .register(GlobalExceptionMapper.class)
        	    .register(new AbstractBinder() {
        	        @Override
        	        protected void configure() {
        	            bind(merchantController).to(MerchantController.class);
        	            bind(qrCodeController).to(QRController.class);
        	            bind(paymentController).to(PaymentController.class);
        	        }
        	    })
        	    .register(MerchantController.class)
        	    .register(QRController.class)
        	    .register(PaymentController.class).register(new LoggingFeature(
        	    	    Logger.getLogger(LoggingFeature.class.getName()),
        	    	    Level.INFO,
        	    	    LoggingFeature.Verbosity.PAYLOAD_ANY,
        	    	    8192
        	    	));


        config.property("jersey.config.server.wadl.disableWadl", true);


        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
        System.out.println("Server running at: " + BASE_URI + "\nPress Enter to stop...");
        System.in.read();
        server.shutdownNow();
    }
}