package com.mitocode.qrpayment.bootstrap;

import java.net.http.HttpClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mitocode.qrpayment.application.mapper.MerchantToMerchantDto;
import com.mitocode.qrpayment.application.mapper.QRCodeToQRDto;
import com.mitocode.qrpayment.application.usecase.authorize.AuthorizedQRUseCase;
import com.mitocode.qrpayment.application.usecase.merchant.CreateMerchantUseCase;
import com.mitocode.qrpayment.application.usecase.merchant.DeleteMerchantUseCase;
import com.mitocode.qrpayment.application.usecase.merchant.GetAllMerchantUseCase;
import com.mitocode.qrpayment.application.usecase.merchant.GetMerchantByIdUseCase;
import com.mitocode.qrpayment.application.usecase.merchant.UpdateMerchantUseCase;
import com.mitocode.qrpayment.application.usecase.qr.CreateQRUseCase;
import com.mitocode.qrpayment.domain.port.out.persistence.MerchantRepository;
import com.mitocode.qrpayment.domain.port.out.persistence.PaymentRepository;
import com.mitocode.qrpayment.domain.port.out.persistence.QRRepository;
import com.mitocode.qrpayment.domain.port.out.persistence.WalletRepository;
import com.mitocode.qrpayment.domain.port.out.proxy.BrandProxy;
import com.mitocode.qrpayment.domain.port.out.proxy.MerchantProxy;
import com.mitocode.qrpayment.domain.port.out.qr.QRGenerator;
import com.mitocode.qrpayment.infraestructure.in.web.service.MerchantService;
import com.mitocode.qrpayment.infraestructure.in.web.service.PaymentService;
import com.mitocode.qrpayment.infraestructure.in.web.service.QRService;
import com.mitocode.qrpayment.infraestructure.out.persistence.adapter.jdbc.MerchantRepositoryJDBCAdapter;
import com.mitocode.qrpayment.infraestructure.out.persistence.adapter.jdbc.PaymentRepositoryJDBCAdapter;
import com.mitocode.qrpayment.infraestructure.out.persistence.adapter.jdbc.QRCodeRepositoryJDBCAdapter;
import com.mitocode.qrpayment.infraestructure.out.persistence.adapter.jdbc.WalletRepositoryJDBCAdapter;
import com.mitocode.qrpayment.infraestructure.out.persistence.repository.jdbc.MerchantRepositoryJDBC;
import com.mitocode.qrpayment.infraestructure.out.persistence.repository.jdbc.PaymentRepositoryJDBC;
import com.mitocode.qrpayment.infraestructure.out.persistence.repository.jdbc.QRCodeRepositoryJDBC;
import com.mitocode.qrpayment.infraestructure.out.persistence.repository.jdbc.WalletRepositoryJDBC;
import com.mitocode.qrpayment.infraestructure.out.proxy.BrandProxyImpl;
import com.mitocode.qrpayment.infraestructure.out.proxy.MerchantProxyImpl;
import com.mitocode.qrpayment.infraestructure.out.qr.QRGeneratorImpl;

@Configuration
public class BeanConfig {
	
	@Bean
	public static MerchantService merchantService() {
        MerchantRepository repository = new MerchantRepositoryJDBCAdapter(new MerchantRepositoryJDBC());
        MerchantToMerchantDto mapper = new MerchantToMerchantDto();

        return new MerchantService(
                new CreateMerchantUseCase(repository, mapper),
                new GetAllMerchantUseCase(repository, mapper),
                new GetMerchantByIdUseCase(repository, mapper),
                new UpdateMerchantUseCase(repository, mapper),
                new DeleteMerchantUseCase(repository, mapper)
        );
    }

    public static QRService qrService() {
        QRRepository qrRepository = new QRCodeRepositoryJDBCAdapter(new QRCodeRepositoryJDBC());
        MerchantRepository merchantRepository = new MerchantRepositoryJDBCAdapter(new MerchantRepositoryJDBC());
        QRGenerator qrGenerator = new QRGeneratorImpl();
        QRCodeToQRDto mapper = new QRCodeToQRDto();

        return new QRService(
                new CreateQRUseCase(qrRepository, mapper, qrGenerator, merchantRepository)
        );
    }

    public static PaymentService paymentService() {
        QRRepository qrRepository = new QRCodeRepositoryJDBCAdapter(new QRCodeRepositoryJDBC());
        MerchantRepository merchantRepository = new MerchantRepositoryJDBCAdapter(new MerchantRepositoryJDBC());
        HttpClient httpClient = HttpClient.newHttpClient();
        MerchantProxy merchantProxy = new MerchantProxyImpl(httpClient);
        WalletRepository walletRepository = new WalletRepositoryJDBCAdapter(new WalletRepositoryJDBC());
        BrandProxy brandProxy = new BrandProxyImpl(httpClient);
        PaymentRepository paymentRepository = new PaymentRepositoryJDBCAdapter(new PaymentRepositoryJDBC());

            return new PaymentService(
                new AuthorizedQRUseCase(merchantProxy, merchantRepository,
                        qrRepository, walletRepository,brandProxy,paymentRepository)
        );
    }

}
