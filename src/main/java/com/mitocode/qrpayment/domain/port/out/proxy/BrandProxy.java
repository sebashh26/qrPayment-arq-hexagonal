package com.mitocode.qrpayment.domain.port.out.proxy;

import com.mitocode.qrpayment.domain.model.vo.BrandAuthorizedRq;
import com.mitocode.qrpayment.domain.model.vo.BrandAuthorizationResult;

public interface BrandProxy {
	//PROXY:	Es todo aquel objeto que se va a comunicar con un servicio externo a la infraestructura, }
	//nuestro caso son los servicios de solicitud de autorizacion de la marca 

	BrandAuthorizationResult authorizePayment(BrandAuthorizedRq brandAuthorized);

}
