package com.codingapi.tx.motan.balance;

import com.weibo.api.motan.cluster.loadbalance.RandomLoadBalance;
import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.core.extension.Activation;
import com.weibo.api.motan.core.extension.SpiMeta;
import com.weibo.api.motan.rpc.Referer;
import com.weibo.api.motan.rpc.Request;

import java.util.List;

/**
 * <p>随机，按权重设置随机概率</p>
 *
 * @author 张峰 zfvip_it@163.com
 *  2017/12/1 10:46
 */
@SpiMeta(name = "randomLcn")
@Activation(key = {MotanConstants.NODE_TYPE_SERVICE, MotanConstants.NODE_TYPE_REFERER})
public class RandomLoadBalanceProxy extends RandomLoadBalance {

    private LCNBalanceProxy lcnBalanceProxy = new LCNBalanceProxy();

    @Override
    protected Referer doSelect(Request request) {
        return lcnBalanceProxy.proxy(getReferers(),super.doSelect(request));
    }

    @Override
    protected void doSelectToHolder(Request request, List refersHolder) {
        super.doSelectToHolder(request, refersHolder);
        refersHolder.set(0, lcnBalanceProxy.proxy(getReferers(),super.doSelect(request)));
    }
}
