package com.mitocode.qrpayment.infraestructure.in.web.aspect;

import java.util.Collection;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.mitocode.qrpayment.infraestructure.in.web.context.AuthorizeContext;
import com.mitocode.qrpayment.infraestructure.out.proxy.exception.ProxyException;

//@Aspect
//@Component
public class AuthorizeAspect {
	
	private static final String ROLE_WALLET = "role_wallets";
    private static final String ROLE_MERCHANT = "role_merchant";


    @Before("@annotation(com.mitocode.qrpayment.infraestructure.in.web.annotation.AuthorizeWalletFilter) || " +
            "@within(com.mitocode.qrpayment.infraestructure.in.web.annotation.AuthorizeWalletFilter)")
    public void resultAuthorizeWallet(){
        Collection<String>  roles = AuthorizeContext.getRoles();
        boolean isValid = false;
        for (String role : roles) {
            if (ROLE_WALLET.equals(role)){
                isValid = true;
            }
        }
        if (!isValid){
            throw new ProxyException("Credenciales no permitidas");
        }
    }

    @Before("@annotation(com.mitocode.qrpayment.infraestructure.in.web.annotation.AuthorizeMerchantFilter) || " +
            "@within(com.mitocode.qrpayment.infraestructure.in.web.annotation.AuthorizeMerchantFilter)")
    public void resultAuthorizeMerchant(){
        Collection<String>  roles = AuthorizeContext.getRoles();
        boolean isValid = false;
        for (String role : roles) {
            if (ROLE_MERCHANT.equals(role)){
                isValid = true;
            }
        }
        if (!isValid){
            throw new ProxyException("Credenciales no permitidas");
        }
    }

}
