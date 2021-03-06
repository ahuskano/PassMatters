package eu.asyncro.passmatters.controllers;

import com.dmacan.lightandroid.data.LightController;
import com.dmacan.lightandroid.util.LightAPIUtil;

import eu.asyncro.passmatters.Settings;
import eu.asyncro.passmatters.api.APIPassMatters;
import eu.asyncro.passmatters.data.responses.ResponseAccount;
import eu.asyncro.passmatters.interfaces.OnAccountSendedListener;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ahuskano on 12/5/2014.
 */
public class ControllerAccount extends LightController {

    private OnAccountSendedListener onAccountSendedListener;

    private Callback<ResponseAccount> callbackAccount = new Callback<ResponseAccount>() {
        @Override
        public void success(ResponseAccount responseAccount, Response response) {
            if (getOnAccountSendedListener() != null)
                getOnAccountSendedListener().onAccountSended(responseAccount);
        }

        @Override
        public void failure(RetrofitError error) {
            if (getOnErrorListener() != null)
                getOnErrorListener().onError(error);
        }
    };

    public void setAccount(String token, int accountId) {
        if (Settings.demoMode)
            demoSetAccount();
        else
            LightAPIUtil.getRestAdapter(APIPassMatters.API_LOCATION_PORT).create(APIPassMatters.class).sendAccount(token, accountId, callbackAccount);
    }
    private void demoSetAccount(){
        ResponseAccount responseAccount=new ResponseAccount();
        responseAccount.setStatus(1);
        onAccountSendedListener.onAccountSended(responseAccount);
    }

    public OnAccountSendedListener getOnAccountSendedListener() {
        return onAccountSendedListener;
    }

    public void setOnAccountSendedListener(OnAccountSendedListener onAccountSendedListener) {
        this.onAccountSendedListener = onAccountSendedListener;
    }

    public Callback<ResponseAccount> getCallbackAccount() {
        return callbackAccount;
    }

    public void setCallbackAccount(Callback<ResponseAccount> callbackAccount) {
        this.callbackAccount = callbackAccount;
    }
}
