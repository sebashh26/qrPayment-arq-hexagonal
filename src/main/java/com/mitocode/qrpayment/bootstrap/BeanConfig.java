package com.mitocode.qrpayment.bootstrap;

//@Configuration
public class BeanConfig {	
	
//	@Bean
//	public static MerchantService merchantService() {
//        MerchantRepository repository = new MerchantRepositoryJDBCAdapter(new MerchantRepositoryJDBC());
//        MerchantToMerchantDto mapper = new MerchantToMerchantDto();
//
//        return new MerchantService(
//                new CreateMerchantUseCase(repository, mapper),
//                new GetAllMerchantUseCase(repository, mapper),
//                new GetMerchantByIdUseCase(repository, mapper),
//                new UpdateMerchantUseCase(repository, mapper),
//                new DeleteMerchantUseCase(repository, mapper)
//        );
//    }
//
//    public static QRService qrService() {
//        QRRepository qrRepository = new QRCodeRepositoryJDBCAdapter(new QRCodeRepositoryJDBC());
//        MerchantRepository merchantRepository = new MerchantRepositoryJDBCAdapter(new MerchantRepositoryJDBC());
//        QRGenerator qrGenerator = new QRGeneratorImpl();
//        QRCodeToQRDto mapper = new QRCodeToQRDto();
//
//        return new QRService(
//                new CreateQRUseCase(qrRepository, mapper, qrGenerator, merchantRepository)
//        );
//    }
//
//    public static PaymentService paymentService() {
//        QRRepository qrRepository = new QRCodeRepositoryJDBCAdapter(new QRCodeRepositoryJDBC());
//        MerchantRepository merchantRepository = new MerchantRepositoryJDBCAdapter(new MerchantRepositoryJDBC());
//        HttpClient httpClient = HttpClient.newHttpClient();
//        MerchantProxy merchantProxy = new MerchantProxyImpl(httpClient);
//        WalletRepository walletRepository = new WalletRepositoryJDBCAdapter(new WalletRepositoryJDBC());
//        BrandProxy brandProxy = new BrandProxyImpl(httpClient);
//        PaymentRepository paymentRepository = new PaymentRepositoryJDBCAdapter(new PaymentRepositoryJDBC());
//
//            return new PaymentService(
//                new AuthorizedQRUseCase(merchantProxy, merchantRepository,
//                        qrRepository, walletRepository,brandProxy,paymentRepository)
//        );
//    }

}
